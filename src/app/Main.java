package app;

import jinjaAntlr.JinjaLexer;
import jinjaAntlr.JinjaParser;
import jinjaClasses.JinjaProgram;

import PyClasses.PyProgram;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import pyAntlr.pyLexer;
import pyAntlr.pyParser;
import semantic.SemanticAnalyzer;
import semantic.TypeChecker;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /** Suppresses ANTLR's default console error output (e.g. mismatched token warnings). */
    private static final BaseErrorListener SILENT_LISTENER = new BaseErrorListener() {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object            offendingSymbol,
                                int               line,
                                int               charPositionInLine,
                                String            msg,
                                RecognitionException e) {
            // intentionally silent
        }
    };

    public static void main(String[] args) throws Exception {

        String pythonFilePath   = "./app.py";
        String templatesDirPath = "./templates";

        // ── Parse & build Python AST ──────────────────────────────────────────
        System.out.println("========================================");
        System.out.println("Processing Python File: " + pythonFilePath);
        System.out.println("========================================");
        PyProgram pythonAst = processPythonFile(pythonFilePath);

        // ── Run both analyzers on Python ──────────────────────────────────────
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyzePython(pythonAst, pythonFilePath);

        TypeChecker typeChecker = new TypeChecker();
        typeChecker.analyzePython(pythonAst, pythonFilePath);

        // ── Process every Jinja/HTML template ────────────────────────────────
        try (var paths = Files.list(Path.of(templatesDirPath))) {
            for (Path htmlPath : paths
                    .filter(p -> p.toString().endsWith(".html"))
                    .sorted()
                    .toList()) {

                String htmlFilePath = htmlPath.toString();
                System.out.println("\n========================================");
                System.out.println("Processing Jinja/HTML File: " + htmlFilePath);
                System.out.println("========================================");

                JinjaProgram htmlAst = processHtmlFile(htmlFilePath);
                semanticAnalyzer.analyzeJinja(htmlAst, htmlFilePath);
                typeChecker.analyzeJinja(htmlAst, htmlFilePath);
            }
        }

        // ── Print all errors ──────────────────────────────────────────────────
        printAllErrors(semanticAnalyzer, typeChecker);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Error printing
    // Merges errors from SemanticAnalyzer (undefined variables) and TypeChecker
    // (type errors).  Both use SemanticError.format() so output looks identical.
    // ─────────────────────────────────────────────────────────────────────────
    private static void printAllErrors(SemanticAnalyzer semanticAnalyzer,
                                       TypeChecker       typeChecker) {

        List<SemanticAnalyzer.SemanticError> all = new ArrayList<>();
        all.addAll(semanticAnalyzer.getErrors());
        all.addAll(typeChecker.getErrors());

        System.out.println("\n=========== SEMANTIC ERRORS ===========");

        if (all.isEmpty()) {
            System.out.println("No semantic errors found.");
            return;
        }

        int undefinedCount = 0;
        int typeErrorCount = 0;

        for (SemanticAnalyzer.SemanticError error : all) {
            System.out.println(error.format());
            if (error.getMessage().startsWith("Type Error")) typeErrorCount++;
            else undefinedCount++;
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Total errors      : " + all.size());
        System.out.println("Undefined variable: " + undefinedCount);
        System.out.println("Type errors       : " + typeErrorCount);
    }

    // ── Python ────────────────────────────────────────────────────────────────
    private static PyProgram processPythonFile(String filePath) throws Exception {
        String code = Files.readString(Path.of(filePath));

        CharStream        input  = CharStreams.fromString(code);
        pyLexer           lexer  = new pyLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SILENT_LISTENER);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pyParser          parser = new pyParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SILENT_LISTENER);

        pyParser.PyProgramContext tree = parser.pyProgram();

        System.out.println("=========== PYTHON PARSE TREE ===========");
        System.out.println(tree.toStringTree(parser));

        Visitor.PyBaseVisitor visitor = new Visitor.PyBaseVisitor();
        PyProgram ast = (PyProgram) visitor.visit(tree);

        System.out.println("\n=========== PYTHON AST ===========");
        System.out.println(ast.toString());

        System.out.println("\n=========== PYTHON SYMBOL TABLE ===========");
        visitor.getSymbolTable().printTable();
        System.out.println(visitor.getSymbolTable().getStatistics());

        return ast;
    }

    // ── Jinja / HTML ──────────────────────────────────────────────────────────
    private static JinjaProgram processHtmlFile(String filePath) throws Exception {
        CharStream        input  = CharStreams.fromFileName(filePath);
        JinjaLexer        lexer  = new JinjaLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SILENT_LISTENER);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JinjaParser       parser = new JinjaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(SILENT_LISTENER);

        ParseTree tree = parser.jinjaProgram();

        Visitor.JinjaBaseVisitor visitor = new Visitor.JinjaBaseVisitor();
        JinjaProgram ast = (JinjaProgram) visitor.visit(tree);

        System.out.println("\n=========== JINJA/HTML AST ===========");
        System.out.println(ast.toString());

        return ast;
    }
}