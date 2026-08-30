import app.Main;
import codegen.JsonValue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * R3: every node in both ASTs must carry its position metadata.
 *
 * The AST JSON is the contract: each node object exposes "node", "line" and
 * "column", so a node whose position was never stamped shows up as line -1.
 */
public final class AstMetadataTest {

    public static void main(String[] args) throws Exception {
        Path project = Path.of("").toAbsolutePath().normalize();
        Path fixture = Files.createTempDirectory("ast-metadata-test-");
        try {
            Path reports = fixture.resolve("compiler_output");
            Main.main(new String[]{project.resolve("app.py").toString(),
                    project.resolve("templates").toString(),
                    fixture.resolve("output").toString(), reports.toString()});

            check(reports.resolve("ast_python.json"), "ast_python.json");
            check(reports.resolve("ast_jinja.json"), "ast_jinja.json");
            System.out.println("AstMetadataTest passed");
        } finally {
            try (var paths = Files.walk(fixture)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(path);
            }
        }
    }

    private static void check(Path file, String label) throws Exception {
        Object tree = JsonValue.read(Files.readString(file));
        List<String> bad = new ArrayList<>();
        int[] counted = {0};
        walk(tree, bad, counted);
        if (counted[0] == 0) throw new AssertionError(label + " contained no nodes at all");
        if (!bad.isEmpty()) {
            throw new AssertionError(label + ": " + bad.size() + " node(s) without position metadata, e.g. "
                    + bad.subList(0, Math.min(8, bad.size())));
        }
        System.out.println(label + ": " + counted[0] + " nodes, all carry line and column");
    }

    private static void walk(Object value, List<String> bad, int[] counted) {
        if (value instanceof List<?> items) {
            for (Object item : items) walk(item, bad, counted);
            return;
        }
        if (!(value instanceof Map<?,?> object)) return;

        if (object.containsKey("node")) {
            counted[0]++;
            String kind = String.valueOf(object.get("node"));
            Object line = object.get("line");
            Object column = object.get("column");
            if (line == null || !(line instanceof Number number) || number.intValue() < 1) {
                bad.add(kind + "(line=" + line + ")");
            } else if (column == null || !(column instanceof Number position) || position.intValue() < 0) {
                bad.add(kind + "(column=" + column + ")");
            }
        }
        for (Object member : object.values()) walk(member, bad, counted);
    }
}
