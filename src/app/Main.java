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
import semantic.EnhancedSemanticAnalyzer;
import codegen.CodeGenerator;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        String pythonFilePath   = "./test.py";
        String templatesDirPath = "./templates";
        String outputDir        = "./generated_app";  // ← NEW: for code generation

        // ── PHASE 1: PARSE & build Python AST ────────────────────────────
        System.out.println("========================================");
        System.out.println("Processing Python File: " + pythonFilePath);
        System.out.println("========================================");
        PyProgram pythonAst = processPythonFile(pythonFilePath);

        // ── PHASE 2A: Run both analyzers on Python ───────────────────────
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
        semanticAnalyzer.analyzePython(pythonAst, pythonFilePath);

        TypeChecker typeChecker = new TypeChecker();
        typeChecker.analyzePython(pythonAst, pythonFilePath);

        // ── PHASE 2B: Run Enhanced Semantic Analyzer ─────────────────────
        // ✨ NEW: Additional error checking
        EnhancedSemanticAnalyzer enhanced = new EnhancedSemanticAnalyzer();
        enhanced.analyzePython(pythonAst, pythonFilePath);

        // ── Process every Jinja/HTML template ────────────────────────────
        Map<String, JinjaProgram> jinjaTemplates = new HashMap<>();  // ← NEW: store templates
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
                jinjaTemplates.put(htmlFilePath, htmlAst);  // ← NEW: save for code generation

                semanticAnalyzer.analyzeJinja(htmlAst, htmlFilePath);
                typeChecker.analyzeJinja(htmlAst, htmlFilePath);
            }
        }

        // ── PHASE 3: Collect all errors and decide ──────────────────────
        List<SemanticAnalyzer.SemanticError> allErrors = new ArrayList<>();
        allErrors.addAll(semanticAnalyzer.getErrors());
        allErrors.addAll(typeChecker.getErrors());
        // Note: EnhancedSemanticAnalyzer errors can be added here if needed

        // ── Print all errors ───────────────────────────────────────────
        printAllErrors(allErrors);

        // ── PHASE 4: Code Generation (if no errors) ──────────────────────
        if (!allErrors.isEmpty()) {
            System.out.println("\n SEMANTIC ERRORS FOUND - CODE GENERATION SKIPPED");
            System.out.println("Fix the errors above and try again.\n");
            return;  // Don't generate code if errors exist
        }

        System.out.println("\n No errors found!");
        System.out.println("\n========================================");
        System.out.println("PHASE 3: CODE GENERATION");
        System.out.println("========================================");

        CodeGenerator generator = new CodeGenerator(outputDir);
        generator.generate(pythonAst, jinjaTemplates);

        System.out.println("\n========================================");
        System.out.println("COMPILATION COMPLETE!");
        System.out.println("========================================");
        System.out.println("To run your Flask app:");
        System.out.println("  $ cd " + outputDir);
        System.out.println("  $ pip install -r requirements.txt");
        System.out.println("  $ python app.py\n");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Error printing - updated version
    // ─────────────────────────────────────────────────────────────────────────
    private static void printAllErrors(List<SemanticAnalyzer.SemanticError> all) {

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
        pyLexer lexer  = new pyLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(SILENT_LISTENER);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pyParser parser = new pyParser(tokens);
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