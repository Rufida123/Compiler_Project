import app.Main;
import java.nio.file.Files;
import java.nio.file.Path;

/** Exercises parser, Jinja semantic validation, and generation gating together. */
public final class JinjaValidationIntegrationTest {
    public static void main(String[] args) throws Exception {
        run("valid", "{{ product.price + 5 }} {{ product.price >= 100 }} {{ product.price ~ \" USD\" }} {{ product.price | string }} {{ url_for('detail', product_id=product.id) }}", true);
        run("number_string", "{{ product.price + \" USD\" }}", false);
        run("unknown_endpoint", "{{ url_for('missing', product_id=product.id) }}", false);
        run("missing_parameter", "{{ url_for('detail') }}", false);
        run("unknown_parameter", "{{ url_for('detail', other=product.id) }}", false);
        run("malformed_jinja", "{{ product.price + }}", false);

        // E-J-08 / E-J-09: the grammar accepts unbalanced control blocks, so the
        // semantic balance pass is what has to catch them.
        run("balanced_blocks", "{% if product %}<b>{{ product.price }}</b>{% else %}<b>0</b>{% endif %}", true);
        expect("unclosed_for",   "{% for p in product %}<b>x</b>", "Unclosed '{% for %}' opened at line 1");
        expect("unclosed_if",    "{% if product %}<b>x</b>",       "Unclosed '{% if %}' opened at line 1");
        expect("unclosed_block", "{% block body %}<b>x</b>",       "Unclosed '{% block %}' opened at line 1");
        expect("stray_endfor",   "{% endfor %}",                   "Unexpected '{% endfor %}' at line 1");
        expect("stray_endif",    "{% endif %}",                    "Unexpected '{% endif %}' at line 1");
        expect("stray_else",     "{% else %}",                     "Unexpected '{% else %}' at line 1");

        // E-J-10: a filter the renderer cannot apply must be rejected during
        // analysis, not silently passed through at render time.
        run("supported_filters", "{{ product.name | upper }} {{ product.price | string }} {{ product.name | trim }}", true);
        expect("unknown_filter", "{{ product.name | mystery }}", "Unknown/unsupported filter 'mystery' at line 1");
        runMalformedPython();
        System.out.println("JinjaValidationIntegrationTest passed");
    }

    /** Asserts the template is rejected AND that semantic_report.txt carries the exact message. */
    private static void expect(String name, String template, String message) throws Exception {
        Path root = Files.createTempDirectory("jinja-validation-" + name);
        Path templates = Files.createDirectory(root.resolve("templates"));
        Files.writeString(templates.resolve("page.html"), template);
        Files.writeString(root.resolve("input.py"), INPUT);
        Path output = root.resolve("generated_app");
        Path reports = root.resolve("compiler_output");
        Main.main(new String[]{root.resolve("input.py").toString(), templates.toString(),
                output.toString(), reports.toString()});
        if (Files.exists(output)) throw new AssertionError(name + " must not generate output");
        String report = Files.readString(reports.resolve("semantic_report.txt"));
        if (!report.contains(message)) {
            throw new AssertionError(name + ": expected \"" + message + "\" in semantic_report.txt, got:\n" + report);
        }
    }

    private static final String INPUT =
            "from flask import Flask, render_template\napp = Flask(__name__)\n"
            + "product = {'id': 1, 'price': 100}\n@app.route('/detail/<int:product_id>')\n"
            + "def detail(product_id):\n    return render_template('page.html', product=product)\n";

    private static void run(String name, String template, boolean shouldGenerate) throws Exception {
        Path root = Files.createTempDirectory("jinja-validation-" + name);
        Path templates = Files.createDirectory(root.resolve("templates"));
        Files.writeString(templates.resolve("page.html"), template);
        Files.writeString(root.resolve("input.py"), "from flask import Flask, render_template\napp = Flask(__name__)\nproduct = {'id': 1, 'price': 100}\n@app.route('/detail/<int:product_id>')\ndef detail(product_id):\n    return render_template('page.html', product=product)\n");
        Path output = root.resolve("generated_app");
        // Always pass the 4th argument: without it Main falls back to the project's
        // own compiler_output/ and the test would overwrite a graded deliverable.
        Main.main(new String[]{root.resolve("input.py").toString(), templates.toString(),
                output.toString(), root.resolve("compiler_output").toString()});
        if (Files.exists(output) != shouldGenerate) throw new AssertionError(name + " generation result was incorrect");
    }

    private static void runMalformedPython() throws Exception {
        Path root = Files.createTempDirectory("python-parse-");
        Path templates = Files.createDirectory(root.resolve("templates"));
        Files.writeString(templates.resolve("page.html"), "ok");
        Files.writeString(root.resolve("input.py"), "def broken(\n");
        Path output = root.resolve("generated_app");
        Main.main(new String[]{root.resolve("input.py").toString(), templates.toString(),
                output.toString(), root.resolve("compiler_output").toString()});
        if (Files.exists(output)) throw new AssertionError("malformed Python generated output");
    }
}
