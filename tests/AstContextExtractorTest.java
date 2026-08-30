import PyClasses.PyProgram;
import codegen.AstContextExtractor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import pyAntlr.pyLexer;
import pyAntlr.pyParser;
import visitor.Visitor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/** Proves that context data is built by walking the Python AST, not by executing it. */
public final class AstContextExtractorTest {

    public static void main(String[] args) throws Exception {
        Path fixture = Files.createTempDirectory("ast-context-test-");
        try {
            literalList(fixture);
            dictInsideList(fixture);
            nameResolution(fixture);
            jsonLoadPattern(fixture);
            fallbackTrigger(fixture);
            System.out.println("AstContextExtractorTest passed");
        } finally {
            deleteTree(fixture);
        }
    }

    // ── cases ────────────────────────────────────────────────────────────────

    private static void literalList(Path root) throws Exception {
        AstContextExtractor.Result result = extract(root, "literal_list.py", """
                from flask import Flask, render_template
                app = Flask(__name__)
                numbers = [1, 2, 3]
                @app.route('/')
                def index():
                    return render_template('page.html', numbers=numbers)
                """);
        Object numbers = context(result, "page.html").get("numbers");
        require(numbers instanceof List<?> list && list.size() == 3, "literal list size");
        require(String.valueOf(((List<?>) numbers).get(0)).equals("1"), "literal list first element");
        require(result.fallbackTemplates().isEmpty(), "literal list must not fall back");
    }

    private static void dictInsideList(Path root) throws Exception {
        AstContextExtractor.Result result = extract(root, "dict_in_list.py", """
                from flask import Flask, render_template
                app = Flask(__name__)
                products = [
                    {'id': 1, 'name': 'One', 'price': 10.5},
                    {'id': 2, 'name': 'Two', 'price': 20.0}
                ]
                @app.route('/')
                def index():
                    return render_template('page.html', products=products)
                """);
        Object products = context(result, "page.html").get("products");
        require(products instanceof List<?> list && list.size() == 2, "dict-in-list size");
        Object first = ((List<?>) products).get(0);
        require(first instanceof Map<?,?> map && "One".equals(map.get("name")), "dict-in-list field access");
        require(String.valueOf(((Map<?,?>) first).get("price")).startsWith("10.5"), "dict-in-list float field");
        require(result.fallbackTemplates().isEmpty(), "dict-in-list must not fall back");
    }

    private static void nameResolution(Path root) throws Exception {
        AstContextExtractor.Result result = extract(root, "name_resolution.py", """
                from flask import Flask, render_template
                app = Flask(__name__)
                site_title = "ProductHub"
                heading = site_title
                @app.route('/')
                def index():
                    return render_template('page.html', title=heading)
                """);
        require("ProductHub".equals(context(result, "page.html").get("title")),
                "name resolved through two module-level assignments");
        require("ProductHub".equals(result.globals().get("site_title")), "module global exposed");
    }

    private static void jsonLoadPattern(Path root) throws Exception {
        Path directory = Files.createDirectories(root.resolve("json_pattern"));
        Files.createDirectories(directory.resolve("data"));
        Files.writeString(directory.resolve("data/products.json"),
                "[{\"id\": 7, \"name\": \"From JSON\", \"price\": 3.5}]");
        AstContextExtractor.Result result = extract(directory, "app.py", """
                import json
                import os
                from flask import Flask, render_template
                app = Flask(__name__)
                DATA_FILE = os.path.join(os.path.dirname(__file__), "data", "products.json")
                def load_products_from_json():
                    if os.path.exists(DATA_FILE):
                        data_file = open(DATA_FILE, "r", encoding="utf-8")
                        loaded_products = json.load(data_file)
                        data_file.close()
                        return loaded_products
                    return []
                products = load_products_from_json()
                @app.route('/')
                def index():
                    return render_template('page.html', products=products)
                """);
        Object products = context(result, "page.html").get("products");
        require(products instanceof List<?> list && list.size() == 1, "json.load pattern produced one product");
        require("From JSON".equals(((Map<?,?>) ((List<?>) products).get(0)).get("name")),
                "json.load pattern read the real file");
        require(result.fallbackTemplates().isEmpty(), "json.load pattern must not fall back");
        require(result.log().stream().anyMatch(line -> line.contains("matched json.load(open(<path>))")),
                "json.load pattern must be logged");
        require(result.log().stream().anyMatch(line -> line.contains("os.path.join")),
                "the data-file path must be folded from the AST");
    }

    private static void fallbackTrigger(Path root) throws Exception {
        AstContextExtractor.Result result = extract(root, "fallback.py", """
                from flask import Flask, render_template
                app = Flask(__name__)
                products = [{'id': 1, 'name': 'One'}]
                @app.route('/detail/<int:product_id>')
                def detail(product_id):
                    product = next((p for p in products if p['id'] == product_id), None)
                    return render_template('page.html', product=product)
                """);
        require(result.fallbackTemplates().contains("page.html"),
                "an unfoldable keyword must mark the template for executor fallback");
        require(result.log().stream().anyMatch(line -> line.contains("could not be folded from the AST")),
                "the fallback reason must be logged");
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private static AstContextExtractor.Result extract(Path directory, String fileName, String source) throws Exception {
        Files.createDirectories(directory);
        Path file = directory.resolve(fileName);
        Files.writeString(file, source);
        return AstContextExtractor.extract(parse(file), file);
    }

    private static PyProgram parse(Path file) throws Exception {
        pyLexer lexer = new pyLexer(CharStreams.fromPath(file));
        pyParser parser = new pyParser(new CommonTokenStream(lexer));
        pyParser.PyProgramContext tree = parser.pyProgram();
        if (parser.getNumberOfSyntaxErrors() > 0) throw new AssertionError("fixture did not parse: " + file);
        return (PyProgram) new Visitor.PyBaseVisitor().visit(tree);
    }

    private static Map<String,Object> context(AstContextExtractor.Result result, String template) {
        Map<String,Object> found = result.templateContexts().get(template);
        require(found != null, "no AST context collected for " + template);
        return found;
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
