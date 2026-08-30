package codegen;

import PyClasses.PyProgram;
import jinjaClasses.JinjaProgram;
import semantic.SemanticAnalyzer;
import sharedSymbolTable.SymbolTable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/** Writes the inspection artefacts requested by the assignment. */
public final class CompilerArtifactWriter {

    private CompilerArtifactWriter() {}

    public static void write(Path dir, PyProgram py, Map<String,JinjaProgram> jinja,
                             List<SemanticAnalyzer.SemanticError> errors, List<String> log) throws IOException {
        Files.createDirectories(dir);

        // Real, recursive JSON trees: every node carries its kind, line and column.
        Files.writeString(dir.resolve("ast_python.json"),
                (py == null ? "null" : py.toPrettyJson()) + System.lineSeparator());

        StringBuilder templates = new StringBuilder("{\n\"templates\": {\n");
        int written = 0;
        for (Map.Entry<String,JinjaProgram> entry : jinja.entrySet()) {
            if (written++ > 0) templates.append(",\n");
            templates.append('"').append(escape(entry.getKey())).append("\": ")
                     .append(entry.getValue() == null ? "null" : entry.getValue().toPrettyJson());
        }
        templates.append("\n}\n}\n");
        Files.writeString(dir.resolve("ast_jinja.json"), templates.toString());

        StringBuilder report = new StringBuilder("Semantic report\n===============\n");
        if (errors.isEmpty()) report.append("No semantic/type errors.\n");
        else for (SemanticAnalyzer.SemanticError error : errors) report.append(error.format()).append('\n');
        Files.writeString(dir.resolve("semantic_report.txt"), report.toString());

        Files.writeString(dir.resolve("generation_log.txt"),
                "Generated: " + Instant.now() + "\n" + String.join("\n", log) + "\n");
    }

    /** Readable dumps requested by R7: tree + symbol table, side by side. */
    public static void writeReadableDumps(Path dir, PyProgram py, Map<String,JinjaProgram> jinja,
                                          SymbolTable pythonSymbols,
                                          Map<String,SymbolTable> jinjaSymbols) throws IOException {
        Files.createDirectories(dir);

        StringBuilder python = new StringBuilder("=========== PYTHON AST ===========\n");
        python.append(py == null ? "<no AST>\n" : py.printTree());
        Files.writeString(dir.resolve("ast_python.txt"), python.toString());

        StringBuilder jinjaDump = new StringBuilder();
        for (Map.Entry<String,JinjaProgram> entry : jinja.entrySet()) {
            jinjaDump.append("=========== JINJA/HTML AST: ").append(entry.getKey()).append(" ===========\n");
            jinjaDump.append(entry.getValue() == null ? "<no AST>\n" : entry.getValue().printTree());
            jinjaDump.append('\n');
        }
        Files.writeString(dir.resolve("ast_jinja.txt"), jinjaDump.toString());

        StringBuilder symbols = new StringBuilder("=========== SYMBOL TABLE: app.py ===========\n");
        symbols.append(pythonSymbols == null ? "<none>\n" : pythonSymbols.print());
        for (Map.Entry<String,SymbolTable> entry : jinjaSymbols.entrySet()) {
            symbols.append("\n=========== SYMBOL TABLE: ").append(entry.getKey()).append(" ===========\n");
            symbols.append(entry.getValue().print());
        }
        Files.writeString(dir.resolve("symbol_table.txt"), symbols.toString());
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
