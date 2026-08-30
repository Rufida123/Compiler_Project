import app.Main;
import codegen.PythonContextExecutor;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;

public class PersistentProductsIntegrationTest {
    public static void main(String[] args) throws Exception {
        Path project = Path.of("").toAbsolutePath().normalize();
        Path fixture = Files.createTempDirectory("persistent-products-test-");
        try {
            Files.copy(project.resolve("app.py"), fixture.resolve("app.py"));
            Files.createDirectories(fixture.resolve("data"));
            Files.copy(project.resolve("data/products.json"), fixture.resolve("data/products.json"));
            Files.writeString(fixture.resolve("style.css"), "body { color: navy; }\n");
            Files.writeString(fixture.resolve("script.js"), "console.log('support asset');\n");

            String helper = """
import sys, types
class App:
 def __init__(self,*a,**k): pass
 def route(self,*a,**k):
  return lambda f:f
 def run(self,*a,**k): pass
flask=types.ModuleType('flask')
flask.Flask=App
flask.render_template=lambda *a,**k:''
flask.redirect=lambda x:x
flask.url_for=lambda *a,**k:'index'
flask.request=types.SimpleNamespace(method='POST',form={
 'name':'Persistent Test Product',
 'price':'42.5',
 'image':'https://example.com/persistent.png',
 'details':'Saved by the persistence integration test'
})
sys.modules['flask']=flask
ns={'__name__':'compiler_test','__file__':sys.argv[1]}
exec(compile(open(sys.argv[1],encoding='utf-8').read(),sys.argv[1],'exec'),ns)
ns['add_product']()
""";
            Process add = new ProcessBuilder("py", "-3.12", "-c", helper,
                    fixture.resolve("app.py").toString()).inheritIO().start();
            if (add.waitFor() != 0) throw new AssertionError("POST persistence simulation failed");

            String saved = Files.readString(fixture.resolve("data/products.json"));
            require(saved.contains("Persistent Test Product"), "products.json was not updated");

            Path output = fixture.resolve("output");
            Path reports = fixture.resolve("compiler_output");
            Main.main(new String[]{fixture.resolve("app.py").toString(),
                    project.resolve("templates").toString(), output.toString(), reports.toString()});

            require(Files.readString(output.resolve("index.html")).contains("Persistent Test Product"),
                    "regenerated index.html does not contain the saved product");
            require(Files.readString(reports.resolve("semantic_report.txt")).contains("No semantic/type errors"),
                    "semantic report contains errors");
            require(Files.readString(output.resolve("style.css")).equals("body { color: navy; }\n"),
                    "style.css was not copied unchanged");
            require(Files.readString(output.resolve("script.js")).equals("console.log('support asset');\n"),
                    "script.js was not copied unchanged");
            require(Files.isRegularFile(output.resolve("data/products.json")),
                    "persistent data was not copied beside output/app.py");

            Path legacyDirectory = Files.createDirectories(fixture.resolve("legacy-input"));
            Path legacy = legacyDirectory.resolve("legacy.py");
            Files.writeString(legacy, "products = [{\"id\": 9, \"name\": \"Legacy Product\"}]\n");
            Object legacyProducts = PythonContextExecutor.execute(legacy).globals().get("products");
            require(String.valueOf(legacyProducts).contains("Legacy Product"),
                    "list-literal fallback stopped working when products.json is absent");

            Process syntax = new ProcessBuilder("py", "-3.12", "-m", "py_compile",
                    output.resolve("app.py").toString()).inheritIO().start();
            if (syntax.waitFor() != 0) throw new AssertionError("generated app.py syntax check failed");
            System.out.println("PersistentProductsIntegrationTest passed");
        } finally {
            try (var paths = Files.walk(fixture)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) Files.deleteIfExists(path);
            }
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
