package app;

import jinjaAntlr.JinjaLexer;
import jinjaAntlr.JinjaParser;
import jinjaClasses.JinjaProgram;

import PyClasses.PyProgram;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import pyAntlr.pyLexer;
import pyAntlr.pyParser;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        // --- File Paths ---
        String pythonFilePath = "./app.py";
        String htmlFilePath = "./templates/base.html";

        // --- Process Python File ---
        System.out.println("========================================");
        System.out.println("Processing Python File: " + pythonFilePath);
        System.out.println("========================================");
        processPythonFile(pythonFilePath);

        // --- Process Jinja/HTML File ---
        System.out.println("\n========================================");
        System.out.println("Processing Jinja/HTML File: " + htmlFilePath);
        System.out.println("========================================");
        processHtmlFile(htmlFilePath);
    }

    // ====================== PYTHON ======================
    private static void processPythonFile(String filePath) throws Exception {
        String code = Files.readString(Path.of(filePath));

        // Lexer & Parser
        CharStream input = CharStreams.fromString(code);
        pyLexer lexer = new pyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        pyParser parser = new pyParser(tokens);

        pyParser.PyProgramContext tree = parser.pyProgram();

        // Print Parse Tree (optional - can comment out if too verbose)
        System.out.println("=========== PYTHON PARSE TREE ===========");
        System.out.println(tree.toStringTree(parser));

        // Build AST
        Visitor.PyBaseVisitor visitor = new Visitor.PyBaseVisitor();
        PyProgram ast = (PyProgram) visitor.visit(tree);

        // Print AST (Simple toString like Jinja)
        System.out.println("\n=========== PYTHON AST ===========");
        System.out.println(ast.toString());

        // Print Symbol Table
        System.out.println("\n=========== PYTHON SYMBOL TABLE ===========");
        visitor.getSymbolTable().printTable();
        System.out.println(visitor.getSymbolTable().getStatistics());
    }

    // ====================== JINJA / HTML ======================
    private static void processHtmlFile(String filePath) throws Exception {
        CharStream input = CharStreams.fromFileName(filePath);

        JinjaLexer lexer = new JinjaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JinjaParser parser = new JinjaParser(tokens);

        ParseTree tree = parser.jinjaProgram();

        // Build AST
        Visitor.JinjaBaseVisitor visitor = new Visitor.JinjaBaseVisitor();
        JinjaProgram ast = (JinjaProgram) visitor.visit(tree);

        // Print AST (Simple toString)
        System.out.println("\n=========== JINJA/HTML AST ===========");
        System.out.println(ast.toString());
    }
}