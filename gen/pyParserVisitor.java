// Generated from C:/Users/rufida/Desktop/New folder/Compiler_Project/src/pyAntlr/pyParser.g4 by ANTLR 4.13.2
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
	 * Visit a parse tree produced by {@link pyParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pyParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(pyParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#simple_import}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimple_import(pyParser.Simple_importContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#from_import}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFrom_import(pyParser.From_importContext ctx);
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
	 * Visit a parse tree produced by {@link pyParser#dotted_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotted_name(pyParser.Dotted_nameContext ctx);
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
	 * Visit a parse tree produced by {@link pyParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(pyParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(pyParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(pyParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(pyParser.Return_stmtContext ctx);
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
	 * Visit a parse tree produced by {@link pyParser#generator_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGenerator_expr(pyParser.Generator_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulExpr(pyParser.MulExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColonExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColonExpr(pyParser.ColonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SquareExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSquareExpr(pyParser.SquareExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(pyParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code DivExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivExpr(pyParser.DivExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GeneratorExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGeneratorExpr(pyParser.GeneratorExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SubExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpr(pyParser.SubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(pyParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExpr(pyParser.AddExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(pyParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CurlyExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurlyExpr(pyParser.CurlyExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegateExpr}
	 * labeled alternative in {@link pyParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegateExpr(pyParser.NegateExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code int}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(pyParser.IntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code float}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat(pyParser.FloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(pyParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(pyParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(pyParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code none}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNone(pyParser.NoneContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(pyParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallExpr(pyParser.CallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link pyParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptExpr(pyParser.SubscriptExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#call_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_expr(pyParser.Call_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#arg_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_list(pyParser.Arg_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link pyParser#subscript_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript_expr(pyParser.Subscript_exprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code import_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_render(pyParser.Import_renderContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call_render}
	 * labeled alternative in {@link pyParser#render_call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_render(pyParser.Call_renderContext ctx);
}