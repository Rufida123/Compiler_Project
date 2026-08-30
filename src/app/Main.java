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
import sharedSymbolTable.SymbolTable;
import sharedSymbolTable.SymbolTable;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class Main {

    private static final List<String> PARSE_ERRORS = new ArrayList<>();
    private static String parsingFile = "<input>";

    /** --print-ast: dump both trees and the symbol table, and write the .txt artefacts. */
    private static boolean printAst = false;
    private static SymbolTable pythonSymbols = null;
    private static final Map<String, SymbolTable> JINJA_SYMBOLS = new LinkedHashMap<>();
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
        JINJA_SYMBOLS.clear();
        pythonSymbols = null;

        // Options may appear anywhere; everything else is a positional path.
        List<String> positional = new ArrayList<>();
        printAst = false;
        for (String argument : args) {
            if ("--print-ast".equals(argument)) printAst = true;
            else positional.add(argument);
        }

        Path projectRoot = Path.of("").toAbsolutePath().normalize();
        String pythonFilePath   = positional.size() > 0 ? positional.get(0) : projectRoot.resolve("app.py").toString();
        String templatesDirPath = positional.size() > 1 ? positional.get(1) : projectRoot.resolve("templates").toString();
        String outputDir        = positional.size() > 2 ? positional.get(2) : projectRoot.resolve("output").toString();
        Path compilerOutputDir  = positional.size() > 3 ? Path.of(positional.get(3)) : projectRoot.resolve("compiler_output");

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
        List<String> generationLog = new ArrayList<>(List.of(
                "Input Python: " + pythonFilePath, "Templates: " + templatesDirPath,
                "Static output: " + outputDir, "Templates parsed: " + jinjaTemplates.size()));
        CompilerArtifactWriter.write(compilerOutputDir, pythonAst, jinjaTemplates, allErrors, generationLog);
        CompilerArtifactWriter.writeReadableDumps(compilerOutputDir, pythonAst, jinjaTemplates,
                pythonSymbols, JINJA_SYMBOLS);
        if (printAst) dumpTreesAndSymbols(pythonAst, jinjaTemplates, compilerOutputDir);

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

        generationLog.addAll(StaticSiteGenerator.generate(Path.of(pythonFilePath), Path.of(templatesDirPath),
                Path.of(outputDir), pythonAst, jinjaTemplates));
        // Rewrite the artefacts so generation_log.txt records how each context was produced.
        CompilerArtifactWriter.write(compilerOutputDir, pythonAst, jinjaTemplates, allErrors, generationLog);

        System.out.println("\n========================================");
        System.out.println("COMPILATION COMPLETE!");
        System.out.println("========================================");
        System.out.println("Static HTML written to: " + outputDir);
        System.out.println("Compiler artefacts written to: " + compilerOutputDir + "\n");
    }

    /**
     * R7: one call prints the whole Python tree, every Jinja tree, and the
     * symbol table for each file, then writes the same content to
     * compiler_output/ast_python.txt, ast_jinja.txt and symbol_table.txt.
     */
    private static void dumpTreesAndSymbols(PyProgram pythonAst,
                                            Map<String, JinjaProgram> jinjaTemplates,
                                            Path compilerOutputDir) {
        System.out.println("\n=========== AST + SYMBOL TABLE DUMP (--print-ast) ===========");

        System.out.println("\n----------- PYTHON AST -----------");
        System.out.println(pythonAst == null ? "<no AST>" : pythonAst.printTree());
        System.out.println("----------- PYTHON SYMBOL TABLE -----------");
        System.out.println(pythonSymbols == null ? "<none>" : pythonSymbols.print());
        if (pythonSymbols != null) System.out.println(pythonSymbols.getStatistics());

        for (Map.Entry<String, JinjaProgram> entry : jinjaTemplates.entrySet()) {
            System.out.println("----------- JINJA AST: " + entry.getKey() + " -----------");
            System.out.println(entry.getValue() == null ? "<no AST>" : entry.getValue().printTree());
            SymbolTable symbols = JINJA_SYMBOLS.get(entry.getKey());
            System.out.println("----------- SYMBOL TABLE: " + entry.getKey() + " -----------");
            System.out.println(symbols == null ? "<none>" : symbols.print());
        }

        System.out.println("Readable dumps written to " + compilerOutputDir.resolve("ast_python.txt")
                + ", " + compilerOutputDir.resolve("ast_jinja.txt")
                + ", " + compilerOutputDir.resolve("symbol_table.txt"));
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
        pythonSymbols = visitor.getSymbolTable();

        System.out.println("\n=========== PYTHON AST ===========");
        System.out.println(ast.toString());

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
        JINJA_SYMBOLS.put(Path.of(filePath).getFileName().toString(), visitor.getSymbolTable());

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
