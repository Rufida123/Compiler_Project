import app.Main;

import java.nio.file.*;
import java.util.Comparator;

public class EnhancedSemanticReportIntegrationTest {
    public static void main(String[] args) throws Exception {
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
            System.out.println("EnhancedSemanticReportIntegrationTest passed");
        } finally {
            try (var paths = Files.walk(fixture)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                    Files.deleteIfExists(path);
                }
            }
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
