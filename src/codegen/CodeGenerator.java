package codegen;

import PyClasses.*;
import jinjaClasses.JinjaProgram;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/** Generates a runnable Flask application from the compiler AST. */
public class CodeGenerator {
    public static class CodeGenerationException extends RuntimeException {
        public CodeGenerationException(String message) { super(message); }
        public CodeGenerationException(String message, Throwable cause) { super(message, cause); }
    }

    private final Path outputDir;
    private final StringBuilder flaskAppCode = new StringBuilder();
    private boolean hasAppInitialization;
    private boolean hasAppRun;

    public CodeGenerator(String outputDir) { this.outputDir = Paths.get(outputDir).toAbsolutePath().normalize(); }

    /** Recreates generated_app; Jinja source files are copied rather than rendered from their AST. */
    public void generate(PyProgram pyProgram, Map<String, JinjaProgram> jinjaTemplates) {
        if (pyProgram == null) throw unsupported("Python program AST is null");
        try {
            recreateOutputDirectory();
            flaskAppCode.setLength(0);
            hasAppInitialization = false;
            hasAppRun = false;
            for (Statement statement : safeList(pyProgram.getStatements())) generateStatement(statement, 0);
            if (!hasAppInitialization) throw unsupported("No Flask initialization found. Expected app = Flask(__name__)");
            if (!hasAppRun) generateDefaultEntryPoint();
            copyTemplates(jinjaTemplates);
            writeAppFile();
            writeConfigFile();
            writeRequirementsFile();
            System.out.println("Flask app generated successfully in " + outputDir);
        } catch (IOException e) {
            throw new CodeGenerationException("Could not generate Flask application in " + outputDir, e);
        }
    }

    private void recreateOutputDirectory() throws IOException {
        if (Files.exists(outputDir)) {
            try (Stream<Path> paths = Files.walk(outputDir)) {
                paths.sorted((a, b) -> b.getNameCount() - a.getNameCount()).forEach(path -> {
                    try { Files.delete(path); }
                    catch (IOException e) { throw new CodeGenerationException("Could not clean " + path, e); }
                });
            }
        }
        Files.createDirectories(outputDir.resolve("templates"));
    }

    private void generateStatement(Statement statement, int level) {
        if (statement == null) throw unsupported("null statement");
        if (statement instanceof ImportStmt node) generateImport(node, level);
        else if (statement instanceof AssignStmt node) generateAssignment(node, level);
        else if (statement instanceof RouteStatement node) { generateDecorator(node, level); generateFunction(node.getFuncDef(), level); }
        else if (statement instanceof FuncDefStatement node) generateFunction(node, level);
        else if (statement instanceof ReturnStmt node) line(level, "return" + (safeList(node.getReturnArgs()).isEmpty() ? "" : " " + joinExpressions(node.getReturnArgs())));
        else if (statement instanceof ExprStmt node) { line(level, generateExpression(node.getExpr())); if (isAppRun(node.getExpr())) hasAppRun = true; }
        else if (statement instanceof IfStatement node) generateIf(node, level);
        else if (statement instanceof ForStatement node) { line(level, "for " + identifier(node.getVarName()) + " in " + generateExpression(node.getExpression()) + ":"); generateBlock(node.getForBlock(), level + 1); }
        else throw unsupported("statement " + statement.getClass().getSimpleName());
    }

    private void generateImport(ImportStmt node, int level) {
        List<ImportItem> items = node.getImportList() == null ? Collections.emptyList() : node.getImportList().getItems();
        if (items.isEmpty()) throw unsupported("import without names");
        StringBuilder names = new StringBuilder();
        for (ImportItem item : items) {
            if (names.length() > 0) names.append(", ");
            names.append(identifier(item.getName()));
            if (item.getAlias() != null && !item.getAlias().isBlank()) names.append(" as ").append(identifier(item.getAlias()));
        }
        if (node.getDottedName() != null && !safeList(node.getDottedName().getParts()).isEmpty()) line(level, "from " + String.join(".", node.getDottedName().getParts()) + " import " + names);
        else line(level, "import " + names);
    }

    private void generateAssignment(AssignStmt node, int level) {
        String name = identifier(node.getName());
        line(level, name + " = " + generateExpression(node.getValue()));
        if ("app".equals(name) && isFlaskInitialization(node.getValue())) hasAppInitialization = true;
    }

    private void generateDecorator(RouteStatement route, int level) {
        if (route.getRoutePath() == null || route.getRoutePath().getPath() == null) throw unsupported("route without a path");
        String decorator = "@app.route(" + quote(route.getRoutePath().getPath());
        if (route.getRouteParams() != null && route.getRouteParams().getMethodsList() != null) decorator += ", methods=" + generateList(route.getRouteParams().getMethodsList());
        line(level, decorator + ")");
    }

    private void generateFunction(FuncDefStatement function, int level) {
        if (function == null) throw unsupported("null function definition");
        line(level, "def " + identifier(function.getName()) + "(" + String.join(", ", safeList(function.getParams())) + "):");
        generateBlock(function.getBody(), level + 1);
        flaskAppCode.append('\n');
    }

    private void generateIf(IfStatement node, int level) {
        line(level, "if " + generateExpression(node.getCondition()) + ":");
        generateBlock(node.getThenSuite(), level + 1);
        if (node.getElseSuite() != null) { line(level, "else:"); generateBlock(node.getElseSuite(), level + 1); }
    }

    private void generateBlock(Suite suite, int level) {
        if (suite instanceof IndentedSuite node) {
            List<Statement> statements = safeList(node.getStatements());
            if (statements.isEmpty()) line(level, "pass"); else for (Statement statement : statements) generateStatement(statement, level);
        } else if (suite instanceof SimpleSuite node) {
            if (node.getStatement() == null) line(level, "pass"); else generateStatement(node.getStatement(), level);
        } else if (suite == null) line(level, "pass");
        else throw unsupported("suite " + suite.getClass().getSimpleName());
    }

    private String generateExpression(Expression expression) {
        if (expression == null) throw unsupported("null expression");
        if (expression instanceof CondExpr node) return node.getCondition() == null ? generateExpression(node.getThenExpr()) : "(" + generateExpression(node.getThenExpr()) + " if " + generateExpression(node.getCondition()) + " else " + generateExpression(node.getElseExpr()) + ")";
        if (expression instanceof OrPassExpr node) return generateExpression(node.getInner());
        if (expression instanceof BinaryExpr node) return "(" + generateExpression(node.getLeft()) + " " + node.getOp() + " " + generateExpression(node.getRight()) + ")";
        if (expression instanceof UnaryMinusExpr node) return "(-" + generateExpression(node.getExpr()) + ")";
        if (expression instanceof UnaryPostfixExpr node) return generatePostfix(node.getExpr());
        if (expression instanceof PostfixExpr node) return generatePostfix(node);
        if (expression instanceof IdentifierExpr node) return identifier(node.getName());
        if (expression instanceof IdExpr node) return identifier(node.getName());
        if (expression instanceof StringExpr node) return quote(node.getValue());
        if (expression instanceof HtmlFileExpr node) return quote(node.getValue());
        if (expression instanceof IntExpr node) return Long.toString(node.getValue());
        if (expression instanceof FloatExpr node) return Double.toString(node.getValue());
        if (expression instanceof TrueExpr) return "True";
        if (expression instanceof FalseExpr) return "False";
        if (expression instanceof NoneExpr) return "None";
        if (expression instanceof ListLiteralExpr node) return generateList(node.getListLiteral());
        if (expression instanceof DictLiteralExpr node) return generateDict(node.getDictLiteral());
        if (expression instanceof GeneratorPrimaryExpr node) return generateGenerator(node.getGeneratorExpr());
        if (expression instanceof ParenExpr node) return "(" + generateExpression(node.getInner()) + ")";
        if (expression instanceof AttrExpr node) return generateExpression(node.getBase()) + "." + identifier(node.getName());
        if (expression instanceof SubscriptExpr node) return generateExpression(node.getBase()) + "[" + generateExpression(node.getIndex()) + "]";
        if (expression instanceof CallExpr node) return generateExpression(node.getCallee()) + "(" + generateArguments(node.getArgs()) + ")";
        throw unsupported("expression " + expression.getClass().getSimpleName());
    }

    private String generatePostfix(PostfixExpr expression) {
        if (expression == null || expression.getPrimary() == null) throw unsupported("postfix expression without primary");
        StringBuilder result = new StringBuilder(generateExpression(expression.getPrimary()));
        for (PostfixOp op : safeList(expression.getOps())) {
            if (op instanceof CallPostfix call) result.append('(').append(call.getArgList() == null ? "" : generateArguments(call.getArgList().getArgs())).append(')');
            else if (op instanceof SubscriptPostfix subscript) result.append('[').append(generateExpression(subscript.getIndex())).append(']');
            else if (op instanceof AttrPostfix attribute) result.append('.').append(identifier(attribute.getName()));
            else throw unsupported("postfix operation " + op.getClass().getSimpleName());
        }
        return result.toString();
    }

    private String generateArguments(List<Arg> arguments) {
        StringBuilder result = new StringBuilder();
        for (Arg argument : safeList(arguments)) {
            if (result.length() > 0) result.append(", ");
            if (argument.getName() != null) result.append(identifier(argument.getName())).append('=');
            result.append(generateExpression(argument.getValue()));
        }
        return result.toString();
    }

    private String generateList(ListLiteral list) { if (list == null) throw unsupported("null list literal"); return "[" + joinExpressions(list.getElements()) + "]"; }
    private String generateDict(DictLiteral dict) {
        if (dict == null) throw unsupported("null dictionary literal");
        StringBuilder result = new StringBuilder("{");
        for (DictEntry entry : safeList(dict.getEntries())) { if (result.length() > 1) result.append(", "); result.append(generateExpression(entry.getKey())).append(": ").append(generateExpression(entry.getValue())); }
        return result.append('}').toString();
    }
    private String generateGenerator(GeneratorExpr generator) {
        if (generator == null) throw unsupported("null generator expression");
        String result = "(" + identifier(generator.getYieldName()) + " for " + identifier(generator.getLoopVarName()) + " in " + generateExpression(generator.getIterable());
        return result + (generator.getFilter() == null ? "" : " if " + generateExpression(generator.getFilter())) + ")";
    }
    private String joinExpressions(List<Expression> expressions) { StringBuilder result = new StringBuilder(); for (Expression expression : safeList(expressions)) { if (result.length() > 0) result.append(", "); result.append(generateExpression(expression)); } return result.toString(); }

    private boolean isFlaskInitialization(Expression value) {
        if (value instanceof CondExpr node && node.getCondition() == null) return isFlaskInitialization(node.getThenExpr());
        if (value instanceof OrPassExpr node) return isFlaskInitialization(node.getInner());
        if (value instanceof UnaryPostfixExpr node) return isFlaskInitialization(node.getExpr());
        return value instanceof PostfixExpr node && node.getPrimary() instanceof IdentifierExpr id && "Flask".equals(id.getName()) && !safeList(node.getOps()).isEmpty() && node.getOps().get(0) instanceof CallPostfix;
    }
    private boolean isAppRun(Expression expression) {
        if (expression instanceof CondExpr node && node.getCondition() == null) return isAppRun(node.getThenExpr());
        if (expression instanceof OrPassExpr node) return isAppRun(node.getInner());
        if (expression instanceof UnaryPostfixExpr node) return isAppRun(node.getExpr());
        if (!(expression instanceof PostfixExpr node) || !(node.getPrimary() instanceof IdentifierExpr id) || !"app".equals(id.getName())) return false;
        List<PostfixOp> ops = safeList(node.getOps());
        return ops.size() >= 2 && ops.get(0) instanceof AttrPostfix attr && "run".equals(attr.getName()) && ops.get(1) instanceof CallPostfix;
    }
    private void generateDefaultEntryPoint() { flaskAppCode.append("\nif __name__ == '__main__':\n    app.run(debug=True)\n"); }

    private void copyTemplates(Map<String, JinjaProgram> templates) throws IOException {
        if (templates == null) return;
        for (String templatePath : templates.keySet()) {
            Path source = Paths.get(templatePath);
            if (!Files.isRegularFile(source)) throw new CodeGenerationException("Template source does not exist: " + source);
            Files.copy(source, outputDir.resolve("templates").resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    private void writeAppFile() throws IOException { Files.writeString(outputDir.resolve("app.py"), flaskAppCode.toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE); }
    private void writeConfigFile() throws IOException { Files.writeString(outputDir.resolve("config.py"), "class Config:\n    SECRET_KEY = 'dev-key'\n    TEMPLATES_AUTO_RELOAD = True\n", StandardCharsets.UTF_8); }
    private void writeRequirementsFile() throws IOException { Files.writeString(outputDir.resolve("requirements.txt"), "Flask==2.3.0\nJinja2==3.1.2\nWerkzeug==2.3.0\n", StandardCharsets.UTF_8); }
    private void line(int level, String text) { flaskAppCode.append("    ".repeat(Math.max(0, level))).append(text).append('\n'); }
    private String identifier(String value) { if (value == null || !value.matches("[A-Za-z_][A-Za-z0-9_]*")) throw unsupported("invalid identifier '" + value + "'"); return value; }
    private String quote(String value) { if (value == null) throw unsupported("null string literal"); return "'" + value.replace("\\", "\\\\").replace("'", "\\'").replace("\n", "\\n").replace("\r", "\\r") + "'"; }
    private <T> List<T> safeList(List<T> values) { return values == null ? Collections.emptyList() : values; }
    private CodeGenerationException unsupported(String detail) { return new CodeGenerationException("Unsupported AST node: " + detail); }
}
