package semantic;

import PyClasses.*;
import sharedSymbolTable.Symbol;
import sharedSymbolTable.SymbolTable;

import java.util.*;

/**
 * Additional Python AST checks that complement SemanticAnalyzer and TypeChecker.
 * Errors are returned to Main and participate in the final report. Findings whose
 * message starts with "Warning:" are reported but do not block generation.
 */
public class EnhancedSemanticAnalyzer extends SemanticAnalyzer {
    private final List<SemanticError> findings = new ArrayList<>();
    private final Set<String> reported = new LinkedHashSet<>();
    /** Declared functions, held as Symbols so arity comes from the symbol's own
     *  parameter list rather than from a second private map. */
    private final SymbolTable declarations = new SymbolTable();
    private final Set<String> importedNames = new LinkedHashSet<>();
    private final Deque<Set<String>> assignedScopes = new ArrayDeque<>();
    private final Deque<Map<String,String>> typeScopes = new ArrayDeque<>();

    private static final Set<String> VALID_MODULES = Set.of(
            "flask", "os", "sys", "json", "datetime", "random", "math"
    );
    private static final Set<String> FLASK_EXPORTS = Set.of(
            "Flask", "render_template", "request", "redirect", "url_for"
    );
    /** Shared with SemanticAnalyzer so the two analyzers cannot disagree. */
    private static final Set<String> BUILTINS = SemanticAnalyzer.PYTHON_BUILTINS;

    @Override
    public void analyzePython(PyProgram program, String filePath) {
        findings.clear();
        reported.clear();
        declarations.initGlobal();
        importedNames.clear();
        assignedScopes.clear();
        typeScopes.clear();
        openScope();

        if (program != null) {
            collectFunctions(program.getStatements(), filePath);
            for (Statement statement : safe(program.getStatements())) {
                analyzeStatement(statement, filePath);
            }
        }
        closeScope();
    }

    @Override
    public List<SemanticError> getErrors() {
        return Collections.unmodifiableList(findings);
    }

    private void collectFunctions(List<Statement> statements, String filePath) {
        for (Statement statement : safe(statements)) {
            FuncDefStatement function = functionOf(statement);
            if (function != null) {
                if (declarations.lookup(function.getName()) != null) {
                    add(filePath, line(statement), function.getName(),
                            "Function '" + function.getName() + "' is defined more than once.");
                } else {
                    declarations.add(new Symbol(function.getName(), "function", 0,
                            line(statement), function.getParams()));
                }
            }
        }
    }

    private void analyzeStatement(Statement statement, String filePath) {
        if (statement == null) return;
        int line = line(statement);

        if (statement instanceof ImportStmt value) {
            analyzeImport(value, line, filePath);
        } else if (statement instanceof AssignStmt value) {
            checkBuiltinRedefinition(value.getName(), line, filePath);
            checkVariableRedefinition(value.getName(), line, filePath);
            analyzeExpression(value.getValue(), line, filePath);
            typeScopes.peek().put(value.getName(), inferSimpleType(value.getValue()));
        } else if (statement instanceof RouteStatement route) {
            analyzeFunction(route.getFuncDef(), line, filePath);
        } else if (statement instanceof FuncDefStatement function) {
            analyzeFunction(function, line, filePath);
        } else if (statement instanceof ExprStmt expression) {
            analyzeExpression(expression.getExpr(), line, filePath);
        } else if (statement instanceof ReturnStmt value) {
            for (Expression expression : safe(value.getReturnArgs())) {
                analyzeExpression(expression, line, filePath);
            }
        } else if (statement instanceof IfStatement value) {
            analyzeExpression(value.getCondition(), line, filePath);
            analyzeSuite(value.getThenSuite(), filePath);
            analyzeSuite(value.getElseSuite(), filePath);
        } else if (statement instanceof ForStatement value) {
            analyzeExpression(value.getExpression(), line, filePath);
            analyzeSuite(value.getForBlock(), filePath);
        }
    }

    private void analyzeFunction(FuncDefStatement function, int line, String filePath) {
        if (function == null) return;
        openScope();
        assignedScopes.peek().addAll(function.getParams());
        for (String parameter : function.getParams()) typeScopes.peek().put(parameter, "unknown");
        analyzeSuite(function.getBody(), filePath);
        checkMissingReturn(function.getName(), containsReturn(function.getBody()), line, filePath);
        closeScope();
    }

    private void analyzeSuite(Suite suite, String filePath) {
        if (suite instanceof IndentedSuite indented) {
            for (Statement statement : safe(indented.getStatements())) analyzeStatement(statement, filePath);
        } else if (suite instanceof SimpleSuite simple) {
            analyzeStatement(simple.getStatement(), filePath);
        }
    }

    private boolean containsReturn(Suite suite) {
        if (suite instanceof SimpleSuite simple) return containsReturn(simple.getStatement());
        if (suite instanceof IndentedSuite indented) {
            for (Statement statement : safe(indented.getStatements())) {
                if (containsReturn(statement)) return true;
            }
        }
        return false;
    }

    private boolean containsReturn(Statement statement) {
        if (statement instanceof ReturnStmt) return true;
        if (statement instanceof IfStatement value) {
            return containsReturn(value.getThenSuite()) || containsReturn(value.getElseSuite());
        }
        if (statement instanceof ForStatement value) return containsReturn(value.getForBlock());
        return false;
    }

    private void analyzeImport(ImportStmt statement, int line, String filePath) {
        String fromModule = statement.getDottedName() == null
                ? null : String.join(".", statement.getDottedName().getParts());
        for (ImportItem item : safe(statement.getImportList() == null
                ? List.<ImportItem>of() : statement.getImportList().getItems())) {
            String importedName = item.getAlias() == null ? item.getName() : item.getAlias();
            importedNames.add(importedName);
            if (fromModule == null) checkInvalidImport(item.getName(), null, line, filePath);
            else checkInvalidImport(fromModule, item.getName(), line, filePath);
        }
    }

    private void analyzeExpression(Expression expression, int line, String filePath) {
        if (expression == null) return;
        if (expression instanceof CondExpr value) {
            analyzeExpression(value.getThenExpr(), line, filePath);
            analyzeExpression(value.getCondition(), line, filePath);
            analyzeExpression(value.getElseExpr(), line, filePath);
        } else if (expression instanceof OrPassExpr value) {
            analyzeExpression(value.getInner(), line, filePath);
        } else if (expression instanceof BinaryExpr value) {
            analyzeExpression(value.getLeft(), line, filePath);
            analyzeExpression(value.getRight(), line, filePath);
        } else if (expression instanceof UnaryPostfixExpr value) {
            analyzePostfix(value.getExpr(), line, filePath);
        } else if (expression instanceof UnaryMinusExpr value) {
            analyzeExpression(value.getExpr(), line, filePath);
        } else if (expression instanceof PostfixExpr value) {
            analyzePostfix(value, line, filePath);
        } else if (expression instanceof ParenExpr value) {
            analyzeExpression(value.getInner(), line, filePath);
        } else if (expression instanceof ListLiteralExpr value && value.getListLiteral() != null) {
            for (Expression item : safe(value.getListLiteral().getElements())) analyzeExpression(item, line, filePath);
        } else if (expression instanceof DictLiteralExpr value && value.getDictLiteral() != null) {
            for (DictEntry entry : safe(value.getDictLiteral().getEntries())) {
                analyzeExpression(entry.getKey(), line, filePath);
                analyzeExpression(entry.getValue(), line, filePath);
            }
        } else if (expression instanceof GeneratorPrimaryExpr value && value.getGeneratorExpr() != null) {
            analyzeExpression(value.getGeneratorExpr().getIterable(), line, filePath);
            analyzeExpression(value.getGeneratorExpr().getFilter(), line, filePath);
        }
    }

    private void analyzePostfix(PostfixExpr expression, int line, String filePath) {
        if (expression == null) return;
        String baseName = expression.getPrimary() instanceof IdentifierExpr identifier
                ? identifier.getName() : null;
        String baseType = lookupType(baseName);
        boolean attributeSeen = false;

        for (PostfixOp operation : safe(expression.getOps())) {
            if (operation instanceof AttrPostfix attribute) {
                if (!attributeSeen && !"unknown".equals(baseType)) {
                    checkInvalidAttribute(baseName, attribute.getName(), baseType, line, filePath);
                }
                attributeSeen = true;
            } else if (operation instanceof SubscriptPostfix subscript) {
                checkIndexType(baseType, inferSimpleType(subscript.getIndex()), line, filePath);
                analyzeExpression(subscript.getIndex(), line, filePath);
            } else if (operation instanceof CallPostfix call) {
                int argumentCount = call.getArgList() == null ? 0 : call.getArgList().getArgs().size();
                if (!attributeSeen && baseName != null) {
                    checkUndefinedFunction(baseName, line, filePath);
                    checkFunctionArguments(baseName, argumentCount, line, filePath);
                }
                if (call.getArgList() != null) {
                    for (Arg argument : safe(call.getArgList().getArgs())) {
                        analyzeExpression(argument.getValue(), line, filePath);
                    }
                }
            }
        }
        analyzeExpression(expression.getPrimary(), line, filePath);
    }

    private void checkUndefinedFunction(String name, int line, String filePath) {
        if (!BUILTINS.contains(name) && declarations.lookup(name) == null && !importedNames.contains(name)) {
            add(filePath, line, name,
                    "Undefined function '" + name + "'. Did you forget to define or import it?");
        }
    }

    private void checkFunctionArguments(String name, int count, int line, String filePath) {
        Symbol definition = declarations.lookup(name);
        if (definition != null && definition.getParameterCount() != count) {
            add(filePath, line, name,
                    "Function '" + name + "' expects " + definition.getParameterCount()
                            + " argument(s) but got " + count
                            + ". Definition is at line " + definition.getLineNumber() + ".");
        }
    }

    private void checkVariableRedefinition(String name, int line, String filePath) {
        Set<String> current = assignedScopes.peek();
        if (!current.add(name)) {
            add(filePath, line, name,
                    "Warning: Variable '" + name + "' is assigned more than once in the same scope.");
        }
    }

    private void checkMissingReturn(String name, boolean hasReturn, int line, String filePath) {
        if (!hasReturn && !isVoidFunction(name)) {
            add(filePath, line, name,
                    "Warning: Function '" + name + "' has no return statement and will return None.");
        }
    }

    private void checkInvalidImport(String module, String item, int line, String filePath) {
        String rootModule = module.contains(".") ? module.substring(0, module.indexOf('.')) : module;
        if (!VALID_MODULES.contains(rootModule)) {
            add(filePath, line, module,
                    "Unknown module '" + module + "'. Supported modules: " + VALID_MODULES + ".");
        }
        if ("flask".equals(module) && item != null && !FLASK_EXPORTS.contains(item)) {
            add(filePath, line, item,
                    "Module 'flask' has no supported export '" + item + "'. Available: "
                            + FLASK_EXPORTS + ".");
        }
    }

    private void checkInvalidAttribute(String variable, String attribute, String type,
                                       int line, String filePath) {
        Map<String,Set<String>> allowed = Map.of(
                "str", Set.of("upper", "lower", "strip", "split", "replace", "startswith", "endswith"),
                "list", Set.of("append", "extend", "insert", "remove", "pop", "clear", "sort", "reverse"),
                "dict", Set.of("keys", "values", "items", "get", "pop", "update", "clear")
        );
        Set<String> attributes = allowed.get(type);
        if (attributes != null && !attributes.contains(attribute)) {
            add(filePath, line, attribute,
                    "Type '" + type + "' has no supported attribute '" + attribute
                            + "'. Available: " + attributes + ".");
        }
    }

    private void checkIndexType(String containerType, String indexType, int line, String filePath) {
        if ("list".equals(containerType) && !"int".equals(indexType) && !"unknown".equals(indexType)) {
            add(filePath, line, null,
                    "Type Error: List indices must be integers, not '" + indexType + "'.");
        } else if ("dict".equals(containerType) && "none".equals(indexType)) {
            add(filePath, line, null, "Type Error: Dict keys cannot be None.");
        }
    }

    private void checkBuiltinRedefinition(String name, int line, String filePath) {
        if (SemanticAnalyzer.CALLABLE_BUILTINS.contains(name)) {
            add(filePath, line, name,
                    "Cannot redefine built-in function '" + name + "'.");
        }
    }

    private String inferSimpleType(Expression expression) {
        Expression current = expression;
        while (true) {
            if (current instanceof CondExpr value && value.getCondition() == null) current = value.getThenExpr();
            else if (current instanceof OrPassExpr value) current = value.getInner();
            else if (current instanceof UnaryPostfixExpr value) current = value.getExpr();
            else if (current instanceof ParenExpr value) current = value.getInner();
            else break;
        }
        // The parser always wraps a primary in a PostfixExpr.  When that wrapper
        // carries no operations it is transparent, so unwrap it before matching
        // literal node types - otherwise every inferred type stays "unknown".
        if (current instanceof PostfixExpr value && safe(value.getOps()).isEmpty()) {
            PrimaryExpr primary = value.getPrimary();
            if (primary instanceof IdentifierExpr id) return lookupType(id.getName());
            current = primary;
        }
        if (current instanceof StringExpr) return "str";
        if (current instanceof IntExpr) return "int";
        if (current instanceof FloatExpr) return "float";
        if (current instanceof TrueExpr || current instanceof FalseExpr) return "bool";
        if (current instanceof NoneExpr) return "none";
        if (current instanceof ListLiteralExpr) return "list";
        if (current instanceof DictLiteralExpr) return "dict";
        if (current instanceof IdentifierExpr value) return lookupType(value.getName());
        if (current instanceof PostfixExpr value && value.getPrimary() instanceof IdentifierExpr id) {
            // A call/subscript/attribute chain on a name: the result type is not
            // the name's own type, so only a bare name resolves here.
            return safe(value.getOps()).isEmpty() ? lookupType(id.getName()) : "unknown";
        }
        return "unknown";
    }

    private String lookupType(String name) {
        if (name == null) return "unknown";
        for (Map<String,String> scope : typeScopes) {
            if (scope.containsKey(name)) return scope.get(name);
        }
        return "unknown";
    }

    private boolean isVoidFunction(String name) {
        return Set.of("print", "setup", "init", "configure").contains(name)
                || name.startsWith("save_");
    }

    private void openScope() {
        assignedScopes.push(new LinkedHashSet<>());
        typeScopes.push(new LinkedHashMap<>());
    }

    private void closeScope() {
        assignedScopes.pop();
        typeScopes.pop();
    }

    private int line(PyProgram node) {
        return node == null ? -1 : node.getLineNumber();
    }

    private FuncDefStatement functionOf(Statement statement) {
        if (statement instanceof FuncDefStatement function) return function;
        if (statement instanceof RouteStatement route) return route.getFuncDef();
        return null;
    }

    private void add(String filePath, int line, String variable, String message) {
        String key = filePath + "|" + line + "|" + message;
        if (reported.add(key)) findings.add(new SemanticError(filePath, line, variable, message));
    }

    private static <T> List<T> safe(List<T> values) {
        return values == null ? List.of() : values;
    }
}
