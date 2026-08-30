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
        runMalformedPython();
        System.out.println("JinjaValidationIntegrationTest passed");
    }

    private static void run(String name, String template, boolean shouldGenerate) throws Exception {
        Path root = Files.createTempDirectory("jinja-validation-" + name);
        Path templates = Files.createDirectory(root.resolve("templates"));
        Files.writeString(templates.resolve("page.html"), template);
        Files.writeString(root.resolve("input.py"), "from flask import Flask, render_template\napp = Flask(__name__)\nproduct = {'id': 1, 'price': 100}\n@app.route('/detail/<int:product_id>')\ndef detail(product_id):\n    return render_template('page.html', product=product)\n");
        Path output = root.resolve("generated_app");
        Main.main(new String[]{root.resolve("input.py").toString(), templates.toString(), output.toString()});
        if (Files.exists(output) != shouldGenerate) throw new AssertionError(name + " generation result was incorrect");
    }

    private static void runMalformedPython() throws Exception {
        Path root = Files.createTempDirectory("python-parse-");
        Path templates = Files.createDirectory(root.resolve("templates"));
        Files.writeString(templates.resolve("page.html"), "ok");
        Files.writeString(root.resolve("input.py"), "def broken(\n");
        Path output = root.resolve("generated_app");
        Main.main(new String[]{root.resolve("input.py").toString(), templates.toString(), output.toString()});
        if (Files.exists(output)) throw new AssertionError("malformed Python generated output");
    }
}
