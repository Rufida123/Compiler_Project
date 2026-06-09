package semantic;

import PyClasses.Arg;
import PyClasses.ArgList;
import PyClasses.AssignStmt;
import PyClasses.AttrPostfix;
import PyClasses.BinaryExpr;
import PyClasses.CallPostfix;
import PyClasses.CondExpr;
import PyClasses.DictEntry;
import PyClasses.DictLiteral;
import PyClasses.DictLiteralExpr;
import PyClasses.Expression;
import PyClasses.ExprStmt;
import PyClasses.ForStatement;
import PyClasses.FuncDefStatement;
import PyClasses.GeneratorExpr;
import PyClasses.GeneratorPrimaryExpr;
import PyClasses.HtmlFileExpr;
import PyClasses.IdentifierExpr;
import PyClasses.IdExpr;
import PyClasses.IfStatement;
import PyClasses.ImportItem;
import PyClasses.ImportList;
import PyClasses.ImportStmt;
import PyClasses.IndentedSuite;
import PyClasses.ListLiteral;
import PyClasses.ListLiteralExpr;
import PyClasses.OrPassExpr;
import PyClasses.ParenExpr;
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
import PyClasses.UnaryMinusExpr;
import PyClasses.UnaryPostfixExpr;
import jinjaClasses.Access;
import jinjaClasses.AccessExpr;
import jinjaClasses.CallKwArgs;
import jinjaClasses.CallMixedArgs;
import jinjaClasses.ControlBlock;
import jinjaClasses.DocumentElement;
import jinjaClasses.EndFor;
import jinjaClasses.For;
import jinjaClasses.FunctionCall;
import jinjaClasses.HtmlAttribute;
import jinjaClasses.If;
import jinjaClasses.IndexAccess;
import jinjaClasses.JinjaArg;
import jinjaClasses.JinjaAttribute;
import jinjaClasses.JinjaBlock;
import jinjaClasses.JinjaCallArgs;
import jinjaClasses.JinjaExpression;
import jinjaClasses.JinjaFilter;
import jinjaClasses.JinjaIdentifierChain;
import jinjaClasses.JinjaKwArg;
import jinjaClasses.JinjaPrimary;
import jinjaClasses.JinjaProgram;
import jinjaClasses.JinjaStatementHeader;
import jinjaClasses.JinjaValueExpr;
import jinjaClasses.NormalAttribute;
import jinjaClasses.PairedTag;
import jinjaClasses.PrintBlock;
import jinjaClasses.SelfClosingTag;

import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzer {
    public static class SemanticError {
        private final String filePath;
        private final int lineNumber;
        private final String variableName;
        private final String message;

        public SemanticError(String filePath, int lineNumber, String variableName, String message) {
            this.filePath = filePath;
            this.lineNumber = lineNumber;
            this.variableName = variableName;
            this.message = message;
        }

        public String getFilePath() {
            return filePath;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getVariableName() {
            return variableName;
        }

        public String getMessage() {
            return message;
        }

        public String format() {
            if (lineNumber > 0) {
                return "Semantic Error in " + filePath + " at line " + lineNumber + ": " + message;
            }
            return "Semantic Error in " + filePath + ": " + message;
        }
    }

    private final List<SemanticError> errors = new ArrayList<>();
    private final Set<String> reportedErrors = new LinkedHashSet<>();
    private final Map<String, Set<String>> templateContexts = new LinkedHashMap<>();
    private final Deque<Set<String>> scopes = new ArrayDeque<>();

    private static final Set<String> PYTHON_BUILTINS = new LinkedHashSet<>(Arrays.asList(
            "__name__", "next", "range", "len", "float", "int", "str", "list", "dict", "print"
    ));

    private static final Set<String> PYTHON_RESERVED_WORDS = new LinkedHashSet<>(Arrays.asList(
            "def", "return", "if", "else", "elif", "for", "in", "import", "from", "as",
            "pass", "None", "True", "False", "and", "or", "not", "is", "del", "class",
            "while", "break", "continue", "try", "except", "finally", "with", "lambda",
            "global", "nonlocal", "yield"
    ));

    private static final Set<String> JINJA_BUILTINS = new LinkedHashSet<>(Arrays.asList(
            "url_for", "range", "loop", "request", "session", "config", "g"
    ));

    public void analyze(PyProgram pyProgram, JinjaProgram jinjaProgram, String templateFileName) {
        analyze(pyProgram, jinjaProgram, "app.py", templateFileName);
    }

    public void analyze(PyProgram pyProgram, JinjaProgram jinjaProgram,
                        String pythonFileName, String templateFileName) {
        analyzePython(pyProgram, pythonFileName);
        analyzeJinja(jinjaProgram, templateFileName);
    }

    public void analyzePython(PyProgram program, String pythonFilePath) {
        errors.clear();
        reportedErrors.clear();
        templateContexts.clear();
        scopes.clear();
        openScope();
        defineAll(PYTHON_BUILTINS);
        String displayPythonPath = displayPath(pythonFilePath);

        if (program != null) {
            for (Statement statement : safeList(program.getStatements())) {
                analyzePythonStatement(statement, displayPythonPath);
            }
        }

        closeScope();
    }

    public void analyzeJinja(JinjaProgram program, String templateFilePath) {
        scopes.clear();
        openScope();
        defineAll(JINJA_BUILTINS);

        String displayTemplatePath = displayPath(templateFilePath);
        String normalizedTemplateName = normalizeTemplateName(templateFilePath);
        defineAll(templateContexts.get(normalizedTemplateName));

        if (program != null) {
            analyzeJinjaElements(program.getHtmlElements(), displayTemplatePath);
        }

        while (scopes.size() > 1) {
            closeScope();
        }
        closeScope();
    }

    public List<SemanticError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public Map<String, Set<String>> getTemplateContexts() {
        return Collections.unmodifiableMap(templateContexts);
    }

    public void printErrors() {
        if (errors.isEmpty()) {
            System.out.println("No semantic errors found.");
            return;
        }

        for (SemanticError error : errors) {
            System.out.println(error.format());
        }
    }

    private void analyzePythonStatement(Statement statement, String pythonFilePath) {
        if (statement == null) return;

        if (statement instanceof ImportStmt importStmt) {
            defineImports(importStmt);
        } else if (statement instanceof AssignStmt assignStmt) {
            analyzePythonExpression(assignStmt.getValue(), pythonFilePath);
            define(assignStmt.getName());
        } else if (statement instanceof FuncDefStatement funcDef) {
            define(funcDef.getName());
            openScope();
            defineAll(funcDef.getParams());
            analyzeSuite(funcDef.getBody(), pythonFilePath);
            closeScope();
        } else if (statement instanceof RouteStatement routeStatement) {
            analyzePythonStatement(routeStatement.getFuncDef(), pythonFilePath);
        } else if (statement instanceof ForStatement forStatement) {
            analyzePythonExpression(forStatement.getExpression(), pythonFilePath);
            openScope();
            define(forStatement.getVarName());
            analyzeSuite(forStatement.getForBlock(), pythonFilePath);
            closeScope();
        } else if (statement instanceof IfStatement ifStatement) {
            analyzePythonExpression(ifStatement.getCondition(), pythonFilePath);
            analyzeSuite(ifStatement.getThenSuite(), pythonFilePath);
            analyzeSuite(ifStatement.getElseSuite(), pythonFilePath);
        } else if (statement instanceof ReturnStmt returnStmt) {
            for (Expression expression : safeList(returnStmt.getReturnArgs())) {
                analyzePythonExpression(expression, pythonFilePath);
            }
        } else if (statement instanceof ExprStmt exprStmt) {
            analyzePythonExpression(exprStmt.getExpr(), pythonFilePath);
        }
    }

    private void defineImports(ImportStmt importStmt) {
        ImportList importList = importStmt.getImportList();
        if (importList == null) return;

        for (ImportItem item : safeList(importList.getItems())) {
            String definedName = item.getAlias() != null ? item.getAlias() : item.getName();
            define(definedName);
        }
    }

    private void analyzeSuite(Suite suite, String pythonFilePath) {
        if (suite == null) return;

        if (suite instanceof IndentedSuite indentedSuite) {
            for (Statement statement : safeList(indentedSuite.getStatements())) {
                analyzePythonStatement(statement, pythonFilePath);
            }
        } else if (suite instanceof SimpleSuite simpleSuite) {
            analyzePythonStatement(simpleSuite.getStatement(), pythonFilePath);
        }
    }

    private void analyzePythonExpression(Expression expression, String pythonFilePath) {
        if (expression == null) return;

        if (expression instanceof CondExpr condExpr) {
            analyzePythonExpression(condExpr.getThenExpr(), pythonFilePath);
            analyzePythonExpression(condExpr.getCondition(), pythonFilePath);
            analyzePythonExpression(condExpr.getElseExpr(), pythonFilePath);
        } else if (expression instanceof OrPassExpr orPassExpr) {
            analyzePythonExpression(orPassExpr.getInner(), pythonFilePath);
        } else if (expression instanceof BinaryExpr binaryExpr) {
            analyzePythonExpression(binaryExpr.getLeft(), pythonFilePath);
            analyzePythonExpression(binaryExpr.getRight(), pythonFilePath);
        } else if (expression instanceof UnaryMinusExpr unaryMinusExpr) {
            analyzePythonExpression(unaryMinusExpr.getExpr(), pythonFilePath);
        } else if (expression instanceof UnaryPostfixExpr unaryPostfixExpr) {
            analyzePostfixExpression(unaryPostfixExpr.getExpr(), pythonFilePath);
        } else if (expression instanceof PostfixExpr postfixExpr) {
            analyzePostfixExpression(postfixExpr, pythonFilePath);
        } else if (expression instanceof IdExpr idExpr) {
            usePythonVariable(idExpr.getName(), idExpr.getLineNumber(), pythonFilePath);
        }
    }

    private void analyzePostfixExpression(PostfixExpr postfixExpr, String pythonFilePath) {
        if (postfixExpr == null) return;

        collectRenderTemplateContext(postfixExpr);
        analyzePrimaryExpression(postfixExpr.getPrimary(), pythonFilePath);

        for (PostfixOp op : safeList(postfixExpr.getOps())) {
            if (op instanceof CallPostfix callPostfix) {
                analyzeArgList(callPostfix.getArgList(), pythonFilePath);
            } else if (op instanceof SubscriptPostfix subscriptPostfix) {
                analyzePythonExpression(subscriptPostfix.getIndex(), pythonFilePath);
            } else if (op instanceof AttrPostfix) {
                // Attribute names are properties/methods, not variables in the current scope.
            }
        }
    }

    private void analyzePrimaryExpression(PrimaryExpr primary, String pythonFilePath) {
        if (primary == null) return;

        if (primary instanceof IdentifierExpr identifierExpr) {
            usePythonVariable(identifierExpr.getName(), identifierExpr.getLineNumber(), pythonFilePath);
        } else if (primary instanceof ParenExpr parenExpr) {
            analyzePythonExpression(parenExpr.getInner(), pythonFilePath);
        } else if (primary instanceof ListLiteralExpr listLiteralExpr) {
            analyzeListLiteral(listLiteralExpr.getListLiteral(), pythonFilePath);
        } else if (primary instanceof DictLiteralExpr dictLiteralExpr) {
            analyzeDictLiteral(dictLiteralExpr.getDictLiteral(), pythonFilePath);
        } else if (primary instanceof GeneratorPrimaryExpr generatorPrimaryExpr) {
            analyzeGenerator(generatorPrimaryExpr.getGeneratorExpr(), pythonFilePath);
        }
    }

    private void analyzeListLiteral(ListLiteral listLiteral, String pythonFilePath) {
        if (listLiteral == null) return;
        for (Expression element : safeList(listLiteral.getElements())) {
            analyzePythonExpression(element, pythonFilePath);
        }
    }

    private void analyzeDictLiteral(DictLiteral dictLiteral, String pythonFilePath) {
        if (dictLiteral == null) return;
        for (DictEntry entry : safeList(dictLiteral.getEntries())) {
            analyzePythonExpression(entry.getKey(), pythonFilePath);
            analyzePythonExpression(entry.getValue(), pythonFilePath);
        }
    }

    private void analyzeGenerator(GeneratorExpr generatorExpr, String pythonFilePath) {
        if (generatorExpr == null) return;

        analyzePythonExpression(generatorExpr.getIterable(), pythonFilePath);
        openScope();
        define(generatorExpr.getLoopVarName());
        usePythonVariable(generatorExpr.getYieldName(), generatorExpr.getLineNumber(), pythonFilePath);
        analyzePythonExpression(generatorExpr.getFilter(), pythonFilePath);
        closeScope();
    }

    private void analyzeArgList(ArgList argList, String pythonFilePath) {
        if (argList == null) return;
        for (Arg arg : safeList(argList.getArgs())) {
            analyzePythonExpression(arg.getValue(), pythonFilePath);
        }
    }

    private void collectRenderTemplateContext(PostfixExpr postfixExpr) {
        if (!(postfixExpr.getPrimary() instanceof IdentifierExpr identifierExpr)) return;
        if (!"render_template".equals(identifierExpr.getName())) return;

        CallPostfix call = firstCallPostfix(postfixExpr);
        if (call == null || call.getArgList() == null) return;

        List<Arg> args = call.getArgList().getArgs();
        if (args.isEmpty()) return;

        String templateName = extractStringValue(args.get(0).getValue());
        if (templateName == null) return;

        Set<String> context = templateContexts.computeIfAbsent(normalizeTemplateName(templateName),
                key -> new LinkedHashSet<>());
        for (int i = 1; i < args.size(); i++) {
            Arg arg = args.get(i);
            if (arg.getName() != null) {
                context.add(arg.getName());
            }
        }
    }

    private CallPostfix firstCallPostfix(PostfixExpr postfixExpr) {
        for (PostfixOp op : safeList(postfixExpr.getOps())) {
            if (op instanceof CallPostfix callPostfix) {
                return callPostfix;
            }
        }
        return null;
    }

    private String extractStringValue(Expression expression) {
        PrimaryExpr primary = extractSimplePrimary(expression);
        if (primary instanceof HtmlFileExpr htmlFileExpr) {
            return htmlFileExpr.getValue();
        }
        if (primary instanceof StringExpr stringExpr) {
            return stringExpr.getValue();
        }
        return null;
    }

    private PrimaryExpr extractSimplePrimary(Expression expression) {
        if (expression instanceof CondExpr condExpr && condExpr.getCondition() == null) {
            return extractSimplePrimary(condExpr.getThenExpr());
        }
        if (expression instanceof OrPassExpr orPassExpr) {
            return extractSimplePrimary(orPassExpr.getInner());
        }
        if (expression instanceof UnaryPostfixExpr unaryPostfixExpr) {
            return extractSimplePrimary(unaryPostfixExpr.getExpr());
        }
        if (expression instanceof PostfixExpr postfixExpr && postfixExpr.getOps().isEmpty()) {
            return postfixExpr.getPrimary();
        }
        return null;
    }

    private void usePythonVariable(String name, int lineNumber, String pythonFilePath) {
        if (name != null && !PYTHON_RESERVED_WORDS.contains(name) && !isDefined(name)) {
            report(pythonFilePath, lineNumber, name, "Undefined variable '" + name + "'");
        }
    }

    private void analyzeJinjaElements(List<DocumentElement> elements, String templateFilePath) {
        for (DocumentElement element : safeList(elements)) {
            analyzeJinjaElement(element, templateFilePath);
        }
    }

    private void analyzeJinjaElement(DocumentElement element, String templateFilePath) {
        if (element instanceof PairedTag pairedTag) {
            analyzeAttributes(pairedTag.getAttributes(), templateFilePath);
            analyzeJinjaElements(pairedTag.getChildren(), templateFilePath);
        } else if (element instanceof SelfClosingTag selfClosingTag) {
            analyzeAttributes(selfClosingTag.getAttributes(), templateFilePath);
        } else if (element instanceof JinjaBlock jinjaBlock) {
            analyzeJinjaBlock(jinjaBlock, templateFilePath);
        }
    }

    private void analyzeAttributes(List<HtmlAttribute> attributes, String templateFilePath) {
        for (HtmlAttribute attribute : safeList(attributes)) {
            if (attribute instanceof JinjaAttribute jinjaAttribute) {
                analyzeJinjaBlock(jinjaAttribute.getJinjaBlock(), templateFilePath);
            } else if (attribute instanceof NormalAttribute normalAttribute
                    && normalAttribute.getValue() instanceof JinjaValueExpr jinjaValueExpr) {
                analyzeJinjaBlock(jinjaValueExpr.getJinjaBlock(), templateFilePath);
            }
        }
    }

    private void analyzeJinjaBlock(JinjaBlock block, String templateFilePath) {
        if (block instanceof PrintBlock printBlock) {
            analyzeJinjaExpression(printBlock.getJinjaExpression(), printBlock.getLine(), templateFilePath);
        } else if (block instanceof ControlBlock controlBlock) {
            analyzeJinjaHeader(controlBlock.getJinjaStatementHeader(), controlBlock.getLine(), templateFilePath);
        }
    }

    private void analyzeJinjaHeader(JinjaStatementHeader header, int lineNumber, String templateFilePath) {
        if (header instanceof If ifHeader) {
            analyzeJinjaExpression(ifHeader.getExpression(), lineNumber, templateFilePath);
        } else if (header instanceof For forHeader) {
            analyzeJinjaExpression(forHeader.getExpression(), lineNumber, templateFilePath);
            openScope();
            define(forHeader.getIdentifier());
        } else if (header instanceof EndFor) {
            if (scopes.size() > 1) {
                closeScope();
            }
        }
    }

    private void analyzeJinjaExpression(JinjaExpression expression, int lineNumber, String templateFilePath) {
        if (expression == null) return;

        analyzeJinjaPrimary(expression.getPrimary(), lineNumber, templateFilePath);
        for (JinjaFilter filter : safeList(expression.getFilters())) {
            analyzeJinjaCallArgs(filter.getArgs(), lineNumber, templateFilePath);
        }
    }

    private void analyzeJinjaPrimary(JinjaPrimary primary, int lineNumber, String templateFilePath) {
        if (primary instanceof AccessExpr accessExpr) {
            JinjaIdentifierChain chain = accessExpr.getChain();
            if (chain == null) return;

            useJinjaVariable(chain.getIdentifier(), lineNumber, templateFilePath);
            for (Access access : safeList(chain.getAccesses())) {
                if (access instanceof IndexAccess indexAccess) {
                    analyzeJinjaExpression(indexAccess.getExpression(), lineNumber, templateFilePath);
                }
            }
        } else if (primary instanceof FunctionCall functionCall) {
            useJinjaVariable(functionCall.getIdentifier(), lineNumber, templateFilePath);
            analyzeJinjaCallArgs(functionCall.getCallArgs(), lineNumber, templateFilePath);
        }
    }

    private void analyzeJinjaCallArgs(JinjaCallArgs callArgs, int lineNumber, String templateFilePath) {
        if (callArgs instanceof CallMixedArgs mixedArgs) {
            for (JinjaArg arg : safeList(mixedArgs.getPosArgs())) {
                analyzeJinjaExpression(arg.getExpression(), lineNumber, templateFilePath);
            }
            for (JinjaKwArg kwArg : safeList(mixedArgs.getKwArgs())) {
                analyzeJinjaExpression(kwArg.getExpression(), lineNumber, templateFilePath);
            }
        } else if (callArgs instanceof CallKwArgs kwArgs) {
            for (JinjaKwArg kwArg : safeList(kwArgs.getKwArgs())) {
                analyzeJinjaExpression(kwArg.getExpression(), lineNumber, templateFilePath);
            }
        }
    }

    private void useJinjaVariable(String name, int lineNumber, String templateFilePath) {
        if (name != null && !isDefined(name)) {
            report(templateFilePath, lineNumber, name, "Undefined variable '" + name + "'");
        }
    }

    private void openScope() {
        scopes.push(new LinkedHashSet<>());
    }

    private void closeScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    private void define(String name) {
        if (name != null && !scopes.isEmpty()) {
            scopes.peek().add(name);
        }
    }

    private void defineAll(Iterable<String> names) {
        if (names == null) return;
        for (String name : names) {
            define(name);
        }
    }

    private boolean isDefined(String name) {
        for (Set<String> scope : scopes) {
            if (scope.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private void report(String filePath, int lineNumber, String variableName, String message) {
        SemanticError error = new SemanticError(filePath, lineNumber, variableName, message);
        String key = error.format();
        if (reportedErrors.add(key)) {
            errors.add(error);
        }
    }

    private String normalizeTemplateName(String templateName) {
        if (templateName == null) return "";
        String normalized = templateName.replace("\\", "/");
        int slashIndex = normalized.lastIndexOf('/');
        if (slashIndex >= 0) {
            normalized = normalized.substring(slashIndex + 1);
        }
        try {
            return Path.of(normalized).getFileName().toString();
        } catch (Exception ignored) {
            return normalized;
        }
    }

    private String displayPath(String filePath) {
        if (filePath == null || filePath.isEmpty()) return "";
        String normalized = filePath.replace("\\", "/");
        while (normalized.startsWith("./")) {
            normalized = normalized.substring(2);
        }
        return normalized;
    }

    private <T> List<T> safeList(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }
}