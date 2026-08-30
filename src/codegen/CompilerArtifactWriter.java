package codegen;

import PyClasses.PyProgram;
import jinjaClasses.JinjaProgram;
import semantic.SemanticAnalyzer;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;

/** Writes the inspection artefacts requested by the assignment. */
public final class CompilerArtifactWriter {
 private CompilerArtifactWriter(){}
 public static void write(Path dir, PyProgram py, Map<String,JinjaProgram> jinja, List<SemanticAnalyzer.SemanticError> errors, List<String> log)throws IOException{
  Files.createDirectories(dir);
  Files.writeString(dir.resolve("ast_python.json"), "{\n  \"ast\": \""+esc(String.valueOf(py))+"\"\n}\n");
  StringBuilder j=new StringBuilder("{\n  \"templates\": {\n"); int n=0;for(var e:jinja.entrySet()){if(n++>0)j.append(",\n");j.append("    \"").append(esc(e.getKey())).append("\": \"").append(esc(String.valueOf(e.getValue()))).append("\"");}j.append("\n  }\n}\n");Files.writeString(dir.resolve("ast_jinja.json"),j);
  StringBuilder r=new StringBuilder("Semantic report\n===============\n");if(errors.isEmpty())r.append("No semantic/type errors.\n");else for(var e:errors)r.append(e.format()).append('\n');Files.writeString(dir.resolve("semantic_report.txt"),r);
  Files.writeString(dir.resolve("generation_log.txt"),"Generated: "+Instant.now()+"\n"+String.join("\n",log)+"\n");
 }
 private static String esc(String s){return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","");}
}
