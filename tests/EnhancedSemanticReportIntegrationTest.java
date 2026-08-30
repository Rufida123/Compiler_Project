import app.Main;

import java.nio.file.*;
import java.util.Comparator;

public class EnhancedSemanticReportIntegrationTest {
    public static void main(String[] args) throws Exception {
        reportedDiagnostics();
        invalidAttribute();
        listIndexType();
        dictKeyNone();
        validTypedAccessIsSilent();
        System.out.println("EnhancedSemanticReportIntegrationTest passed");
    }

    /** The original case: imports, arity, undefined function, and the two warnings. */
    private static void reportedDiagnostics() throws Exception {
        Path fixture = Files.createTempDirectory("enhanced-semantic-test-");
        try {
            Path input = fixture.resolve("invalid.py");
            Path templates = Files.createDirectories(fixture.resolve("templates"));
            Path output = fixture.resolve("output");
            Path reportDirectory = fixture.resolve("compiler_output");
            Files.writeString(input, """
import mystery_module
value = 1
value = 2

def greet(name):
    return name

def no_return():
    value = 3

greet()
missing_function()
""");

            Main.main(new String[]{input.toString(), templates.toString(),
                    output.toString(), reportDirectory.toString()});

            String report = Files.readString(reportDirectory.resolve("semantic_report.txt"));
            require(report.contains("Unknown module 'mystery_module'"), "invalid import missing");
            require(report.contains("expects 1 argument(s) but got 0"), "argument-count error missing");
            require(report.contains("Undefined function 'missing_function'"), "undefined function missing");
            require(report.contains("Warning: Variable 'value' is assigned more than once"),
                    "redefinition warning missing");
            require(report.contains("Warning: Function 'no_return' has no return statement"),
                    "missing-return warning missing");
            require(!Files.exists(output), "blocking enhanced errors must prevent generation");
        } finally {
            deleteTree(fixture);
        }
    }

    /** E-PY-08: attribute that the inferred type does not support. */
    private static void invalidAttribute() throws Exception {
        String report = report("invalid-attribute", """
text = "abc"
bad = text.nosuchmethod
""");
        require(report.contains("Type 'str' has no supported attribute 'nosuchmethod'"),
                "invalid-attribute error missing");
    }

    /** E-PY-09: non-integer index into a value inferred as a list. */
    private static void listIndexType() throws Exception {
        String report = report("list-index", """
numbers = [1, 2, 3]
bad = numbers["k"]
""");
        require(report.contains("Type Error: List indices must be integers, not 'str'."),
                "list-index error missing");
    }

    /** E-PY-10: None used as a dict key. */
    private static void dictKeyNone() throws Exception {
        String report = report("dict-key-none", """
mapping = {"a": 1}
bad = mapping[None]
""");
        require(report.contains("Type Error: Dict keys cannot be None."), "dict-key-None error missing");
    }

    /** The same inference must not fire on legitimate typed access. */
    private static void validTypedAccessIsSilent() throws Exception {
        String report = report("valid-typed-access", """
text = "abc"
numbers = [1, 2, 3]
mapping = {"a": 1}
good_attr = text.upper
good_index = numbers[0]
good_key = mapping["a"]
""");
        require(!report.contains("has no supported attribute"), "false positive on a valid attribute");
        require(!report.contains("List indices must be integers"), "false positive on a valid list index");
        require(!report.contains("Dict keys cannot be None"), "false positive on a valid dict key");
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    /** Compiles one snippet in an isolated fixture and returns semantic_report.txt. */
    private static String report(String name, String source) throws Exception {
        Path fixture = Files.createTempDirectory("enhanced-" + name + "-");
        try {
            Path input = fixture.resolve("input.py");
            Path templates = Files.createDirectories(fixture.resolve("templates"));
            Files.writeString(input, source);
            Main.main(new String[]{input.toString(), templates.toString(),
                    fixture.resolve("output").toString(), fixture.resolve("compiler_output").toString()});
            return Files.readString(fixture.resolve("compiler_output/semantic_report.txt"));
        } finally {
            deleteTree(fixture);
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void deleteTree(Path root) throws Exception {
        try (var paths = Files.walk(root)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(path);
        }
    }
}
