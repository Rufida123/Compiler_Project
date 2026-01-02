// Generated from C:/Users/Lenovo/Desktop/Projects/Compiler/Last/Compiler_Project/src/pyAntlr/pyParser.g4 by ANTLR 4.13.2
package pyAntlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pyParser}.
 */
public interface pyParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pyParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(pyParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(pyParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(pyParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(pyParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ImportStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(pyParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ImportStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(pyParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(pyParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(pyParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(pyParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(pyParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(pyParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(pyParser.ExprStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RouteStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterRouteStatement(pyParser.RouteStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RouteStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitRouteStatement(pyParser.RouteStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FuncDefStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterFuncDefStatement(pyParser.FuncDefStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FuncDefStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitFuncDefStatement(pyParser.FuncDefStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(pyParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(pyParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(pyParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(pyParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmt(pyParser.Import_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmt(pyParser.Import_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void enterDotted_name(pyParser.Dotted_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#dotted_name}.
	 * @param ctx the parse tree
	 */
	void exitDotted_name(pyParser.Dotted_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#import_list}.
	 * @param ctx the parse tree
	 */
	void enterImport_list(pyParser.Import_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#import_list}.
	 * @param ctx the parse tree
	 */
	void exitImport_list(pyParser.Import_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#import_item}.
	 * @param ctx the parse tree
	 */
	void enterImport_item(pyParser.Import_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#import_item}.
	 * @param ctx the parse tree
	 */
	void exitImport_item(pyParser.Import_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(pyParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(pyParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#func_def}.
	 * @param ctx the parse tree
	 */
	void enterFunc_def(pyParser.Func_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#func_def}.
	 * @param ctx the parse tree
	 */
	void exitFunc_def(pyParser.Func_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#param_list}.
	 * @param ctx the parse tree
	 */
	void enterParam_list(pyParser.Param_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#param_list}.
	 * @param ctx the parse tree
	 */
	void exitParam_list(pyParser.Param_listContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IndentedSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterIndentedSuite(pyParser.IndentedSuiteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IndentedSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitIndentedSuite(pyParser.IndentedSuiteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SimpleSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSimpleSuite(pyParser.SimpleSuiteContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SimpleSuite}
	 * labeled alternative in {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSimpleSuite(pyParser.SimpleSuiteContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#route_def}.
	 * @param ctx the parse tree
	 */
	void enterRoute_def(pyParser.Route_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#route_def}.
	 * @param ctx the parse tree
	 */
	void exitRoute_def(pyParser.Route_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#route_path}.
	 * @param ctx the parse tree
	 */
	void enterRoute_path(pyParser.Route_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#route_path}.
	 * @param ctx the parse tree
	 */
	void exitRoute_path(pyParser.Route_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#route_params}.
	 * @param ctx the parse tree
	 */
	void enterRoute_params(pyParser.Route_paramsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#route_params}.
	 * @param ctx the parse tree
	 */
	void exitRoute_params(pyParser.Route_paramsContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(pyParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(pyParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(pyParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(pyParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(pyParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(pyParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#return_args}.
	 * @param ctx the parse tree
	 */
	void enterReturn_args(pyParser.Return_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#return_args}.
	 * @param ctx the parse tree
	 */
	void exitReturn_args(pyParser.Return_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(pyParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(pyParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CondExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCondExpr(pyParser.CondExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CondExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCondExpr(pyParser.CondExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OrPassExpr}
	 * labeled alternative in {@link pyParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrPassExpr(pyParser.OrPassExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OrPassExpr}
	 * labeled alternative in {@link pyParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrPassExpr(pyParser.OrPassExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(pyParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(pyParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(pyParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#relationalExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(pyParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(pyParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(pyParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(pyParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(pyParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(pyParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryMinusExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(pyParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryPostfixExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryPostfixExpr(pyParser.UnaryPostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryPostfixExpr}
	 * labeled alternative in {@link pyParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryPostfixExpr(pyParser.UnaryPostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(pyParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(pyParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterCallPostfix(pyParser.CallPostfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitCallPostfix(pyParser.CallPostfixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubscriptPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptPostfix(pyParser.SubscriptPostfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubscriptPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptPostfix(pyParser.SubscriptPostfixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttrPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void enterAttrPostfix(pyParser.AttrPostfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttrPostfix}
	 * labeled alternative in {@link pyParser#postfixOp}.
	 * @param ctx the parse tree
	 */
	void exitAttrPostfix(pyParser.AttrPostfixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteralExpr(pyParser.IntLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteralExpr(pyParser.IntLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteralExpr(pyParser.FloatLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteralExpr(pyParser.FloatLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteralExpr(pyParser.StringLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteralExpr(pyParser.StringLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code HtmlFileLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterHtmlFileLiteralExpr(pyParser.HtmlFileLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code HtmlFileLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitHtmlFileLiteralExpr(pyParser.HtmlFileLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteralExpr(pyParser.TrueLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteralExpr(pyParser.TrueLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteralExpr(pyParser.FalseLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteralExpr(pyParser.FalseLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NoneLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterNoneLiteralExpr(pyParser.NoneLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NoneLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitNoneLiteralExpr(pyParser.NoneLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(pyParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(pyParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ListLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterListLiteralExpr(pyParser.ListLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ListLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitListLiteralExpr(pyParser.ListLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DictLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteralExpr(pyParser.DictLiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DictLiteralExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteralExpr(pyParser.DictLiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GeneratorPrimaryExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorPrimaryExpr(pyParser.GeneratorPrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GeneratorPrimaryExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorPrimaryExpr(pyParser.GeneratorPrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#primaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#generator_expr}.
	 * @param ctx the parse tree
	 */
	void enterGenerator_expr(pyParser.Generator_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#generator_expr}.
	 * @param ctx the parse tree
	 */
	void exitGenerator_expr(pyParser.Generator_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#list_literal}.
	 * @param ctx the parse tree
	 */
	void enterList_literal(pyParser.List_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#list_literal}.
	 * @param ctx the parse tree
	 */
	void exitList_literal(pyParser.List_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#dict_literal}.
	 * @param ctx the parse tree
	 */
	void enterDict_literal(pyParser.Dict_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#dict_literal}.
	 * @param ctx the parse tree
	 */
	void exitDict_literal(pyParser.Dict_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#dict_entry}.
	 * @param ctx the parse tree
	 */
	void enterDict_entry(pyParser.Dict_entryContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#dict_entry}.
	 * @param ctx the parse tree
	 */
	void exitDict_entry(pyParser.Dict_entryContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(pyParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(pyParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void enterArg_list(pyParser.Arg_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void exitArg_list(pyParser.Arg_listContext ctx);
}