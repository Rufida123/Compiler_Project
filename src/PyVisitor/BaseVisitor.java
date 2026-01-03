 package PyVisitor;

import PyAstClasses.*;
import PySymbolTable.SymbolTable;
import pyAntlr.pyParser;
import pyAntlr.pyParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.List;

public class BaseVisitor extends pyParserBaseVisitor<Object> {
    private final AstPrettyPrinter pp = new AstPrettyPrinter();

    private final SymbolTable symbolTable = new SymbolTable();

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    // ================= Helpers =================

    /*private String symVal(Object v) {
        if (v == null) return "null";
        if (v instanceof Expression e) return pp.formatExpr(e);
        return String.valueOf(v);
    }*/
    private void setLine(Object node, ParserRuleContext ctx) {
        if (node instanceof PyAstClasses.Program) {
            ((PyAstClasses.Program) node).setLineNumber(ctx.getStart().getLine());
        }
    }

    private String stripQuotes(String s) {
        if (s == null) return null;
        if (s.length() >= 2) {
            char a = s.charAt(0);
            char b = s.charAt(s.length() - 1);
            if ((a == '\'' && b == '\'') || (a == '"' && b == '"')) {
                return s.substring(1, s.length() - 1);
            }
        }
        return s;
    }

    // ================= Program =================

    @Override
    public PyAstClasses.Program visitProgram(pyParser.ProgramContext ctx) {
        symbolTable.initGlobal();
        PyAstClasses.Program program = new PyAstClasses.Program() {};
        setLine(program, ctx);

        for (pyParser.StatementContext stCtx : ctx.statement()) {
            Statement st = (Statement) visit(stCtx);
            if (st != null) program.addStatement(st);
        }

        return program;
    }

    // ================= Statements (labeled) =================

    @Override
    public Statement visitImportStatement(pyParser.ImportStatementContext ctx) {
        ImportStmt stmt = (ImportStmt) visit(ctx.import_stmt());
        setLine(stmt, ctx);

        // SymbolTable: store each imported item
        if (stmt.getImportList() != null && stmt.getDottedName() != null) {
            String module = String.join(".", stmt.getDottedName().getParts());
            for (ImportItem it : stmt.getImportList().getItems()) {
                String name = it.getName();
                String alias = it.getAlias();
                String key = (alias != null) ? alias : name;
            }
        }

        return stmt;
    }

    @Override
    public Statement visitAssignmentStatement(pyParser.AssignmentStatementContext ctx) {
        AssignStmt stmt = (AssignStmt) visit(ctx.assignment());
        setLine(stmt, ctx);

        // SymbolTable: var assignment
        symbolTable.addSymbol(stmt.getName(), stmt.getValue(), "Assignment", ctx.getStart().getLine());

        return stmt;
    }

    @Override
    public Statement visitReturnStatement(pyParser.ReturnStatementContext ctx) {
        ReturnStmt stmt = (ReturnStmt) visit(ctx.return_stmt());
        setLine(stmt, ctx);
        //  symbolTable.addSymbol("return " + stmt.getLineNumber(), "return", "ReturnStmt", ctx.getStart().getLine());
        return stmt;
    }

    @Override
    public Statement visitExprStatement(pyParser.ExprStatementContext ctx) {
        ExprStmt stmt = (ExprStmt) visit(ctx.expr_stmt());
        setLine(stmt, ctx);
        //  symbolTable.addSymbol("expr " + stmt.getLineNumber(), stmt.getExpr(), "ExprStmt",ctx.getStart().getLine());
        return stmt;
    }

    @Override
    public Statement visitRouteStatement(pyParser.RouteStatementContext ctx) {
        RouteStatement stmt = (RouteStatement) visit(ctx.route_def());
        setLine(stmt, ctx);

        if (stmt.getRoutePath() != null && stmt.getFuncDef() != null) {

        }

        return stmt;
    }

    @Override
    public Statement visitFuncDefStatement(pyParser.FuncDefStatementContext ctx) {
        FuncDefStatement stmt = (FuncDefStatement) visit(ctx.func_def());
        setLine(stmt, ctx);

        symbolTable.addSymbol(stmt.getName(), "Function", "FuncDef");

        for (String p : stmt.getParams()) {
            symbolTable.addSymbol(stmt.getName() + ":" + p, "param", "Parameter",ctx.getStart().getLine());
        }

        return stmt;
    }

    @Override
    public Statement visitIfStatement(pyParser.IfStatementContext ctx) {
        IfStatement stmt = (IfStatement) visit(ctx.if_stmt());
        setLine(stmt, ctx);
        // symbolTable.addSymbol("if " + stmt.getLineNumber(), "if", "IfStmt");
        return stmt;
    }

    @Override
    public Statement visitForStatement(pyParser.ForStatementContext ctx) {
        ForStatement stmt = (ForStatement) visit(ctx.for_stmt());
        setLine(stmt, ctx);

        if (stmt.getVarName() != null) {
            symbolTable.addSymbol(stmt.getVarName(), "loopVar", "ForVar");
        }

        symbolTable.addSymbol("for@ " + stmt.getLineNumber(), "for", "ForStmt",ctx.getStart().getLine());
        return stmt;
    }

    // ================= import rules =================

    @Override
    public ImportStmt visitImport_stmt(pyParser.Import_stmtContext ctx) {
        ImportStmt node = new ImportStmt();
        setLine(node, ctx);

        DottedName dn = (DottedName) visit(ctx.dotted_name());
        ImportList il = (ImportList) visit(ctx.import_list());

        node.setDottedName(dn);
        node.setImportList(il);

        return node;
    }

    @Override
    public DottedName visitDotted_name(pyParser.Dotted_nameContext ctx) {
        DottedName node = new DottedName();
        setLine(node, ctx);

        for (var idTok : ctx.ID()) {
            node.addPart(idTok.getText());
        }

        return node;
    }

    @Override
    public ImportList visitImport_list(pyParser.Import_listContext ctx) {
        ImportList node = new ImportList();
        setLine(node, ctx);

        for (pyParser.Import_itemContext itCtx : ctx.import_item()) {
            ImportItem it = (ImportItem) visit(itCtx);
            node.addItem(it);
        }

        return node;
    }

    @Override
    public ImportItem visitImport_item(pyParser.Import_itemContext ctx) {
        ImportItem node = new ImportItem();
        setLine(node, ctx);

        String name = ctx.identifier().getText();
        node.setName(name);

        if (ctx.AS() != null && ctx.ID() != null) {
            node.setAlias(ctx.ID().getText());
        } else {
            node.setAlias(null);
        }

        return node;
    }

    // ================= assignment / func / suite =================

    @Override
    public AssignStmt visitAssignment(pyParser.AssignmentContext ctx) {
        AssignStmt node = new AssignStmt();
        setLine(node, ctx);

        String name = ctx.getChild(0).getText(); // APP or ID
        node.setName(name);

        Expression value = (Expression) visit(ctx.expr());
        node.setValue(value);

        return node;
    }

    @Override
    public FuncDefStatement visitFunc_def(pyParser.Func_defContext ctx) {
        FuncDefStatement node = new FuncDefStatement();
        setLine(node, ctx);

        String fname = ctx.ID().getText();
        int line = ctx.getStart().getLine();

        node.setName(fname);

        // Fuction Scoop
        symbolTable.enterScope("Func@" + line + ":" + fname);

        ArrayList<String> params = new ArrayList<>();
        if (ctx.param_list() != null) {
            params.addAll(visitParam_list(ctx.param_list()));
        }
        node.setParams(params);

        // params in func scope
        for (String p : params) {
            symbolTable.addSymbol(p, "param", "Parameter", line);
        }

        Suite body = (Suite) visit(ctx.suite());
        node.setBody(body);

        symbolTable.exitScope();
        return node;
    }


    public ArrayList<String> visitParam_list(pyParser.Param_listContext ctx) {
        ArrayList<String> params = new ArrayList<>();
        for (var idTok : ctx.ID()) {
            params.add(idTok.getText());
        }
        return params;
    }

    @Override
    public Suite visitIndentedSuite(pyParser.IndentedSuiteContext ctx) {
        IndentedSuite node = new IndentedSuite();
        setLine(node, ctx);

        int line = ctx.getStart().getLine();
        symbolTable.enterScope("Suite@" + line);

        for (pyParser.StatementContext stCtx : ctx.statement()) {
            Statement st = (Statement) visit(stCtx);
            if (st != null) node.addStatement(st);
        }

        symbolTable.exitScope();
        return node;
    }

    @Override
    public Suite visitSimpleSuite(pyParser.SimpleSuiteContext ctx) {
        SimpleSuite node = new SimpleSuite();
        setLine(node, ctx);

        int line = ctx.getStart().getLine();
        symbolTable.enterScope("Suite@" + line);

        Statement st = (Statement) visit(ctx.statement());
        node.setStatement(st);

        symbolTable.exitScope();
        return node;
    }


    // ================= route =================

    @Override
    public RouteStatement visitRoute_def(pyParser.Route_defContext ctx) {
        RouteStatement node = new RouteStatement();
        setLine(node, ctx);

        RoutePath rp = (RoutePath) visit(ctx.route_path());
        node.setRoutePath(rp);

        if (ctx.route_params() != null) {
            RouteParams params = (RouteParams) visit(ctx.route_params());
            node.setRouteParams(params);
        } else {
            node.setRouteParams(null);
        }

        FuncDefStatement fd = (FuncDefStatement) visit(ctx.func_def());
        node.setFuncDef(fd);

        return node;
    }

    @Override
    public RoutePath visitRoute_path(pyParser.Route_pathContext ctx) {
        RoutePath node = new RoutePath();
        setLine(node, ctx);

        String raw = ctx.getText();
        if (ctx.STRING() != null) raw = stripQuotes(raw);

        node.setPath(raw);
        return node;
    }

    @Override
    public RouteParams visitRoute_params(pyParser.Route_paramsContext ctx) {
        RouteParams node = new RouteParams();
        setLine(node, ctx);

        ListLiteral methods = (ListLiteral) visit(ctx.list_literal());
        node.setMethodsList(methods);

        return node;
    }

    // ================= if / for / return / expr_stmt =================

    @Override
    public IfStatement visitIf_stmt(pyParser.If_stmtContext ctx) {
        IfStatement node = new IfStatement();
        setLine(node, ctx);

        node.setCondition((Expression) visit(ctx.expr()));
        node.setThenSuite((Suite) visit(ctx.suite(0)));

        if (ctx.ELSE() != null) {
            node.setElseSuite((Suite) visit(ctx.suite(1)));
        } else {
            node.setElseSuite(null);
        }

        return node;
    }

    @Override
    public ForStatement visitFor_stmt(pyParser.For_stmtContext ctx) {
        ForStatement node = new ForStatement();
        int line = ctx.getStart().getLine();

        String var = ctx.ID().getText();
        symbolTable.addSymbol(var, "loopVar", "ForVar", line);

        node.setVarName(var);
        node.setExpression((Expression) visit(ctx.expr()));
        node.setForBlock((Suite) visit(ctx.suite()));

        return node;
    }

    @Override
    public ReturnStmt visitReturn_stmt(pyParser.Return_stmtContext ctx) {
        ReturnStmt node = new ReturnStmt();
        setLine(node, ctx);

        if (ctx.return_args() != null) {
            List<Expression> args = (List<Expression>) visit(ctx.return_args());
            node.setReturnArgs(args);
        }

        return node;
    }

    @Override
    public Object visitReturn_args(pyParser.Return_argsContext ctx) {
        List<Expression> args = new ArrayList<>();
        for (pyParser.ExprContext eCtx : ctx.expr()) {
            args.add((Expression) visit(eCtx));
        }
        return args;
    }

    @Override
    public ExprStmt visitExpr_stmt(pyParser.Expr_stmtContext ctx) {
        ExprStmt node = new ExprStmt();
        setLine(node, ctx);

        node.setExpr((Expression) visit(ctx.expr()));
        return node;
    }

    // ================= Expressions =================

    @Override
    public Expression visitCondExpr(pyParser.CondExprContext ctx) {
        CondExpr node = new CondExpr();
        setLine(node, ctx);

        // thenExpr
        node.setThenExpr((Expression) visit(ctx.orExpr(0)));

        if (ctx.IF() != null) {
            node.setCondition((Expression) visit(ctx.orExpr(1)));
            node.setElseExpr((Expression) visit(ctx.orExpr(2)));
        } else {
            node.setCondition(null);
            node.setElseExpr(null);
        }

        return node;
    }

    @Override
    public Object visitOrPassExpr(pyParser.OrPassExprContext ctx) {
        OrPassExpr node = new OrPassExpr();
        setLine(node, ctx);

        Expression inner = (Expression) visit(ctx.equalityExpr());
        node.setInner(inner);

        return node;
    }

    @Override
    public Expression visitEqualityExpr(pyParser.EqualityExprContext ctx) {
        Expression left = (Expression) visit(ctx.relationalExpr(0));

        for (int i = 1; i < ctx.relationalExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expression right = (Expression) visit(ctx.relationalExpr(i));

            BinaryExpr bin = new BinaryExpr();
            setLine(bin, ctx);
            bin.setLeft(left);
            bin.setOp(op);
            bin.setRight(right);

            left = bin;
        }

        return left;
    }

    @Override
    public Expression visitRelationalExpr(pyParser.RelationalExprContext ctx) {
        Expression left = (Expression) visit(ctx.additiveExpr(0));

        for (int i = 1; i < ctx.additiveExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expression right = (Expression) visit(ctx.additiveExpr(i));

            BinaryExpr bin = new BinaryExpr();
            setLine(bin, ctx);
            bin.setLeft(left);
            bin.setOp(op);
            bin.setRight(right);

            left = bin;
        }

        return left;
    }

    @Override
    public Expression visitAdditiveExpr(pyParser.AdditiveExprContext ctx) {
        Expression left = (Expression) visit(ctx.multiplicativeExpr(0));

        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expression right = (Expression) visit(ctx.multiplicativeExpr(i));

            BinaryExpr bin = new BinaryExpr();
            setLine(bin, ctx);
            bin.setLeft(left);
            bin.setOp(op);
            bin.setRight(right);

            left = bin;
        }

        return left;
    }

    @Override
    public Expression visitMultiplicativeExpr(pyParser.MultiplicativeExprContext ctx) {
        Expression left = (Expression) visit(ctx.unaryExpr(0));

        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expression right = (Expression) visit(ctx.unaryExpr(i));

            BinaryExpr bin = new BinaryExpr();
            setLine(bin, ctx);
            bin.setLeft(left);
            bin.setOp(op);
            bin.setRight(right);

            left = bin;
        }

        return left;
    }

    @Override
    public UnaryExpr visitUnaryMinusExpr(pyParser.UnaryMinusExprContext ctx) {
        UnaryMinusExpr node = new UnaryMinusExpr();
        setLine(node, ctx);

        node.setExpr((Expression) visit(ctx.unaryExpr()));
        return node;
    }

    @Override
    public UnaryExpr visitUnaryPostfixExpr(pyParser.UnaryPostfixExprContext ctx) {
        UnaryPostfixExpr node = new UnaryPostfixExpr();
        setLine(node, ctx);

        node.setExpr((PostfixExpr) visit(ctx.postfixExpr()));
        return node;
    }

    @Override
    public PostfixExpr visitPostfixExpr(pyParser.PostfixExprContext ctx) {
        PostfixExpr node = new PostfixExpr();
        setLine(node, ctx);

        node.setPrimary((PrimaryExpr) visit(ctx.primaryExpr()));

        for (pyParser.PostfixOpContext opCtx : ctx.postfixOp()) {
            PostfixOp op = (PostfixOp) visit(opCtx);
            node.addOp(op);
        }
        return node;
    }

    // ================= Postfix ops =================

    @Override
    public PostfixOp visitCallPostfix(pyParser.CallPostfixContext ctx) {
        CallPostfix node = new CallPostfix();
        setLine(node, ctx);

        if (ctx.arg_list() != null) {
            node.setArgList((ArgList) visit(ctx.arg_list()));
        } else {
            node.setArgList(null);
        }

        return node;
    }

    @Override
    public PostfixOp visitSubscriptPostfix(pyParser.SubscriptPostfixContext ctx) {
        SubscriptPostfix node = new SubscriptPostfix();
        setLine(node, ctx);

        node.setIndex((Expression) visit(ctx.expr()));
        return node;
    }

    @Override
    public PostfixOp visitAttrPostfix(pyParser.AttrPostfixContext ctx) {
        AttrPostfix node = new AttrPostfix();
        setLine(node, ctx);

        node.setName(ctx.ID().getText());
        return node;
    }

    // ================= Primary =================

    @Override
    public PrimaryExpr visitIntLiteralExpr(pyParser.IntLiteralExprContext ctx) {
        IntExpr node = new IntExpr();
        setLine(node, ctx);

        node.setValue(Long.parseLong(ctx.INT().getText()));
        return node;
    }

    @Override
    public PrimaryExpr visitFloatLiteralExpr(pyParser.FloatLiteralExprContext ctx) {
        FloatExpr node = new FloatExpr();
        setLine(node, ctx);

        node.setValue(Double.parseDouble(ctx.FLOAT().getText()));
        return node;
    }

    @Override
    public PrimaryExpr visitStringLiteralExpr(pyParser.StringLiteralExprContext ctx) {
        StringExpr node = new StringExpr();
        setLine(node, ctx);

        node.setValue(stripQuotes(ctx.STRING().getText()));
        return node;
    }

    @Override
    public PrimaryExpr visitHtmlFileLiteralExpr(pyParser.HtmlFileLiteralExprContext ctx) {
        HtmlFileExpr node = new HtmlFileExpr();
        setLine(node, ctx);

        node.setValue(stripQuotes(ctx.HTML_FILE().getText()));
        return node;
    }

    @Override
    public PrimaryExpr visitTrueLiteralExpr(pyParser.TrueLiteralExprContext ctx) {
        TrueExpr node = new TrueExpr();
        setLine(node, ctx);
        return node;
    }

    @Override
    public PrimaryExpr visitFalseLiteralExpr(pyParser.FalseLiteralExprContext ctx) {
        FalseExpr node = new FalseExpr();
        setLine(node, ctx);
        return node;
    }

    @Override
    public PrimaryExpr visitNoneLiteralExpr(pyParser.NoneLiteralExprContext ctx) {
        NoneExpr node = new NoneExpr();
        setLine(node, ctx);
        return node;
    }

    @Override
    public PrimaryExpr visitIdentifierExpr(pyParser.IdentifierExprContext ctx) {
        IdentifierExpr node = new IdentifierExpr();
        setLine(node, ctx);

        node.setName(ctx.identifier().getText());
        return node;
    }

    @Override
    public PrimaryExpr visitListLiteralExpr(pyParser.ListLiteralExprContext ctx) {
        ListLiteralExpr node = new ListLiteralExpr();
        setLine(node, ctx);

        ListLiteral list = (ListLiteral) visit(ctx.list_literal());
        node.setListLiteral(list);

        return node;
    }

    @Override
    public PrimaryExpr visitDictLiteralExpr(pyParser.DictLiteralExprContext ctx) {
        DictLiteralExpr node = new DictLiteralExpr();
        setLine(node, ctx);

        DictLiteral dict = (DictLiteral) visit(ctx.dict_literal());
        node.setDictLiteral(dict);

        return node;
    }

    @Override
    public PrimaryExpr visitGeneratorPrimaryExpr(pyParser.GeneratorPrimaryExprContext ctx) {
        GeneratorPrimaryExpr node = new GeneratorPrimaryExpr();
        setLine(node, ctx);

        GeneratorExpr g = (GeneratorExpr) visit(ctx.generator_expr());
        node.setGeneratorExpr(g);

        return node;
    }

    @Override
    public PrimaryExpr visitParenExpr(pyParser.ParenExprContext ctx) {
        ParenExpr node = new ParenExpr();
        setLine(node, ctx);

        node.setInner((Expression) visit(ctx.expr()));
        return node;
    }
// ================= generator_expr =================

    @Override
    public GeneratorExpr visitGenerator_expr(pyParser.Generator_exprContext ctx) {
        GeneratorExpr node = new GeneratorExpr();
        setLine(node, ctx);

        // grammar: ( ID FOR ID IN expr (IF expr)? )
        node.setYieldName(ctx.ID(0).getText());
        node.setLoopVarName(ctx.ID(1).getText());
        node.setIterable((Expression) visit(ctx.expr(0)));

        if (ctx.IF() != null && ctx.expr().size() > 1) {
            node.setFilter((Expression) visit(ctx.expr(1)));
        } else {
            node.setFilter(null);
        }

        return node;
    }

    // ================= list / dict literals =================

    @Override
    public ListLiteral visitList_literal(pyParser.List_literalContext ctx) {
        ListLiteral node = new ListLiteral();
        setLine(node, ctx);

        for (pyParser.ExprContext eCtx : ctx.expr()) {
            node.addElement((Expression) visit(eCtx));
        }

        return node;
    }

    @Override
    public DictLiteral visitDict_literal(pyParser.Dict_literalContext ctx) {
        DictLiteral node = new DictLiteral();
        setLine(node, ctx);

        for (pyParser.Dict_entryContext deCtx : ctx.dict_entry()) {
            DictEntry entry = (DictEntry) visit(deCtx);
            node.addEntry(entry);
        }

        return node;
    }

    @Override
    public DictEntry visitDict_entry(pyParser.Dict_entryContext ctx) {
        DictEntry node = new DictEntry();
        setLine(node, ctx);

        node.setKey((Expression) visit(ctx.expr(0)));
        node.setValue((Expression) visit(ctx.expr(1)));

        return node;
    }

    // ================= arguments =================

    @Override
    public ArgList visitArg_list(pyParser.Arg_listContext ctx) {
        ArgList node = new ArgList();
        setLine(node, ctx);

        for (pyParser.ArgContext aCtx : ctx.arg()) {
            Arg a = (Arg) visit(aCtx);
            node.addArg(a);
        }

        return node;
    }

    @Override
    public Arg visitArg(pyParser.ArgContext ctx) {
        Arg node = new Arg();
        setLine(node, ctx);

        if (ctx.ID() != null && ctx.ASSIGN() != null) {
            node.setName(ctx.ID().getText());
            node.setValue((Expression) visit(ctx.expr()));
        } else {
            node.setName(null);
            node.setValue((Expression) visit(ctx.expr()));
        }

        return node;
    }
}