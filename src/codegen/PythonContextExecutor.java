package codegen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Executes trusted coursework input with a tiny Flask stub.  This gives the
 * renderer the same global data and render_template contexts as Python bytecode
 * without starting a server or importing/installing Flask.
 *
 * <p>Used only as a fallback: {@link AstContextExtractor} handles everything it
 * can fold from the AST, and this runs for the remaining templates.</p>
 *
 * <p>Execution is deliberately side-effect free.  Route functions are probed to
 * capture their render_template contexts, and a route may legitimately write
 * (the delete handler saves products.json), so {@code open} is stubbed to
 * discard writes and module globals are snapshotted <em>before</em> any route
 * runs.  Without that, compiling the project would mutate the project's data.</p>
 */
public final class PythonContextExecutor {
    public record Result(Map<String,Object> globals, Map<String,Map<String,Object>> templateContexts) {}
    private PythonContextExecutor() {}

    public static Result execute(Path source) throws IOException, InterruptedException {
        String helper = """
import sys, types, json, inspect, builtins, io as _io
events=[]; routes=[]
def safe(v):
 if isinstance(v,(str,int,float,bool)) or v is None:return v
 if isinstance(v,(list,tuple)):return [safe(x) for x in v]
 if isinstance(v,dict):return {str(k):safe(x) for k,x in v.items()}
 return None
class App:
 def __init__(self,*a,**k): pass
 def route(self,path,*a,**k):
  def dec(f): routes.append((path,f)); return f
  return dec
 def run(self,*a,**k): pass
def render_template(name, **ctx): events.append((name,safe(ctx))); return ''
flask=types.ModuleType('flask'); flask.Flask=App; flask.render_template=render_template
flask.request=types.SimpleNamespace(method='GET',form={},args={}); flask.redirect=lambda x:x; flask.url_for=lambda x,**k:x
sys.modules['flask']=flask
ns={'__name__':'compiler_input','__file__':sys.argv[1]}
exec(compile(open(sys.argv[1],encoding='utf-8').read(),sys.argv[1],'exec'),ns)
g={k:safe(v) for k,v in ns.items() if not k.startswith('__') and safe(v) is not None}
_real_open=builtins.open
def _no_write_open(file,mode='r',*a,**k):
 if any(c in str(mode) for c in 'wax+'): return _io.BytesIO() if 'b' in str(mode) else _io.StringIO()
 return _real_open(file,mode,*a,**k)
builtins.open=_no_write_open
for path,f in routes:
 try: f(*([1]*len(inspect.signature(f).parameters)))
 except Exception: pass
builtins.open=_real_open
c={name:ctx for name,ctx in events}
print(json.dumps({'globals':g,'contexts':c}))
""";
        List<String> command = new ArrayList<>(interpreter());
        command.add("-c"); command.add(helper); command.add(source.toAbsolutePath().toString());
        Process p = new ProcessBuilder(command).redirectErrorStream(true).start();
        String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (p.waitFor()!=0) throw new IOException("Python context execution failed: " + output);
        Object root = JsonValue.read(output.trim());
        if (!(root instanceof Map<?,?> m)) throw new IOException("Invalid Python context output");
        Map<String,Object> globals = map(m.get("globals"));
        Map<String,Map<String,Object>> templateContexts = contexts(m.get("contexts"));

        /*
         * Persistent-data precedence:
         * 1) When data/products.json exists beside the Python input, it is the
         *    authoritative products context used by static generation.
         * 2) When it does not exist, keep the previous behavior and use the
         *    products value obtained by executing the parsed Python source.
         *    This preserves compatibility with older inputs that still define
         *    products as a list literal in app.py/test.py.
         */
        Path sourceDirectory = source.toAbsolutePath().getParent();
        Path productsFile = sourceDirectory.resolve("data").resolve("products.json");
        if (Files.isRegularFile(productsFile)) {
            Object storedProducts = JsonValue.read(Files.readString(productsFile, StandardCharsets.UTF_8));
            if (!(storedProducts instanceof List<?>)) {
                throw new IOException(productsFile + " must contain a JSON array of products");
            }
            globals.put("products", storedProducts);
            for (Map<String,Object> context : templateContexts.values()) {
                if (context.containsKey("products")) context.put("products", storedProducts);
            }
        }

        return new Result(globals, templateContexts);
    }

    /** The resolved interpreter command, e.g. [".venv/Scripts/python.exe"] or ["py","-3.12"]. */
    public static List<String> pythonCommand() throws IOException { return interpreter(); }

    /** Interpreter actually used by the last {@link #execute} call, for the log. */
    public static String lastInterpreter() { return lastInterpreter; }
    private static volatile String lastInterpreter = "<not resolved>";

    /**
     * Resolves a Python 3 interpreter, in order:
     * project {@code .venv} → {@code COMPILER_PYTHON} → {@code py -3.12} → {@code python3}.
     */
    private static List<String> interpreter() throws IOException {
        List<String> tried = new ArrayList<>();
        for (List<String> candidate : candidates(tried)) {
            if (works(candidate)) { lastInterpreter = String.join(" ", candidate); return candidate; }
        }
        throw new IOException("No usable Python 3 interpreter found. Tried, in order: " + String.join(", ", tried)
                + ". Set COMPILER_PYTHON to the interpreter to use, or create .venv with: py -3.12 -m venv .venv");
    }

    private static List<List<String>> candidates(List<String> tried) {
        List<List<String>> options = new ArrayList<>();
        Path projectRoot = Path.of("").toAbsolutePath().normalize();
        for (Path base : List.of(projectRoot)) {
            for (String relative : List.of(".venv/Scripts/python.exe", ".venv/bin/python")) {
                Path venv = base.resolve(relative);
                tried.add(venv.toString());
                if (Files.isRegularFile(venv)) options.add(List.of(venv.toString()));
            }
        }
        String configured = System.getenv("COMPILER_PYTHON");
        tried.add("$COMPILER_PYTHON" + (configured == null ? " (unset)" : "=" + configured));
        if (configured != null && !configured.isBlank()) options.add(List.of(configured));

        tried.add("py -3.12");
        options.add(List.of("py", "-3.12"));
        tried.add("python3");
        options.add(List.of("python3"));
        return options;
    }

    private static boolean works(List<String> command) {
        try {
            List<String> probe = new ArrayList<>(command);
            probe.add("-c");
            probe.add("import sys;sys.exit(0 if sys.version_info[0]==3 else 1)");
            Process process = new ProcessBuilder(probe).redirectErrorStream(true).start();
            process.getInputStream().readAllBytes();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException failure) {
            return false;
        }
    }

    @SuppressWarnings("unchecked") private static Map<String,Object> map(Object v) { Map<String,Object> out=new LinkedHashMap<>(); if(v instanceof Map<?,?> m) for(var e:m.entrySet())out.put(String.valueOf(e.getKey()),e.getValue()); return out; }
    private static Map<String,Map<String,Object>> contexts(Object v) { Map<String,Map<String,Object>> out=new LinkedHashMap<>(); if(v instanceof Map<?,?> m)for(var e:m.entrySet())out.put(String.valueOf(e.getKey()),map(e.getValue())); return out; }
}
