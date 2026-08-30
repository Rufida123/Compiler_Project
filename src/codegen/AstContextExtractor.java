package codegen;

import PyClasses.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Builds template context data by walking the <em>Python AST</em>, without
 * executing the program.  This is the course-required path
 *
 * <pre>Python AST → Semantic Analysis → Generator → Context Data → Jinja AST</pre>
 *
 * <p>What it folds:</p>
 * <ul>
 *   <li>literals — {@link IntExpr}, {@link FloatExpr}, {@link StringExpr},
 *       {@link HtmlFileExpr}, {@link TrueExpr}, {@link FalseExpr},
 *       {@link NoneExpr}, {@link ListLiteralExpr}, {@link DictLiteralExpr};</li>
 *   <li>names — resolved through module-level {@link AssignStmt}s (constant folding);</li>
 *   <li>{@code os.path.join} / {@code os.path.dirname} / {@code __file__}, so a
 *       data-file path written in the source can be recovered from the AST;</li>
 *   <li>the {@code load_products_from_json()} shape — a zero-argument module
 *       function whose body assigns {@code open(<path>, ...)} and calls
 *       {@code json.load(<that variable>)}.  The path expression is folded from
 *       the AST and the resulting JSON file is read.</li>
 * </ul>
 *
 * <p>Anything it cannot fold is reported through {@link Result#fallbackTemplates()}
 * so the caller can fall back to {@link PythonContextExecutor} for that template
 * only.</p>
 */
public final class AstContextExtractor {

    /** Sentinel meaning "this expression could not be folded from the AST". */
    private static final Object UNRESOLVED = new Object() {
        @Override public String toString() { return "<unresolved>"; }
    };

    /**
     * @param globals          module-level names that folded to a constant
     * @param templateContexts template basename → context built from render_template kwargs
     * @param fallbackTemplates templates whose context could not be fully folded
     * @param log              human-readable trace of which AST nodes were used
     */
    public record Result(Map<String,Object> globals,
                         Map<String,Map<String,Object>> templateContexts,
                         Set<String> fallbackTemplates,
                         List<String> log) {}

    private final PyProgram program;
    private final Path sourceFile;
    private final Path sourceDirectory;

    private final Map<String,AssignStmt>        moduleAssignments = new LinkedHashMap<>();
    private final Map<String,FuncDefStatement>  moduleFunctions   = new LinkedHashMap<>();
    private final Map<String,Object>            foldedNames       = new LinkedHashMap<>();
    private final Set<String>                   foldingInProgress = new LinkedHashSet<>();
    private final Map<String,Map<String,Object>> templateContexts = new LinkedHashMap<>();
    private final Set<String>                   fallbackTemplates = new LinkedHashSet<>();
    private final List<String>                  log               = new ArrayList<>();

    private AstContextExtractor(PyProgram program, Path sourceFile) {
        this.program         = program;
        this.sourceFile      = sourceFile.toAbsolutePath().normalize();
        this.sourceDirectory = this.sourceFile.getParent();
    }

    public static Result extract(PyProgram program, Path sourceFile) {
        AstContextExtractor extractor = new AstContextExtractor(program, sourceFile);
        extractor.run();
        return new Result(extractor.globals(), extractor.templateContexts,
                          extractor.fallbackTemplates, extractor.log);
    }

    // ── main walk ────────────────────────────────────────────────────────────

    private void run() {
        if (program == null) return;
        indexModuleLevel();
        for (Statement statement : safe(program.getStatements())) {
            if (statement instanceof RouteStatement route) {
                collectRenderTemplates(route);
            }
        }
    }

    /** Records module-level assignments and function definitions for name resolution. */
    private void indexModuleLevel() {
        for (Statement statement : safe(program.getStatements())) {
            if (statement instanceof AssignStmt assignment) {
                moduleAssignments.put(assignment.getName(), assignment);
            } else if (statement instanceof FuncDefStatement function) {
                moduleFunctions.put(function.getName(), function);
            } else if (statement instanceof RouteStatement route && route.getFuncDef() != null) {
                moduleFunctions.put(route.getFuncDef().getName(), route.getFuncDef());
            }
        }
    }

    /** Every module-level name that folded to a constant becomes a renderer global. */
    private Map<String,Object> globals() {
        Map<String,Object> result = new LinkedHashMap<>();
        for (String name : moduleAssignments.keySet()) {
            Object value = foldName(name);
            if (value != UNRESOLVED) result.put(name, value);
        }
        return result;
    }

    // ── render_template discovery ────────────────────────────────────────────

    private void collectRenderTemplates(RouteStatement route) {
        FuncDefStatement function = route.getFuncDef();
        if (function == null) return;
        List<PostfixExpr> calls = new ArrayList<>();
        findRenderTemplateCalls(function.getBody(), calls);
        for (PostfixExpr call : calls) {
            buildContext(call, function.getName());
        }
    }

    private void buildContext(PostfixExpr call, String endpoint) {
        CallPostfix arguments = firstCall(call);
        if (arguments == null || arguments.getArgList() == null) return;
        List<Arg> args = safe(arguments.getArgList().getArgs());
        if (args.isEmpty()) return;

        String templateName = literalString(args.get(0).getValue());
        if (templateName == null) return;
        String key = basename(templateName);

        Map<String,Object> context = new LinkedHashMap<>();
        boolean complete = true;
        for (int i = 1; i < args.size(); i++) {
            Arg argument = args.get(i);
            if (argument.getName() == null) continue;         // positional extras are ignored
            Object value = fold(argument.getValue());
            if (value == UNRESOLVED) {
                complete = false;
                log.add("  ! " + key + ": keyword '" + argument.getName()
                        + "' could not be folded from the AST (line " + call.getLineNumber() + ")");
            } else {
                context.put(argument.getName(), value);
            }
        }

        templateContexts.put(key, context);
        if (!complete) fallbackTemplates.add(key);
        log.add("  AST render_template('" + templateName + "') in " + endpoint + "() at line "
                + call.getLineNumber() + " → context keys " + context.keySet());
    }

    private void findRenderTemplateCalls(Suite suite, List<PostfixExpr> out) {
        if (suite instanceof IndentedSuite indented) {
            for (Statement statement : safe(indented.getStatements())) findRenderTemplateCalls(statement, out);
        } else if (suite instanceof SimpleSuite simple) {
            findRenderTemplateCalls(simple.getStatement(), out);
        }
    }

    private void findRenderTemplateCalls(Statement statement, List<PostfixExpr> out) {
        if (statement == null) return;
        if (statement instanceof ReturnStmt value) {
            for (Expression expression : safe(value.getReturnArgs())) findRenderTemplateCalls(expression, out);
        } else if (statement instanceof ExprStmt value) {
            findRenderTemplateCalls(value.getExpr(), out);
        } else if (statement instanceof AssignStmt value) {
            findRenderTemplateCalls(value.getValue(), out);
        } else if (statement instanceof IfStatement value) {
            findRenderTemplateCalls(value.getThenSuite(), out);
            findRenderTemplateCalls(value.getElseSuite(), out);
        } else if (statement instanceof ForStatement value) {
            findRenderTemplateCalls(value.getForBlock(), out);
        }
    }

    private void findRenderTemplateCalls(Expression expression, List<PostfixExpr> out) {
        PostfixExpr postfix = asPostfix(expression);
        if (postfix != null) {
            if (postfix.getPrimary() instanceof IdentifierExpr id && "render_template".equals(id.getName())) {
                out.add(postfix);
            }
            CallPostfix call = firstCall(postfix);
            if (call != null && call.getArgList() != null) {
                for (Arg argument : safe(call.getArgList().getArgs())) findRenderTemplateCalls(argument.getValue(), out);
            }
            return;
        }
        Expression inner = unwrapOnce(expression);
        if (inner != null) { findRenderTemplateCalls(inner, out); return; }
        if (expression instanceof CondExpr value) {
            findRenderTemplateCalls(value.getThenExpr(), out);
            findRenderTemplateCalls(value.getElseExpr(), out);
        } else if (expression instanceof BinaryExpr value) {
            findRenderTemplateCalls(value.getLeft(), out);
            findRenderTemplateCalls(value.getRight(), out);
        }
    }

    // ── constant folding ─────────────────────────────────────────────────────

    private Object fold(Expression expression) {
        if (expression == null) return UNRESOLVED;

        Expression inner = unwrapOnce(expression);
        if (inner != null) return fold(inner);

        if (expression instanceof CondExpr value) {
            if (value.getCondition() == null) return fold(value.getThenExpr());
            return UNRESOLVED;                                  // real ternary: not folded
        }
        PostfixExpr postfix = asPostfix(expression);
        if (postfix != null) return foldPostfix(postfix);
        if (expression instanceof PrimaryExpr primary) return foldPrimary(primary);
        if (expression instanceof UnaryMinusExpr value) {
            Object folded = fold(value.getExpr());
            if (folded instanceof Long number)   return -number;
            if (folded instanceof Double number) return -number;
            return UNRESOLVED;
        }
        return UNRESOLVED;
    }

    private Object foldPrimary(PrimaryExpr primary) {
        if (primary == null) return UNRESOLVED;
        if (primary instanceof IntExpr value)      return value.getValue();
        if (primary instanceof FloatExpr value)    return value.getValue();
        if (primary instanceof StringExpr value)   return value.getValue();
        if (primary instanceof HtmlFileExpr value) return value.getValue();
        if (primary instanceof TrueExpr)           return Boolean.TRUE;
        if (primary instanceof FalseExpr)          return Boolean.FALSE;
        if (primary instanceof NoneExpr)           return null;
        if (primary instanceof ParenExpr value)    return fold(value.getInner());
        if (primary instanceof IdentifierExpr value) return foldName(value.getName());
        if (primary instanceof ListLiteralExpr value) {
            if (value.getListLiteral() == null) return new ArrayList<>();
            List<Object> items = new ArrayList<>();
            for (Expression element : safe(value.getListLiteral().getElements())) {
                Object item = fold(element);
                if (item == UNRESOLVED) return UNRESOLVED;
                items.add(item);
            }
            return items;
        }
        if (primary instanceof DictLiteralExpr value) {
            if (value.getDictLiteral() == null) return new LinkedHashMap<String,Object>();
            Map<String,Object> entries = new LinkedHashMap<>();
            for (DictEntry entry : safe(value.getDictLiteral().getEntries())) {
                Object key   = fold(entry.getKey());
                Object item  = fold(entry.getValue());
                if (key == UNRESOLVED || item == UNRESOLVED) return UNRESOLVED;
                entries.put(String.valueOf(key), item);
            }
            return entries;
        }
        return UNRESOLVED;
    }

    /** Resolves a bare name through module-level assignments, with cycle protection. */
    private Object foldName(String name) {
        if (name == null) return UNRESOLVED;
        if ("__file__".equals(name)) return sourceFile.toString();
        if (foldedNames.containsKey(name)) return foldedNames.get(name);
        AssignStmt assignment = moduleAssignments.get(name);
        if (assignment == null) return UNRESOLVED;
        if (!foldingInProgress.add(name)) return UNRESOLVED;    // recursive definition
        try {
            Object value = fold(assignment.getValue());
            if (value != UNRESOLVED) {
                foldedNames.put(name, value);
                log.add("  AST AssignStmt '" + name + "' at line " + assignment.getLineNumber()
                        + " → " + describe(value));
            }
            return value;
        } finally {
            foldingInProgress.remove(name);
        }
    }

    private Object foldPostfix(PostfixExpr postfix) {
        List<PostfixOp> ops = safe(postfix.getOps());
        if (ops.isEmpty()) return foldPrimary(postfix.getPrimary());

        if (postfix.getPrimary() instanceof IdentifierExpr id) {
            StringBuilder dotted = new StringBuilder(id.getName());
            int cursor = 0;
            while (cursor < ops.size() && ops.get(cursor) instanceof AttrPostfix attribute) {
                dotted.append('.').append(attribute.getName());
                cursor++;
            }
            if (cursor < ops.size() && ops.get(cursor) instanceof CallPostfix call) {
                Object value = foldCall(dotted.toString(), call, postfix.getLineNumber());
                return value == UNRESOLVED ? UNRESOLVED : applyOps(value, ops, cursor + 1);
            }
        }

        Object base = foldPrimary(postfix.getPrimary());
        return base == UNRESOLVED ? UNRESOLVED : applyOps(base, ops, 0);
    }

    /** Applies remaining subscript/attribute operations to an already folded value. */
    private Object applyOps(Object value, List<PostfixOp> ops, int from) {
        Object current = value;
        for (int i = from; i < ops.size(); i++) {
            if (current == UNRESOLVED) return UNRESOLVED;
            PostfixOp op = ops.get(i);
            if (op instanceof SubscriptPostfix subscript) {
                Object index = fold(subscript.getIndex());
                if (index == UNRESOLVED) return UNRESOLVED;
                current = member(current, index);
            } else if (op instanceof AttrPostfix attribute) {
                if (current instanceof Map<?,?> map) current = map.get(attribute.getName());
                else return UNRESOLVED;
            } else {
                return UNRESOLVED;                              // a call on folded data
            }
        }
        return current;
    }

    private Object member(Object container, Object key) {
        if (container instanceof Map<?,?> map) return map.get(String.valueOf(key));
        if (container instanceof List<?> list && key instanceof Number number) {
            int position = number.intValue();
            if (position < 0) position += list.size();
            if (position < 0 || position >= list.size()) return UNRESOLVED;
            return list.get(position);
        }
        return UNRESOLVED;
    }

    private Object foldCall(String callee, CallPostfix call, int line) {
        List<Arg> args = call.getArgList() == null ? List.of() : safe(call.getArgList().getArgs());

        switch (callee) {
            case "os.path.dirname" -> {
                if (args.size() != 1) return UNRESOLVED;
                Object value = fold(args.get(0).getValue());
                if (!(value instanceof String text)) return UNRESOLVED;
                Path parent = Path.of(text).getParent();
                log.add("  AST folded os.path.dirname(...) at line " + line + " → " + parent);
                return parent == null ? "" : parent.toString();
            }
            case "os.path.join" -> {
                Path joined = null;
                for (Arg argument : args) {
                    Object value = fold(argument.getValue());
                    if (!(value instanceof String text)) return UNRESOLVED;
                    joined = joined == null ? Path.of(text) : joined.resolve(text);
                }
                if (joined == null) return UNRESOLVED;
                log.add("  AST folded os.path.join(...) at line " + line + " → " + joined);
                return joined.toString();
            }
            case "int" -> {
                Object value = args.size() == 1 ? fold(args.get(0).getValue()) : UNRESOLVED;
                if (value instanceof Number number) return number.longValue();
                if (value instanceof String text) { try { return Long.parseLong(text.trim()); } catch (RuntimeException ignored) { return UNRESOLVED; } }
                return UNRESOLVED;
            }
            case "float" -> {
                Object value = args.size() == 1 ? fold(args.get(0).getValue()) : UNRESOLVED;
                if (value instanceof Number number) return number.doubleValue();
                if (value instanceof String text) { try { return Double.parseDouble(text.trim()); } catch (RuntimeException ignored) { return UNRESOLVED; } }
                return UNRESOLVED;
            }
            case "str" -> {
                Object value = args.size() == 1 ? fold(args.get(0).getValue()) : UNRESOLVED;
                return value == UNRESOLVED ? UNRESOLVED : String.valueOf(value);
            }
            case "len" -> {
                Object value = args.size() == 1 ? fold(args.get(0).getValue()) : UNRESOLVED;
                if (value instanceof List<?> list) return (long) list.size();
                if (value instanceof Map<?,?> map) return (long) map.size();
                if (value instanceof String text)  return (long) text.length();
                return UNRESOLVED;
            }
            default -> { /* fall through to user functions */ }
        }

        if (!args.isEmpty()) return UNRESOLVED;                 // only zero-arg user calls are folded
        FuncDefStatement function = moduleFunctions.get(callee);
        if (function == null) return UNRESOLVED;
        return foldJsonLoadingFunction(callee, function, line);
    }

    // ── the json.load(open(<path>)) pattern ──────────────────────────────────

    /**
     * Recognises a module function that loads a JSON file, e.g.
     * <pre>
     * def load_products_from_json():
     *     if os.path.exists(DATA_FILE):
     *         data_file = open(DATA_FILE, "r", encoding="utf-8")
     *         loaded = json.load(data_file)
     *         ...
     * </pre>
     * The path argument of {@code open(...)} is folded from the AST and the file
     * is read.  Returns {@link #UNRESOLVED} if the shape does not match.
     */
    private Object foldJsonLoadingFunction(String name, FuncDefStatement function, int line) {
        Map<String,Expression> openedFiles = new LinkedHashMap<>();
        List<Expression> jsonLoadArgs = new ArrayList<>();
        scanForJsonLoad(function.getBody(), openedFiles, jsonLoadArgs);
        if (openedFiles.isEmpty() || jsonLoadArgs.isEmpty()) return UNRESOLVED;

        for (Expression loaded : jsonLoadArgs) {
            PostfixExpr postfix = asPostfix(loaded);
            if (postfix == null || !safe(postfix.getOps()).isEmpty()) continue;
            if (!(postfix.getPrimary() instanceof IdentifierExpr handle)) continue;
            Expression pathExpression = openedFiles.get(handle.getName());
            if (pathExpression == null) continue;

            Object path = fold(pathExpression);
            if (!(path instanceof String text)) continue;
            Path file = Path.of(text);
            if (!file.isAbsolute() && sourceDirectory != null) file = sourceDirectory.resolve(file);
            if (!Files.isRegularFile(file)) {
                log.add("  AST json.load pattern in " + name + "() resolved to " + file
                        + " but the file does not exist — using the AST's empty-list fallback");
                return new ArrayList<>();
            }
            try {
                Object data = JsonValue.read(Files.readString(file, StandardCharsets.UTF_8));
                log.add("  AST FuncDef '" + name + "' (line " + function.getLineNumber()
                        + ") matched json.load(open(<path>)); path folded from AST → " + file
                        + "; loaded " + describe(data));
                return data;
            } catch (IOException | RuntimeException failure) {
                log.add("  ! AST json.load pattern in " + name + "() failed to read " + file
                        + ": " + failure.getMessage());
                return UNRESOLVED;
            }
        }
        return UNRESOLVED;
    }

    private void scanForJsonLoad(Suite suite, Map<String,Expression> openedFiles, List<Expression> jsonLoadArgs) {
        if (suite instanceof IndentedSuite indented) {
            for (Statement statement : safe(indented.getStatements())) scanForJsonLoad(statement, openedFiles, jsonLoadArgs);
        } else if (suite instanceof SimpleSuite simple) {
            scanForJsonLoad(simple.getStatement(), openedFiles, jsonLoadArgs);
        }
    }

    private void scanForJsonLoad(Statement statement, Map<String,Expression> openedFiles, List<Expression> jsonLoadArgs) {
        if (statement == null) return;
        if (statement instanceof AssignStmt assignment) {
            Expression opened = openCallPath(assignment.getValue());
            if (opened != null) openedFiles.put(assignment.getName(), opened);
            Expression loaded = jsonLoadArgument(assignment.getValue());
            if (loaded != null) jsonLoadArgs.add(loaded);
        } else if (statement instanceof ExprStmt expression) {
            Expression loaded = jsonLoadArgument(expression.getExpr());
            if (loaded != null) jsonLoadArgs.add(loaded);
        } else if (statement instanceof ReturnStmt value) {
            for (Expression expression : safe(value.getReturnArgs())) {
                Expression loaded = jsonLoadArgument(expression);
                if (loaded != null) jsonLoadArgs.add(loaded);
            }
        } else if (statement instanceof IfStatement value) {
            scanForJsonLoad(value.getThenSuite(), openedFiles, jsonLoadArgs);
            scanForJsonLoad(value.getElseSuite(), openedFiles, jsonLoadArgs);
        } else if (statement instanceof ForStatement value) {
            scanForJsonLoad(value.getForBlock(), openedFiles, jsonLoadArgs);
        }
    }

    /** Returns the first argument of an {@code open(...)} call, or null. */
    private Expression openCallPath(Expression expression) {
        PostfixExpr postfix = asPostfix(expression);
        if (postfix == null) return null;
        if (!(postfix.getPrimary() instanceof IdentifierExpr id) || !"open".equals(id.getName())) return null;
        CallPostfix call = firstCall(postfix);
        if (call == null || call.getArgList() == null) return null;
        List<Arg> args = safe(call.getArgList().getArgs());
        return args.isEmpty() ? null : args.get(0).getValue();
    }

    /** Returns the first argument of a {@code json.load(...)} call, or null. */
    private Expression jsonLoadArgument(Expression expression) {
        PostfixExpr postfix = asPostfix(expression);
        if (postfix == null) return null;
        if (!(postfix.getPrimary() instanceof IdentifierExpr id) || !"json".equals(id.getName())) return null;
        List<PostfixOp> ops = safe(postfix.getOps());
        if (ops.size() < 2) return null;
        if (!(ops.get(0) instanceof AttrPostfix attribute) || !"load".equals(attribute.getName())) return null;
        if (!(ops.get(1) instanceof CallPostfix call) || call.getArgList() == null) return null;
        List<Arg> args = safe(call.getArgList().getArgs());
        return args.isEmpty() ? null : args.get(0).getValue();
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    /** Unwraps the single-child precedence wrappers the parser produces. */
    private static Expression unwrapOnce(Expression expression) {
        if (expression instanceof OrPassExpr value) return value.getInner();
        if (expression instanceof CondExpr value && value.getCondition() == null) return value.getThenExpr();
        if (expression instanceof ParenExpr value) return value.getInner();
        return null;
    }

    private static PostfixExpr asPostfix(Expression expression) {
        Expression current = expression;
        while (true) {
            if (current instanceof PostfixExpr postfix) return postfix;
            if (current instanceof UnaryPostfixExpr unary) return unary.getExpr();
            Expression inner = unwrapOnce(current);
            if (inner == null) return null;
            current = inner;
        }
    }

    private static CallPostfix firstCall(PostfixExpr postfix) {
        for (PostfixOp op : safe(postfix.getOps())) if (op instanceof CallPostfix call) return call;
        return null;
    }

    /** Extracts a plain string literal (used for the template name). */
    private String literalString(Expression expression) {
        Object value = fold(expression);
        return value instanceof String text ? text : null;
    }

    private static String basename(String name) {
        if (name == null) return "";
        String normalized = name.replace('\\', '/');
        int slash = normalized.lastIndexOf('/');
        return slash >= 0 ? normalized.substring(slash + 1) : normalized;
    }

    private static String describe(Object value) {
        if (value instanceof List<?> list) return "list(" + list.size() + " items)";
        if (value instanceof Map<?,?> map)  return "dict(" + map.size() + " keys)";
        if (value instanceof String text)   return "str(\"" + (text.length() > 40 ? text.substring(0, 40) + "…" : text) + "\")";
        return String.valueOf(value);
    }

    private static <T> List<T> safe(List<T> list) { return list == null ? List.of() : list; }
}
