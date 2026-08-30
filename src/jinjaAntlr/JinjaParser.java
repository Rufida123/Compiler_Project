// Generated from JinjaParser.g4 by ANTLR 4.13.2
 package jinjaAntlr; 
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class JinjaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		JINJA_BLOCK_START=1, JINJA_EXPR_START=2, TAG_OPEN=3, TAG_CLOSE_START=4, 
		HTML_COMMENT=5, DOCTYPE=6, STYLE_OPEN=7, HTML_TEXT=8, WS=9, TAG_CLOSE=10, 
		TAG_SLASH_CLOSE=11, TAG_ID=12, TAG_EQUALS=13, TAG_ATTR_VALUE=14, TAG_JINJA_BLOCK_START=15, 
		TAG_JINJA_EXPR_START=16, TAG_WS=17, STYLE_CLOSE=18, CSS_COMMENT=19, CSS_STRING=20, 
		CSS_WORD=21, CSS_DOT=22, CSS_NUMBER=23, CSS_COLOR=24, CSS_COLON=25, CSS_OPEN_BRACE=26, 
		CSS_CLOSE_BRACE=27, SEMICOLON=28, CSS_COMMA=29, CSS_LPAREN=30, CSS_RPAREN=31, 
		CSS_WS=32, JINJA_BLOCK_END=33, JINJA_EXPR_END=34, JINJA_EXTENDS=35, JINJA_BLOCK=36, 
		JINJA_ENDBLOCK=37, JINJA_FORMAT=38, JINJA_IF=39, JINJA_FOR=40, JINJA_IN=41, 
		JINJA_ELSE=42, JINJA_ENDIF=43, JINJA_ENDFOR=44, JINJA_TRUE=45, JINJA_FALSE=46, 
		JINJA_NONE=47, JINJA_PIPE=48, JINJA_DOT=49, JINJA_COMMA=50, JINJA_COLON=51, 
		JINJA_EQ=52, JINJA_NEQ=53, JINJA_LE=54, JINJA_GE=55, JINJA_EQUALS=56, 
		JINJA_PLUS=57, JINJA_MINUS=58, JINJA_TILDE=59, JINJA_MULT=60, JINJA_DIV=61, 
		JINJA_LT=62, JINJA_GT=63, JINJA_OPAR=64, JINJA_CPAR=65, JINJA_OBRACK=66, 
		JINJA_CBRACK=67, JINJA_IDENTIFIER=68, JINJA_NUMBER=69, JINJA_STRING=70, 
		JINJA_WS=71, JINJA_COMMENT=72;
	public static final int
		RULE_jinjaProgram = 0, RULE_documentElement = 1, RULE_doctype = 2, RULE_htmlTag = 3, 
		RULE_htmlAttribute = 4, RULE_attributeValue = 5, RULE_jinjaBlock = 6, 
		RULE_jinjaStatementHeader = 7, RULE_jinjaExpression = 8, RULE_jinjaComparison = 9, 
		RULE_jinjaAdditive = 10, RULE_jinjaMultiplicative = 11, RULE_jinjaPrimary = 12, 
		RULE_jinjaIdentifierChain = 13, RULE_jinjaFilter = 14, RULE_jinjaCallArgs = 15, 
		RULE_jinjaArg = 16, RULE_jinjaKwArg = 17, RULE_htmlText = 18, RULE_styleTag = 19, 
		RULE_cssRule = 20, RULE_cssSelectorList = 21, RULE_cssSelector = 22, RULE_selectorPart = 23, 
		RULE_cssProperty = 24, RULE_valueList = 25, RULE_cssValue = 26, RULE_cssFunction = 27;
	private static String[] makeRuleNames() {
		return new String[] {
			"jinjaProgram", "documentElement", "doctype", "htmlTag", "htmlAttribute", 
			"attributeValue", "jinjaBlock", "jinjaStatementHeader", "jinjaExpression", 
			"jinjaComparison", "jinjaAdditive", "jinjaMultiplicative", "jinjaPrimary", 
			"jinjaIdentifierChain", "jinjaFilter", "jinjaCallArgs", "jinjaArg", "jinjaKwArg", 
			"htmlText", "styleTag", "cssRule", "cssSelectorList", "cssSelector", 
			"selectorPart", "cssProperty", "valueList", "cssValue", "cssFunction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'</'", null, null, null, null, null, null, "'/>'", 
			null, null, null, null, null, null, "'</style>'", null, null, null, null, 
			null, null, null, "'{'", "'}'", "';'", null, null, null, null, "'%}'", 
			"'}}'", "'extends'", "'block'", "'endblock'", "'format'", "'if'", "'for'", 
			"'in'", "'else'", "'endif'", "'endfor'", "'true'", "'false'", "'none'", 
			"'|'", null, null, null, "'=='", "'!='", "'<='", "'>='", null, "'+'", 
			"'-'", "'~'", "'*'", "'/'", null, null, null, null, "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "JINJA_BLOCK_START", "JINJA_EXPR_START", "TAG_OPEN", "TAG_CLOSE_START", 
			"HTML_COMMENT", "DOCTYPE", "STYLE_OPEN", "HTML_TEXT", "WS", "TAG_CLOSE", 
			"TAG_SLASH_CLOSE", "TAG_ID", "TAG_EQUALS", "TAG_ATTR_VALUE", "TAG_JINJA_BLOCK_START", 
			"TAG_JINJA_EXPR_START", "TAG_WS", "STYLE_CLOSE", "CSS_COMMENT", "CSS_STRING", 
			"CSS_WORD", "CSS_DOT", "CSS_NUMBER", "CSS_COLOR", "CSS_COLON", "CSS_OPEN_BRACE", 
			"CSS_CLOSE_BRACE", "SEMICOLON", "CSS_COMMA", "CSS_LPAREN", "CSS_RPAREN", 
			"CSS_WS", "JINJA_BLOCK_END", "JINJA_EXPR_END", "JINJA_EXTENDS", "JINJA_BLOCK", 
			"JINJA_ENDBLOCK", "JINJA_FORMAT", "JINJA_IF", "JINJA_FOR", "JINJA_IN", 
			"JINJA_ELSE", "JINJA_ENDIF", "JINJA_ENDFOR", "JINJA_TRUE", "JINJA_FALSE", 
			"JINJA_NONE", "JINJA_PIPE", "JINJA_DOT", "JINJA_COMMA", "JINJA_COLON", 
			"JINJA_EQ", "JINJA_NEQ", "JINJA_LE", "JINJA_GE", "JINJA_EQUALS", "JINJA_PLUS", 
			"JINJA_MINUS", "JINJA_TILDE", "JINJA_MULT", "JINJA_DIV", "JINJA_LT", 
			"JINJA_GT", "JINJA_OPAR", "JINJA_CPAR", "JINJA_OBRACK", "JINJA_CBRACK", 
			"JINJA_IDENTIFIER", "JINJA_NUMBER", "JINJA_STRING", "JINJA_WS", "JINJA_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JinjaParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JinjaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JinjaParser.EOF, 0); }
		public List<DocumentElementContext> documentElement() {
			return getRuleContexts(DocumentElementContext.class);
		}
		public DocumentElementContext documentElement(int i) {
			return getRuleContext(DocumentElementContext.class,i);
		}
		public JinjaProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaProgram; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaProgramContext jinjaProgram() throws RecognitionException {
		JinjaProgramContext _localctx = new JinjaProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_jinjaProgram);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 98766L) != 0)) {
				{
				{
				setState(56);
				documentElement();
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DocumentElementContext extends ParserRuleContext {
		public DoctypeContext doctype() {
			return getRuleContext(DoctypeContext.class,0);
		}
		public StyleTagContext styleTag() {
			return getRuleContext(StyleTagContext.class,0);
		}
		public JinjaBlockContext jinjaBlock() {
			return getRuleContext(JinjaBlockContext.class,0);
		}
		public HtmlTagContext htmlTag() {
			return getRuleContext(HtmlTagContext.class,0);
		}
		public HtmlTextContext htmlText() {
			return getRuleContext(HtmlTextContext.class,0);
		}
		public DocumentElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_documentElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterDocumentElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitDocumentElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitDocumentElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocumentElementContext documentElement() throws RecognitionException {
		DocumentElementContext _localctx = new DocumentElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_documentElement);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOCTYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				doctype();
				}
				break;
			case STYLE_OPEN:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				styleTag();
				}
				break;
			case JINJA_BLOCK_START:
			case JINJA_EXPR_START:
			case TAG_JINJA_BLOCK_START:
			case TAG_JINJA_EXPR_START:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				jinjaBlock();
				}
				break;
			case TAG_OPEN:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				htmlTag();
				}
				break;
			case HTML_TEXT:
				enterOuterAlt(_localctx, 5);
				{
				setState(68);
				htmlText();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DoctypeContext extends ParserRuleContext {
		public TerminalNode DOCTYPE() { return getToken(JinjaParser.DOCTYPE, 0); }
		public DoctypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doctype; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterDoctype(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitDoctype(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitDoctype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DoctypeContext doctype() throws RecognitionException {
		DoctypeContext _localctx = new DoctypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_doctype);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(DOCTYPE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlTagContext extends ParserRuleContext {
		public HtmlTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlTag; }
	 
		public HtmlTagContext() { }
		public void copyFrom(HtmlTagContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PairedTagContext extends HtmlTagContext {
		public TerminalNode TAG_OPEN() { return getToken(JinjaParser.TAG_OPEN, 0); }
		public List<TerminalNode> TAG_ID() { return getTokens(JinjaParser.TAG_ID); }
		public TerminalNode TAG_ID(int i) {
			return getToken(JinjaParser.TAG_ID, i);
		}
		public List<TerminalNode> TAG_CLOSE() { return getTokens(JinjaParser.TAG_CLOSE); }
		public TerminalNode TAG_CLOSE(int i) {
			return getToken(JinjaParser.TAG_CLOSE, i);
		}
		public TerminalNode TAG_CLOSE_START() { return getToken(JinjaParser.TAG_CLOSE_START, 0); }
		public List<HtmlAttributeContext> htmlAttribute() {
			return getRuleContexts(HtmlAttributeContext.class);
		}
		public HtmlAttributeContext htmlAttribute(int i) {
			return getRuleContext(HtmlAttributeContext.class,i);
		}
		public List<DocumentElementContext> documentElement() {
			return getRuleContexts(DocumentElementContext.class);
		}
		public DocumentElementContext documentElement(int i) {
			return getRuleContext(DocumentElementContext.class,i);
		}
		public PairedTagContext(HtmlTagContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterPairedTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitPairedTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitPairedTag(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelfClosingTagContext extends HtmlTagContext {
		public TerminalNode TAG_OPEN() { return getToken(JinjaParser.TAG_OPEN, 0); }
		public TerminalNode TAG_ID() { return getToken(JinjaParser.TAG_ID, 0); }
		public TerminalNode TAG_SLASH_CLOSE() { return getToken(JinjaParser.TAG_SLASH_CLOSE, 0); }
		public List<HtmlAttributeContext> htmlAttribute() {
			return getRuleContexts(HtmlAttributeContext.class);
		}
		public HtmlAttributeContext htmlAttribute(int i) {
			return getRuleContext(HtmlAttributeContext.class,i);
		}
		public SelfClosingTagContext(HtmlTagContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterSelfClosingTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitSelfClosingTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitSelfClosingTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlTagContext htmlTag() throws RecognitionException {
		HtmlTagContext _localctx = new HtmlTagContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_htmlTag);
		int _la;
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new PairedTagContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				match(TAG_OPEN);
				setState(74);
				match(TAG_ID);
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 102406L) != 0)) {
					{
					{
					setState(75);
					htmlAttribute();
					}
					}
					setState(80);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(81);
				match(TAG_CLOSE);
				setState(85);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 98766L) != 0)) {
					{
					{
					setState(82);
					documentElement();
					}
					}
					setState(87);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(88);
				match(TAG_CLOSE_START);
				setState(89);
				match(TAG_ID);
				setState(90);
				match(TAG_CLOSE);
				}
				break;
			case 2:
				_localctx = new SelfClosingTagContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(91);
				match(TAG_OPEN);
				setState(92);
				match(TAG_ID);
				setState(96);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 102406L) != 0)) {
					{
					{
					setState(93);
					htmlAttribute();
					}
					}
					setState(98);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(99);
				match(TAG_SLASH_CLOSE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlAttributeContext extends ParserRuleContext {
		public HtmlAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlAttribute; }
	 
		public HtmlAttributeContext() { }
		public void copyFrom(HtmlAttributeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaAttributeContext extends HtmlAttributeContext {
		public JinjaBlockContext jinjaBlock() {
			return getRuleContext(JinjaBlockContext.class,0);
		}
		public JinjaAttributeContext(HtmlAttributeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaAttribute(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NormalAttributeContext extends HtmlAttributeContext {
		public TerminalNode TAG_ID() { return getToken(JinjaParser.TAG_ID, 0); }
		public TerminalNode TAG_EQUALS() { return getToken(JinjaParser.TAG_EQUALS, 0); }
		public AttributeValueContext attributeValue() {
			return getRuleContext(AttributeValueContext.class,0);
		}
		public NormalAttributeContext(HtmlAttributeContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterNormalAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitNormalAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitNormalAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlAttributeContext htmlAttribute() throws RecognitionException {
		HtmlAttributeContext _localctx = new HtmlAttributeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_htmlAttribute);
		int _la;
		try {
			setState(108);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TAG_ID:
				_localctx = new NormalAttributeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				match(TAG_ID);
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TAG_EQUALS) {
					{
					setState(103);
					match(TAG_EQUALS);
					setState(104);
					attributeValue();
					}
				}

				}
				break;
			case JINJA_BLOCK_START:
			case JINJA_EXPR_START:
			case TAG_JINJA_BLOCK_START:
			case TAG_JINJA_EXPR_START:
				_localctx = new JinjaAttributeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				jinjaBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeValueContext extends ParserRuleContext {
		public AttributeValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeValue; }
	 
		public AttributeValueContext() { }
		public void copyFrom(AttributeValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PlainValueContext extends AttributeValueContext {
		public TerminalNode TAG_ATTR_VALUE() { return getToken(JinjaParser.TAG_ATTR_VALUE, 0); }
		public PlainValueContext(AttributeValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterPlainValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitPlainValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitPlainValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaValueExprContext extends AttributeValueContext {
		public JinjaBlockContext jinjaBlock() {
			return getRuleContext(JinjaBlockContext.class,0);
		}
		public JinjaValueExprContext(AttributeValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaValueExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaValueExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaValueExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeValueContext attributeValue() throws RecognitionException {
		AttributeValueContext _localctx = new AttributeValueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_attributeValue);
		try {
			setState(112);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TAG_ATTR_VALUE:
				_localctx = new PlainValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(110);
				match(TAG_ATTR_VALUE);
				}
				break;
			case JINJA_BLOCK_START:
			case JINJA_EXPR_START:
			case TAG_JINJA_BLOCK_START:
			case TAG_JINJA_EXPR_START:
				_localctx = new JinjaValueExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
				jinjaBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaBlockContext extends ParserRuleContext {
		public JinjaBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaBlock; }
	 
		public JinjaBlockContext() { }
		public void copyFrom(JinjaBlockContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ControlBlockContext extends JinjaBlockContext {
		public JinjaStatementHeaderContext jinjaStatementHeader() {
			return getRuleContext(JinjaStatementHeaderContext.class,0);
		}
		public TerminalNode JINJA_BLOCK_END() { return getToken(JinjaParser.JINJA_BLOCK_END, 0); }
		public TerminalNode JINJA_BLOCK_START() { return getToken(JinjaParser.JINJA_BLOCK_START, 0); }
		public TerminalNode TAG_JINJA_BLOCK_START() { return getToken(JinjaParser.TAG_JINJA_BLOCK_START, 0); }
		public ControlBlockContext(JinjaBlockContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterControlBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitControlBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitControlBlock(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class PrintBlockContext extends JinjaBlockContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public TerminalNode JINJA_EXPR_END() { return getToken(JinjaParser.JINJA_EXPR_END, 0); }
		public TerminalNode JINJA_EXPR_START() { return getToken(JinjaParser.JINJA_EXPR_START, 0); }
		public TerminalNode TAG_JINJA_EXPR_START() { return getToken(JinjaParser.TAG_JINJA_EXPR_START, 0); }
		public PrintBlockContext(JinjaBlockContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterPrintBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitPrintBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitPrintBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaBlockContext jinjaBlock() throws RecognitionException {
		JinjaBlockContext _localctx = new JinjaBlockContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_jinjaBlock);
		int _la;
		try {
			setState(122);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_BLOCK_START:
			case TAG_JINJA_BLOCK_START:
				_localctx = new ControlBlockContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				_la = _input.LA(1);
				if ( !(_la==JINJA_BLOCK_START || _la==TAG_JINJA_BLOCK_START) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(115);
				jinjaStatementHeader();
				setState(116);
				match(JINJA_BLOCK_END);
				}
				break;
			case JINJA_EXPR_START:
			case TAG_JINJA_EXPR_START:
				_localctx = new PrintBlockContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				_la = _input.LA(1);
				if ( !(_la==JINJA_EXPR_START || _la==TAG_JINJA_EXPR_START) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(119);
				jinjaExpression();
				setState(120);
				match(JINJA_EXPR_END);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaStatementHeaderContext extends ParserRuleContext {
		public JinjaStatementHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaStatementHeader; }
	 
		public JinjaStatementHeaderContext() { }
		public void copyFrom(JinjaStatementHeaderContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockStartContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_BLOCK() { return getToken(JinjaParser.JINJA_BLOCK, 0); }
		public TerminalNode JINJA_IDENTIFIER() { return getToken(JinjaParser.JINJA_IDENTIFIER, 0); }
		public BlockStartContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterBlockStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitBlockStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitBlockStart(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BlockEndContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_ENDBLOCK() { return getToken(JinjaParser.JINJA_ENDBLOCK, 0); }
		public BlockEndContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterBlockEnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitBlockEnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitBlockEnd(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EndForContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_ENDFOR() { return getToken(JinjaParser.JINJA_ENDFOR, 0); }
		public EndForContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterEndFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitEndFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitEndFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EndIfContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_ENDIF() { return getToken(JinjaParser.JINJA_ENDIF, 0); }
		public EndIfContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterEndIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitEndIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitEndIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ElseContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_ELSE() { return getToken(JinjaParser.JINJA_ELSE, 0); }
		public ElseContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterElse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitElse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitElse(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ForContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_FOR() { return getToken(JinjaParser.JINJA_FOR, 0); }
		public TerminalNode JINJA_IDENTIFIER() { return getToken(JinjaParser.JINJA_IDENTIFIER, 0); }
		public TerminalNode JINJA_IN() { return getToken(JinjaParser.JINJA_IN, 0); }
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public ForContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ExtendsContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_EXTENDS() { return getToken(JinjaParser.JINJA_EXTENDS, 0); }
		public TerminalNode JINJA_STRING() { return getToken(JinjaParser.JINJA_STRING, 0); }
		public ExtendsContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterExtends(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitExtends(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitExtends(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfContext extends JinjaStatementHeaderContext {
		public TerminalNode JINJA_IF() { return getToken(JinjaParser.JINJA_IF, 0); }
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public IfContext(JinjaStatementHeaderContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaStatementHeaderContext jinjaStatementHeader() throws RecognitionException {
		JinjaStatementHeaderContext _localctx = new JinjaStatementHeaderContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_jinjaStatementHeader);
		try {
			setState(138);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_EXTENDS:
				_localctx = new ExtendsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(124);
				match(JINJA_EXTENDS);
				setState(125);
				match(JINJA_STRING);
				}
				break;
			case JINJA_BLOCK:
				_localctx = new BlockStartContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(126);
				match(JINJA_BLOCK);
				setState(127);
				match(JINJA_IDENTIFIER);
				}
				break;
			case JINJA_ENDBLOCK:
				_localctx = new BlockEndContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				match(JINJA_ENDBLOCK);
				}
				break;
			case JINJA_IF:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(129);
				match(JINJA_IF);
				setState(130);
				jinjaExpression();
				}
				break;
			case JINJA_ELSE:
				_localctx = new ElseContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(131);
				match(JINJA_ELSE);
				}
				break;
			case JINJA_ENDIF:
				_localctx = new EndIfContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(132);
				match(JINJA_ENDIF);
				}
				break;
			case JINJA_FOR:
				_localctx = new ForContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(133);
				match(JINJA_FOR);
				setState(134);
				match(JINJA_IDENTIFIER);
				setState(135);
				match(JINJA_IN);
				setState(136);
				jinjaExpression();
				}
				break;
			case JINJA_ENDFOR:
				_localctx = new EndForContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(137);
				match(JINJA_ENDFOR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaExpressionContext extends ParserRuleContext {
		public JinjaComparisonContext jinjaComparison() {
			return getRuleContext(JinjaComparisonContext.class,0);
		}
		public List<TerminalNode> JINJA_PIPE() { return getTokens(JinjaParser.JINJA_PIPE); }
		public TerminalNode JINJA_PIPE(int i) {
			return getToken(JinjaParser.JINJA_PIPE, i);
		}
		public List<JinjaFilterContext> jinjaFilter() {
			return getRuleContexts(JinjaFilterContext.class);
		}
		public JinjaFilterContext jinjaFilter(int i) {
			return getRuleContext(JinjaFilterContext.class,i);
		}
		public JinjaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaExpressionContext jinjaExpression() throws RecognitionException {
		JinjaExpressionContext _localctx = new JinjaExpressionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_jinjaExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			jinjaComparison();
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JINJA_PIPE) {
				{
				{
				setState(141);
				match(JINJA_PIPE);
				setState(142);
				jinjaFilter();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaComparisonContext extends ParserRuleContext {
		public List<JinjaAdditiveContext> jinjaAdditive() {
			return getRuleContexts(JinjaAdditiveContext.class);
		}
		public JinjaAdditiveContext jinjaAdditive(int i) {
			return getRuleContext(JinjaAdditiveContext.class,i);
		}
		public List<TerminalNode> JINJA_EQ() { return getTokens(JinjaParser.JINJA_EQ); }
		public TerminalNode JINJA_EQ(int i) {
			return getToken(JinjaParser.JINJA_EQ, i);
		}
		public List<TerminalNode> JINJA_NEQ() { return getTokens(JinjaParser.JINJA_NEQ); }
		public TerminalNode JINJA_NEQ(int i) {
			return getToken(JinjaParser.JINJA_NEQ, i);
		}
		public List<TerminalNode> JINJA_LT() { return getTokens(JinjaParser.JINJA_LT); }
		public TerminalNode JINJA_LT(int i) {
			return getToken(JinjaParser.JINJA_LT, i);
		}
		public List<TerminalNode> JINJA_GT() { return getTokens(JinjaParser.JINJA_GT); }
		public TerminalNode JINJA_GT(int i) {
			return getToken(JinjaParser.JINJA_GT, i);
		}
		public List<TerminalNode> JINJA_LE() { return getTokens(JinjaParser.JINJA_LE); }
		public TerminalNode JINJA_LE(int i) {
			return getToken(JinjaParser.JINJA_LE, i);
		}
		public List<TerminalNode> JINJA_GE() { return getTokens(JinjaParser.JINJA_GE); }
		public TerminalNode JINJA_GE(int i) {
			return getToken(JinjaParser.JINJA_GE, i);
		}
		public JinjaComparisonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaComparison; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaComparison(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaComparison(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaComparison(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaComparisonContext jinjaComparison() throws RecognitionException {
		JinjaComparisonContext _localctx = new JinjaComparisonContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_jinjaComparison);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			jinjaAdditive();
			setState(153);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -4544132024016830464L) != 0)) {
				{
				{
				setState(149);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & -4544132024016830464L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(150);
				jinjaAdditive();
				}
				}
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaAdditiveContext extends ParserRuleContext {
		public List<JinjaMultiplicativeContext> jinjaMultiplicative() {
			return getRuleContexts(JinjaMultiplicativeContext.class);
		}
		public JinjaMultiplicativeContext jinjaMultiplicative(int i) {
			return getRuleContext(JinjaMultiplicativeContext.class,i);
		}
		public List<TerminalNode> JINJA_PLUS() { return getTokens(JinjaParser.JINJA_PLUS); }
		public TerminalNode JINJA_PLUS(int i) {
			return getToken(JinjaParser.JINJA_PLUS, i);
		}
		public List<TerminalNode> JINJA_MINUS() { return getTokens(JinjaParser.JINJA_MINUS); }
		public TerminalNode JINJA_MINUS(int i) {
			return getToken(JinjaParser.JINJA_MINUS, i);
		}
		public List<TerminalNode> JINJA_TILDE() { return getTokens(JinjaParser.JINJA_TILDE); }
		public TerminalNode JINJA_TILDE(int i) {
			return getToken(JinjaParser.JINJA_TILDE, i);
		}
		public JinjaAdditiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaAdditive; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaAdditive(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaAdditive(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaAdditive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaAdditiveContext jinjaAdditive() throws RecognitionException {
		JinjaAdditiveContext _localctx = new JinjaAdditiveContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_jinjaAdditive);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			jinjaMultiplicative();
			setState(161);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1008806316530991104L) != 0)) {
				{
				{
				setState(157);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1008806316530991104L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(158);
				jinjaMultiplicative();
				}
				}
				setState(163);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaMultiplicativeContext extends ParserRuleContext {
		public List<JinjaPrimaryContext> jinjaPrimary() {
			return getRuleContexts(JinjaPrimaryContext.class);
		}
		public JinjaPrimaryContext jinjaPrimary(int i) {
			return getRuleContext(JinjaPrimaryContext.class,i);
		}
		public List<TerminalNode> JINJA_MULT() { return getTokens(JinjaParser.JINJA_MULT); }
		public TerminalNode JINJA_MULT(int i) {
			return getToken(JinjaParser.JINJA_MULT, i);
		}
		public List<TerminalNode> JINJA_DIV() { return getTokens(JinjaParser.JINJA_DIV); }
		public TerminalNode JINJA_DIV(int i) {
			return getToken(JinjaParser.JINJA_DIV, i);
		}
		public JinjaMultiplicativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaMultiplicative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaMultiplicative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaMultiplicative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaMultiplicative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaMultiplicativeContext jinjaMultiplicative() throws RecognitionException {
		JinjaMultiplicativeContext _localctx = new JinjaMultiplicativeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_jinjaMultiplicative);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			jinjaPrimary();
			setState(169);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JINJA_MULT || _la==JINJA_DIV) {
				{
				{
				setState(165);
				_la = _input.LA(1);
				if ( !(_la==JINJA_MULT || _la==JINJA_DIV) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(166);
				jinjaPrimary();
				}
				}
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaPrimaryContext extends ParserRuleContext {
		public JinjaPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaPrimary; }
	 
		public JinjaPrimaryContext() { }
		public void copyFrom(JinjaPrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AccessExprContext extends JinjaPrimaryContext {
		public JinjaIdentifierChainContext jinjaIdentifierChain() {
			return getRuleContext(JinjaIdentifierChainContext.class,0);
		}
		public AccessExprContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterAccessExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitAccessExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitAccessExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_STRING() { return getToken(JinjaParser.JINJA_STRING, 0); }
		public StringLiteralContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TrueLiteralContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_TRUE() { return getToken(JinjaParser.JINJA_TRUE, 0); }
		public TrueLiteralContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterTrueLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitTrueLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitTrueLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NoneLiteralContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_NONE() { return getToken(JinjaParser.JINJA_NONE, 0); }
		public NoneLiteralContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterNoneLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitNoneLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitNoneLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesizedExprContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_OPAR() { return getToken(JinjaParser.JINJA_OPAR, 0); }
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public TerminalNode JINJA_CPAR() { return getToken(JinjaParser.JINJA_CPAR, 0); }
		public ParenthesizedExprContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterParenthesizedExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitParenthesizedExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitParenthesizedExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_IDENTIFIER() { return getToken(JinjaParser.JINJA_IDENTIFIER, 0); }
		public TerminalNode JINJA_OPAR() { return getToken(JinjaParser.JINJA_OPAR, 0); }
		public JinjaCallArgsContext jinjaCallArgs() {
			return getRuleContext(JinjaCallArgsContext.class,0);
		}
		public TerminalNode JINJA_CPAR() { return getToken(JinjaParser.JINJA_CPAR, 0); }
		public FunctionCallContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberLiteralContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_NUMBER() { return getToken(JinjaParser.JINJA_NUMBER, 0); }
		public NumberLiteralContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterNumberLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitNumberLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitNumberLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FalseLiteralContext extends JinjaPrimaryContext {
		public TerminalNode JINJA_FALSE() { return getToken(JinjaParser.JINJA_FALSE, 0); }
		public FalseLiteralContext(JinjaPrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterFalseLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitFalseLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitFalseLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaPrimaryContext jinjaPrimary() throws RecognitionException {
		JinjaPrimaryContext _localctx = new JinjaPrimaryContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_jinjaPrimary);
		try {
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new AccessExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(172);
				jinjaIdentifierChain();
				}
				break;
			case 2:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(173);
				match(JINJA_STRING);
				}
				break;
			case 3:
				_localctx = new NumberLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(174);
				match(JINJA_NUMBER);
				}
				break;
			case 4:
				_localctx = new TrueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(175);
				match(JINJA_TRUE);
				}
				break;
			case 5:
				_localctx = new FalseLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(176);
				match(JINJA_FALSE);
				}
				break;
			case 6:
				_localctx = new NoneLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(177);
				match(JINJA_NONE);
				}
				break;
			case 7:
				_localctx = new FunctionCallContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(178);
				match(JINJA_IDENTIFIER);
				setState(179);
				match(JINJA_OPAR);
				setState(180);
				jinjaCallArgs();
				setState(181);
				match(JINJA_CPAR);
				}
				break;
			case 8:
				_localctx = new ParenthesizedExprContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(183);
				match(JINJA_OPAR);
				setState(184);
				jinjaExpression();
				setState(185);
				match(JINJA_CPAR);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaIdentifierChainContext extends ParserRuleContext {
		public List<TerminalNode> JINJA_IDENTIFIER() { return getTokens(JinjaParser.JINJA_IDENTIFIER); }
		public TerminalNode JINJA_IDENTIFIER(int i) {
			return getToken(JinjaParser.JINJA_IDENTIFIER, i);
		}
		public List<TerminalNode> JINJA_DOT() { return getTokens(JinjaParser.JINJA_DOT); }
		public TerminalNode JINJA_DOT(int i) {
			return getToken(JinjaParser.JINJA_DOT, i);
		}
		public List<TerminalNode> JINJA_OBRACK() { return getTokens(JinjaParser.JINJA_OBRACK); }
		public TerminalNode JINJA_OBRACK(int i) {
			return getToken(JinjaParser.JINJA_OBRACK, i);
		}
		public List<JinjaExpressionContext> jinjaExpression() {
			return getRuleContexts(JinjaExpressionContext.class);
		}
		public JinjaExpressionContext jinjaExpression(int i) {
			return getRuleContext(JinjaExpressionContext.class,i);
		}
		public List<TerminalNode> JINJA_CBRACK() { return getTokens(JinjaParser.JINJA_CBRACK); }
		public TerminalNode JINJA_CBRACK(int i) {
			return getToken(JinjaParser.JINJA_CBRACK, i);
		}
		public JinjaIdentifierChainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaIdentifierChain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaIdentifierChain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaIdentifierChain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaIdentifierChain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaIdentifierChainContext jinjaIdentifierChain() throws RecognitionException {
		JinjaIdentifierChainContext _localctx = new JinjaIdentifierChainContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_jinjaIdentifierChain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			match(JINJA_IDENTIFIER);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JINJA_DOT || _la==JINJA_OBRACK) {
				{
				setState(196);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case JINJA_DOT:
					{
					{
					setState(190);
					match(JINJA_DOT);
					setState(191);
					match(JINJA_IDENTIFIER);
					}
					}
					break;
				case JINJA_OBRACK:
					{
					{
					setState(192);
					match(JINJA_OBRACK);
					setState(193);
					jinjaExpression();
					setState(194);
					match(JINJA_CBRACK);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(200);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaFilterContext extends ParserRuleContext {
		public TerminalNode JINJA_IDENTIFIER() { return getToken(JinjaParser.JINJA_IDENTIFIER, 0); }
		public TerminalNode JINJA_FORMAT() { return getToken(JinjaParser.JINJA_FORMAT, 0); }
		public TerminalNode JINJA_OPAR() { return getToken(JinjaParser.JINJA_OPAR, 0); }
		public JinjaCallArgsContext jinjaCallArgs() {
			return getRuleContext(JinjaCallArgsContext.class,0);
		}
		public TerminalNode JINJA_CPAR() { return getToken(JinjaParser.JINJA_CPAR, 0); }
		public JinjaFilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaFilter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaFilter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaFilter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaFilterContext jinjaFilter() throws RecognitionException {
		JinjaFilterContext _localctx = new JinjaFilterContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_jinjaFilter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			_la = _input.LA(1);
			if ( !(_la==JINJA_FORMAT || _la==JINJA_IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(206);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(202);
				match(JINJA_OPAR);
				setState(203);
				jinjaCallArgs();
				setState(204);
				match(JINJA_CPAR);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaCallArgsContext extends ParserRuleContext {
		public JinjaCallArgsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaCallArgs; }
	 
		public JinjaCallArgsContext() { }
		public void copyFrom(JinjaCallArgsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallKwArgsContext extends JinjaCallArgsContext {
		public List<JinjaKwArgContext> jinjaKwArg() {
			return getRuleContexts(JinjaKwArgContext.class);
		}
		public JinjaKwArgContext jinjaKwArg(int i) {
			return getRuleContext(JinjaKwArgContext.class,i);
		}
		public List<TerminalNode> JINJA_COMMA() { return getTokens(JinjaParser.JINJA_COMMA); }
		public TerminalNode JINJA_COMMA(int i) {
			return getToken(JinjaParser.JINJA_COMMA, i);
		}
		public CallKwArgsContext(JinjaCallArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCallKwArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCallKwArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCallKwArgs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EmptyArgsContext extends JinjaCallArgsContext {
		public EmptyArgsContext(JinjaCallArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterEmptyArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitEmptyArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitEmptyArgs(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallMixedArgsContext extends JinjaCallArgsContext {
		public List<JinjaArgContext> jinjaArg() {
			return getRuleContexts(JinjaArgContext.class);
		}
		public JinjaArgContext jinjaArg(int i) {
			return getRuleContext(JinjaArgContext.class,i);
		}
		public List<TerminalNode> JINJA_COMMA() { return getTokens(JinjaParser.JINJA_COMMA); }
		public TerminalNode JINJA_COMMA(int i) {
			return getToken(JinjaParser.JINJA_COMMA, i);
		}
		public List<JinjaKwArgContext> jinjaKwArg() {
			return getRuleContexts(JinjaKwArgContext.class);
		}
		public JinjaKwArgContext jinjaKwArg(int i) {
			return getRuleContext(JinjaKwArgContext.class,i);
		}
		public CallMixedArgsContext(JinjaCallArgsContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCallMixedArgs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCallMixedArgs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCallMixedArgs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaCallArgsContext jinjaCallArgs() throws RecognitionException {
		JinjaCallArgsContext _localctx = new JinjaCallArgsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_jinjaCallArgs);
		int _la;
		try {
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new CallMixedArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(209); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(208);
					jinjaArg();
					}
					}
					setState(211); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( ((((_la - 45)) & ~0x3f) == 0 && ((1L << (_la - 45)) & 59244551L) != 0) );
				setState(222);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==JINJA_COMMA) {
					{
					setState(213);
					match(JINJA_COMMA);
					setState(214);
					jinjaKwArg();
					setState(219);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==JINJA_COMMA) {
						{
						{
						setState(215);
						match(JINJA_COMMA);
						setState(216);
						jinjaKwArg();
						}
						}
						setState(221);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
				break;
			case 2:
				_localctx = new CallKwArgsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(224);
				jinjaKwArg();
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==JINJA_COMMA) {
					{
					{
					setState(225);
					match(JINJA_COMMA);
					setState(226);
					jinjaKwArg();
					}
					}
					setState(231);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case 3:
				_localctx = new EmptyArgsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaArgContext extends ParserRuleContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaArgContext jinjaArg() throws RecognitionException {
		JinjaArgContext _localctx = new JinjaArgContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_jinjaArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			jinjaExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaKwArgContext extends ParserRuleContext {
		public TerminalNode JINJA_IDENTIFIER() { return getToken(JinjaParser.JINJA_IDENTIFIER, 0); }
		public TerminalNode JINJA_EQUALS() { return getToken(JinjaParser.JINJA_EQUALS, 0); }
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaKwArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaKwArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterJinjaKwArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitJinjaKwArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitJinjaKwArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaKwArgContext jinjaKwArg() throws RecognitionException {
		JinjaKwArgContext _localctx = new JinjaKwArgContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_jinjaKwArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			match(JINJA_IDENTIFIER);
			setState(238);
			match(JINJA_EQUALS);
			setState(239);
			jinjaExpression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlTextContext extends ParserRuleContext {
		public TerminalNode HTML_TEXT() { return getToken(JinjaParser.HTML_TEXT, 0); }
		public HtmlTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterHtmlText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitHtmlText(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitHtmlText(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlTextContext htmlText() throws RecognitionException {
		HtmlTextContext _localctx = new HtmlTextContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_htmlText);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(HTML_TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StyleTagContext extends ParserRuleContext {
		public TerminalNode STYLE_OPEN() { return getToken(JinjaParser.STYLE_OPEN, 0); }
		public TerminalNode STYLE_CLOSE() { return getToken(JinjaParser.STYLE_CLOSE, 0); }
		public List<CssRuleContext> cssRule() {
			return getRuleContexts(CssRuleContext.class);
		}
		public CssRuleContext cssRule(int i) {
			return getRuleContext(CssRuleContext.class,i);
		}
		public StyleTagContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_styleTag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterStyleTag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitStyleTag(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitStyleTag(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StyleTagContext styleTag() throws RecognitionException {
		StyleTagContext _localctx = new StyleTagContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_styleTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(STYLE_OPEN);
			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_WORD || _la==CSS_DOT) {
				{
				{
				setState(244);
				cssRule();
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(250);
			match(STYLE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssRuleContext extends ParserRuleContext {
		public CssSelectorListContext cssSelectorList() {
			return getRuleContext(CssSelectorListContext.class,0);
		}
		public TerminalNode CSS_OPEN_BRACE() { return getToken(JinjaParser.CSS_OPEN_BRACE, 0); }
		public TerminalNode CSS_CLOSE_BRACE() { return getToken(JinjaParser.CSS_CLOSE_BRACE, 0); }
		public List<CssPropertyContext> cssProperty() {
			return getRuleContexts(CssPropertyContext.class);
		}
		public CssPropertyContext cssProperty(int i) {
			return getRuleContext(CssPropertyContext.class,i);
		}
		public CssRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssRule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCssRule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCssRule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCssRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssRuleContext cssRule() throws RecognitionException {
		CssRuleContext _localctx = new CssRuleContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_cssRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			cssSelectorList();
			setState(253);
			match(CSS_OPEN_BRACE);
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_WORD) {
				{
				{
				setState(254);
				cssProperty();
				}
				}
				setState(259);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(260);
			match(CSS_CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssSelectorListContext extends ParserRuleContext {
		public List<CssSelectorContext> cssSelector() {
			return getRuleContexts(CssSelectorContext.class);
		}
		public CssSelectorContext cssSelector(int i) {
			return getRuleContext(CssSelectorContext.class,i);
		}
		public List<TerminalNode> CSS_COMMA() { return getTokens(JinjaParser.CSS_COMMA); }
		public TerminalNode CSS_COMMA(int i) {
			return getToken(JinjaParser.CSS_COMMA, i);
		}
		public CssSelectorListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssSelectorList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCssSelectorList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCssSelectorList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCssSelectorList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssSelectorListContext cssSelectorList() throws RecognitionException {
		CssSelectorListContext _localctx = new CssSelectorListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_cssSelectorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(262);
			cssSelector();
			setState(267);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_COMMA) {
				{
				{
				setState(263);
				match(CSS_COMMA);
				setState(264);
				cssSelector();
				}
				}
				setState(269);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssSelectorContext extends ParserRuleContext {
		public List<SelectorPartContext> selectorPart() {
			return getRuleContexts(SelectorPartContext.class);
		}
		public SelectorPartContext selectorPart(int i) {
			return getRuleContext(SelectorPartContext.class,i);
		}
		public TerminalNode CSS_COLON() { return getToken(JinjaParser.CSS_COLON, 0); }
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public CssSelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssSelector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCssSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCssSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCssSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssSelectorContext cssSelector() throws RecognitionException {
		CssSelectorContext _localctx = new CssSelectorContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_cssSelector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(270);
				selectorPart();
				}
				}
				setState(273); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CSS_WORD || _la==CSS_DOT );
			setState(277);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CSS_COLON) {
				{
				setState(275);
				match(CSS_COLON);
				setState(276);
				match(CSS_WORD);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorPartContext extends ParserRuleContext {
		public SelectorPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectorPart; }
	 
		public SelectorPartContext() { }
		public void copyFrom(SelectorPartContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ClassPartContext extends SelectorPartContext {
		public TerminalNode CSS_DOT() { return getToken(JinjaParser.CSS_DOT, 0); }
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public ClassPartContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterClassPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitClassPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitClassPart(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class TagPartContext extends SelectorPartContext {
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public TagPartContext(SelectorPartContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterTagPart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitTagPart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitTagPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorPartContext selectorPart() throws RecognitionException {
		SelectorPartContext _localctx = new SelectorPartContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_selectorPart);
		try {
			setState(282);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CSS_DOT:
				_localctx = new ClassPartContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(279);
				match(CSS_DOT);
				setState(280);
				match(CSS_WORD);
				}
				break;
			case CSS_WORD:
				_localctx = new TagPartContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
				match(CSS_WORD);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssPropertyContext extends ParserRuleContext {
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public TerminalNode CSS_COLON() { return getToken(JinjaParser.CSS_COLON, 0); }
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(JinjaParser.SEMICOLON, 0); }
		public CssPropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssProperty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCssProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCssProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCssProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssPropertyContext cssProperty() throws RecognitionException {
		CssPropertyContext _localctx = new CssPropertyContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_cssProperty);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			match(CSS_WORD);
			setState(285);
			match(CSS_COLON);
			setState(286);
			valueList();
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(287);
				match(SEMICOLON);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueListContext extends ParserRuleContext {
		public List<CssValueContext> cssValue() {
			return getRuleContexts(CssValueContext.class);
		}
		public CssValueContext cssValue(int i) {
			return getRuleContext(CssValueContext.class,i);
		}
		public List<TerminalNode> CSS_COMMA() { return getTokens(JinjaParser.CSS_COMMA); }
		public TerminalNode CSS_COMMA(int i) {
			return getToken(JinjaParser.CSS_COMMA, i);
		}
		public ValueListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterValueList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitValueList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitValueList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueListContext valueList() throws RecognitionException {
		ValueListContext _localctx = new ValueListContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_valueList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			cssValue();
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(292);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==CSS_COMMA) {
						{
						setState(291);
						match(CSS_COMMA);
						}
					}

					setState(294);
					cssValue();
					}
					} 
				}
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssValueContext extends ParserRuleContext {
		public CssValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssValue; }
	 
		public CssValueContext() { }
		public void copyFrom(CssValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberValueContext extends CssValueContext {
		public TerminalNode CSS_NUMBER() { return getToken(JinjaParser.CSS_NUMBER, 0); }
		public NumberValueContext(CssValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterNumberValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitNumberValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitNumberValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ColorValueContext extends CssValueContext {
		public TerminalNode CSS_COLOR() { return getToken(JinjaParser.CSS_COLOR, 0); }
		public ColorValueContext(CssValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterColorValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitColorValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitColorValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FunctionValueContext extends CssValueContext {
		public CssFunctionContext cssFunction() {
			return getRuleContext(CssFunctionContext.class,0);
		}
		public FunctionValueContext(CssValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterFunctionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitFunctionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitFunctionValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringValueContext extends CssValueContext {
		public TerminalNode CSS_STRING() { return getToken(JinjaParser.CSS_STRING, 0); }
		public StringValueContext(CssValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterStringValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitStringValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitStringValue(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class WordValueContext extends CssValueContext {
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public WordValueContext(CssValueContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterWordValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitWordValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitWordValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssValueContext cssValue() throws RecognitionException {
		CssValueContext _localctx = new CssValueContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_cssValue);
		try {
			setState(305);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				_localctx = new WordValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				match(CSS_WORD);
				}
				break;
			case 2:
				_localctx = new NumberValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				match(CSS_NUMBER);
				}
				break;
			case 3:
				_localctx = new ColorValueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(302);
				match(CSS_COLOR);
				}
				break;
			case 4:
				_localctx = new StringValueContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(303);
				match(CSS_STRING);
				}
				break;
			case 5:
				_localctx = new FunctionValueContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(304);
				cssFunction();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CssFunctionContext extends ParserRuleContext {
		public TerminalNode CSS_WORD() { return getToken(JinjaParser.CSS_WORD, 0); }
		public TerminalNode CSS_LPAREN() { return getToken(JinjaParser.CSS_LPAREN, 0); }
		public ValueListContext valueList() {
			return getRuleContext(ValueListContext.class,0);
		}
		public TerminalNode CSS_RPAREN() { return getToken(JinjaParser.CSS_RPAREN, 0); }
		public CssFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cssFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterCssFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitCssFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitCssFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CssFunctionContext cssFunction() throws RecognitionException {
		CssFunctionContext _localctx = new CssFunctionContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_cssFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			match(CSS_WORD);
			setState(308);
			match(CSS_LPAREN);
			setState(309);
			valueList();
			setState(310);
			match(CSS_RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001H\u0139\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0001\u0000\u0005\u0000:\b\u0000\n\u0000\f\u0000=\t\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0003\u0001F\b\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u0003M\b\u0003\n\u0003\f\u0003P\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u0003T\b\u0003\n\u0003\f\u0003W\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003"+
		"_\b\u0003\n\u0003\f\u0003b\t\u0003\u0001\u0003\u0003\u0003e\b\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0003\u0004j\b\u0004\u0001\u0004\u0003"+
		"\u0004m\b\u0004\u0001\u0005\u0001\u0005\u0003\u0005q\b\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006{\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007"+
		"\u008b\b\u0007\u0001\b\u0001\b\u0001\b\u0005\b\u0090\b\b\n\b\f\b\u0093"+
		"\t\b\u0001\t\u0001\t\u0001\t\u0005\t\u0098\b\t\n\t\f\t\u009b\t\t\u0001"+
		"\n\u0001\n\u0001\n\u0005\n\u00a0\b\n\n\n\f\n\u00a3\t\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0005\u000b\u00a8\b\u000b\n\u000b\f\u000b\u00ab\t\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00bc\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u00c5\b\r\n"+
		"\r\f\r\u00c8\t\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0003\u000e\u00cf\b\u000e\u0001\u000f\u0004\u000f\u00d2\b\u000f"+
		"\u000b\u000f\f\u000f\u00d3\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0005\u000f\u00da\b\u000f\n\u000f\f\u000f\u00dd\t\u000f\u0003\u000f\u00df"+
		"\b\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00e4\b\u000f"+
		"\n\u000f\f\u000f\u00e7\t\u000f\u0001\u000f\u0003\u000f\u00ea\b\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0005\u0013\u00f6\b\u0013\n"+
		"\u0013\f\u0013\u00f9\t\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001"+
		"\u0014\u0001\u0014\u0005\u0014\u0100\b\u0014\n\u0014\f\u0014\u0103\t\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015"+
		"\u010a\b\u0015\n\u0015\f\u0015\u010d\t\u0015\u0001\u0016\u0004\u0016\u0110"+
		"\b\u0016\u000b\u0016\f\u0016\u0111\u0001\u0016\u0001\u0016\u0003\u0016"+
		"\u0116\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0003\u0017\u011b\b"+
		"\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003\u0018\u0121"+
		"\b\u0018\u0001\u0019\u0001\u0019\u0003\u0019\u0125\b\u0019\u0001\u0019"+
		"\u0005\u0019\u0128\b\u0019\n\u0019\f\u0019\u012b\t\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003\u001a\u0132\b\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0000"+
		"\u0000\u001c\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.0246\u0000\u0006\u0002\u0000\u0001\u0001"+
		"\u000f\u000f\u0002\u0000\u0002\u0002\u0010\u0010\u0002\u000047>?\u0001"+
		"\u00009;\u0001\u0000<=\u0002\u0000&&DD\u0151\u0000;\u0001\u0000\u0000"+
		"\u0000\u0002E\u0001\u0000\u0000\u0000\u0004G\u0001\u0000\u0000\u0000\u0006"+
		"d\u0001\u0000\u0000\u0000\bl\u0001\u0000\u0000\u0000\np\u0001\u0000\u0000"+
		"\u0000\fz\u0001\u0000\u0000\u0000\u000e\u008a\u0001\u0000\u0000\u0000"+
		"\u0010\u008c\u0001\u0000\u0000\u0000\u0012\u0094\u0001\u0000\u0000\u0000"+
		"\u0014\u009c\u0001\u0000\u0000\u0000\u0016\u00a4\u0001\u0000\u0000\u0000"+
		"\u0018\u00bb\u0001\u0000\u0000\u0000\u001a\u00bd\u0001\u0000\u0000\u0000"+
		"\u001c\u00c9\u0001\u0000\u0000\u0000\u001e\u00e9\u0001\u0000\u0000\u0000"+
		" \u00eb\u0001\u0000\u0000\u0000\"\u00ed\u0001\u0000\u0000\u0000$\u00f1"+
		"\u0001\u0000\u0000\u0000&\u00f3\u0001\u0000\u0000\u0000(\u00fc\u0001\u0000"+
		"\u0000\u0000*\u0106\u0001\u0000\u0000\u0000,\u010f\u0001\u0000\u0000\u0000"+
		".\u011a\u0001\u0000\u0000\u00000\u011c\u0001\u0000\u0000\u00002\u0122"+
		"\u0001\u0000\u0000\u00004\u0131\u0001\u0000\u0000\u00006\u0133\u0001\u0000"+
		"\u0000\u00008:\u0003\u0002\u0001\u000098\u0001\u0000\u0000\u0000:=\u0001"+
		"\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000"+
		"<>\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000>?\u0005\u0000\u0000"+
		"\u0001?\u0001\u0001\u0000\u0000\u0000@F\u0003\u0004\u0002\u0000AF\u0003"+
		"&\u0013\u0000BF\u0003\f\u0006\u0000CF\u0003\u0006\u0003\u0000DF\u0003"+
		"$\u0012\u0000E@\u0001\u0000\u0000\u0000EA\u0001\u0000\u0000\u0000EB\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000ED\u0001\u0000\u0000\u0000"+
		"F\u0003\u0001\u0000\u0000\u0000GH\u0005\u0006\u0000\u0000H\u0005\u0001"+
		"\u0000\u0000\u0000IJ\u0005\u0003\u0000\u0000JN\u0005\f\u0000\u0000KM\u0003"+
		"\b\u0004\u0000LK\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001"+
		"\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000"+
		"PN\u0001\u0000\u0000\u0000QU\u0005\n\u0000\u0000RT\u0003\u0002\u0001\u0000"+
		"SR\u0001\u0000\u0000\u0000TW\u0001\u0000\u0000\u0000US\u0001\u0000\u0000"+
		"\u0000UV\u0001\u0000\u0000\u0000VX\u0001\u0000\u0000\u0000WU\u0001\u0000"+
		"\u0000\u0000XY\u0005\u0004\u0000\u0000YZ\u0005\f\u0000\u0000Ze\u0005\n"+
		"\u0000\u0000[\\\u0005\u0003\u0000\u0000\\`\u0005\f\u0000\u0000]_\u0003"+
		"\b\u0004\u0000^]\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001"+
		"\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000ac\u0001\u0000\u0000\u0000"+
		"b`\u0001\u0000\u0000\u0000ce\u0005\u000b\u0000\u0000dI\u0001\u0000\u0000"+
		"\u0000d[\u0001\u0000\u0000\u0000e\u0007\u0001\u0000\u0000\u0000fi\u0005"+
		"\f\u0000\u0000gh\u0005\r\u0000\u0000hj\u0003\n\u0005\u0000ig\u0001\u0000"+
		"\u0000\u0000ij\u0001\u0000\u0000\u0000jm\u0001\u0000\u0000\u0000km\u0003"+
		"\f\u0006\u0000lf\u0001\u0000\u0000\u0000lk\u0001\u0000\u0000\u0000m\t"+
		"\u0001\u0000\u0000\u0000nq\u0005\u000e\u0000\u0000oq\u0003\f\u0006\u0000"+
		"pn\u0001\u0000\u0000\u0000po\u0001\u0000\u0000\u0000q\u000b\u0001\u0000"+
		"\u0000\u0000rs\u0007\u0000\u0000\u0000st\u0003\u000e\u0007\u0000tu\u0005"+
		"!\u0000\u0000u{\u0001\u0000\u0000\u0000vw\u0007\u0001\u0000\u0000wx\u0003"+
		"\u0010\b\u0000xy\u0005\"\u0000\u0000y{\u0001\u0000\u0000\u0000zr\u0001"+
		"\u0000\u0000\u0000zv\u0001\u0000\u0000\u0000{\r\u0001\u0000\u0000\u0000"+
		"|}\u0005#\u0000\u0000}\u008b\u0005F\u0000\u0000~\u007f\u0005$\u0000\u0000"+
		"\u007f\u008b\u0005D\u0000\u0000\u0080\u008b\u0005%\u0000\u0000\u0081\u0082"+
		"\u0005\'\u0000\u0000\u0082\u008b\u0003\u0010\b\u0000\u0083\u008b\u0005"+
		"*\u0000\u0000\u0084\u008b\u0005+\u0000\u0000\u0085\u0086\u0005(\u0000"+
		"\u0000\u0086\u0087\u0005D\u0000\u0000\u0087\u0088\u0005)\u0000\u0000\u0088"+
		"\u008b\u0003\u0010\b\u0000\u0089\u008b\u0005,\u0000\u0000\u008a|\u0001"+
		"\u0000\u0000\u0000\u008a~\u0001\u0000\u0000\u0000\u008a\u0080\u0001\u0000"+
		"\u0000\u0000\u008a\u0081\u0001\u0000\u0000\u0000\u008a\u0083\u0001\u0000"+
		"\u0000\u0000\u008a\u0084\u0001\u0000\u0000\u0000\u008a\u0085\u0001\u0000"+
		"\u0000\u0000\u008a\u0089\u0001\u0000\u0000\u0000\u008b\u000f\u0001\u0000"+
		"\u0000\u0000\u008c\u0091\u0003\u0012\t\u0000\u008d\u008e\u00050\u0000"+
		"\u0000\u008e\u0090\u0003\u001c\u000e\u0000\u008f\u008d\u0001\u0000\u0000"+
		"\u0000\u0090\u0093\u0001\u0000\u0000\u0000\u0091\u008f\u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0011\u0001\u0000\u0000"+
		"\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0094\u0099\u0003\u0014\n\u0000"+
		"\u0095\u0096\u0007\u0002\u0000\u0000\u0096\u0098\u0003\u0014\n\u0000\u0097"+
		"\u0095\u0001\u0000\u0000\u0000\u0098\u009b\u0001\u0000\u0000\u0000\u0099"+
		"\u0097\u0001\u0000\u0000\u0000\u0099\u009a\u0001\u0000\u0000\u0000\u009a"+
		"\u0013\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009c"+
		"\u00a1\u0003\u0016\u000b\u0000\u009d\u009e\u0007\u0003\u0000\u0000\u009e"+
		"\u00a0\u0003\u0016\u000b\u0000\u009f\u009d\u0001\u0000\u0000\u0000\u00a0"+
		"\u00a3\u0001\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1"+
		"\u00a2\u0001\u0000\u0000\u0000\u00a2\u0015\u0001\u0000\u0000\u0000\u00a3"+
		"\u00a1\u0001\u0000\u0000\u0000\u00a4\u00a9\u0003\u0018\f\u0000\u00a5\u00a6"+
		"\u0007\u0004\u0000\u0000\u00a6\u00a8\u0003\u0018\f\u0000\u00a7\u00a5\u0001"+
		"\u0000\u0000\u0000\u00a8\u00ab\u0001\u0000\u0000\u0000\u00a9\u00a7\u0001"+
		"\u0000\u0000\u0000\u00a9\u00aa\u0001\u0000\u0000\u0000\u00aa\u0017\u0001"+
		"\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ac\u00bc\u0003"+
		"\u001a\r\u0000\u00ad\u00bc\u0005F\u0000\u0000\u00ae\u00bc\u0005E\u0000"+
		"\u0000\u00af\u00bc\u0005-\u0000\u0000\u00b0\u00bc\u0005.\u0000\u0000\u00b1"+
		"\u00bc\u0005/\u0000\u0000\u00b2\u00b3\u0005D\u0000\u0000\u00b3\u00b4\u0005"+
		"@\u0000\u0000\u00b4\u00b5\u0003\u001e\u000f\u0000\u00b5\u00b6\u0005A\u0000"+
		"\u0000\u00b6\u00bc\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005@\u0000\u0000"+
		"\u00b8\u00b9\u0003\u0010\b\u0000\u00b9\u00ba\u0005A\u0000\u0000\u00ba"+
		"\u00bc\u0001\u0000\u0000\u0000\u00bb\u00ac\u0001\u0000\u0000\u0000\u00bb"+
		"\u00ad\u0001\u0000\u0000\u0000\u00bb\u00ae\u0001\u0000\u0000\u0000\u00bb"+
		"\u00af\u0001\u0000\u0000\u0000\u00bb\u00b0\u0001\u0000\u0000\u0000\u00bb"+
		"\u00b1\u0001\u0000\u0000\u0000\u00bb\u00b2\u0001\u0000\u0000\u0000\u00bb"+
		"\u00b7\u0001\u0000\u0000\u0000\u00bc\u0019\u0001\u0000\u0000\u0000\u00bd"+
		"\u00c6\u0005D\u0000\u0000\u00be\u00bf\u00051\u0000\u0000\u00bf\u00c5\u0005"+
		"D\u0000\u0000\u00c0\u00c1\u0005B\u0000\u0000\u00c1\u00c2\u0003\u0010\b"+
		"\u0000\u00c2\u00c3\u0005C\u0000\u0000\u00c3\u00c5\u0001\u0000\u0000\u0000"+
		"\u00c4\u00be\u0001\u0000\u0000\u0000\u00c4\u00c0\u0001\u0000\u0000\u0000"+
		"\u00c5\u00c8\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000\u0000"+
		"\u00c6\u00c7\u0001\u0000\u0000\u0000\u00c7\u001b\u0001\u0000\u0000\u0000"+
		"\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c9\u00ce\u0007\u0005\u0000\u0000"+
		"\u00ca\u00cb\u0005@\u0000\u0000\u00cb\u00cc\u0003\u001e\u000f\u0000\u00cc"+
		"\u00cd\u0005A\u0000\u0000\u00cd\u00cf\u0001\u0000\u0000\u0000\u00ce\u00ca"+
		"\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000\u00cf\u001d"+
		"\u0001\u0000\u0000\u0000\u00d0\u00d2\u0003 \u0010\u0000\u00d1\u00d0\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d3\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4\u00de\u0001"+
		"\u0000\u0000\u0000\u00d5\u00d6\u00052\u0000\u0000\u00d6\u00db\u0003\""+
		"\u0011\u0000\u00d7\u00d8\u00052\u0000\u0000\u00d8\u00da\u0003\"\u0011"+
		"\u0000\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00dd\u0001\u0000\u0000"+
		"\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000"+
		"\u0000\u00dc\u00df\u0001\u0000\u0000\u0000\u00dd\u00db\u0001\u0000\u0000"+
		"\u0000\u00de\u00d5\u0001\u0000\u0000\u0000\u00de\u00df\u0001\u0000\u0000"+
		"\u0000\u00df\u00ea\u0001\u0000\u0000\u0000\u00e0\u00e5\u0003\"\u0011\u0000"+
		"\u00e1\u00e2\u00052\u0000\u0000\u00e2\u00e4\u0003\"\u0011\u0000\u00e3"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e4\u00e7\u0001\u0000\u0000\u0000\u00e5"+
		"\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6"+
		"\u00ea\u0001\u0000\u0000\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8"+
		"\u00ea\u0001\u0000\u0000\u0000\u00e9\u00d1\u0001\u0000\u0000\u0000\u00e9"+
		"\u00e0\u0001\u0000\u0000\u0000\u00e9\u00e8\u0001\u0000\u0000\u0000\u00ea"+
		"\u001f\u0001\u0000\u0000\u0000\u00eb\u00ec\u0003\u0010\b\u0000\u00ec!"+
		"\u0001\u0000\u0000\u0000\u00ed\u00ee\u0005D\u0000\u0000\u00ee\u00ef\u0005"+
		"8\u0000\u0000\u00ef\u00f0\u0003\u0010\b\u0000\u00f0#\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0005\b\u0000\u0000\u00f2%\u0001\u0000\u0000\u0000"+
		"\u00f3\u00f7\u0005\u0007\u0000\u0000\u00f4\u00f6\u0003(\u0014\u0000\u00f5"+
		"\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f9\u0001\u0000\u0000\u0000\u00f7"+
		"\u00f5\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000\u00f8"+
		"\u00fa\u0001\u0000\u0000\u0000\u00f9\u00f7\u0001\u0000\u0000\u0000\u00fa"+
		"\u00fb\u0005\u0012\u0000\u0000\u00fb\'\u0001\u0000\u0000\u0000\u00fc\u00fd"+
		"\u0003*\u0015\u0000\u00fd\u0101\u0005\u001a\u0000\u0000\u00fe\u0100\u0003"+
		"0\u0018\u0000\u00ff\u00fe\u0001\u0000\u0000\u0000\u0100\u0103\u0001\u0000"+
		"\u0000\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0101\u0102\u0001\u0000"+
		"\u0000\u0000\u0102\u0104\u0001\u0000\u0000\u0000\u0103\u0101\u0001\u0000"+
		"\u0000\u0000\u0104\u0105\u0005\u001b\u0000\u0000\u0105)\u0001\u0000\u0000"+
		"\u0000\u0106\u010b\u0003,\u0016\u0000\u0107\u0108\u0005\u001d\u0000\u0000"+
		"\u0108\u010a\u0003,\u0016\u0000\u0109\u0107\u0001\u0000\u0000\u0000\u010a"+
		"\u010d\u0001\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010b"+
		"\u010c\u0001\u0000\u0000\u0000\u010c+\u0001\u0000\u0000\u0000\u010d\u010b"+
		"\u0001\u0000\u0000\u0000\u010e\u0110\u0003.\u0017\u0000\u010f\u010e\u0001"+
		"\u0000\u0000\u0000\u0110\u0111\u0001\u0000\u0000\u0000\u0111\u010f\u0001"+
		"\u0000\u0000\u0000\u0111\u0112\u0001\u0000\u0000\u0000\u0112\u0115\u0001"+
		"\u0000\u0000\u0000\u0113\u0114\u0005\u0019\u0000\u0000\u0114\u0116\u0005"+
		"\u0015\u0000\u0000\u0115\u0113\u0001\u0000\u0000\u0000\u0115\u0116\u0001"+
		"\u0000\u0000\u0000\u0116-\u0001\u0000\u0000\u0000\u0117\u0118\u0005\u0016"+
		"\u0000\u0000\u0118\u011b\u0005\u0015\u0000\u0000\u0119\u011b\u0005\u0015"+
		"\u0000\u0000\u011a\u0117\u0001\u0000\u0000\u0000\u011a\u0119\u0001\u0000"+
		"\u0000\u0000\u011b/\u0001\u0000\u0000\u0000\u011c\u011d\u0005\u0015\u0000"+
		"\u0000\u011d\u011e\u0005\u0019\u0000\u0000\u011e\u0120\u00032\u0019\u0000"+
		"\u011f\u0121\u0005\u001c\u0000\u0000\u0120\u011f\u0001\u0000\u0000\u0000"+
		"\u0120\u0121\u0001\u0000\u0000\u0000\u01211\u0001\u0000\u0000\u0000\u0122"+
		"\u0129\u00034\u001a\u0000\u0123\u0125\u0005\u001d\u0000\u0000\u0124\u0123"+
		"\u0001\u0000\u0000\u0000\u0124\u0125\u0001\u0000\u0000\u0000\u0125\u0126"+
		"\u0001\u0000\u0000\u0000\u0126\u0128\u00034\u001a\u0000\u0127\u0124\u0001"+
		"\u0000\u0000\u0000\u0128\u012b\u0001\u0000\u0000\u0000\u0129\u0127\u0001"+
		"\u0000\u0000\u0000\u0129\u012a\u0001\u0000\u0000\u0000\u012a3\u0001\u0000"+
		"\u0000\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012c\u0132\u0005\u0015"+
		"\u0000\u0000\u012d\u0132\u0005\u0017\u0000\u0000\u012e\u0132\u0005\u0018"+
		"\u0000\u0000\u012f\u0132\u0005\u0014\u0000\u0000\u0130\u0132\u00036\u001b"+
		"\u0000\u0131\u012c\u0001\u0000\u0000\u0000\u0131\u012d\u0001\u0000\u0000"+
		"\u0000\u0131\u012e\u0001\u0000\u0000\u0000\u0131\u012f\u0001\u0000\u0000"+
		"\u0000\u0131\u0130\u0001\u0000\u0000\u0000\u01325\u0001\u0000\u0000\u0000"+
		"\u0133\u0134\u0005\u0015\u0000\u0000\u0134\u0135\u0005\u001e\u0000\u0000"+
		"\u0135\u0136\u00032\u0019\u0000\u0136\u0137\u0005\u001f\u0000\u0000\u0137"+
		"7\u0001\u0000\u0000\u0000\";ENU`dilpz\u008a\u0091\u0099\u00a1\u00a9\u00bb"+
		"\u00c4\u00c6\u00ce\u00d3\u00db\u00de\u00e5\u00e9\u00f7\u0101\u010b\u0111"+
		"\u0115\u011a\u0120\u0124\u0129\u0131";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}