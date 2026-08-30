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
import codegen.CompilerArtifactWriter;
import codegen.StaticSiteGenerator;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Main {

    private static final List<String> PARSE_ERRORS = new ArrayList<>();
    private static String parsingFile = "<input>";
    private static final BaseErrorListener COLLECTING_LISTENER = new BaseErrorListener() {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object            offendingSymbol,
                                int               line,
                                int               charPositionInLine,
                                String            msg,
                                RecognitionException e) {
            PARSE_ERRORS.add(parsingFile + ":" + line + ":" + charPositionInLine + " " + msg);
        }
    };

    public static void main(String[] args) throws Exception {
        PARSE_ERRORS.clear();

        Path projectRoot = Path.of("").toAbsolutePath().normalize();
        String pythonFilePath   = args.length > 0 ? args[0] : projectRoot.resolve("app.py").toString();
        String templatesDirPath = args.length > 1 ? args[1] : projectRoot.resolve("templates").toString();
        String outputDir        = args.length > 2 ? args[2] : projectRoot.resolve("output").toString();
        Path compilerOutputDir  = args.length > 3 ? Path.of(args[3]) : projectRoot.resolve("compiler_output");

        // ── PHASE 1: PARSE & build Python AST ────────────────────────────
        System.out.println("========================================");
        System.out.println("Processing Python File: " + pythonFilePath);
        System.out.println("========================================");
        PyProgram pythonAst = processPythonFile(pythonFilePath);
        if (printParseErrorsAndStop()) return;

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
                    .filter(p -> p.toString().endsWith(".html") || p.toString().endsWith(".jinja"))
                    .sorted()
                    .toList()) {

                String htmlFilePath = htmlPath.toString();
                System.out.println("\n========================================");
                System.out.println("Processing Jinja/HTML File: " + htmlFilePath);
                System.out.println("========================================");

                JinjaProgram htmlAst = processHtmlFile(htmlFilePath);
                if (printParseErrorsAndStop()) return;
                jinjaTemplates.put(htmlPath.getFileName().toString(), htmlAst);

                semanticAnalyzer.analyzeJinja(htmlAst, htmlFilePath);
                typeChecker.analyzeJinja(htmlAst, htmlFilePath);
            }
        }

        // ── PHASE 3: Collect all errors and decide ──────────────────────
        List<SemanticAnalyzer.SemanticError> allErrors = new ArrayList<>();
        allErrors.addAll(semanticAnalyzer.getErrors());
        allErrors.addAll(typeChecker.getErrors());
        allErrors.addAll(enhanced.getErrors());

        // Enhanced warnings belong in the final report, but only real errors
        // block generation. Python permits patterns such as reassignment and
        // functions that intentionally return None.
        List<SemanticAnalyzer.SemanticError> blockingErrors = allErrors.stream()
                .filter(error -> !error.getMessage().startsWith("Warning:"))
                .toList();

        // ── Print all errors ───────────────────────────────────────────
        printAllErrors(allErrors);
        CompilerArtifactWriter.write(compilerOutputDir, pythonAst, jinjaTemplates, allErrors,
                List.of("Input Python: " + pythonFilePath, "Templates: " + templatesDirPath,
                        "Static output: " + outputDir, "Templates parsed: " + jinjaTemplates.size()));

        // ── PHASE 4: Code Generation (if no errors) ──────────────────────
        if (!blockingErrors.isEmpty()) {
            System.out.println("\n SEMANTIC ERRORS FOUND - CODE GENERATION SKIPPED");
            System.out.println("Fix the errors above and try again.\n");
            return;  // Don't generate code if errors exist
        }

        System.out.println("\n No errors found!");
        System.out.println("\n========================================");
        System.out.println("PHASE 3: STATIC HTML GENERATION");
        System.out.println("========================================");

        StaticSiteGenerator.generate(Path.of(pythonFilePath), Path.of(templatesDirPath), Path.of(outputDir), pythonAst, jinjaTemplates);

        System.out.println("\n========================================");
        System.out.println("COMPILATION COMPLETE!");
        System.out.println("========================================");
        System.out.println("Static HTML written to: " + outputDir);
        System.out.println("Compiler artefacts written to: " + compilerOutputDir + "\n");
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

        int semanticErrorCount = 0;
        int typeErrorCount = 0;
        int warningCount = 0;

        for (SemanticAnalyzer.SemanticError error : all) {
            System.out.println(error.format());
            if (error.getMessage().startsWith("Warning:")) warningCount++;
            else if (error.getMessage().startsWith("Type Error")) typeErrorCount++;
            else semanticErrorCount++;
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Total errors      : " + all.size());
        System.out.println("Semantic errors   : " + semanticErrorCount);
        System.out.println("Type errors       : " + typeErrorCount);
        System.out.println("Warnings          : " + warningCount);
    }

    // ── Python ────────────────────────────────────────────────────────────────
    private static PyProgram processPythonFile(String filePath) throws Exception {
        parsingFile = filePath;
        String code = Files.readString(Path.of(filePath));

        CharStream        input  = CharStreams.fromString(code);
        pyLexer lexer  = new pyLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(COLLECTING_LISTENER);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pyParser parser = new pyParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(COLLECTING_LISTENER);

        pyParser.PyProgramContext tree = parser.pyProgram();

        if (!PARSE_ERRORS.isEmpty()) return null;

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
        parsingFile = filePath;
        CharStream        input  = CharStreams.fromFileName(filePath);
        JinjaLexer        lexer  = new JinjaLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(COLLECTING_LISTENER);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JinjaParser       parser = new JinjaParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(COLLECTING_LISTENER);

        ParseTree tree = parser.jinjaProgram();

        if (!PARSE_ERRORS.isEmpty()) return null;

        Visitor.JinjaBaseVisitor visitor = new Visitor.JinjaBaseVisitor();
        JinjaProgram ast = (JinjaProgram) visitor.visit(tree);

        System.out.println("\n=========== JINJA/HTML AST ===========");
        System.out.println(ast.toString());

        return ast;
    }

    private static boolean printParseErrorsAndStop() {
        if (PARSE_ERRORS.isEmpty()) return false;
        System.out.println("=========== PARSE ERRORS ===========");
        for (String error : PARSE_ERRORS) System.out.println(error);
        System.out.println("PARSING FAILED - CODE GENERATION SKIPPED");
        return true;
    }
}
