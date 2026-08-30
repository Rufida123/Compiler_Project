package codegen;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/** Executes trusted coursework input with a tiny Flask stub.  This gives the
 * renderer the same global data and render_template contexts as Python bytecode
 * without starting a server or importing/installing Flask. */
public final class PythonContextExecutor {
    public record Result(Map<String,Object> globals, Map<String,Map<String,Object>> templateContexts) {}
    private PythonContextExecutor() {}
    public static Result execute(Path source) throws IOException, InterruptedException {
        String helper = """
import sys, types, json, inspect
events=[]; routes=[]
class App:
 def __init__(self,*a,**k): pass
 def route(self,path,*a,**k):
  def dec(f): routes.append((path,f)); return f
  return dec
 def run(self,*a,**k): pass
def render_template(name, **ctx): events.append((name,ctx)); return ''
flask=types.ModuleType('flask'); flask.Flask=App; flask.render_template=render_template
flask.request=types.SimpleNamespace(method='GET',form={},args={}); flask.redirect=lambda x:x; flask.url_for=lambda x,**k:x
sys.modules['flask']=flask
ns={'__name__':'compiler_input','__file__':sys.argv[1]}
exec(compile(open(sys.argv[1],encoding='utf-8').read(),sys.argv[1],'exec'),ns)
for path,f in routes:
 try: f(*([1]*len(inspect.signature(f).parameters)))
 except Exception: pass
def safe(v):
 if isinstance(v,(str,int,float,bool)) or v is None:return v
 if isinstance(v,(list,tuple)):return [safe(x) for x in v]
 if isinstance(v,dict):return {str(k):safe(x) for k,x in v.items()}
 return None
g={k:safe(v) for k,v in ns.items() if not k.startswith('__') and safe(v) is not None}
c={name:safe(ctx) for name,ctx in events}
print(json.dumps({'globals':g,'contexts':c}))
""";
        List<String> command = new ArrayList<>();
        command.add("py"); command.add("-3.12"); command.add("-c"); command.add(helper); command.add(source.toAbsolutePath().toString());
        Process p = new ProcessBuilder(command).redirectErrorStream(true).start();
        String output = new String(p.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (p.waitFor()!=0) throw new IOException("Python context execution failed: " + output);
        Object root = new Json(output.trim()).read();
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
            Object storedProducts = new Json(Files.readString(productsFile, StandardCharsets.UTF_8)).read();
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
    @SuppressWarnings("unchecked") private static Map<String,Object> map(Object v) { Map<String,Object> out=new LinkedHashMap<>(); if(v instanceof Map<?,?> m) for(var e:m.entrySet())out.put(String.valueOf(e.getKey()),e.getValue()); return out; }
    private static Map<String,Map<String,Object>> contexts(Object v) { Map<String,Map<String,Object>> out=new LinkedHashMap<>(); if(v instanceof Map<?,?> m)for(var e:m.entrySet())out.put(String.valueOf(e.getKey()),map(e.getValue())); return out; }
    /** Small JSON reader; avoids adding a dependency just to read Python output. */
    private static final class Json { final String s; int i; Json(String s){this.s=s;} Object read(){ws(); return val();} Object val(){ws(); char c=s.charAt(i); if(c=='{')return obj();if(c=='[')return arr();if(c=='\"')return str();if(s.startsWith("true",i)){i+=4;return true;}if(s.startsWith("false",i)){i+=5;return false;}if(s.startsWith("null",i)){i+=4;return null;}int a=i;while(i<s.length()&&"-+.0123456789eE".indexOf(s.charAt(i))>=0)i++;return Double.valueOf(s.substring(a,i));} Map<String,Object> obj(){Map<String,Object>m=new LinkedHashMap<>();i++;ws();while(s.charAt(i)!='}'){String k=str();ws();i++;m.put(k,val());ws();if(s.charAt(i)==','){i++;ws();}}i++;return m;} List<Object> arr(){List<Object>a=new ArrayList<>();i++;ws();while(s.charAt(i)!=']'){a.add(val());ws();if(s.charAt(i)==','){i++;ws();}}i++;return a;} String str(){StringBuilder b=new StringBuilder();i++;while(s.charAt(i)!='\"'){char c=s.charAt(i++);if(c=='\\'){char e=s.charAt(i++);b.append(e=='n'?'\n':e=='r'?'\r':e=='t'?'\t':e);}else b.append(c);}i++;return b.toString();}void ws(){while(i<s.length()&&Character.isWhitespace(s.charAt(i)))i++;}}
}
