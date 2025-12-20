// Generated from C:/Users/rufida/Desktop/New folder/Compiler_Project/src/pyAntlr/pyParser.g4 by ANTLR 4.13.2
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
	 * Enter a parse tree produced by {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(pyParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(pyParser.StatementContext ctx);
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
	 * Enter a parse tree produced by {@link pyParser#simple_import}.
	 * @param ctx the parse tree
	 */
	void enterSimple_import(pyParser.Simple_importContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#simple_import}.
	 * @param ctx the parse tree
	 */
	void exitSimple_import(pyParser.Simple_importContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#from_import}.
	 * @param ctx the parse tree
	 */
	void enterFrom_import(pyParser.From_importContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#from_import}.
	 * @param ctx the parse tree
	 */
	void exitFrom_import(pyParser.From_importContext ctx);
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
	 * Enter a parse tree produced by {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(pyParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(pyParser.SuiteContext ctx);
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
	 * Enter a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMulExpr(pyParser.MulExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMulExpr(pyParser.MulExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColonExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterColonExpr(pyParser.ColonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColonExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitColonExpr(pyParser.ColonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SquareExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSquareExpr(pyParser.SquareExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SquareExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSquareExpr(pyParser.SquareExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(pyParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(pyParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterDivExpr(pyParser.DivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitDivExpr(pyParser.DivExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GeneratorExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGeneratorExpr(pyParser.GeneratorExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GeneratorExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGeneratorExpr(pyParser.GeneratorExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSubExpr(pyParser.SubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSubExpr(pyParser.SubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(pyParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(pyParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddExpr(pyParser.AddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddExpr(pyParser.AddExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CurlyExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCurlyExpr(pyParser.CurlyExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CurlyExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCurlyExpr(pyParser.CurlyExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegateExpr(pyParser.NegateExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegateExpr(pyParser.NegateExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterInt(pyParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitInt(pyParser.IntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code float}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFloat(pyParser.FloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code float}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFloat(pyParser.FloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterString(pyParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitString(pyParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code true}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterTrue(pyParser.TrueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code true}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitTrue(pyParser.TrueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code false}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFalse(pyParser.FalseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code false}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFalse(pyParser.FalseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code none}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNone(pyParser.NoneContext ctx);
	/**
	 * Exit a parse tree produced by the {@code none}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNone(pyParser.NoneContext ctx);
	/**
	 * Enter a parse tree produced by the {@code id}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterId(pyParser.IdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code id}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitId(pyParser.IdContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(pyParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(pyParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpr(pyParser.SubscriptExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpr(pyParser.SubscriptExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pyParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void enterCall_expr(pyParser.Call_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void exitCall_expr(pyParser.Call_exprContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link pyParser#subscript_expr}.
	 * @param ctx the parse tree
	 */
	void enterSubscript_expr(pyParser.Subscript_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pyParser#subscript_expr}.
	 * @param ctx the parse tree
	 */
	void exitSubscript_expr(pyParser.Subscript_exprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code import_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 */
	void enterImport_render(pyParser.Import_renderContext ctx);
	/**
	 * Exit a parse tree produced by the {@code import_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 */
	void exitImport_render(pyParser.Import_renderContext ctx);
	/**
	 * Enter a parse tree produced by the {@code call_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 */
	void enterCall_render(pyParser.Call_renderContext ctx);
	/**
	 * Exit a parse tree produced by the {@code call_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 */
	void exitCall_render(pyParser.Call_renderContext ctx);
}