package semantic;

import PyClasses.Arg;
import PyClasses.AssignStmt;
import PyClasses.BinaryExpr;
import PyClasses.CallPostfix;
import PyClasses.CondExpr;
import PyClasses.DictLiteralExpr;
import PyClasses.Expression;
import PyClasses.ExprStmt;
import PyClasses.FalseExpr;
import PyClasses.FloatExpr;
import PyClasses.ForStatement;
import PyClasses.FuncDefStatement;
import PyClasses.HtmlFileExpr;
import PyClasses.IdentifierExpr;
import PyClasses.IdExpr;
import PyClasses.IfStatement;
import PyClasses.IndentedSuite;
import PyClasses.IntExpr;
import PyClasses.ListLiteralExpr;
import PyClasses.NoneExpr;
import PyClasses.OrPassExpr;
import PyClasses.PostfixExpr;
import PyClasses.PostfixOp;
import PyClasses.PrimaryExpr;
import PyClasses.PyProgram;
import PyClasses.ReturnStmt;
import PyClasses.RouteStatement;
import PyClasses.SimpleSuite;
import PyClasses.Statement;
import PyClasses.StringExpr;
import PyClasses.SubscriptPostfix;
import PyClasses.Suite;
import PyClasses.TrueExpr;
import PyClasses.UnaryMinusExpr;
import PyClasses.UnaryPostfixExpr;
import jinjaClasses.AccessExpr;
import jinjaClasses.DotAccess;
import jinjaClasses.JinjaBinaryExpr;
import jinjaClasses.ControlBlock;
import jinjaClasses.DocumentElement;
import jinjaClasses.For;
import jinjaClasses.HtmlAttribute;
import jinjaClasses.JinjaAttribute;
import jinjaClasses.JinjaBlock;
import jinjaClasses.JinjaExpression;
import jinjaClasses.JinjaFilter;
import jinjaClasses.JinjaIdentifierChain;
import jinjaClasses.JinjaPrimary;
import jinjaClasses.JinjaParenthesizedExpr;
import jinjaClasses.JinjaProgram;
import jinjaClasses.JinjaStatementHeader;
import jinjaClasses.JinjaValueExpr;
import jinjaClasses.NormalAttribute;
import jinjaClasses.PairedTag;
import jinjaClasses.PrintBlock;
import jinjaClasses.SelfClosingTag;
import jinjaClasses.StringLiteral;
import jinjaClasses.NumberLiteral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeChecker {

    private final List<SemanticAnalyzer.SemanticError> errors        = new ArrayList<>();
    private final Set<String>                          reportedKeys  = new LinkedHashSet<>();

    private final Map<String, String>              varTypes              = new LinkedHashMap<>();
    private final Map<String, Map<String, String>> templateContextVarTypes = new LinkedHashMap<>();
    private final Map<String, Map<String, String>> routeEndpointParamTypes = new LinkedHashMap<>();

    private final Map<String, String>              templateVarTypes  = new LinkedHashMap<>();
    private final Map<String, Map<String, String>> dictFieldTypes = new LinkedHashMap<>();

    public void analyzePython(PyProgram program, String pythonFilePath) {
        errors.clear();
        reportedKeys.clear();
        varTypes.clear();
        templateContextVarTypes.clear();
        routeEndpointParamTypes.clear();
        dictFieldTypes.clear();

        if (program == null) return;
        String path = displayPath(pythonFilePath);

        for (Statement s : safeList(program.getStatements())) {
            collectRouteParams(s);
        }

        for (Statement s : safeList(program.getStatements())) {
            checkStatement(s, path);
        }
    }

    public void analyzeJinja(JinjaProgram program, String templateFilePath) {
        if (program == null) return;
        String path             = displayPath(templateFilePath);
        String normalizedName   = normalizeTemplateName(templateFilePath);

        templateVarTypes.clear();
        Map<String, String> ctx = templateContextVarTypes.get(normalizedName);
        if (ctx != null) templateVarTypes.putAll(ctx);

        checkJinjaElements(program.getHtmlElements(), path);
    }

    public List<SemanticAnalyzer.SemanticError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    private void collectRouteParams(Statement statement) {
        if (!(statement instanceof RouteStatement routeStatement)) return;

        String path = routeStatement.getRoutePath() != null
                ? routeStatement.getRoutePath().getPath() : null;
        if (path == null) return;

        FuncDefStatement funcDef = routeStatement.getFuncDef();
        String funcName = funcDef != null ? funcDef.getName() : null;
        if (funcName == null) return;

        Map<String, String> params = new LinkedHashMap<>();
        java.util.regex.Matcher m =
                java.util.regex.Pattern
                        .compile("<([a-z]+):([a-zA-Z_][a-zA-Z0-9_]*)>")
                        .matcher(path);
        while (m.find()) {
            params.put(m.group(2), m.group(1));
        }
        if (!params.isEmpty()) {
            routeEndpointParamTypes.put(funcName, params);
        }
    }

    private void checkStatement(Statement statement, String filePath) {
        if (statement == null) return;

        if (statement instanceof AssignStmt assignStmt) {
            checkExpression(assignStmt.getValue(),
                    getExprLine(assignStmt.getValue()), filePath);
            String t = inferType(assignStmt.getValue());
            if (!"unknown".equals(t)) varTypes.put(assignStmt.getName(), t);
            collectDictFieldTypes(assignStmt);

        } else if (statement instanceof FuncDefStatement funcDef) {
            checkSuite(funcDef.getBody(), filePath);

        } else if (statement instanceof RouteStatement routeStatement) {
            checkStatement(routeStatement.getFuncDef(), filePath);

        } else if (statement instanceof ForStatement forStatement) {
            checkForLoopIterable(forStatement,
                    getExprLine(forStatement.getExpression()), filePath);
            checkExpression(forStatement.getExpression(),
                    getExprLine(forStatement.getExpression()), filePath);
            checkSuite(forStatement.getForBlock(), filePath);

        } else if (statement instanceof IfStatement ifStatement) {
            checkExpression(ifStatement.getCondition(),
                    getExprLine(ifStatement.getCondition()), filePath);
            checkSuite(ifStatement.getThenSuite(), filePath);
            checkSuite(ifStatement.getElseSuite(), filePath);

        } else if (statement instanceof ReturnStmt returnStmt) {
            for (Expression e : safeList(returnStmt.getReturnArgs())) {
                checkExpression(e, getExprLine(e), filePath);
            }

        } else if (statement instanceof ExprStmt exprStmt) {
            checkExpression(exprStmt.getExpr(),
                    getExprLine(exprStmt.getExpr()), filePath);
        }
    }

    private void checkSuite(Suite suite, String filePath) {
        if (suite == null) return;
        if (suite instanceof IndentedSuite indented) {
            for (Statement s : safeList(indented.getStatements()))
                checkStatement(s, filePath);
        } else if (suite instanceof SimpleSuite simple) {
            checkStatement(simple.getStatement(), filePath);
        }
    }

    private void checkExpression(Expression expression, int line, String filePath) {
        if (expression == null) return;

        if (expression instanceof BinaryExpr bin) {
            checkBinaryExpr(bin, line, filePath);

        } else if (expression instanceof CondExpr cond) {
            checkExpression(cond.getThenExpr(),  line, filePath);
            checkExpression(cond.getCondition(), line, filePath);
            checkExpression(cond.getElseExpr(),  line, filePath);

        } else if (expression instanceof OrPassExpr orPass) {
            checkExpression(orPass.getInner(), line, filePath);

        } else if (expression instanceof UnaryMinusExpr unary) {
            checkExpression(unary.getExpr(), line, filePath);

        } else if (expression instanceof UnaryPostfixExpr unary) {
            checkPostfixExpr(unary.getExpr(), line, filePath);

        } else if (expression instanceof PostfixExpr postfix) {
            checkPostfixExpr(postfix, line, filePath);
        }
    }

    private void checkBinaryExpr(BinaryExpr bin, int line, String filePath) {
        checkExpression(bin.getLeft(),  line, filePath);
        checkExpression(bin.getRight(), line, filePath);

        if (!"+".equals(bin.getOp())) return;

        String left  = inferType(bin.getLeft());
        String right = inferType(bin.getRight());

        if ("str".equals(left) && isNumeric(right)) {
            report(filePath, line, null,
                    "Type Error: Cannot use '+' between 'str' and '" + right + "'. "
                            + "Use str() to convert: "
                            + "\"...\" + str(" + describeExpr(bin.getRight()) + ")");

        } else if (isNumeric(left) && "str".equals(right)) {
            report(filePath, line, null,
                    "Type Error: Cannot use '+' between '" + left + "' and 'str'. "
                            + "Use str() to convert: "
                            + "str(" + describeExpr(bin.getLeft()) + ") + "
                            + describeExpr(bin.getRight()));
        }
    }

    private void checkPostfixExpr(PostfixExpr postfix, int line, String filePath) {
        if (postfix == null) return;

        PrimaryExpr primary  = postfix.getPrimary();
        String      baseType = inferPrimaryType(primary);
        int         baseLine = getLineFromPrimary(primary);
        if (baseLine <= 0) baseLine = line;

        String funcName = (primary instanceof IdentifierExpr id) ? id.getName() : null;

        if ("render_template".equals(funcName)) {
            checkRenderTemplateArgs(postfix, baseLine, filePath);
        }

        for (PostfixOp op : safeList(postfix.getOps())) {

            if (op instanceof SubscriptPostfix sub) {
                if ("str".equals(baseType)) {
                    String varName = describeExprFromPrimary(primary);
                    report(filePath, baseLine, varName,
                            "Type Error: '" + varName + "' is a 'str', not a dict. "
                                    + "Strings do not support key-based subscript access like ["
                                    + describeExpr(sub.getIndex()) + "]. "
                                    + "Did you mean to use a dict?");
                }

            } else if (op instanceof CallPostfix call) {
                if ("len".equals(funcName))
                    checkLenArg(call, baseLine, filePath);

                if ("url_for".equals(funcName))
                    checkUrlForArgs(call, baseLine, filePath);

                if (call.getArgList() != null) {
                    for (Arg arg : safeList(call.getArgList().getArgs()))
                        checkExpression(arg.getValue(), baseLine, filePath);
                }
            }
        }
    }

    private void checkRenderTemplateArgs(PostfixExpr postfix, int line, String filePath) {
        CallPostfix call = firstCallPostfix(postfix);
        if (call == null || call.getArgList() == null) return;

        List<Arg> args = call.getArgList().getArgs();
        if (args.isEmpty()) return;

        String templateName = extractStringValue(args.get(0).getValue());
        if (templateName == null) return;

        for (int i = 1; i < args.size(); i++) {
            Arg    arg     = args.get(i);
            if (arg.getName() == null) continue;

            String argType = inferType(arg.getValue());

            if (!"unknown".equals(argType)) {
                templateContextVarTypes
                        .computeIfAbsent(normalizeTemplateName(templateName),
                                k -> new LinkedHashMap<>())
                        .put(arg.getName(), argType);
            }

            if (isNumeric(argType)) {
                report(filePath, line, arg.getName(),
                        "Type Error: render_template('" + templateName + "', "
                                + arg.getName() + "=" + describeExpr(arg.getValue())
                                + ") — '" + arg.getName() + "' is '" + argType
                                + "', but templates usually expect a list or dict. "
                                + "Iterating this with 'for' will fail at runtime");
            }
        }
    }

    private void checkForLoopIterable(ForStatement stmt, int line, String filePath) {
        String iterableType = inferType(stmt.getExpression());
        if (isNumeric(iterableType) || "bool".equals(iterableType)) {
            String name = describeExpr(stmt.getExpression());
            report(filePath, line, name,
                    "Type Error: Cannot iterate over '" + name + "' — '"
                            + iterableType + "' is not iterable. "
                            + "For loops require a list, dict, str, or range");
        }
    }

    private void checkUrlForArgs(CallPostfix call, int line, String filePath) {
        if (call.getArgList() == null) return;
        List<Arg> args = safeList(call.getArgList().getArgs());
        if (args.isEmpty()) return;

        PrimaryExpr firstPrimary = extractSimplePrimary(args.get(0).getValue());
        if (!(firstPrimary instanceof StringExpr se)) return;
        String endpointName = se.getValue();

        Map<String, String> paramTypes = routeEndpointParamTypes.get(endpointName);
        if (paramTypes == null || paramTypes.isEmpty()) return;

        for (int i = 1; i < args.size(); i++) {
            Arg    arg          = args.get(i);
            if (arg.getName() == null) continue;
            String expectedType = paramTypes.get(arg.getName());
            if (expectedType == null) continue;
            String actualType   = inferType(arg.getValue());
            if ("unknown".equals(actualType)) continue;

            if (!expectedType.equals(actualType)) {
                report(filePath, line, arg.getName(),
                        "Type Error: url_for('" + endpointName + "', "
                                + arg.getName() + "=...) expects <" + expectedType
                                + ":" + arg.getName() + "> but got '" + actualType + "'");
            }
        }
    }

    private void checkLenArg(CallPostfix call, int line, String filePath) {
        if (call.getArgList() == null) return;
        List<Arg> args = safeList(call.getArgList().getArgs());
        if (args.isEmpty()) return;

        Expression argExpr = args.get(0).getValue();
        String     argType = inferType(argExpr);

        if (isNumeric(argType) || "bool".equals(argType)) {
            report(filePath, line, "len",
                    "Type Error: 'len()' requires an iterable (str, list, dict, ...) "
                            + "but got '" + argType + "'. "
                            + "'" + describeExpr(argExpr) + "' is not iterable");
        }
    }

    private void checkJinjaElements(List<DocumentElement> elements, String filePath) {
        for (DocumentElement el : safeList(elements))
            checkJinjaElement(el, filePath);
    }

    private void checkJinjaElement(DocumentElement element, String filePath) {
        if (element instanceof PairedTag tag) {
            checkJinjaAttributes(tag.getAttributes(), filePath);
            checkJinjaElements(tag.getChildren(), filePath);
        } else if (element instanceof SelfClosingTag tag) {
            checkJinjaAttributes(tag.getAttributes(), filePath);
        } else if (element instanceof JinjaBlock block) {
            checkJinjaBlock(block, filePath);
        }
    }

    private void checkJinjaAttributes(List<HtmlAttribute> attributes, String filePath) {
        for (HtmlAttribute attr : safeList(attributes)) {
            if (attr instanceof JinjaAttribute ja) {
                checkJinjaBlock(ja.getJinjaBlock(), filePath);
            } else if (attr instanceof NormalAttribute na
                    && na.getValue() instanceof JinjaValueExpr jve) {
                checkJinjaBlock(jve.getJinjaBlock(), filePath);
            }
        }
    }

    private void checkJinjaBlock(JinjaBlock block, String filePath) {
        if (block instanceof PrintBlock print) {
            checkJinjaExpr(print.getJinjaExpression(), print.getLine(), filePath);
        } else if (block instanceof ControlBlock ctrl) {
            checkJinjaHeader(ctrl.getJinjaStatementHeader(), ctrl.getLine(), filePath);
        }
    }

    private void checkJinjaHeader(JinjaStatementHeader header, int line, String filePath) {
        if (header instanceof For forHeader) {
            checkJinjaForLoopType(forHeader, line, filePath);
        }
    }

    private void checkJinjaExpr(JinjaExpression expression, int line, String filePath) {
        if (expression == null) return;
        checkJinjaPrimary(expression.getPrimary(), line, filePath);
        inferJinjaExpressionType(expression);
    }

    private void checkJinjaPrimary(JinjaPrimary primary, int line, String filePath) {
        if (primary instanceof JinjaBinaryExpr binary) {
            checkJinjaPrimary(binary.getLeft(), line, filePath);
            checkJinjaPrimary(binary.getRight(), line, filePath);
            String left = inferJinjaType(binary.getLeft()), right = inferJinjaType(binary.getRight());
            String op = binary.getOperator();
            if (Set.of("+", "-", "*", "/").contains(op) && (!isNumeric(left) || !isNumeric(right)))
                report(filePath, binary.getLine() > 0 ? binary.getLine() : line, null,
                        "Type Error (Jinja): operator '" + op + "' cannot use '" + left + "' and '" + right + "'. Separate text from the value.");
            else if (Set.of("==", "!=", "<", ">", "<=", ">=").contains(op) && !"unknown".equals(left) && !"unknown".equals(right) && !left.equals(right) && !(isNumeric(left) && isNumeric(right)))
                report(filePath, binary.getLine() > 0 ? binary.getLine() : line, null,
                        "Type Error (Jinja): operator '" + op + "' compares incompatible types '" + left + "' and '" + right + "'.");
        } else if (primary instanceof JinjaParenthesizedExpr parenthesized) {
            checkJinjaExpr(parenthesized.getExpression(), line, filePath);
        }
    }

    private String inferJinjaType(JinjaPrimary primary) {
        if (primary instanceof NumberLiteral) return "int";
        if (primary instanceof StringLiteral) return "str";
        if (primary instanceof JinjaParenthesizedExpr p) return p.getExpression() == null ? "unknown" : inferJinjaType(p.getExpression().getPrimary());
        if (primary instanceof JinjaBinaryExpr b) {
            if ("~".equals(b.getOperator())) return "str";
            return Set.of("==", "!=", "<", ">", "<=", ">=").contains(b.getOperator()) ? "bool" : "int";
        }
        if (primary instanceof AccessExpr a && a.getChain() != null) {
            String root = a.getChain().getIdentifier();
            for (var access : a.getChain().getAccesses()) if (access instanceof DotAccess dot)
                return dictFieldTypes.getOrDefault(root, Collections.emptyMap()).getOrDefault(dot.getIdentifier(), "unknown");
            return templateVarTypes.getOrDefault(root, "unknown");
        }
        return "unknown";
    }

    private String inferJinjaExpressionType(JinjaExpression expression) {
        if (expression == null) return "unknown";
        String type = inferJinjaType(expression.getPrimary());
        for (JinjaFilter filter : safeList(expression.getFilters())) {
            String name = filter.getName();
            if (Set.of("string", "upper", "lower", "trim", "replace", "format").contains(name)) type = "str";
            else if ("int".equals(name)) type = "int";
            else if ("float".equals(name)) type = "float";
            else if ("list".equals(name)) type = "list";
        }
        return type;
    }

    private void collectDictFieldTypes(AssignStmt assignment) {
        PrimaryExpr primary = extractSimplePrimary(assignment.getValue());
        if (!(primary instanceof DictLiteralExpr dict) || dict.getDictLiteral() == null) return;
        Map<String, String> fields = new LinkedHashMap<>();
        for (var entry : safeList(dict.getDictLiteral().getEntries())) {
            PrimaryExpr key = extractSimplePrimary(entry.getKey());
            if (key instanceof StringExpr string) fields.put(string.getValue(), inferType(entry.getValue()));
        }
        dictFieldTypes.put(assignment.getName(), fields);
    }

    private void checkJinjaForLoopType(For forHeader, int line, String filePath) {
        JinjaExpression expr = forHeader.getExpression();
        if (expr == null) return;
        JinjaPrimary primary = expr.getPrimary();
        if (!(primary instanceof AccessExpr accessExpr)) return;

        JinjaIdentifierChain chain = accessExpr.getChain();
        if (chain == null || !chain.getAccesses().isEmpty()) return;

        String type = templateVarTypes.getOrDefault(chain.getIdentifier(), "unknown");
        if (isNumeric(type)) {
            report(filePath, line, chain.getIdentifier(),
                    "Type Error (Jinja): Cannot iterate over '"
                            + chain.getIdentifier() + "' — it is '"
                            + type + "', not iterable");
        }
    }

    private String inferType(Expression expression) {
        if (expression == null) return "unknown";

        if (expression instanceof CondExpr c && c.getCondition() == null)
            return inferType(c.getThenExpr());
        if (expression instanceof OrPassExpr o)
            return inferType(o.getInner());

        if (expression instanceof BinaryExpr bin) {
            String left  = inferType(bin.getLeft());
            String right = inferType(bin.getRight());
            String op    = bin.getOp();
            if (Set.of("+","-","*","/","//","%","**").contains(op)) {
                if ("str".equals(left) || "str".equals(right)) return "str";
                if ("float".equals(left) || "float".equals(right)) return "float";
                if ("int".equals(left) && "int".equals(right))    return "int";
            }
            if (Set.of("==","!=","<",">","<=",">=","and","or","not","in","is").contains(op))
                return "bool";
            return "unknown";
        }

        if (expression instanceof UnaryMinusExpr unary) {
            String inner = inferType(unary.getExpr());
            return "float".equals(inner) ? "float" : "int".equals(inner) ? "int" : "unknown";
        }

        if (expression instanceof UnaryPostfixExpr u)
            return inferPostfixType(u.getExpr());
        if (expression instanceof PostfixExpr p)
            return inferPostfixType(p);
        if (expression instanceof IdExpr id)
            return varTypes.getOrDefault(id.getName(), "unknown");

        return "unknown";
    }

    private String inferPostfixType(PostfixExpr postfix) {
        if (postfix == null) return "unknown";
        if (safeList(postfix.getOps()).isEmpty())
            return inferPrimaryType(postfix.getPrimary());

        if (postfix.getPrimary() instanceof IdentifierExpr id) {
            return switch (id.getName()) {
                case "int"   -> "int";
                case "len"   -> "int";
                case "float" -> "float";
                case "str"   -> "str";
                case "list"  -> "list";
                case "dict"  -> "dict";
                case "range" -> "list";
                default      -> "unknown";
            };
        }
        return "unknown";
    }

    private String inferPrimaryType(PrimaryExpr primary) {
        if (primary instanceof StringExpr)       return "str";
        if (primary instanceof IntExpr)           return "int";
        if (primary instanceof FloatExpr)         return "float";
        if (primary instanceof TrueExpr)          return "bool";
        if (primary instanceof FalseExpr)         return "bool";
        if (primary instanceof NoneExpr)          return "none";
        if (primary instanceof ListLiteralExpr)   return "list";
        if (primary instanceof DictLiteralExpr)   return "dict";
        if (primary instanceof IdentifierExpr id)
            return varTypes.getOrDefault(id.getName(), "unknown");
        return "unknown";
    }

    private boolean isNumeric(String type) {
        return "int".equals(type) || "float".equals(type);
    }

    private String describeExpr(Expression expression) {
        if (expression == null) return "?";
        if (expression instanceof OrPassExpr o)
            return describeExpr(o.getInner());
        if (expression instanceof CondExpr c && c.getCondition() == null)
            return describeExpr(c.getThenExpr());
        if (expression instanceof BinaryExpr b)
            return describeExpr(b.getLeft()) + " " + b.getOp() + " " + describeExpr(b.getRight());
        if (expression instanceof PostfixExpr p)
            return describeExprFromPrimary(p.getPrimary());
        if (expression instanceof UnaryPostfixExpr u && u.getExpr() != null)
            return describeExprFromPrimary(u.getExpr().getPrimary());
        if (expression instanceof IdExpr id)
            return id.getName();
        return "expression";
    }

    private String describeExprFromPrimary(PrimaryExpr primary) {
        if (primary instanceof IdentifierExpr id) return id.getName();
        if (primary instanceof StringExpr se)     return "\"" + se.getValue() + "\"";
        if (primary instanceof IntExpr ie)         return String.valueOf(ie.getValue());
        if (primary instanceof FloatExpr fe)       return String.valueOf(fe.getValue());
        if (primary instanceof ListLiteralExpr)    return "[...]";
        if (primary instanceof DictLiteralExpr)    return "{...}";
        return "expression";
    }

    private int getLineFromPrimary(PrimaryExpr primary) {
        if (primary instanceof IdentifierExpr id) return id.getLineNumber();
        if (primary instanceof IntExpr ie)         return ie.getLineNumber();
        if (primary instanceof FloatExpr fe)       return fe.getLineNumber();
        if (primary instanceof StringExpr se)      return se.getLineNumber();
        return 0;
    }

    private int getExprLine(Expression expression) {
        if (expression == null) return 0;
        if (expression instanceof OrPassExpr o)
            return getExprLine(o.getInner());
        if (expression instanceof CondExpr c && c.getCondition() == null)
            return getExprLine(c.getThenExpr());
        if (expression instanceof BinaryExpr b)
            return getExprLine(b.getLeft());
        if (expression instanceof PostfixExpr p)
            return getLineFromPrimary(p.getPrimary());
        if (expression instanceof UnaryPostfixExpr u && u.getExpr() != null)
            return getLineFromPrimary(u.getExpr().getPrimary());
        if (expression instanceof IdExpr id)
            return id.getLineNumber();
        return 0;
    }

    private CallPostfix firstCallPostfix(PostfixExpr postfix) {
        for (PostfixOp op : safeList(postfix.getOps()))
            if (op instanceof CallPostfix c) return c;
        return null;
    }

    private String extractStringValue(Expression expression) {
        PrimaryExpr primary = extractSimplePrimary(expression);
        if (primary instanceof HtmlFileExpr html)  return html.getValue();
        if (primary instanceof StringExpr str)      return str.getValue();
        return null;
    }

    private PrimaryExpr extractSimplePrimary(Expression expression) {
        if (expression instanceof CondExpr c && c.getCondition() == null)
            return extractSimplePrimary(c.getThenExpr());
        if (expression instanceof OrPassExpr o)
            return extractSimplePrimary(o.getInner());
        if (expression instanceof UnaryPostfixExpr u)
            return extractSimplePrimary(u.getExpr());
        if (expression instanceof PostfixExpr p && p.getOps().isEmpty())
            return p.getPrimary();
        return null;
    }

    private void report(String filePath, int lineNumber,
                        String variableName, String message) {
        SemanticAnalyzer.SemanticError error =
                new SemanticAnalyzer.SemanticError(filePath, lineNumber, variableName, message);
        String key = error.format();
        if (reportedKeys.add(key)) errors.add(error);
    }

    private String displayPath(String filePath) {
        if (filePath == null || filePath.isEmpty()) return "";
        String n = filePath.replace("\\", "/");
        while (n.startsWith("./")) n = n.substring(2);
        return n;
    }

    private String normalizeTemplateName(String name) {
        if (name == null) return "";
        String n = name.replace("\\", "/");
        int slash = n.lastIndexOf('/');
        if (slash >= 0) n = n.substring(slash + 1);
        try { return java.nio.file.Path.of(n).getFileName().toString(); }
        catch (Exception ignored) { return n; }
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
