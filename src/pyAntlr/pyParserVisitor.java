// Generated from C:/Users/Lenovo/Desktop/Projects/NEW/Compiler_Project/src/pyAntlr/pyParser.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pyParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pyParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pyParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pyParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(pyParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ImportStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(pyParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(pyParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(pyParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(pyParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RouteStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRouteStatement(pyParser.RouteStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FuncDefStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDefStatement(pyParser.FuncDefStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(pyParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(pyParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(pyParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(pyParser.Dotted_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#import_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_list(pyParser.Import_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#import_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_item(pyParser.Import_itemContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(pyParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#func_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_def(pyParser.Func_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#param_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_list(pyParser.Param_listContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IndentedSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndentedSuite(pyParser.IndentedSuiteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SimpleSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleSuite(pyParser.SimpleSuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#route_def}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoute_def(pyParser.Route_defContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#route_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoute_path(pyParser.Route_pathContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#route_params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoute_params(pyParser.Route_paramsContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(pyParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#for_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(pyParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(pyParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#return_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_args(pyParser.Return_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(pyParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CondExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondExpr(pyParser.CondExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OrPassExpr}
	 * labeled alternative in {@link pyParser#orExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrPassExpr(pyParser.OrPassExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(pyParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(pyParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(pyParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(pyParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(pyParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryPostfixExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryPostfixExpr(pyParser.UnaryPostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(pyParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallPostfix(pyParser.CallPostfixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubscriptPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptPostfix(pyParser.SubscriptPostfixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AttrPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttrPostfix(pyParser.AttrPostfixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteralExpr(pyParser.IntLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteralExpr(pyParser.FloatLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteralExpr(pyParser.StringLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code HtmlFileLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlFileLiteralExpr(pyParser.HtmlFileLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteralExpr(pyParser.TrueLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteralExpr(pyParser.FalseLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NoneLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoneLiteralExpr(pyParser.NoneLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(pyParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ListLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteralExpr(pyParser.ListLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DictLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteralExpr(pyParser.DictLiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeneratorPrimaryExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneratorPrimaryExpr(pyParser.GeneratorPrimaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#generator_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenerator_expr(pyParser.Generator_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#list_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_literal(pyParser.List_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#dict_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDict_literal(pyParser.Dict_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#dict_entry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDict_entry(pyParser.Dict_entryContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(pyParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#arg_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_list(pyParser.Arg_listContext ctx);
}