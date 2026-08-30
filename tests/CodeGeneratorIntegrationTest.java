import PyClasses.*;
import codegen.CodeGenerator;
import jinjaClasses.JinjaProgram;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Lightweight integration test runnable without a third-party test framework. */
public final class CodeGeneratorIntegrationTest {
    public static void main(String[] args) throws Exception {
        Path root = Files.createTempDirectory("codegen-test-");
        Path sourceTemplate = root.resolve("source.html");
        Path output = root.resolve("generated_app");
        Files.writeString(sourceTemplate, "<h1>{{ products }}</h1>");

        PyProgram program = new PyProgram() { };
        program.addStatement(flaskImport());
        program.addStatement(assign("app", call("Flask", id("__name__"))));
        program.addStatement(assign("products", list(dict("id", integer(1), "name", string("One")))));
        program.addStatement(route());

        new CodeGenerator(output.toString()).generate(program, Map.of(sourceTemplate.toString(), new JinjaProgram()));
        String app = Files.readString(output.resolve("app.py"));

        require(app.contains("@app.route('/product/<int:product_id>', methods=['GET', 'POST'])"), "dynamic route/methods");
        require(app.contains("product = {'id': product_id, 'name': 'One'}"), "dictionary assignment");
        require(app.contains("for item in products:"), "for loop");
        require(app.contains("if (product_id == 1):"), "if statement");
        require(app.contains("return redirect(url_for('product_detail', product_id=product_id))"), "nested calls");
        require(app.contains("return render_template('source.html', products=products)"), "render_template keyword argument");
        require(Files.exists(output.resolve("templates/source.html")), "template copy");
        require("Flask==2.3.0\nJinja2==3.1.2\nWerkzeug==2.3.0\n".equals(Files.readString(output.resolve("requirements.txt"))), "legacy direct requirements");
        new CodeGenerator(output.toString()).generate(program, Map.of(sourceTemplate.toString(), new JinjaProgram()));
        require("Flask==2.3.0\nJinja2==3.1.2\nWerkzeug==2.3.0\n".equals(Files.readString(output.resolve("requirements.txt"))), "regenerated requirement");
        System.out.println("CodeGeneratorIntegrationTest passed: " + output);
    }

    private static RouteStatement route() {
        RouteStatement route = new RouteStatement();
        RoutePath path = new RoutePath(); path.setPath("/product/<int:product_id>"); route.setRoutePath(path);
        RouteParams params = new RouteParams();
        ListLiteral methods = new ListLiteral(); methods.addElement(string("GET")); methods.addElement(string("POST"));
        params.setMethodsList(methods); route.setRouteParams(params);

        FuncDefStatement function = new FuncDefStatement(); function.setName("product_detail"); function.setParams(new ArrayList<>(List.of("product_id")));
        IndentedSuite body = new IndentedSuite();
        body.addStatement(assign("product", dict("id", id("product_id"), "name", string("One"))));
        IndentedSuite loopBody = new IndentedSuite(); loopBody.addStatement(assign("last_item", id("item")));
        ForStatement loop = new ForStatement(); loop.setVarName("item"); loop.setExpression(id("products")); loop.setForBlock(loopBody); body.addStatement(loop);
        IfStatement conditional = new IfStatement(); conditional.setCondition(binary(id("product_id"), "==", integer(1)));
        IndentedSuite thenBody = new IndentedSuite();
        Expression target = call("url_for", arg(string("product_detail")), named("product_id", id("product_id")));
        thenBody.addStatement(returning(call("redirect", arg(target))));
        conditional.setThenSuite(thenBody);
        IndentedSuite elseBody = new IndentedSuite(); elseBody.addStatement(returning(call("render_template", arg(string("source.html")), named("products", id("products"))))); conditional.setElseSuite(elseBody);
        body.addStatement(conditional); function.setBody(body); route.setFuncDef(function);
        return route;
    }

    private static ImportStmt flaskImport() {
        ImportStmt statement = new ImportStmt(); DottedName module = new DottedName(); module.addPart("flask"); statement.setDottedName(module);
        ImportList imports = new ImportList(); for (String name : List.of("Flask", "render_template", "redirect", "url_for")) { ImportItem item = new ImportItem(); item.setName(name); imports.addItem(item); } statement.setImportList(imports); return statement;
    }
    private static AssignStmt assign(String name, Expression value) { AssignStmt s = new AssignStmt(); s.setName(name); s.setValue(value); return s; }
    private static ReturnStmt returning(Expression expression) { ReturnStmt s = new ReturnStmt(); s.addReturnArg(expression); return s; }
    private static Arg arg(Expression value) { Arg a = new Arg(); a.setValue(value); return a; }
    private static Arg named(String name, Expression value) { Arg a = arg(value); a.setName(name); return a; }
    private static Expression call(String name, Arg... args) { PostfixExpr p = new PostfixExpr(); p.setPrimary(id(name)); CallPostfix call = new CallPostfix(); ArgList list = new ArgList(); for (Arg arg : args) list.addArg(arg); call.setArgList(list); p.addOp(call); return p; }
    private static Expression call(String name, Expression expression) { return call(name, arg(expression)); }
    private static IdentifierExpr id(String name) { IdentifierExpr e = new IdentifierExpr(); e.setName(name); return e; }
    private static StringExpr string(String value) { StringExpr e = new StringExpr(); e.setValue(value); return e; }
    private static IntExpr integer(long value) { IntExpr e = new IntExpr(); e.setValue(value); return e; }
    private static BinaryExpr binary(Expression left, String op, Expression right) { BinaryExpr e = new BinaryExpr(); e.setLeft(left); e.setOp(op); e.setRight(right); return e; }
    private static ListLiteralExpr list(Expression... values) { ListLiteral l = new ListLiteral(); for (Expression value : values) l.addElement(value); ListLiteralExpr e = new ListLiteralExpr(); e.setListLiteral(l); return e; }
    private static DictLiteralExpr dict(String key1, Expression value1, String key2, Expression value2) { DictLiteral d = new DictLiteral(); d.addEntry(entry(key1, value1)); d.addEntry(entry(key2, value2)); DictLiteralExpr e = new DictLiteralExpr(); e.setDictLiteral(d); return e; }
    private static DictEntry entry(String key, Expression value) { DictEntry e = new DictEntry(); e.setKey(string(key)); e.setValue(value); return e; }
    private static void require(boolean condition, String name) { if (!condition) throw new AssertionError("Missing generated " + name); }
}
