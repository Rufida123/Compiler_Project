package codegen;

import PyClasses.*;
import jinjaClasses.JinjaProgram;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/** Official course-output generator: produces static HTML and copies support files unchanged. */
public final class StaticSiteGenerator {
    private StaticSiteGenerator() {}
    public static void generate(Path pythonFile, Path sourceTemplates, Path output, PyProgram pythonAst,
                                Map<String,JinjaProgram> templates) throws Exception {
        recreate(output);
        Map<String,String> routes = routes(pythonAst);
        PythonContextExecutor.Result runtime = PythonContextExecutor.execute(pythonFile);
        JinjaRenderer renderer = new JinjaRenderer(templates, routes);
        for (String name : templates.keySet()) {
            if (isLayout(name, templates)) continue;
            Map<String,Object> context = new LinkedHashMap<>(runtime.globals());
            context.putAll(runtime.templateContexts().getOrDefault(name, Map.of()));
            Files.writeString(output.resolve(htmlName(name)), renderer.render(name, context));
        }
        Files.copy(pythonFile, output.resolve("app.py"), StandardCopyOption.REPLACE_EXISTING);
        // CSS/JavaScript are support files: copy them byte-for-byte without parsing.
        copyAsset(sourceTemplates, output, "style.css");
        copyAsset(sourceTemplates, output, "script.js");
        Path sourceRoot = pythonFile.toAbsolutePath().getParent();
        copyAsset(sourceRoot, output, "style.css");
        copyAsset(sourceRoot, output, "script.js");

        // Keep the copied app.py runnable from output/ with the same persistent data.
        copyDirectory(sourceRoot.resolve("data"), output.resolve("data"));
    }
    private static boolean isLayout(String n,Map<String,JinjaProgram> all){ for(JinjaProgram p:all.values())for(var e:p.getHtmlElements())if(e instanceof jinjaClasses.ControlBlock b && b.getJinjaStatementHeader() instanceof jinjaClasses.Extends x&&strip(x.getString()).equals(n))return true; return false; }
    private static String htmlName(String name){int i=name.lastIndexOf('.');return (i<0?name:name.substring(0,i))+".html";}
    private static String strip(String s){return s!=null&&s.length()>1&&((s.startsWith("\"")&&s.endsWith("\""))||(s.startsWith("'")&&s.endsWith("'")))?s.substring(1,s.length()-1):s;}
    private static void copyAsset(Path from,Path to,String name)throws IOException{Path p=from.resolve(name);if(Files.isRegularFile(p))Files.copy(p,to.resolve(name),StandardCopyOption.REPLACE_EXISTING);}
    private static void copyDirectory(Path from,Path to)throws IOException{if(!Files.isDirectory(from))return;try(var paths=Files.walk(from)){for(Path p:paths.toList()){Path target=to.resolve(from.relativize(p));if(Files.isDirectory(p))Files.createDirectories(target);else Files.copy(p,target,StandardCopyOption.REPLACE_EXISTING);}}}
    private static Map<String,String> routes(PyProgram p){Map<String,String>m=new LinkedHashMap<>();for(Statement s:p.getStatements())if(s instanceof RouteStatement r)m.put(r.getFuncDef().getName(),strip(r.getRoutePath().getPath()));return m;}
    private static void recreate(Path out)throws IOException{if(Files.exists(out)){try(var s=Files.walk(out)){for(Path p:s.sorted(Comparator.reverseOrder()).toList())Files.delete(p);}}Files.createDirectories(out);}
}
