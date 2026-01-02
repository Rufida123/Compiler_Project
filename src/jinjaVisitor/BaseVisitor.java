package jinjaVisitor;
import jinjaSymbolTable.Symbol;
import jinjaSymbolTable.SymbolTable;
import jinjaAntlr.JinjaParser;
import jinjaAntlr.JinjaParserBaseVisitor;
import jinjaAstClasses.*;
import java.util.ArrayList;
import java.util.List;

public class BaseVisitor extends JinjaParserBaseVisitor<Object> {
    private SymbolTable symbolTable = new SymbolTable();
    @Override
    public Document visitDocument(JinjaParser.DocumentContext ctx) {
        Document document = new Document();
        document.setLine(ctx.getStart().getLine());
        for (JinjaParser.ProgramElementContext elemCtx : ctx.programElement()) {
            if (elemCtx != null) {
                ProgramElement element = (ProgramElement) visitProgramElement(elemCtx);
                if (element != null) {
                    document.getHtmlElements().add(element);
                }
            }
        }
        // طباعة جدول الرموز النهائي
        symbolTable.printTable();
        // طباعة الإحصائيات
        System.out.println(symbolTable.getStatistics());
        return document;
    }
    @Override
    public ProgramElement visitProgramElement(JinjaParser.ProgramElementContext ctx) {
        if (ctx.styleTag() != null) {
            return (ProgramElement) visitStyleTag(ctx.styleTag());
        } else if (ctx.jinjaBlock() != null) {
            return (ProgramElement) visit(ctx.jinjaBlock());
        } else if (ctx.htmlTag() != null) {
            return (ProgramElement) visit(ctx.htmlTag());
        } else if (ctx.htmlText() != null) {
            return (HtmlText) visitHtmlText(ctx.htmlText());
        }
        return null;
    }
    @Override
    public StyleTag visitStyleTag(JinjaParser.StyleTagContext ctx) {
        StyleTag style = new StyleTag();
        style.setLine(ctx.getStart().getLine());
        style.setTagName("<style>");
        for (JinjaParser.CssRuleContext ruleCtx : ctx.cssRule()) {
            if (ruleCtx != null) {
                style.getCssRules().add((CssRule) visit(ruleCtx));
            }
        }
        return style;
    }
    // === HTML Tags ===
    @Override
    public PairedTag visitPairedTag(JinjaParser.PairedTagContext ctx) {
        PairedTag tag = new PairedTag();
        tag.setLine(ctx.getStart().getLine());
        if (ctx.TAG_ID(0) != null) {
            tag.setTagName(ctx.TAG_ID(0).getText());
        }
        for (JinjaParser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
            if (attrCtx != null) {
                HtmlAttribute attribute = (HtmlAttribute) visit(attrCtx);
                if (attribute != null) {
                    tag.getAttributes().add(attribute);
                }
            }
        }
        for (JinjaParser.ProgramElementContext childCtx : ctx.programElement()) {
            if (childCtx != null) {
                ProgramElement child = (ProgramElement) visitProgramElement(childCtx);
                if (child != null) {
                    tag.getChildren().add(child);
                }
            }
        }
        return tag;
    }
    @Override
    public SelfClosingTag visitSelfClosingTag(JinjaParser.SelfClosingTagContext ctx) {
        SelfClosingTag tag = new SelfClosingTag();
        tag.setLine(ctx.getStart().getLine());
        if (ctx.TAG_ID() != null) {
            tag.setTagName(ctx.TAG_ID().getText());
        }
        for (JinjaParser.HtmlAttributeContext attrCtx : ctx.htmlAttribute()) {
            if (attrCtx != null) {
                HtmlAttribute attribute = (HtmlAttribute) visit(attrCtx);
                if (attribute != null) {
                    tag.getAttributes().add(attribute);
                }
            }
        }
        return tag;
    }
    // === HTML Attributes ===
    @Override
    public HtmlAttribute visitNormalAttribute(JinjaParser.NormalAttributeContext ctx) {
        NormalAttribute attr = new NormalAttribute();
        if (ctx.TAG_ID() != null) {
            attr.setKey(ctx.TAG_ID().getText());
        }
        if (ctx.attributeValue() != null) {
            attr.setValue((AttributeValue) visit(ctx.attributeValue()));
        }

        if ("class".equals(attr.getKey()) && attr.getValue() instanceof PlainValue) {
            String classValue = ((PlainValue) attr.getValue()).getText();
            classValue = classValue.replaceAll("^\"|\"$", ""); // إزالة " من البداية والنهاية
            classValue = classValue.trim(); // إزالة مسافات
            if (!classValue.isEmpty()) {
                String[] classes = classValue.split("\\s+");
                for (String className : classes) {
                    className = className.trim();
                    if (!className.isEmpty()) {
                      //  System.out.println("DEBUG: Processing selector '" + className + "'");

                        // الحل الجديد: إضافة في السكوب العالمي دائمًا
                        Symbol existing = symbolTable.lookup(className);

                        if (existing == null) {
                            // 2. إنشاء الرمز
                            Symbol selectorSymbol = new Symbol(className, "selector",
                                    symbolTable.getCurrentScopeLevel(), ctx.getStart().getLine());

                            boolean added = symbolTable.addToGlobalScope(selectorSymbol);
                           // System.out.println("DEBUG: Added to global scope = " + added);

                            if (added) {
                              //  System.out.println("DEBUG: Successfully added selector '" + className + "' to global scope");
                            }
                        } else {
                           // System.out.println("DEBUG: Selector '" + className + "' already exists in scope " + existing.getScopeLevel());
                        }
                    }
                }
            }
        }

        return attr;
    }
    @Override
    public HtmlAttribute visitJinjaAttribute(JinjaParser.JinjaAttributeContext ctx) {
        JinjaAttribute attr = new JinjaAttribute();
        if (ctx.jinjaBlock() != null) {
            attr.setJinjaBlock((JinjaBlock) visit(ctx.jinjaBlock()));
        }
        return attr;
    }
    // === Attribute Values ===
    @Override
    public AttributeValue visitPlainValue(JinjaParser.PlainValueContext ctx) {
        String text = ctx.TAG_ATTR_VALUE().getText();
        // إذا كان النص يحتوي على Jinja
        if (text.contains("{{") || text.contains("{%")) {
            if (text.length() >= 2 && (text.startsWith("\"") || text.startsWith("'"))) {
                text = text.substring(1, text.length() - 1);
            }
            // إذا كان يحتوي على Jinja، عالجه كـ JinjaValueExpr
            JinjaValueExpr jinjaValue = new JinjaValueExpr();
            if (text.trim().startsWith("{{") && text.trim().endsWith("}}")) {
                String jinjaContent = text.trim().substring(2, text.trim().length() - 2).trim();
                // استخدام الدالة المحسنة لتحليل جميع الحالات
                JinjaExpression expr = parseJinjaContent(jinjaContent);
                PrintBlock printBlock = new PrintBlock();
                printBlock.setJinjaExpression(expr);
                jinjaValue.setJinjaBlock(printBlock);
                return jinjaValue;
            }
        }
        // وإلا عالج كـ PlainValue عادي
        PlainValue plain = new PlainValue();
        if (text.length() >= 2 && (text.startsWith("\"") || text.startsWith("'"))) {
            plain.setText(text.substring(1, text.length() - 1));
        } else {
            plain.setText(text);
        }
        return plain;
    }
    @Override
    public AttributeValue visitJinjaValueExpr(JinjaParser.JinjaValueExprContext ctx) {
        JinjaValueExpr jinjaValue = new JinjaValueExpr();
        if (ctx.jinjaBlock() != null) {
            jinjaValue.setJinjaBlock((JinjaBlock) visit(ctx.jinjaBlock()));
        }
        return jinjaValue;
    }
    @Override
    public HtmlText visitHtmlText(JinjaParser.HtmlTextContext ctx) {
        HtmlText text = new HtmlText();
        text.setLine(ctx.getStart().getLine());
        if (ctx.HTML_TEXT() != null) {
            text.setText(ctx.HTML_TEXT().getText());
        }
        return text;
    }
    // === Jinja Blocks ===
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
        print.setJinjaExpression((JinjaExpression) visitJinjaExpression(ctx.jinjaExpression()));
        return print;
    }
    // === Jinja Statement Headers ===
    @Override
    public Extends visitExtends(JinjaParser.ExtendsContext ctx) {
        Extends ext = new Extends();
        if (ctx.JINJA_STRING() != null) {
            ext.setString(ctx.JINJA_STRING().getText());
        }
        return ext;
    }
    @Override
    public BlockStart visitBlockStart(JinjaParser.BlockStartContext ctx) {
        BlockStart block = new BlockStart();
        if (ctx.JINJA_IDENTIFIER() != null) {
            String name = ctx.JINJA_IDENTIFIER().getText();
            block.setIdentifier(name);
            // إضافة block مع اسم السكوب
            Symbol blockSymbol = new Symbol(name, "block",
                    symbolTable.getCurrentScopeLevel(),
                    ctx.getStart().getLine());
            symbolTable.add(blockSymbol);
        }
        // فتح سكوب جديد للـ block
        symbolTable.openScope("block_" + block.getIdentifier());
        return block;
    }
    @Override
    public BlockEnd visitBlockEnd(JinjaParser.BlockEndContext ctx) {
        symbolTable.closeScope();
        return new BlockEnd();
    }
    @Override
    public If visitIf(JinjaParser.IfContext ctx) {
        If ifStmt = new If();
        ifStmt.setExpression((JinjaExpression) visitJinjaExpression(ctx.jinjaExpression()));
        return ifStmt;
    }
    @Override
    public Else visitElse(JinjaParser.ElseContext ctx) {
        return new Else();
    }
    @Override
    public EndIf visitEndIf(JinjaParser.EndIfContext ctx) {
        return new EndIf();
    }
    @Override
    public For visitFor(JinjaParser.ForContext ctx) {
       // System.out.println("DEBUG: visitFor called for variable: " + ctx.JINJA_IDENTIFIER().getText());
        For forStmt = new For();
        // 1. فتح السكوب الجديد أولاً
        symbolTable.openScope("for_loop");
        // 2. الآن أضف الرمز في السكوب الجديد
        if (ctx.JINJA_IDENTIFIER() != null) {
            String varName = ctx.JINJA_IDENTIFIER().getText();
            forStmt.setIdentifier(varName);
            Symbol varSymbol = new Symbol(varName, "loop_variable",
                    symbolTable.getCurrentScopeLevel(),
                    ctx.getStart().getLine());
            symbolTable.add(varSymbol);
        }
        forStmt.setExpression((JinjaExpression) visitJinjaExpression(ctx.jinjaExpression()));
        return forStmt;
    }
    @Override
    public EndFor visitEndFor(JinjaParser.EndForContext ctx) {
        // إغلاق سكوب الـ for loop
        symbolTable.closeScope();
        return new EndFor();
    }
    // === Jinja Expressions ===
    @Override
    public JinjaExpression visitJinjaExpression(JinjaParser.JinjaExpressionContext ctx) {
        JinjaExpression expr = new JinjaExpression();
        if (ctx.jinjaPrimary() != null) {
            expr.setPrimary((JinjaPrimary) visit(ctx.jinjaPrimary()));
        }
        for (JinjaParser.JinjaFilterContext filterCtx : ctx.jinjaFilter()) {
            if (filterCtx != null) {
                expr.getFilters().add((JinjaFilter) visitJinjaFilter(filterCtx));
            }
        }
        return expr;
    }
    @Override
    public AccessExpr visitAccessExpr(JinjaParser.AccessExprContext ctx) {
        AccessExpr access = new AccessExpr();
        access.setChain((JinjaIdentifierChain) visitJinjaIdentifierChain(ctx.jinjaIdentifierChain()));
        // لا نتحقق من وجود المتغير (undefined variable) – ده semantic check إضافي مش مطلوب
        JinjaIdentifierChain chain = access.getChain();
        if (chain != null && chain.getIdentifier() != null) {
            String name = chain.getIdentifier();
            // نحافظ فقط على إضافة المتغير لو كان معرف (مثل في for loop)
            // لكن ما نعملش أي طباعة أو تحذير
            // الفكرة الأساسية (منع التكرار في نفس السكوب) موجودة في add() في SymbolTable
        }
        return access;
    }
    @Override
    public StringLiteral visitStringLiteral(JinjaParser.StringLiteralContext ctx) {
        StringLiteral str = new StringLiteral();
        if (ctx.JINJA_STRING() != null) {
            str.setString(ctx.JINJA_STRING().getText());
        }
        return str;
    }
    @Override
    public NumberLiteral visitNumberLiteral(JinjaParser.NumberLiteralContext ctx) {
        NumberLiteral num = new NumberLiteral();
        if (ctx.JINJA_NUMBER() != null) {
            num.setNumber(ctx.JINJA_NUMBER().getText());
        }
        return num;
    }
    @Override
    public TrueLiteral visitTrueLiteral(JinjaParser.TrueLiteralContext ctx) {
        return new TrueLiteral();
    }
    @Override
    public FalseLiteral visitFalseLiteral(JinjaParser.FalseLiteralContext ctx) {
        return new FalseLiteral();
    }
    @Override
    public NoneLiteral visitNoneLiteral(JinjaParser.NoneLiteralContext ctx) {
        return new NoneLiteral();
    }
    @Override
    public FunctionCall visitFunctionCall(JinjaParser.FunctionCallContext ctx) {
        FunctionCall func = new FunctionCall();
        if (ctx.JINJA_IDENTIFIER() != null) {
            func.setIdentifier(ctx.JINJA_IDENTIFIER().getText());
        }
        if (ctx.jinjaCallArgs() != null) {
            func.setCallArgs((JinjaCallArgs) visit(ctx.jinjaCallArgs()));
        }
        return func;
    }
    @Override
    public JinjaIdentifierChain visitJinjaIdentifierChain(JinjaParser.JinjaIdentifierChainContext ctx) {
        // إذا كان جزء من FunctionCall (مثل url_for(...))، نتخطى
        if (ctx.getParent() instanceof JinjaParser.FunctionCallContext) {
            return null; // دع visitFunctionCall يتعامل معها
        }
        JinjaIdentifierChain chain = new JinjaIdentifierChain();
        if (!ctx.JINJA_IDENTIFIER().isEmpty()) {
            chain.setIdentifier(ctx.JINJA_IDENTIFIER(0).getText());
        }
        int dotSize = ctx.JINJA_DOT().size();
        for (int i = 0; i < dotSize; i++) {
            if (i + 1 < ctx.JINJA_IDENTIFIER().size()) {
                DotAccess dot = new DotAccess();
                dot.setIdentifier(ctx.JINJA_IDENTIFIER(i + 1).getText());
                chain.getAccesses().add(dot);
            }
        }
        for (JinjaParser.JinjaExpressionContext exprCtx : ctx.jinjaExpression()) {
            if (exprCtx != null) {
                IndexAccess index = new IndexAccess();
                index.setExpression((JinjaExpression) visitJinjaExpression(exprCtx));
                chain.getAccesses().add(index);
            }
        }
        return chain;
    }
    @Override
    public JinjaFilter visitJinjaFilter(JinjaParser.JinjaFilterContext ctx) {
        JinjaFilter filter = new JinjaFilter();
        if (ctx.JINJA_IDENTIFIER() != null) {
            filter.setName(ctx.JINJA_IDENTIFIER().getText());
        } else if (ctx.JINJA_FORMAT() != null) {
            filter.setName(ctx.JINJA_FORMAT().getText());
        }
        if (ctx.jinjaCallArgs() != null) {
            filter.setArgs((JinjaCallArgs) visit(ctx.jinjaCallArgs()));
        } else {
            filter.setArgs(new EmptyArgs());
        }
        return filter;
    }
    // === Jinja Call Args ===
    @Override
    public CallMixedArgs visitCallMixedArgs(JinjaParser.CallMixedArgsContext ctx) {
        CallMixedArgs mixed = new CallMixedArgs();
        for (JinjaParser.JinjaArgContext argCtx : ctx.jinjaArg()) {
            mixed.getPosArgs().add((JinjaArg) visitJinjaArg(argCtx));
        }
        for (JinjaParser.JinjaKwArgContext kwCtx : ctx.jinjaKwArg()) {
            mixed.getKwArgs().add((JinjaKwArg) visitJinjaKwArg(kwCtx));
        }
        return mixed;
    }
    @Override
    public CallKwArgs visitCallKwArgs(JinjaParser.CallKwArgsContext ctx) {
        CallKwArgs kw = new CallKwArgs();
        for (JinjaParser.JinjaKwArgContext kwCtx : ctx.jinjaKwArg()) {
            kw.getKwArgs().add((JinjaKwArg) visitJinjaKwArg(kwCtx));
        }
        return kw;
    }
    @Override
    public EmptyArgs visitEmptyArgs(JinjaParser.EmptyArgsContext ctx) {
        return new EmptyArgs();
    }
    @Override
    public JinjaArg visitJinjaArg(JinjaParser.JinjaArgContext ctx) {
        JinjaArg arg = new JinjaArg();
        if (ctx.jinjaExpression() != null) {
            arg.setExpression((JinjaExpression) visitJinjaExpression(ctx.jinjaExpression()));
        }
        return arg;
    }
    @Override
    public JinjaKwArg visitJinjaKwArg(JinjaParser.JinjaKwArgContext ctx) {
        JinjaKwArg kw = new JinjaKwArg();
        if (ctx.JINJA_IDENTIFIER() != null) {
            kw.setIdentifier(ctx.JINJA_IDENTIFIER().getText());
        }
        if (ctx.jinjaExpression() != null) {
            kw.setExpression((JinjaExpression) visitJinjaExpression(ctx.jinjaExpression()));
        }
        return kw;
    }
    // === CSS ===
    @Override
    public CssRule visitCssRule(JinjaParser.CssRuleContext ctx) {
        CssRule rule = new CssRule();
        if (ctx.cssSelectorList() != null) {
            rule.setSelectorList((CssSelectorList) visitCssSelectorList(ctx.cssSelectorList()));
        }
        for (JinjaParser.CssPropertyContext propCtx : ctx.cssProperty()) {
            if (propCtx != null) {
                rule.getProperties().add((CssProperty) visitCssProperty(propCtx));
            }
        }
        return rule;
    }
    @Override
    public CssSelectorList visitCssSelectorList(JinjaParser.CssSelectorListContext ctx) {
        CssSelectorList list = new CssSelectorList();
        for (JinjaParser.CssSelectorContext selCtx : ctx.cssSelector()) {
            if (selCtx != null) {
                list.getSelectors().add((CssSelector) visitCssSelector(selCtx));
            }
        }
        return list;
    }
    @Override
    public CssSelector visitCssSelector(JinjaParser.CssSelectorContext ctx) {
        CssSelector sel = new CssSelector();
        // زيارة جميع أجزاء المحدد
        for (JinjaParser.SelectorPartContext partCtx : ctx.selectorPart()) {
            if (partCtx != null) {
                SelectorPart part = (SelectorPart) visit(partCtx);
                sel.getParts().add(part);
            }
        }
        // التعامل مع pseudo-class إذا وجد
        if (ctx.CSS_COLON() != null && ctx.CSS_WORD() != null) {
            CssPseudo pseudo = new CssPseudo();
            pseudo.setWord(ctx.CSS_WORD().getText());
            sel.setPseudo(pseudo);
        }
        // معالجة أجزاء selector لإضافة الرموز
        if (!sel.getParts().isEmpty()) {
            for (SelectorPart part : sel.getParts()) {
                String selectorName = "";
                if (part instanceof ClassPart) {
                    selectorName = ((ClassPart) part).getWord();
                } else if (part instanceof TagPart) {
                    selectorName = ((TagPart) part).getWord();
                }
                if (!selectorName.isEmpty()) {
                //    System.out.println("DEBUG [CSS]: Processing selector '" + selectorName + "'");

                    // نفس المنطق: إضافة في السكوب العالمي
                    Symbol existing = symbolTable.lookup(selectorName);
                    if (existing == null) {
                        Symbol selectorSymbol = new Symbol(selectorName, "selector",
                                0, ctx.getStart().getLine());

                        boolean added = symbolTable.addToGlobalScope(selectorSymbol);
                     //   System.out.println("DEBUG [CSS]: Added to global scope = " + added);
                    } else {
                      //  System.out.println("DEBUG [CSS]: Selector '" + selectorName + "' already exists");
                    }
                }
            }
        }
        return sel;
    }
    // === CSS Selector Parts ===
    @Override
    public SelectorPart visitClassPart(JinjaParser.ClassPartContext ctx) {
        ClassPart classPart = new ClassPart();
        if (ctx.CSS_WORD() != null) {
            classPart.setWord(ctx.CSS_WORD().getText());
        }
        return classPart;
    }
    @Override
    public SelectorPart visitTagPart(JinjaParser.TagPartContext ctx) {
        TagPart tagPart = new TagPart();
        if (ctx.CSS_WORD() != null) {
            tagPart.setWord(ctx.CSS_WORD().getText());
        }
        return tagPart;
    }
    @Override
    public CssProperty visitCssProperty(JinjaParser.CssPropertyContext ctx) {
        CssProperty prop = new CssProperty();
        if (ctx.CSS_WORD() != null) {
            prop.setWord(ctx.CSS_WORD().getText());
        }
        if (ctx.valueList() != null) {
            prop.setValueList((ValueList) visitValueList(ctx.valueList()));
        }
        return prop;
    }
    @Override
    public ValueList visitValueList(JinjaParser.ValueListContext ctx) {
        ValueList list = new ValueList();
        for (JinjaParser.CssValueContext valCtx : ctx.cssValue()) {
            if (valCtx != null) {
                list.getValues().add((CssValue) visit(valCtx));
            }
        }
        return list;
    }
    @Override
    public WordValue visitWordValue(JinjaParser.WordValueContext ctx) {
        WordValue word = new WordValue();
        if (ctx.CSS_WORD() != null) {
            word.setWord(ctx.CSS_WORD().getText());
        }
        return word;
    }
    @Override
    public NumberValue visitNumberValue(JinjaParser.NumberValueContext ctx) {
        NumberValue num = new NumberValue();
        if (ctx.CSS_NUMBER() != null) {
            num.setNumber(ctx.CSS_NUMBER().getText());
        }
        return num;
    }
    @Override
    public ColorValue visitColorValue(JinjaParser.ColorValueContext ctx) {
        ColorValue color = new ColorValue();
        if (ctx.CSS_COLOR() != null) {
            color.setColor(ctx.CSS_COLOR().getText());
        }
        return color;
    }
    @Override
    public StringValue visitStringValue(JinjaParser.StringValueContext ctx) {
        StringValue str = new StringValue();
        if (ctx.CSS_STRING() != null) {
            str.setString(ctx.CSS_STRING().getText());
        }
        return str;
    }
    @Override
    public FunctionValue visitFunctionValue(JinjaParser.FunctionValueContext ctx) {
        FunctionValue func = new FunctionValue();
        if (ctx.cssFunction() != null) {
            func.setFunction((CssFunction) visitCssFunction(ctx.cssFunction()));
        }
        return func;
    }
    @Override
    public CssFunction visitCssFunction(JinjaParser.CssFunctionContext ctx) {
        CssFunction func = new CssFunction();
        if (ctx.CSS_WORD() != null) {
            func.setWord(ctx.CSS_WORD().getText());
        }
        if (ctx.valueList() != null) {
            func.setValueList((ValueList) visitValueList(ctx.valueList()));
        }
        return func;
    }
    // ============ الدوال المساعدة الجديدة لتحليل Jinja ============
    // دالة جديدة للتحليل اليدوي العام
    private JinjaExpression parseJinjaContent(String content) {
        JinjaExpression expr = new JinjaExpression();
        content = content.trim();
        // 1. تحقق من وجود فلترات (|)
        String[] parts = content.split("\\|");
        String primaryPart = parts[0].trim();
        // 2. معالجة الجزء الأساسي
        expr.setPrimary(parseJinjaPrimary(primaryPart));
        // 3. معالجة الفلترات (إذا وجدت)
        for (int i = 1; i < parts.length; i++) {
            String filterPart = parts[i].trim();
            JinjaFilter filter = parseJinjaFilter(filterPart);
            expr.getFilters().add(filter);
        }
        return expr;
    }
    // دالة لتحليل الجزء الأساسي
    private JinjaPrimary parseJinjaPrimary(String content) {
        content = content.trim();
        // 1. إذا كان استدعاء دالة (مثل url_for(...), format_price(...))
        if (content.matches("[a-zA-Z_][a-zA-Z0-9_]*\\(.*\\)")) {
            return parseFunctionCall(content);
        }
        // 2. إذا كان dot access (مثل product.id, user.profile.name)
        if (content.contains(".")) {
            return parseAccessExpr(content);
        }
        // 3. إذا كان string literal
        if ((content.startsWith("'") && content.endsWith("'")) ||
                (content.startsWith("\"") && content.endsWith("\""))) {
            StringLiteral lit = new StringLiteral();
            lit.setString(content.substring(1, content.length() - 1));
            return lit;
        }
        // 4. إذا كان number literal
        if (content.matches("\\d+(\\.\\d+)?")) {
            NumberLiteral lit = new NumberLiteral();
            lit.setNumber(content);
            return lit;
        }
        // 5. إذا كان true/false
        if (content.equals("true")) {
            return new TrueLiteral();
        }
        if (content.equals("false")) {
            return new FalseLiteral();
        }
        // 6. إذا كان none
        if (content.equals("none")) {
            return new NoneLiteral();
        }
        // 7. إذا كان يحتوي على + - * / (عمليات بسيطة)
        if (content.contains("+") || content.contains("-") ||
                content.contains("*") || content.contains("/")) {
            return parseBinaryExpr(content);
        }
        // 8. إذا كان conditional expression (if-else)
        if (content.contains(" if ") && content.contains(" else ")) {
            return parseConditionalExpr(content);
        }
        // 9. إذا كان يحتوي على or/and
        if (content.contains(" or ") || content.contains(" and ")) {
            return parseLogicalExpr(content);
        }
        // 10. إذا لم ينطبق أي شيء أعلاه، نعتبره معرف بسيط
        AccessExpr access = new AccessExpr();
        JinjaIdentifierChain chain = new JinjaIdentifierChain();
        chain.setIdentifier(content);
        access.setChain(chain);
        return access;
    }
    // دالة لتحليل استدعاء الدوال (نسخة مساعدة)
    private FunctionCall parseFunctionCall(String funcCallStr) {
        int parenIndex = funcCallStr.indexOf('(');
        String funcName = funcCallStr.substring(0, parenIndex).trim();
        String argsStr = funcCallStr.substring(parenIndex + 1, funcCallStr.lastIndexOf(')')).trim();
        FunctionCall funcCall = new FunctionCall();
        funcCall.setIdentifier(funcName);
        funcCall.setCallArgs(parseArgs(argsStr));
        return funcCall;
    }
    // دالة لتحليل الوسائط (positional و keyword)
    private JinjaCallArgs parseArgs(String argsStr) {
        if (argsStr.trim().isEmpty()) {
            return new EmptyArgs();
        }
        CallMixedArgs callArgs = new CallMixedArgs();
        // تقسيم الوسائط مع مراعاة الأقواس المتداخلة
        List<String> args = splitArgs(argsStr);
        for (String arg : args) {
            arg = arg.trim();
            if (arg.contains("=")) {
                // kwArg مثل product_id=product.id
                String[] kwParts = arg.split("=", 2);
                JinjaKwArg kwArg = new JinjaKwArg();
                kwArg.setIdentifier(kwParts[0].trim());
                kwArg.setExpression(parseJinjaContent(kwParts[1].trim()));
                callArgs.getKwArgs().add(kwArg);
            } else {
                // posArg
                JinjaArg posArg = new JinjaArg();
                posArg.setExpression(parseJinjaContent(arg));
                callArgs.getPosArgs().add(posArg);
            }
        }
        return callArgs;
    }
    // دالة لتقسيم الوسائط مع مراعاة الأقواس المتداخلة
    private List<String> splitArgs(String argsStr) {
        List<String> args = new ArrayList<>();
        int start = 0;
        int parenCount = 0;
        int bracketCount = 0;
        for (int i = 0; i < argsStr.length(); i++) {
            char c = argsStr.charAt(i);
            if (c == '(') parenCount++;
            else if (c == ')') parenCount--;
            else if (c == '[') bracketCount++;
            else if (c == ']') bracketCount--;
            if (c == ',' && parenCount == 0 && bracketCount == 0) {
                args.add(argsStr.substring(start, i).trim());
                start = i + 1;
            }
        }
        // إضافة الوسيط الأخير
        if (start < argsStr.length()) {
            args.add(argsStr.substring(start).trim());
        }
        return args;
    }
    // دالة لتحليل Dot Access
    private AccessExpr parseAccessExpr(String accessStr) {
        AccessExpr access = new AccessExpr();
        JinjaIdentifierChain chain = new JinjaIdentifierChain();
        List<String> parts = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        int bracketCount = 0;
        for (int i = 0; i < accessStr.length(); i++) {
            char c = accessStr.charAt(i);
            if (c == '[') {
                bracketCount++;
                currentPart.append(c);
            } else if (c == ']') {
                bracketCount--;
                currentPart.append(c);
            } else if (c == '.' && bracketCount == 0) {
                parts.add(currentPart.toString());
                currentPart = new StringBuilder();
            } else {
                currentPart.append(c);
            }
        }
        if (currentPart.length() > 0) {
            parts.add(currentPart.toString());
        }
        if (!parts.isEmpty()) {
            chain.setIdentifier(parts.get(0).trim());
            for (int i = 1; i < parts.size(); i++) {
                String part = parts.get(i).trim();
                if (part.startsWith("[") && part.endsWith("]")) {
                    // Index access مثل ["theme"]
                    IndexAccess indexAccess = new IndexAccess();
                    String indexContent = part.substring(1, part.length() - 1);
                    indexAccess.setExpression(parseJinjaContent(indexContent));
                    chain.getAccesses().add(indexAccess);
                } else {
                    // Dot access مثل profile
                    DotAccess dotAccess = new DotAccess();
                    dotAccess.setIdentifier(part);
                    chain.getAccesses().add(dotAccess);
                }
            }
        }
        access.setChain(chain);
        return access;
    }
    // دالة لتحليل الفلترات
    private JinjaFilter parseJinjaFilter(String filterStr) {
        JinjaFilter filter = new JinjaFilter();
        if (filterStr.contains("(")) {
            // فلتر مع وسائط مثل format(2)
            int parenIndex = filterStr.indexOf('(');
            String filterName = filterStr.substring(0, parenIndex).trim();
            String argsStr = filterStr.substring(parenIndex + 1, filterStr.lastIndexOf(')')).trim();
            filter.setName(filterName);
            filter.setArgs(parseArgs(argsStr));
        } else {
            // فلتر بدون وسائط مثل upper
            filter.setName(filterStr);
            filter.setArgs(new EmptyArgs());
        }
        return filter;
    }
    private JinjaPrimary parseBinaryExpr(String expr) {
        // تبسيط: نعتبره string literal مؤقتاً
        // لاحقاً يمكن إضافة BinaryExpr إلى AST
        StringLiteral lit = new StringLiteral();
        lit.setString(expr);
        return lit;
    }
    private JinjaPrimary parseConditionalExpr(String expr) {
        // تبسيط: نعتبره string literal مؤقتاً
        // لاحقاً يمكن إضافة ConditionalExpr إلى AST
        StringLiteral lit = new StringLiteral();
        lit.setString(expr);
        return lit;
    }
    private JinjaPrimary parseLogicalExpr(String expr) {
        // تبسيط: نعتبره string literal مؤقتاً
        // لاحقاً يمكن إضافة LogicalExpr إلى AST
        StringLiteral lit = new StringLiteral();
        lit.setString(expr);
        return lit;
    }
}