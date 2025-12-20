// Generated from D:/DOC/Telegram Desktop/Compiler (2)/Compiler/src/antlr/JinjaParser.g4 by ANTLR 4.13.2
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JinjaParser}.
 */
public interface JinjaParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JinjaParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(JinjaParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(JinjaParser.DocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterHtmlElement(JinjaParser.HtmlElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitHtmlElement(JinjaParser.HtmlElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PairedTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void enterPairedTag(JinjaParser.PairedTagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PairedTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void exitPairedTag(JinjaParser.PairedTagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SelfClosingTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void enterSelfClosingTag(JinjaParser.SelfClosingTagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SelfClosingTag}
	 * labeled alternative in {@link JinjaParser#htmlTag}.
	 * @param ctx the parse tree
	 */
	void exitSelfClosingTag(JinjaParser.SelfClosingTagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ControlBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void enterControlBlock(JinjaParser.ControlBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ControlBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void exitControlBlock(JinjaParser.ControlBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrintBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void enterPrintBlock(JinjaParser.PrintBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrintBlock}
	 * labeled alternative in {@link JinjaParser#jinjaBlock}.
	 * @param ctx the parse tree
	 */
	void exitPrintBlock(JinjaParser.PrintBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Extends}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterExtends(JinjaParser.ExtendsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Extends}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitExtends(JinjaParser.ExtendsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockStart}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterBlockStart(JinjaParser.BlockStartContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockStart}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitBlockStart(JinjaParser.BlockStartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockEnd}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterBlockEnd(JinjaParser.BlockEndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockEnd}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitBlockEnd(JinjaParser.BlockEndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code If}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterIf(JinjaParser.IfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code If}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitIf(JinjaParser.IfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Else}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterElse(JinjaParser.ElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Else}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitElse(JinjaParser.ElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EndIf}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterEndIf(JinjaParser.EndIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EndIf}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitEndIf(JinjaParser.EndIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code For}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterFor(JinjaParser.ForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code For}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitFor(JinjaParser.ForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EndFor}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void enterEndFor(JinjaParser.EndForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EndFor}
	 * labeled alternative in {@link JinjaParser#jinjaStatementHeader}.
	 * @param ctx the parse tree
	 */
	void exitEndFor(JinjaParser.EndForContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void enterJinjaExpression(JinjaParser.JinjaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void exitJinjaExpression(JinjaParser.JinjaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AccessExpr}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterAccessExpr(JinjaParser.AccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AccessExpr}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitAccessExpr(JinjaParser.AccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(JinjaParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(JinjaParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(JinjaParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(JinjaParser.NumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(JinjaParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TrueLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(JinjaParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(JinjaParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FalseLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(JinjaParser.FalseLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterNoneLiteral(JinjaParser.NoneLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NoneLiteral}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitNoneLiteral(JinjaParser.NoneLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(JinjaParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link JinjaParser#jinjaPrimary}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(JinjaParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#jinjaIdentifierChain}.
	 * @param ctx the parse tree
	 */
	void enterJinjaIdentifierChain(JinjaParser.JinjaIdentifierChainContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#jinjaIdentifierChain}.
	 * @param ctx the parse tree
	 */
	void exitJinjaIdentifierChain(JinjaParser.JinjaIdentifierChainContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#jinjaFilter}.
	 * @param ctx the parse tree
	 */
	void enterJinjaFilter(JinjaParser.JinjaFilterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#jinjaFilter}.
	 * @param ctx the parse tree
	 */
	void exitJinjaFilter(JinjaParser.JinjaFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallMixedArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void enterCallMixedArgs(JinjaParser.CallMixedArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallMixedArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void exitCallMixedArgs(JinjaParser.CallMixedArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CallKwArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void enterCallKwArgs(JinjaParser.CallKwArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CallKwArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void exitCallKwArgs(JinjaParser.CallKwArgsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void enterEmptyArgs(JinjaParser.EmptyArgsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyArgs}
	 * labeled alternative in {@link JinjaParser#jinjaCallArgs}.
	 * @param ctx the parse tree
	 */
	void exitEmptyArgs(JinjaParser.EmptyArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#jinjaArg}.
	 * @param ctx the parse tree
	 */
	void enterJinjaArg(JinjaParser.JinjaArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#jinjaArg}.
	 * @param ctx the parse tree
	 */
	void exitJinjaArg(JinjaParser.JinjaArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#jinjaKwArg}.
	 * @param ctx the parse tree
	 */
	void enterJinjaKwArg(JinjaParser.JinjaKwArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#jinjaKwArg}.
	 * @param ctx the parse tree
	 */
	void exitJinjaKwArg(JinjaParser.JinjaKwArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#htmlText}.
	 * @param ctx the parse tree
	 */
	void enterHtmlText(JinjaParser.HtmlTextContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#htmlText}.
	 * @param ctx the parse tree
	 */
	void exitHtmlText(JinjaParser.HtmlTextContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#styleTag}.
	 * @param ctx the parse tree
	 */
	void enterStyleTag(JinjaParser.StyleTagContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#styleTag}.
	 * @param ctx the parse tree
	 */
	void exitStyleTag(JinjaParser.StyleTagContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#cssRule}.
	 * @param ctx the parse tree
	 */
	void enterCssRule(JinjaParser.CssRuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#cssRule}.
	 * @param ctx the parse tree
	 */
	void exitCssRule(JinjaParser.CssRuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#cssSelectorList}.
	 * @param ctx the parse tree
	 */
	void enterCssSelectorList(JinjaParser.CssSelectorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#cssSelectorList}.
	 * @param ctx the parse tree
	 */
	void exitCssSelectorList(JinjaParser.CssSelectorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#cssSelector}.
	 * @param ctx the parse tree
	 */
	void enterCssSelector(JinjaParser.CssSelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#cssSelector}.
	 * @param ctx the parse tree
	 */
	void exitCssSelector(JinjaParser.CssSelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#cssProperty}.
	 * @param ctx the parse tree
	 */
	void enterCssProperty(JinjaParser.CssPropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#cssProperty}.
	 * @param ctx the parse tree
	 */
	void exitCssProperty(JinjaParser.CssPropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#valueList}.
	 * @param ctx the parse tree
	 */
	void enterValueList(JinjaParser.ValueListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#valueList}.
	 * @param ctx the parse tree
	 */
	void exitValueList(JinjaParser.ValueListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WordValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void enterWordValue(JinjaParser.WordValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WordValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void exitWordValue(JinjaParser.WordValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void enterNumberValue(JinjaParser.NumberValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void exitNumberValue(JinjaParser.NumberValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void enterColorValue(JinjaParser.ColorValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ColorValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void exitColorValue(JinjaParser.ColorValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void enterStringValue(JinjaParser.StringValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void exitStringValue(JinjaParser.StringValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void enterFunctionValue(JinjaParser.FunctionValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionValue}
	 * labeled alternative in {@link JinjaParser#cssValue}.
	 * @param ctx the parse tree
	 */
	void exitFunctionValue(JinjaParser.FunctionValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JinjaParser#cssFunction}.
	 * @param ctx the parse tree
	 */
	void enterCssFunction(JinjaParser.CssFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JinjaParser#cssFunction}.
	 * @param ctx the parse tree
	 */
	void exitCssFunction(JinjaParser.CssFunctionContext ctx);
}