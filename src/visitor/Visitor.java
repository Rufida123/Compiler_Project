package visitor;

// ── Shared symbol table ──────────────────────────────────────────────────────
import sharedSymbolTable.Symbol;
import sharedSymbolTable.SymbolTable;

// ── Jinja / ANTLR ───────────────────────────────────────────────────────────
import jinjaAntlr.JinjaParser;
import jinjaAntlr.JinjaParserBaseVisitor;
import jinjaClasses.*;

// ── Python / ANTLR ──────────────────────────────────────────────────────────
import PyClasses.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import pyAntlr.pyParser;
import pyAntlr.pyParserBaseVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that houses both visitors in a single file.
 *
 * Usage:
 *   Visitors.JinjaBaseVisitor jinjaV = new Visitors.JinjaBaseVisitor();
 *   Visitors.PyBaseVisitor    pyV    = new Visitors.PyBaseVisitor();
 */
public class Visitor {

    // ══════════════════════════════════════════════════════════════════════════
    //  JINJA VISITOR
    // ══════════════════════════════════════════════════════════════════════════

    public static class JinjaBaseVisitor extends JinjaParserBaseVisitor<Object> {

        private final SymbolTable symbolTable = new SymbolTable();

        public SymbolTable getSymbolTable() { return symbolTable; }

        /**
         * Single stamping point: every node returned by a rule visit gets the
         * line and column of that rule's first token.  This is what makes the
         * "every node carries its position" requirement hold for the whole
         * Jinja/HTML/CSS tree rather than for a handful of node kinds.
         */
        @Override
        public Object visit(ParseTree tree) {
            Object node = super.visit(tree);
            if (tree instanceof ParserRuleContext ctx) position(node, ctx);
            return node;
        }

        private static <T> T position(T node, ParserRuleContext ctx) {
            if (node instanceof JinjaNode jinjaNode) {
                // Fill each field independently: a node may already carry a line
                // from an inner rule while its column is still unset.
                if (jinjaNode.getLine() <= 0)   jinjaNode.setLine(ctx.getStart().getLine());
                if (jinjaNode.getColumn() < 0)  jinjaNode.setColumn(ctx.getStart().getCharPositionInLine());
            }
            return node;
        }

        /** Stamps a subtree built without a parse context (embedded attribute Jinja). */
        private static void positionSubtree(Object root, ParserRuleContext ctx) {
            if (root == null) return;
            if (root instanceof java.util.List<?> items) {
                for (Object item : items) positionSubtree(item, ctx);
                return;
            }
            if (!(root instanceof JinjaNode node)) return;
            position(node, ctx);
            for (Class<?> type = node.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
                for (java.lang.reflect.Field field : type.getDeclaredFields()) {
                    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
                    try {
                        field.setAccessible(true);
                        positionSubtree(field.get(node), ctx);
                    } catch (ReflectiveOperationException | RuntimeException ignored) {
                        // a field we cannot read simply keeps its default position
                    }
                }
            }
        }

        // ── Program ──────────────────────────────────────────────────────────

        @Override
        public JinjaProgram visitJinjaProgram(JinjaParser.JinjaProgramContext ctx) {
            JinjaProgram program = new JinjaProgram();
            program.setLine(ctx.getStart().getLine());
            for (JinjaParser.DocumentElementContext elemCtx : ctx.documentElement()) {
                if (elemCtx != null) {
                    DocumentElement element = (DocumentElement) visitDocumentElement(elemCtx);
                    if (element != null) program.getHtmlElements().add(element);
                }
            }
            return program;
        }



        @Override
        public DocumentElement visitDocumentElement(JinjaParser.DocumentElementContext ctx) {
            if      (ctx.styleTag()   != null) return (DocumentElement) visit(ctx.styleTag());
            else if (ctx.jinjaBlock() != null) return (DocumentElement) visit(ctx.jinjaBlock());
            else if (ctx.htmlTag()    != null) return (DocumentElement) visit(ctx.htmlTag());
            else if (ctx.htmlText()   != null) return (HtmlText)        visit(ctx.htmlText());
            return null;
        }

        // ── Style tag ────────────────────────────────────────────────────────

        @Override
        public StyleTag visitStyleTag(JinjaParser.StyleTagContext ctx) {
            StyleTag style = new StyleTag();
            style.setLine(ctx.getStart().getLine());
            style.setTagName("<style>");
            for (JinjaParser.CssRuleContext ruleCtx : ctx.cssRule()) {
                if (ruleCtx != null) style.getCssRules().add((CssRule) visit(ruleCtx));
            }
            return style;
        }

        // ── HTML tags ─────────────────────────────────────────────────────────

        @Override
        public PairedTag visitPairedTag(JinjaParser.PairedTagContext ctx) {
            PairedTag tag = new PairedTag();
            tag.setLine(ctx.getStart().getLine());
            if (ctx.TAG_ID(0) != null) tag.setTagName(ctx.TAG_ID(0).getText());
            for (JinjaParser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
                if (attrCtx != null) {
                    HtmlAttribute attr = (HtmlAttribute) visit(attrCtx);
                    if (attr != null) tag.getAttributes().add(attr);
                }
            }
            for (JinjaParser.DocumentElementContext childCtx : ctx.documentElement()) {
                if (childCtx != null) {
                    DocumentElement child = (DocumentElement) visitDocumentElement(childCtx);
                    if (child != null) tag.getChildren().add(child);
                }
            }
            return tag;
        }

        @Override
        public SelfClosingTag visitSelfClosingTag(JinjaParser.SelfClosingTagContext ctx) {
            SelfClosingTag tag = new SelfClosingTag();
            tag.setLine(ctx.getStart().getLine());
            if (ctx.TAG_ID() != null) tag.setTagName(ctx.TAG_ID().getText());
            for (JinjaParser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
                if (attrCtx != null) {
                    HtmlAttribute attr = (HtmlAttribute) visit(attrCtx);
                    if (attr != null) tag.getAttributes().add(attr);
                }
            }
            return tag;
        }

        // ── HTML attributes ──────────────────────────────────────────────────

        @Override
        public HtmlAttribute visitNormalAttribute(JinjaParser.NormalAttributeContext ctx) {
            NormalAttribute attr = new NormalAttribute();
            if (ctx.TAG_ID()         != null) attr.setKey(ctx.TAG_ID().getText());
            if (ctx.attributeValue() != null) attr.setValue((AttributeValue) visit(ctx.attributeValue()));

            if ("class".equals(attr.getKey()) && attr.getValue() instanceof PlainValue) {
                String classValue = ((PlainValue) attr.getValue()).getText()
                        .replaceAll("^\"|\"$", "").trim();
                if (!classValue.isEmpty()) {
                    for (String cls : classValue.split("\\s+")) {
                        cls = cls.trim();
                        if (!cls.isEmpty() && symbolTable.lookup(cls) == null) {
                            symbolTable.addToGlobalScope(
                                    new Symbol(cls, "selector",
                                            symbolTable.getCurrentScopeLevel(),
                                            ctx.getStart().getLine()));
                        }
                    }
                }
            }
            return attr;
        }

        @Override
        public HtmlAttribute visitJinjaAttribute(JinjaParser.JinjaAttributeContext ctx) {
            JinjaAttribute attr = new JinjaAttribute();
            if (ctx.jinjaBlock() != null) attr.setJinjaBlock((JinjaBlock) visit(ctx.jinjaBlock()));
            return attr;
        }

        // ── Attribute values ─────────────────────────────────────────────────

        @Override
        public AttributeValue visitPlainValue(JinjaParser.PlainValueContext ctx) {
            String text = ctx.TAG_ATTR_VALUE().getText();
            if (text.contains("{{") || text.contains("{%")) {
                if (text.length() >= 2 && (text.startsWith("\"") || text.startsWith("'")))
                    text = text.substring(1, text.length() - 1);
                if (text.trim().startsWith("{{") && text.trim().endsWith("}}")) {
                    String jinjaContent = text.trim().substring(2, text.trim().length() - 2).trim();
                    PrintBlock printBlock = new PrintBlock();
                    printBlock.setJinjaExpression(parseJinjaContent(jinjaContent));
                    JinjaValueExpr jinjaValue = new JinjaValueExpr();
                    jinjaValue.setJinjaBlock(printBlock);
                    positionSubtree(jinjaValue, ctx);
                    return jinjaValue;
                }
            }
            PlainValue plain = new PlainValue();
            if (text.length() >= 2 && (text.startsWith("\"") || text.startsWith("'")))
                plain.setText(text.substring(1, text.length() - 1));
            else
                plain.setText(text);
            return position(plain, ctx);
        }

        @Override
        public AttributeValue visitJinjaValueExpr(JinjaParser.JinjaValueExprContext ctx) {
            JinjaValueExpr jinjaValue = new JinjaValueExpr();
            if (ctx.jinjaBlock() != null) jinjaValue.setJinjaBlock((JinjaBlock) visit(ctx.jinjaBlock()));
            return jinjaValue;
        }

        @Override
        public HtmlText visitHtmlText(JinjaParser.HtmlTextContext ctx) {
            HtmlText text = new HtmlText();
            text.setLine(ctx.getStart().getLine());
            if (ctx.HTML_TEXT() != null) text.setText(ctx.HTML_TEXT().getText());
            return text;
        }

        // ── Jinja blocks ─────────────────────────────────────────────────────

        @Override
        public ControlBlock visitControlBlock(JinjaParser.ControlBlockContext ctx) {
            ControlBlock control = new ControlBlock();
            control.setLine(ctx.getStart().getLine());
            control.setJinjaStatementHeader((JinjaStatementHeader) visit(ctx.jinjaStatementHeader()));
            return control;
        }

        @Override
        public PrintBlock visitPrintBlock(JinjaParser.PrintBlockContext ctx) {
            PrintBlock print = new PrintBlock();
            print.setLine(ctx.getStart().getLine());
            print.setJinjaExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return print;
        }

        // ── Jinja statement headers ──────────────────────────────────────────

        @Override
        public Extends visitExtends(JinjaParser.ExtendsContext ctx) {
            Extends ext = new Extends();
            if (ctx.JINJA_STRING() != null) ext.setString(ctx.JINJA_STRING().getText());
            return ext;
        }

        @Override
        public BlockStart visitBlockStart(JinjaParser.BlockStartContext ctx) {
            BlockStart block = new BlockStart();
            if (ctx.JINJA_IDENTIFIER() != null) {
                String name = ctx.JINJA_IDENTIFIER().getText();
                block.setIdentifier(name);
                symbolTable.add(new Symbol(name, "block",
                        symbolTable.getCurrentScopeLevel(), ctx.getStart().getLine()));
            }
            symbolTable.openScope("block_" + block.getIdentifier());
            return block;
        }

        @Override
        public BlockEnd visitBlockEnd(JinjaParser.BlockEndContext ctx) {
            // An unbalanced {% endblock %} must not pop the global scope: the
            // semantic balance pass reports it, the visitor just stays stable.
            if (symbolTable.getCurrentScopeLevel() > 0) symbolTable.closeScope();
            return new BlockEnd();
        }

        @Override
        public If visitIf(JinjaParser.IfContext ctx) {
            If ifStmt = new If();
            ifStmt.setExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return ifStmt;
        }

        @Override
        public Else visitElse(JinjaParser.ElseContext ctx) { return new Else(); }

        @Override
        public EndIf visitEndIf(JinjaParser.EndIfContext ctx) { return new EndIf(); }

        @Override
        public For visitFor(JinjaParser.ForContext ctx) {
            For forStmt = new For();
            symbolTable.openScope("for_loop");
            if (ctx.JINJA_IDENTIFIER() != null) {
                String varName = ctx.JINJA_IDENTIFIER().getText();
                forStmt.setIdentifier(varName);
                symbolTable.add(new Symbol(varName, "loop_variable",
                        symbolTable.getCurrentScopeLevel(), ctx.getStart().getLine()));
            }
            forStmt.setExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return forStmt;
        }

        @Override
        public EndFor visitEndFor(JinjaParser.EndForContext ctx) {
            if (symbolTable.getCurrentScopeLevel() > 0) symbolTable.closeScope();
            return new EndFor();
        }

        // ── Jinja expressions ────────────────────────────────────────────────

        @Override
        public JinjaExpression visitJinjaExpression(JinjaParser.JinjaExpressionContext ctx) {
            JinjaExpression expr = new JinjaExpression();
            if (ctx.jinjaComparison() != null)
                expr.setPrimary((JinjaPrimary) visit(ctx.jinjaComparison()));
            for (JinjaParser.JinjaFilterContext filterCtx : ctx.jinjaFilter())
                if (filterCtx != null)
                    expr.getFilters().add((JinjaFilter) visit(filterCtx));
            return expr;
        }

        @Override
        public JinjaPrimary visitJinjaComparison(JinjaParser.JinjaComparisonContext ctx) {
            return buildJinjaBinary(ctx.jinjaAdditive(), ctx);
        }

        @Override
        public JinjaPrimary visitJinjaAdditive(JinjaParser.JinjaAdditiveContext ctx) {
            return buildJinjaBinary(ctx.jinjaMultiplicative(), ctx);
        }

        @Override
        public JinjaPrimary visitJinjaMultiplicative(JinjaParser.JinjaMultiplicativeContext ctx) {
            return buildJinjaBinary(ctx.jinjaPrimary(), ctx);
        }

        private JinjaPrimary buildJinjaBinary(List<? extends ParserRuleContext> operands,
                                              ParserRuleContext context) {
            if (operands == null || operands.isEmpty()) return null;
            JinjaPrimary left = (JinjaPrimary) visit(operands.get(0));
            for (int i = 1; i < operands.size(); i++) {
                JinjaBinaryExpr binary = new JinjaBinaryExpr();
                binary.setLine(context.getStart().getLine());
                binary.setLeft(left);
                binary.setOperator(context.getChild(2 * i - 1).getText());
                binary.setRight((JinjaPrimary) visit(operands.get(i)));
                left = binary;
            }
            return left;
        }

        @Override
        public AccessExpr visitAccessExpr(JinjaParser.AccessExprContext ctx) {
            AccessExpr access = new AccessExpr();
            access.setChain((JinjaIdentifierChain) visit(ctx.jinjaIdentifierChain()));
            return access;
        }

        @Override
        public StringLiteral visitStringLiteral(JinjaParser.StringLiteralContext ctx) {
            StringLiteral str = new StringLiteral();
            if (ctx.JINJA_STRING() != null) str.setString(ctx.JINJA_STRING().getText());
            return str;
        }

        @Override
        public NumberLiteral visitNumberLiteral(JinjaParser.NumberLiteralContext ctx) {
            NumberLiteral num = new NumberLiteral();
            if (ctx.JINJA_NUMBER() != null) num.setNumber(ctx.JINJA_NUMBER().getText());
            return num;
        }

        @Override
        public TrueLiteral  visitTrueLiteral(JinjaParser.TrueLiteralContext ctx)   { return new TrueLiteral(); }
        @Override
        public FalseLiteral visitFalseLiteral(JinjaParser.FalseLiteralContext ctx) { return new FalseLiteral(); }
        @Override
        public NoneLiteral  visitNoneLiteral(JinjaParser.NoneLiteralContext ctx)   { return new NoneLiteral(); }

        @Override
        public FunctionCall visitFunctionCall(JinjaParser.FunctionCallContext ctx) {
            FunctionCall func = new FunctionCall();
            if (ctx.JINJA_IDENTIFIER() != null) func.setIdentifier(ctx.JINJA_IDENTIFIER().getText());
            if (ctx.jinjaCallArgs()     != null) func.setCallArgs(buildJinjaCallArgs(ctx.jinjaCallArgs()));
            return func;
        }

        @Override
        public JinjaParenthesizedExpr visitParenthesizedExpr(JinjaParser.ParenthesizedExprContext ctx) {
            JinjaParenthesizedExpr expr = new JinjaParenthesizedExpr();
            expr.setExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return expr;
        }

        @Override
        public JinjaIdentifierChain visitJinjaIdentifierChain(JinjaParser.JinjaIdentifierChainContext ctx) {
            if (ctx.getParent() instanceof JinjaParser.FunctionCallContext) return null;
            JinjaIdentifierChain chain = new JinjaIdentifierChain();
            if (!ctx.JINJA_IDENTIFIER().isEmpty())
                chain.setIdentifier(ctx.JINJA_IDENTIFIER(0).getText());
            int dotSize = ctx.JINJA_DOT().size();
            for (int i = 0; i < dotSize; i++) {
                if (i + 1 < ctx.JINJA_IDENTIFIER().size()) {
                    DotAccess dot = new DotAccess();
                    dot.setIdentifier(ctx.JINJA_IDENTIFIER(i + 1).getText());
                    chain.getAccesses().add(position(dot, ctx));
                }
            }
            for (JinjaParser.JinjaExpressionContext exprCtx : ctx.jinjaExpression()) {
                if (exprCtx != null) {
                    IndexAccess index = new IndexAccess();
                    index.setExpression((JinjaExpression) visit(exprCtx));
                    chain.getAccesses().add(position(index, ctx));
                }
            }
            return chain;
        }

        @Override
        public JinjaFilter visitJinjaFilter(JinjaParser.JinjaFilterContext ctx) {
            JinjaFilter filter = new JinjaFilter();
            if      (ctx.JINJA_IDENTIFIER() != null) filter.setName(ctx.JINJA_IDENTIFIER().getText());
            else if (ctx.JINJA_FORMAT()     != null) filter.setName(ctx.JINJA_FORMAT().getText());
            if (ctx.jinjaCallArgs() != null) filter.setArgs(buildJinjaCallArgs(ctx.jinjaCallArgs()));
            else                             filter.setArgs(position(new EmptyArgs(), ctx));
            return filter;
        }

        private JinjaCallArgs buildJinjaCallArgs(JinjaParser.JinjaCallArgsContext ctx) {
            if (ctx == null) return new EmptyArgs();
            Object args = visit(ctx);
            if (args instanceof JinjaCallArgs callArgs) return callArgs;

            // Defensive fallback for default visitor behavior: wrap a single child arg.
            if (args instanceof JinjaArg arg) {
                CallMixedArgs mixedArgs = new CallMixedArgs();
                mixedArgs.getPosArgs().add(arg);
                return mixedArgs;
            }
            if (args instanceof JinjaKwArg kwArg) {
                CallKwArgs kwArgs = new CallKwArgs();
                kwArgs.getKwArgs().add(kwArg);
                return kwArgs;
            }
            return new EmptyArgs();
        }

        @Override
        public JinjaCallArgs visitEmptyArgs(JinjaParser.EmptyArgsContext ctx) { return new EmptyArgs(); }

        @Override
        public JinjaCallArgs visitCallMixedArgs(JinjaParser.CallMixedArgsContext ctx) {
            CallMixedArgs args = new CallMixedArgs();
            for (JinjaParser.JinjaArgContext argCtx : ctx.jinjaArg()) {
                if (argCtx != null) args.getPosArgs().add((JinjaArg) visit(argCtx));
            }
            for (JinjaParser.JinjaKwArgContext kwArgCtx : ctx.jinjaKwArg()) {
                if (kwArgCtx != null) args.getKwArgs().add((JinjaKwArg) visit(kwArgCtx));
            }
            return args;
        }

        @Override
        public JinjaCallArgs visitCallKwArgs(JinjaParser.CallKwArgsContext ctx) {
            CallKwArgs args = new CallKwArgs();
            for (JinjaParser.JinjaKwArgContext kwArgCtx : ctx.jinjaKwArg()) {
                if (kwArgCtx != null) args.getKwArgs().add((JinjaKwArg) visit(kwArgCtx));
            }
            return args;
        }

        @Override
        public JinjaArg visitJinjaArg(JinjaParser.JinjaArgContext ctx) {
            JinjaArg arg = new JinjaArg();
            if (ctx.jinjaExpression() != null)
                arg.setExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return arg;
        }

        @Override
        public JinjaKwArg visitJinjaKwArg(JinjaParser.JinjaKwArgContext ctx) {
            JinjaKwArg kw = new JinjaKwArg();
            if (ctx.JINJA_IDENTIFIER() != null) kw.setIdentifier(ctx.JINJA_IDENTIFIER().getText());
            if (ctx.jinjaExpression()  != null)
                kw.setExpression((JinjaExpression) visit(ctx.jinjaExpression()));
            return kw;
        }

        // ── CSS ──────────────────────────────────────────────────────────────

        @Override
        public CssRule visitCssRule(JinjaParser.CssRuleContext ctx) {
            CssRule rule = new CssRule();
            if (ctx.cssSelectorList() != null)
                rule.setSelectorList((CssSelectorList) visit(ctx.cssSelectorList()));
            for (JinjaParser.CssPropertyContext propCtx : ctx.cssProperty())
                if (propCtx != null)
                    rule.getProperties().add((CssProperty) visit(propCtx));
            return rule;
        }

        @Override
        public CssSelectorList visitCssSelectorList(JinjaParser.CssSelectorListContext ctx) {
            CssSelectorList list = new CssSelectorList();
            for (JinjaParser.CssSelectorContext selCtx : ctx.cssSelector())
                if (selCtx != null)
                    list.getSelectors().add((CssSelector) visit(selCtx));
            return list;
        }

        @Override
        public CssSelector visitCssSelector(JinjaParser.CssSelectorContext ctx) {
            CssSelector sel = new CssSelector();
            for (JinjaParser.SelectorPartContext partCtx : ctx.selectorPart())
                if (partCtx != null) sel.getParts().add((SelectorPart) visit(partCtx));
            if (ctx.CSS_COLON() != null && ctx.CSS_WORD() != null) {
                CssPseudo pseudo = new CssPseudo();
                pseudo.setWord(ctx.CSS_WORD().getText());
                sel.setPseudo(position(pseudo, ctx));
            }
            for (SelectorPart part : sel.getParts()) {
                String selectorName = "";
                if      (part instanceof ClassPart) selectorName = ((ClassPart) part).getWord();
                else if (part instanceof TagPart)   selectorName = ((TagPart)   part).getWord();
                if (!selectorName.isEmpty() && symbolTable.lookup(selectorName) == null) {
                    symbolTable.addToGlobalScope(
                            new Symbol(selectorName, "selector", 0, ctx.getStart().getLine()));
                }
            }
            return sel;
        }

        @Override
        public SelectorPart visitClassPart(JinjaParser.ClassPartContext ctx) {
            ClassPart p = new ClassPart();
            if (ctx.CSS_WORD() != null) p.setWord(ctx.CSS_WORD().getText());
            return p;
        }

        @Override
        public SelectorPart visitTagPart(JinjaParser.TagPartContext ctx) {
            TagPart p = new TagPart();
            if (ctx.CSS_WORD() != null) p.setWord(ctx.CSS_WORD().getText());
            return p;
        }

        @Override
        public CssProperty visitCssProperty(JinjaParser.CssPropertyContext ctx) {
            CssProperty prop = new CssProperty();
            if (ctx.CSS_WORD() != null)  prop.setWord(ctx.CSS_WORD().getText());
            if (ctx.valueList() != null) prop.setValueList((ValueList) visit(ctx.valueList()));
            return prop;
        }

        @Override
        public ValueList visitValueList(JinjaParser.ValueListContext ctx) {
            ValueList list = new ValueList();
            for (JinjaParser.CssValueContext valCtx : ctx.cssValue())
                if (valCtx != null) list.getValues().add((CssValue) visit(valCtx));
            return list;
        }

        @Override
        public WordValue visitWordValue(JinjaParser.WordValueContext ctx) {
            WordValue w = new WordValue();
            if (ctx.CSS_WORD() != null) w.setWord(ctx.CSS_WORD().getText());
            return w;
        }

        @Override
        public NumberValue visitNumberValue(JinjaParser.NumberValueContext ctx) {
            NumberValue n = new NumberValue();
            if (ctx.CSS_NUMBER() != null) n.setNumber(ctx.CSS_NUMBER().getText());
            return n;
        }

        @Override
        public ColorValue visitColorValue(JinjaParser.ColorValueContext ctx) {
            ColorValue c = new ColorValue();
            if (ctx.CSS_COLOR() != null) c.setColor(ctx.CSS_COLOR().getText());
            return c;
        }

        @Override
        public StringValue visitStringValue(JinjaParser.StringValueContext ctx) {
            StringValue s = new StringValue();
            if (ctx.CSS_STRING() != null) s.setString(ctx.CSS_STRING().getText());
            return s;
        }

        @Override
        public FunctionValue visitFunctionValue(JinjaParser.FunctionValueContext ctx) {
            FunctionValue fv = new FunctionValue();
            if (ctx.cssFunction() != null) fv.setFunction((CssFunction) visit(ctx.cssFunction()));
            return fv;
        }

        @Override
        public CssFunction visitCssFunction(JinjaParser.CssFunctionContext ctx) {
            CssFunction func = new CssFunction();
            if (ctx.CSS_WORD()  != null) func.setWord(ctx.CSS_WORD().getText());
            if (ctx.valueList() != null) func.setValueList((ValueList) visit(ctx.valueList()));
            return func;
        }

        // ── Private helpers ──────────────────────────────────────────────────

        private JinjaExpression parseJinjaContent(String content) {
            JinjaExpression expr = new JinjaExpression();
            content = content.trim();
            String[] parts = content.split("\\|");
            expr.setPrimary(parseJinjaPrimary(parts[0].trim()));
            for (int i = 1; i < parts.length; i++)
                expr.getFilters().add(parseJinjaFilter(parts[i].trim()));
            return expr;
        }

        private JinjaPrimary parseJinjaPrimary(String content) {
            content = content.trim();
            if (content.matches("[a-zA-Z_][a-zA-Z0-9_]*\\(.*\\)"))
                return parseFunctionCall(content);
            if (content.contains("."))
                return parseAccessExpr(content);
            if ((content.startsWith("'") && content.endsWith("'"))
                    || (content.startsWith("\"") && content.endsWith("\""))) {
                StringLiteral lit = new StringLiteral();
                lit.setString(content.substring(1, content.length() - 1));
                return lit;
            }
            if (content.matches("\\d+(\\.\\d+)?")) {
                NumberLiteral lit = new NumberLiteral();
                lit.setNumber(content);
                return lit;
            }
            if (content.equals("true"))  return new TrueLiteral();
            if (content.equals("false")) return new FalseLiteral();
            if (content.equals("none"))  return new NoneLiteral();
            if (content.contains("+") || content.contains("-")
                    || content.contains("*") || content.contains("/"))
                return parseBinaryExpr(content);
            if (content.contains(" if ") && content.contains(" else "))
                return parseConditionalExpr(content);
            if (content.contains(" or ") || content.contains(" and "))
                return parseLogicalExpr(content);
            AccessExpr access = new AccessExpr();
            JinjaIdentifierChain chain = new JinjaIdentifierChain();
            chain.setIdentifier(content);
            access.setChain(chain);
            return access;
        }

        private FunctionCall parseFunctionCall(String funcCallStr) {
            int parenIdx = funcCallStr.indexOf('(');
            FunctionCall fc = new FunctionCall();
            fc.setIdentifier(funcCallStr.substring(0, parenIdx).trim());
            fc.setCallArgs(parseArgs(
                    funcCallStr.substring(parenIdx + 1, funcCallStr.lastIndexOf(')')).trim()));
            return fc;
        }

        private JinjaCallArgs parseArgs(String argsStr) {
            if (argsStr.trim().isEmpty()) return new EmptyArgs();
            CallMixedArgs callArgs = new CallMixedArgs();
            for (String arg : splitArgs(argsStr)) {
                arg = arg.trim();
                if (arg.contains("=")) {
                    String[] kw = arg.split("=", 2);
                    JinjaKwArg kwArg = new JinjaKwArg();
                    kwArg.setIdentifier(kw[0].trim());
                    kwArg.setExpression(parseJinjaContent(kw[1].trim()));
                    callArgs.getKwArgs().add(kwArg);
                } else {
                    JinjaArg posArg = new JinjaArg();
                    posArg.setExpression(parseJinjaContent(arg));
                    callArgs.getPosArgs().add(posArg);
                }
            }
            return callArgs;
        }

        private List<String> splitArgs(String argsStr) {
            List<String> args = new ArrayList<>();
            int start = 0, parenCount = 0, bracketCount = 0;
            for (int i = 0; i < argsStr.length(); i++) {
                char c = argsStr.charAt(i);
                if      (c == '(') parenCount++;
                else if (c == ')') parenCount--;
                else if (c == '[') bracketCount++;
                else if (c == ']') bracketCount--;
                if (c == ',' && parenCount == 0 && bracketCount == 0) {
                    args.add(argsStr.substring(start, i).trim());
                    start = i + 1;
                }
            }
            if (start < argsStr.length()) args.add(argsStr.substring(start).trim());
            return args;
        }

        private AccessExpr parseAccessExpr(String accessStr) {
            AccessExpr access = new AccessExpr();
            JinjaIdentifierChain chain = new JinjaIdentifierChain();
            List<String> parts = new ArrayList<>();
            StringBuilder cur = new StringBuilder();
            int bracketCount = 0;
            for (int i = 0; i < accessStr.length(); i++) {
                char c = accessStr.charAt(i);
                if      (c == '[') { bracketCount++; cur.append(c); }
                else if (c == ']') { bracketCount--; cur.append(c); }
                else if (c == '.' && bracketCount == 0) { parts.add(cur.toString()); cur = new StringBuilder(); }
                else cur.append(c);
            }
            if (cur.length() > 0) parts.add(cur.toString());
            if (!parts.isEmpty()) {
                chain.setIdentifier(parts.get(0).trim());
                for (int i = 1; i < parts.size(); i++) {
                    String part = parts.get(i).trim();
                    if (part.startsWith("[") && part.endsWith("]")) {
                        IndexAccess ia = new IndexAccess();
                        ia.setExpression(parseJinjaContent(part.substring(1, part.length() - 1)));
                        chain.getAccesses().add(ia);
                    } else {
                        DotAccess da = new DotAccess();
                        da.setIdentifier(part);
                        chain.getAccesses().add(da);
                    }
                }
            }
            access.setChain(chain);
            return access;
        }

        private JinjaFilter parseJinjaFilter(String filterStr) {
            JinjaFilter filter = new JinjaFilter();
            if (filterStr.contains("(")) {
                int parenIdx = filterStr.indexOf('(');
                filter.setName(filterStr.substring(0, parenIdx).trim());
                filter.setArgs(parseArgs(
                        filterStr.substring(parenIdx + 1, filterStr.lastIndexOf(')')).trim()));
            } else {
                filter.setName(filterStr);
                filter.setArgs(new EmptyArgs());
            }
            return filter;
        }

        private JinjaPrimary parseBinaryExpr(String expr)     { StringLiteral l = new StringLiteral(); l.setString(expr); return l; }
        private JinjaPrimary parseConditionalExpr(String expr) { StringLiteral l = new StringLiteral(); l.setString(expr); return l; }
        private JinjaPrimary parseLogicalExpr(String expr)     { StringLiteral l = new StringLiteral(); l.setString(expr); return l; }

    } // end JinjaBaseVisitor


    // ══════════════════════════════════════════════════════════════════════════
    //  PYTHON VISITOR
    // ══════════════════════════════════════════════════════════════════════════

    public static class PyBaseVisitor extends pyParserBaseVisitor<Object> {

        private final SymbolTable symbolTable = new SymbolTable();

        public SymbolTable getSymbolTable() { return symbolTable; }

        // ── Helpers ───────────────────────────────────────────────────────────

        private void setLine(Object node, ParserRuleContext ctx) {
            if (node instanceof PyProgram p) {
                p.setLineNumber(ctx.getStart().getLine());
                p.setColumn(ctx.getStart().getCharPositionInLine());
            }
        }

        private String stripQuotes(String s) {
            if (s == null || s.length() < 2) return s;
            char first = s.charAt(0), last = s.charAt(s.length() - 1);
            return ((first == '"' && last == '"') || (first == '\'' && last == '\''))
                    ? s.substring(1, s.length() - 1) : s;
        }

        // ── Program ──────────────────────────────────────────────────────────

        @Override
        public PyProgram visitPyProgram(pyParser.PyProgramContext ctx) {
            symbolTable.initGlobal();
            PyProgram program = new PyProgram() {};
            setLine(program, ctx);
            for (pyParser.StatementContext stCtx : ctx.statement()) {
                if (stCtx != null) {
                    Statement stmt = (Statement) visit(stCtx);
                    if (stmt != null) program.addStatement(stmt);
                }
            }
            return program;
        }

        // ── Statements ───────────────────────────────────────────────────────

        @Override
        public Statement visitImportStatement(pyParser.ImportStatementContext ctx) {
            ImportStmt stmt = (ImportStmt) visit(ctx.import_stmt());
            return stmt;
        }

        @Override
        public Statement visitAssignmentStatement(pyParser.AssignmentStatementContext ctx) {
            AssignStmt stmt = (AssignStmt) visit(ctx.assignment());
            symbolTable.addSymbol(stmt.getName(), "Assignment", "variable", stmt.getLineNumber());
            return stmt;
        }

        @Override
        public Statement visitReturnStatement(pyParser.ReturnStatementContext ctx) {
            ReturnStmt stmt = (ReturnStmt) visit(ctx.return_stmt());
            return stmt;
        }

        @Override
        public Statement visitExprStatement(pyParser.ExprStatementContext ctx) {
            ExprStmt stmt = (ExprStmt) visit(ctx.expr_stmt());
            return stmt;
        }

        @Override
        public Statement visitRouteStatement(pyParser.RouteStatementContext ctx) {
            RouteStatement stmt = (RouteStatement) visit(ctx.route_def());
            return stmt;
        }

        @Override
        public Statement visitFuncDefStatement(pyParser.FuncDefStatementContext ctx) {
            FuncDefStatement stmt = (FuncDefStatement) visit(ctx.func_def());
            symbolTable.addSymbol(stmt.getName(), "FuncDef", "function", stmt.getLineNumber());
            return stmt;
        }

        @Override
        public Statement visitIfStatement(pyParser.IfStatementContext ctx) {
            IfStatement stmt = (IfStatement) visit(ctx.if_stmt());
            return stmt;
        }

        @Override
        public Statement visitForStatement(pyParser.ForStatementContext ctx) {
            ForStatement stmt = (ForStatement) visit(ctx.for_stmt());
            return stmt;
        }

        // ── Import ────────────────────────────────────────────────────────────

        @Override
        public ImportStmt visitImport_stmt(pyParser.Import_stmtContext ctx) {
            ImportStmt node = new ImportStmt();
            setLine(node, ctx);
            if (!ctx.dotted_name().isEmpty())
                node.setDottedName((DottedName) visit(ctx.dotted_name(0)));
            node.setImportList((ImportList) visit(ctx.import_list()));
            return node;
        }

        @Override
        public DottedName visitDotted_name(pyParser.Dotted_nameContext ctx) {
            DottedName node = new DottedName();
            setLine(node, ctx);
            for (var id : ctx.ID()) node.addPart(id.getText());
            return node;
        }

        @Override
        public ImportList visitImport_list(pyParser.Import_listContext ctx) {
            ImportList node = new ImportList();
            setLine(node, ctx);
            for (var itemCtx : ctx.import_item()) node.addItem((ImportItem) visit(itemCtx));
            return node;
        }

        @Override
        public ImportItem visitImport_item(pyParser.Import_itemContext ctx) {
            ImportItem node = new ImportItem();
            setLine(node, ctx);
            node.setName(ctx.identifier().getText());
            if (ctx.AS() != null && ctx.ID() != null) node.setAlias(ctx.ID().getText());
            return node;
        }

        // ── Assignment ────────────────────────────────────────────────────────

        @Override
        public AssignStmt visitAssignment(pyParser.AssignmentContext ctx) {
            AssignStmt node = new AssignStmt();
            setLine(node, ctx);
            node.setName(ctx.getChild(0).getText());
            node.setValue((Expression) visit(ctx.expr()));
            return node;
        }

        // ── Function definition ───────────────────────────────────────────────

        @Override
        public FuncDefStatement visitFunc_def(pyParser.Func_defContext ctx) {
            FuncDefStatement node = new FuncDefStatement();
            setLine(node, ctx);
            String name = ctx.ID().getText();
            node.setName(name);
            symbolTable.enterScope("func_" + name);
            ArrayList<String> params = new ArrayList<>();
            if (ctx.param_list() != null)
                for (var id : ctx.param_list().ID()) params.add(id.getText());
            node.setParams(params);
            for (String p : params)
                symbolTable.addSymbol(p, "Param", "parameter", ctx.getStart().getLine());
            node.setBody((Suite) visit(ctx.suite()));
            symbolTable.exitScope();
            return node;
        }

        // ── Suites ────────────────────────────────────────────────────────────

        @Override
        public Suite visitIndentedSuite(pyParser.IndentedSuiteContext ctx) {
            IndentedSuite node = new IndentedSuite();
            setLine(node, ctx);
            symbolTable.enterScope("suite_" + ctx.getStart().getLine());
            for (pyParser.StatementContext stCtx : ctx.statement()) {
                Statement s = (Statement) visit(stCtx);
                if (s != null) node.addStatement(s);
            }
            symbolTable.exitScope();
            return node;
        }

        @Override
        public Suite visitSimpleSuite(pyParser.SimpleSuiteContext ctx) {
            SimpleSuite node = new SimpleSuite();
            setLine(node, ctx);
            node.setStatement((Statement) visit(ctx.statement()));
            return node;
        }

        // ── Route ─────────────────────────────────────────────────────────────

        @Override
        public RouteStatement visitRoute_def(pyParser.Route_defContext ctx) {
            RouteStatement node = new RouteStatement();
            setLine(node, ctx);
            node.setRoutePath((RoutePath) visit(ctx.route_path()));
            if (ctx.route_params() != null)
                node.setRouteParams((RouteParams) visit(ctx.route_params()));
            node.setFuncDef((FuncDefStatement) visit(ctx.func_def()));
            return node;
        }

        @Override
        public RoutePath visitRoute_path(pyParser.Route_pathContext ctx) {
            RoutePath node = new RoutePath();
            setLine(node, ctx);
            node.setPath(stripQuotes(ctx.getText()));
            return node;
        }

        @Override
        public RouteParams visitRoute_params(pyParser.Route_paramsContext ctx) {
            RouteParams node = new RouteParams();
            setLine(node, ctx);
            node.setMethodsList((ListLiteral) visit(ctx.list_literal()));
            return node;
        }

        // ── Control flow ──────────────────────────────────────────────────────

        @Override
        public IfStatement visitIf_stmt(pyParser.If_stmtContext ctx) {
            IfStatement node = new IfStatement();
            setLine(node, ctx);
            node.setCondition((Expression) visit(ctx.expr()));
            node.setThenSuite((Suite) visit(ctx.suite(0)));
            if (ctx.ELSE() != null) node.setElseSuite((Suite) visit(ctx.suite(1)));
            return node;
        }

        @Override
        public ForStatement visitFor_stmt(pyParser.For_stmtContext ctx) {
            ForStatement node = new ForStatement();
            setLine(node, ctx);
            String varName = ctx.ID().getText();
            node.setVarName(varName);
            node.setExpression((Expression) visit(ctx.expr()));
            node.setForBlock((Suite) visit(ctx.suite()));
            symbolTable.addSymbol(varName, "ForVar", "loop_variable", ctx.getStart().getLine());
            return node;
        }

        @Override
        public ReturnStmt visitReturn_stmt(pyParser.Return_stmtContext ctx) {
            ReturnStmt node = new ReturnStmt();
            setLine(node, ctx);
            if (ctx.return_args() != null)
                node.setReturnArgs((List<Expression>) visit(ctx.return_args()));
            return node;
        }

        @Override
        public List<Expression> visitReturn_args(pyParser.Return_argsContext ctx) {
            List<Expression> args = new ArrayList<>();
            for (pyParser.ExprContext eCtx : ctx.expr()) args.add((Expression) visit(eCtx));
            return args;
        }

        @Override
        public ExprStmt visitExpr_stmt(pyParser.Expr_stmtContext ctx) {
            ExprStmt node = new ExprStmt();
            setLine(node, ctx);
            node.setExpr((Expression) visit(ctx.expr()));
            return node;
        }

        // ── Expressions ──────────────────────────────────────────────────────

        private Expression buildBinary(ParserRuleContext ctx,
                                       List<? extends ParserRuleContext> subExprList) {
            if (subExprList.isEmpty()) return null;
            Expression left = (Expression) visit(subExprList.get(0));
            for (int i = 1; i < subExprList.size(); i++) {
                ParseTree opNode = ctx.getChild(2 * i - 1);
                String op = (opNode != null) ? opNode.getText() : "";
                BinaryExpr bin = new BinaryExpr();
                setLine(bin, ctx);
                bin.setLeft(left);
                bin.setOp(op);
                bin.setRight((Expression) visit(subExprList.get(i)));
                left = bin;
            }
            return left;
        }

        @Override
        public Expression visitCondExpr(pyParser.CondExprContext ctx) {
            CondExpr node = new CondExpr();
            setLine(node, ctx);
            node.setThenExpr((Expression) visit(ctx.orExpr(0)));
            if (ctx.IF() != null) {
                node.setCondition((Expression) visit(ctx.orExpr(1)));
                node.setElseExpr((Expression)  visit(ctx.orExpr(2)));
            }
            return node;
        }

        @Override
        public Expression visitOrPassExpr(pyParser.OrPassExprContext ctx) {
            OrPassExpr node = new OrPassExpr();
            setLine(node, ctx);
            node.setInner((Expression) visit(ctx.equalityExpr()));
            return node;
        }

        @Override
        public Expression visitEqualityExpr(pyParser.EqualityExprContext ctx)       { return buildBinary(ctx, ctx.relationalExpr()); }
        @Override
        public Expression visitRelationalExpr(pyParser.RelationalExprContext ctx)   { return buildBinary(ctx, ctx.additiveExpr()); }
        @Override
        public Expression visitAdditiveExpr(pyParser.AdditiveExprContext ctx)       { return buildBinary(ctx, ctx.multiplicativeExpr()); }
        @Override
        public Expression visitMultiplicativeExpr(pyParser.MultiplicativeExprContext ctx) { return buildBinary(ctx, ctx.unaryExpr()); }

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
            for (pyParser.PostfixOpContext opCtx : ctx.postfixOp())
                node.addOp((PostfixOp) visit(opCtx));
            return node;
        }

        // ── Postfix ops ───────────────────────────────────────────────────────

        @Override
        public PostfixOp visitCallPostfix(pyParser.CallPostfixContext ctx) {
            CallPostfix node = new CallPostfix();
            setLine(node, ctx);
            if (ctx.arg_list() != null) node.setArgList((ArgList) visit(ctx.arg_list()));
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

        // ── Primary expressions ───────────────────────────────────────────────

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
            TrueExpr node = new TrueExpr(); setLine(node, ctx); return node;
        }

        @Override
        public PrimaryExpr visitFalseLiteralExpr(pyParser.FalseLiteralExprContext ctx) {
            FalseExpr node = new FalseExpr(); setLine(node, ctx); return node;
        }

        @Override
        public PrimaryExpr visitNoneLiteralExpr(pyParser.NoneLiteralExprContext ctx) {
            NoneExpr node = new NoneExpr(); setLine(node, ctx); return node;
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
            node.setListLiteral((ListLiteral) visit(ctx.list_literal()));
            return node;
        }

        @Override
        public PrimaryExpr visitDictLiteralExpr(pyParser.DictLiteralExprContext ctx) {
            DictLiteralExpr node = new DictLiteralExpr();
            setLine(node, ctx);
            node.setDictLiteral((DictLiteral) visit(ctx.dict_literal()));
            return node;
        }

        @Override
        public PrimaryExpr visitGeneratorPrimaryExpr(pyParser.GeneratorPrimaryExprContext ctx) {
            GeneratorPrimaryExpr node = new GeneratorPrimaryExpr();
            setLine(node, ctx);
            node.setGeneratorExpr((GeneratorExpr) visit(ctx.generator_expr()));
            return node;
        }

        @Override
        public PrimaryExpr visitParenExpr(pyParser.ParenExprContext ctx) {
            ParenExpr node = new ParenExpr();
            setLine(node, ctx);
            node.setInner((Expression) visit(ctx.expr()));
            return node;
        }

        // ── Literals & containers ─────────────────────────────────────────────

        @Override
        public GeneratorExpr visitGenerator_expr(pyParser.Generator_exprContext ctx) {
            GeneratorExpr node = new GeneratorExpr();
            setLine(node, ctx);
            node.setYieldName(ctx.ID(0).getText());
            node.setLoopVarName(ctx.ID(1).getText());
            node.setIterable((Expression) visit(ctx.expr(0)));
            if (ctx.IF() != null && ctx.expr().size() > 1)
                node.setFilter((Expression) visit(ctx.expr(1)));
            return node;
        }

        @Override
        public ListLiteral visitList_literal(pyParser.List_literalContext ctx) {
            ListLiteral node = new ListLiteral();
            setLine(node, ctx);
            for (pyParser.ExprContext e : ctx.expr()) node.addElement((Expression) visit(e));
            return node;
        }

        @Override
        public DictLiteral visitDict_literal(pyParser.Dict_literalContext ctx) {
            DictLiteral node = new DictLiteral();
            setLine(node, ctx);
            for (pyParser.Dict_entryContext de : ctx.dict_entry())
                node.addEntry((DictEntry) visit(de));
            return node;
        }

        @Override
        public DictEntry visitDict_entry(pyParser.Dict_entryContext ctx) {
            DictEntry node = new DictEntry();
            setLine(node, ctx);
            node.setKey((Expression)   visit(ctx.expr(0)));
            node.setValue((Expression) visit(ctx.expr(1)));
            return node;
        }

        @Override
        public ArgList visitArg_list(pyParser.Arg_listContext ctx) {
            ArgList node = new ArgList();
            setLine(node, ctx);
            for (pyParser.ArgContext a : ctx.arg()) node.addArg((Arg) visit(a));
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

    } // end PyBaseVisitor

} // end Visitors
