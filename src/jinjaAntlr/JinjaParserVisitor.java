// Generated from D:/DOC/Telegram Desktop/Compiler (2)/Compiler/src/antlr/JinjaParser.g4 by ANTLR 4.13.2
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JinjaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JinjaParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JinjaParser#document}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDocument(JinjaParser.DocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlElement(JinjaParser.HtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PairedTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPairedTag(JinjaParser.PairedTagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code SelfClosingTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfClosingTag(JinjaParser.SelfClosingTagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ControlBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitControlBlock(JinjaParser.ControlBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PrintBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintBlock(JinjaParser.PrintBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Extends}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtends(JinjaParser.ExtendsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockStart}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStart(JinjaParser.BlockStartContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockEnd}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockEnd(JinjaParser.BlockEndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code If}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(JinjaParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Else}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse(JinjaParser.ElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EndIf}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndIf(JinjaParser.EndIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code For}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor(JinjaParser.ForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EndFor}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEndFor(JinjaParser.EndForContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#jinjaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaExpression(JinjaParser.JinjaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AccessExpr}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessExpr(JinjaParser.AccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(JinjaParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(JinjaParser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(JinjaParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(JinjaParser.FalseLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoneLiteral(JinjaParser.NoneLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(JinjaParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#jinjaIdentifierChain}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaIdentifierChain(JinjaParser.JinjaIdentifierChainContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#jinjaFilter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaFilter(JinjaParser.JinjaFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallMixedArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallMixedArgs(JinjaParser.CallMixedArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CallKwArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallKwArgs(JinjaParser.CallKwArgsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyArgs(JinjaParser.EmptyArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#jinjaArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaArg(JinjaParser.JinjaArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#jinjaKwArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaKwArg(JinjaParser.JinjaKwArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#htmlText}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlText(JinjaParser.HtmlTextContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#styleTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyleTag(JinjaParser.StyleTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#cssRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssRule(JinjaParser.CssRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#cssSelectorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssSelectorList(JinjaParser.CssSelectorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#cssSelector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssSelector(JinjaParser.CssSelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#cssProperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssProperty(JinjaParser.CssPropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#valueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueList(JinjaParser.ValueListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code WordValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWordValue(JinjaParser.WordValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberValue(JinjaParser.NumberValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColorValue(JinjaParser.ColorValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringValue(JinjaParser.StringValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionValue(JinjaParser.FunctionValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link JinjaParser#cssFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCssFunction(JinjaParser.CssFunctionContext ctx);
}