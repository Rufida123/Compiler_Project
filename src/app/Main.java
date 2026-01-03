package app;

import jinjaAntlr.JinjaLexer;
import jinjaAntlr.JinjaParser;
import pyAntlr.pyLexer;
import pyAntlr.pyParser;
import jinjaAstClasses.Document;
import PyAstClasses.Program;
import PySymbolTable.SymbolTable;
import PyVisitor.AstPrettyPrinter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {

        // --- 1. SET YOUR FILE PATHS HERE ---
        // Make sure these paths match exactly where your files are on your computer
        // (relative to your project folder)
        String pythonFilePath = "./app.py";
        String htmlFilePath = "./templates/base.html";


        // --- 2. PROCESS PYTHON FILE ---
        System.out.println("========================================");
        System.out.println("Processing: " + pythonFilePath);
        System.out.println("========================================");
        processPythonFile(pythonFilePath);


        // --- 3. PROCESS HTML FILE ---
        System.out.println("\n========================================");
        System.out.println("Processing: " + htmlFilePath);
        System.out.println("========================================");
        processHtmlFile(htmlFilePath);
    }

    // --- Logic for Python Files ---
    private static void processPythonFile(String filePath) throws Exception {
        // 1) Read file
        String code = Files.readString(Path.of(filePath));

        // 2) Lexer
        CharStream input = CharStreams.fromString(code);
        pyLexer lexer = new pyLexer(input);

        // 3) Tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 4) Parser
        pyParser parser = new pyParser(tokens);
        pyParser.ProgramContext tree = parser.program();

        // 5) Print Parse Tree
        System.out.println("=========== PARSE TREE ===========");
        System.out.println(tree.toStringTree(parser));

        // 6) Build AST
        PyVisitor.BaseVisitor visitor = new PyVisitor.BaseVisitor();
        Program ast = (Program) visitor.visit(tree);

        // 7) Print AST
        System.out.println("\n=========== AST ===========");
        AstPrettyPrinter printer = new AstPrettyPrinter();
        System.out.println(printer.printStrict(ast));

        // 8) Symbol Table
        System.out.println("\n=========== SYMBOL TABLE ===========");
        SymbolTable st = visitor.getSymbolTable();
        st.printSymbolTable(printer);
    }

    // --- Logic for HTML/Jinja Files ---
    private static void processHtmlFile(String filePath) throws Exception {
        // 1) Read File
        CharStream input = CharStreams.fromFileName(filePath);

        // 2) Create Lexer
        JinjaLexer lexer = new JinjaLexer(input);

        // 3) Create Tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 4) Create Parser
        JinjaParser parser = new JinjaParser(tokens);

        // 5) Build ParseTree
        ParseTree tree = parser.document();

        // 6) Build AST
        jinjaVisitor.BaseVisitor visitor = new jinjaVisitor.BaseVisitor();
        Document ast = (Document) visitor.visit(tree);

        // 7) Print AST
        System.out.println("\nAbstract Syntax Tree (AST):");
        System.out.println(ast.toString());
    }
}