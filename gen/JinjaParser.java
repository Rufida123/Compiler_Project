// Generated from C:/Users/rufida/Desktop/New folder/Compiler_Project/src/jinjaAntlr/JinjaParser.g4 by ANTLR 4.13.2
 package antlr; 
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
		HTML_COMMENT=1, DOCTYPE=2, STYLE_OPEN=3, JINJA_BLOCK_START=4, JINJA_EXPR_START=5, 
		HTML_OPEN_TAG=6, HTML_CLOSE_TAG=7, HTML_SELF_CLOSING_TAG=8, HTML_TEXT=9, 
		WS=10, HTML_ATTRIBUTE=11, HTML_ATTRIBUTE_VALUE=12, CSS_COMMENT=13, STYLE_CLOSE=14, 
		CSS_STRING=15, CSS_WORD=16, CSS_DOT=17, CSS_NUMBER=18, CSS_COLOR=19, CSS_COLON=20, 
		CSS_OPEN_BRACE=21, CSS_CLOSE_BRACE=22, SEMICOLON=23, CSS_COMMA=24, CSS_LPAREN=25, 
		CSS_RPAREN=26, CSS_WS=27, JINJA_BLOCK_END=28, JINJA_EXPR_END=29, JINJA_EXTENDS=30, 
		JINJA_BLOCK=31, JINJA_ENDBLOCK=32, JINJA_FORMAT=33, JINJA_IF=34, JINJA_FOR=35, 
		JINJA_IN=36, JINJA_ELSE=37, JINJA_ENDIF=38, JINJA_ENDFOR=39, JINJA_TRUE=40, 
		JINJA_FALSE=41, JINJA_NONE=42, JINJA_PIPE=43, JINJA_DOT=44, JINJA_COMMA=45, 
		JINJA_COLON=46, JINJA_EQUALS=47, JINJA_PLUS=48, JINJA_MINUS=49, JINJA_MULT=50, 
		JINJA_DIV=51, JINJA_OPAR=52, JINJA_CPAR=53, JINJA_OBRACK=54, JINJA_CBRACK=55, 
		JINJA_IDENTIFIER=56, JINJA_NUMBER=57, JINJA_STRING=58, JINJA_WS=59, JINJA_COMMENT=60;
	public static final int
		RULE_document = 0, RULE_htmlElement = 1, RULE_htmlTag = 2, RULE_jinjaBlock = 3, 
		RULE_jinjaStatementHeader = 4, RULE_jinjaExpression = 5, RULE_jinjaPrimary = 6, 
		RULE_jinjaIdentifierChain = 7, RULE_jinjaFilter = 8, RULE_jinjaCallArgs = 9, 
		RULE_jinjaArg = 10, RULE_jinjaKwArg = 11, RULE_htmlText = 12, RULE_styleTag = 13, 
		RULE_cssRule = 14, RULE_cssSelectorList = 15, RULE_cssSelector = 16, RULE_cssProperty = 17, 
		RULE_valueList = 18, RULE_cssValue = 19, RULE_cssFunction = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"document", "htmlElement", "htmlTag", "jinjaBlock", "jinjaStatementHeader", 
			"jinjaExpression", "jinjaPrimary", "jinjaIdentifierChain", "jinjaFilter", 
			"jinjaCallArgs", "jinjaArg", "jinjaKwArg", "htmlText", "styleTag", "cssRule", 
			"cssSelectorList", "cssSelector", "cssProperty", "valueList", "cssValue", 
			"cssFunction"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'{%'", "'{{'", null, null, null, null, null, 
			null, null, null, "'</style>'", null, null, null, null, null, null, "'{'", 
			"'}'", "';'", null, null, null, null, "'%}'", "'}}'", "'extends'", "'block'", 
			"'endblock'", "'format'", "'if'", "'for'", "'in'", "'else'", "'endif'", 
			"'endfor'", "'true'", "'false'", "'none'", "'|'", null, null, null, "'='", 
			"'+'", "'-'", "'*'", "'/'", null, null, "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "HTML_COMMENT", "DOCTYPE", "STYLE_OPEN", "JINJA_BLOCK_START", "JINJA_EXPR_START", 
			"HTML_OPEN_TAG", "HTML_CLOSE_TAG", "HTML_SELF_CLOSING_TAG", "HTML_TEXT", 
			"WS", "HTML_ATTRIBUTE", "HTML_ATTRIBUTE_VALUE", "CSS_COMMENT", "STYLE_CLOSE", 
			"CSS_STRING", "CSS_WORD", "CSS_DOT", "CSS_NUMBER", "CSS_COLOR", "CSS_COLON", 
			"CSS_OPEN_BRACE", "CSS_CLOSE_BRACE", "SEMICOLON", "CSS_COMMA", "CSS_LPAREN", 
			"CSS_RPAREN", "CSS_WS", "JINJA_BLOCK_END", "JINJA_EXPR_END", "JINJA_EXTENDS", 
			"JINJA_BLOCK", "JINJA_ENDBLOCK", "JINJA_FORMAT", "JINJA_IF", "JINJA_FOR", 
			"JINJA_IN", "JINJA_ELSE", "JINJA_ENDIF", "JINJA_ENDFOR", "JINJA_TRUE", 
			"JINJA_FALSE", "JINJA_NONE", "JINJA_PIPE", "JINJA_DOT", "JINJA_COMMA", 
			"JINJA_COLON", "JINJA_EQUALS", "JINJA_PLUS", "JINJA_MINUS", "JINJA_MULT", 
			"JINJA_DIV", "JINJA_OPAR", "JINJA_CPAR", "JINJA_OBRACK", "JINJA_CBRACK", 
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
	public static class DocumentContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JinjaParser.EOF, 0); }
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public DocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_document; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitDocument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DocumentContext document() throws RecognitionException {
		DocumentContext _localctx = new DocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_document);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 888L) != 0)) {
				{
				{
				setState(42);
				htmlElement();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
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
	public static class HtmlElementContext extends ParserRuleContext {
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
		public HtmlElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).enterHtmlElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JinjaParserListener ) ((JinjaParserListener)listener).exitHtmlElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JinjaParserVisitor ) return ((JinjaParserVisitor<? extends T>)visitor).visitHtmlElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlElementContext htmlElement() throws RecognitionException {
		HtmlElementContext _localctx = new HtmlElementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_htmlElement);
		try {
			setState(54);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STYLE_OPEN:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				styleTag();
				}
				break;
			case JINJA_BLOCK_START:
			case JINJA_EXPR_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(51);
				jinjaBlock();
				}
				break;
			case HTML_OPEN_TAG:
			case HTML_SELF_CLOSING_TAG:
				enterOuterAlt(_localctx, 3);
				{
				setState(52);
				htmlTag();
				}
				break;
			case HTML_TEXT:
				enterOuterAlt(_localctx, 4);
				{
				setState(53);
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
		public TerminalNode HTML_OPEN_TAG() { return getToken(JinjaParser.HTML_OPEN_TAG, 0); }
		public TerminalNode HTML_CLOSE_TAG() { return getToken(JinjaParser.HTML_CLOSE_TAG, 0); }
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
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
		public TerminalNode HTML_SELF_CLOSING_TAG() { return getToken(JinjaParser.HTML_SELF_CLOSING_TAG, 0); }
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
		enterRule(_localctx, 4, RULE_htmlTag);
		int _la;
		try {
			setState(65);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HTML_OPEN_TAG:
				_localctx = new PairedTagContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(HTML_OPEN_TAG);
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 888L) != 0)) {
					{
					{
					setState(57);
					htmlElement();
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(63);
				match(HTML_CLOSE_TAG);
				}
				break;
			case HTML_SELF_CLOSING_TAG:
				_localctx = new SelfClosingTagContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(64);
				match(HTML_SELF_CLOSING_TAG);
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
		public TerminalNode JINJA_BLOCK_START() { return getToken(JinjaParser.JINJA_BLOCK_START, 0); }
		public JinjaStatementHeaderContext jinjaStatementHeader() {
			return getRuleContext(JinjaStatementHeaderContext.class,0);
		}
		public TerminalNode JINJA_BLOCK_END() { return getToken(JinjaParser.JINJA_BLOCK_END, 0); }
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
		public TerminalNode JINJA_EXPR_START() { return getToken(JinjaParser.JINJA_EXPR_START, 0); }
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public TerminalNode JINJA_EXPR_END() { return getToken(JinjaParser.JINJA_EXPR_END, 0); }
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
		enterRule(_localctx, 6, RULE_jinjaBlock);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_BLOCK_START:
				_localctx = new ControlBlockContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				match(JINJA_BLOCK_START);
				setState(68);
				jinjaStatementHeader();
				setState(69);
				match(JINJA_BLOCK_END);
				}
				break;
			case JINJA_EXPR_START:
				_localctx = new PrintBlockContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				match(JINJA_EXPR_START);
				setState(72);
				jinjaExpression();
				setState(73);
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
		enterRule(_localctx, 8, RULE_jinjaStatementHeader);
		try {
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_EXTENDS:
				_localctx = new ExtendsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				match(JINJA_EXTENDS);
				setState(78);
				match(JINJA_STRING);
				}
				break;
			case JINJA_BLOCK:
				_localctx = new BlockStartContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				match(JINJA_BLOCK);
				setState(80);
				match(JINJA_IDENTIFIER);
				}
				break;
			case JINJA_ENDBLOCK:
				_localctx = new BlockEndContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(81);
				match(JINJA_ENDBLOCK);
				}
				break;
			case JINJA_IF:
				_localctx = new IfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(82);
				match(JINJA_IF);
				setState(83);
				jinjaExpression();
				}
				break;
			case JINJA_ELSE:
				_localctx = new ElseContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(84);
				match(JINJA_ELSE);
				}
				break;
			case JINJA_ENDIF:
				_localctx = new EndIfContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(85);
				match(JINJA_ENDIF);
				}
				break;
			case JINJA_FOR:
				_localctx = new ForContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(86);
				match(JINJA_FOR);
				setState(87);
				match(JINJA_IDENTIFIER);
				setState(88);
				match(JINJA_IN);
				setState(89);
				jinjaExpression();
				}
				break;
			case JINJA_ENDFOR:
				_localctx = new EndForContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(90);
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
		public JinjaPrimaryContext jinjaPrimary() {
			return getRuleContext(JinjaPrimaryContext.class,0);
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
		enterRule(_localctx, 10, RULE_jinjaExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			jinjaPrimary();
			setState(98);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JINJA_PIPE) {
				{
				{
				setState(94);
				match(JINJA_PIPE);
				setState(95);
				jinjaFilter();
				}
				}
				setState(100);
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
		enterRule(_localctx, 12, RULE_jinjaPrimary);
		try {
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				_localctx = new AccessExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				jinjaIdentifierChain();
				}
				break;
			case 2:
				_localctx = new StringLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				match(JINJA_STRING);
				}
				break;
			case 3:
				_localctx = new NumberLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				match(JINJA_NUMBER);
				}
				break;
			case 4:
				_localctx = new TrueLiteralContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(104);
				match(JINJA_TRUE);
				}
				break;
			case 5:
				_localctx = new FalseLiteralContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(105);
				match(JINJA_FALSE);
				}
				break;
			case 6:
				_localctx = new NoneLiteralContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(106);
				match(JINJA_NONE);
				}
				break;
			case 7:
				_localctx = new FunctionCallContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(107);
				match(JINJA_IDENTIFIER);
				setState(108);
				match(JINJA_OPAR);
				setState(109);
				jinjaCallArgs();
				setState(110);
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
		enterRule(_localctx, 14, RULE_jinjaIdentifierChain);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(JINJA_IDENTIFIER);
			setState(123);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==JINJA_DOT || _la==JINJA_OBRACK) {
				{
				setState(121);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case JINJA_DOT:
					{
					{
					setState(115);
					match(JINJA_DOT);
					setState(116);
					match(JINJA_IDENTIFIER);
					}
					}
					break;
				case JINJA_OBRACK:
					{
					{
					setState(117);
					match(JINJA_OBRACK);
					setState(118);
					jinjaExpression();
					setState(119);
					match(JINJA_CBRACK);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(125);
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
		enterRule(_localctx, 16, RULE_jinjaFilter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			_la = _input.LA(1);
			if ( !(_la==JINJA_FORMAT || _la==JINJA_IDENTIFIER) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(131);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==JINJA_OPAR) {
				{
				setState(127);
				match(JINJA_OPAR);
				setState(128);
				jinjaCallArgs();
				setState(129);
				match(JINJA_CPAR);
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
		enterRule(_localctx, 18, RULE_jinjaCallArgs);
		int _la;
		try {
			setState(158);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new CallMixedArgsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(134); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(133);
					jinjaArg();
					}
					}
					setState(136); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 504410854846889984L) != 0) );
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==JINJA_COMMA) {
					{
					setState(138);
					match(JINJA_COMMA);
					setState(139);
					jinjaKwArg();
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==JINJA_COMMA) {
						{
						{
						setState(140);
						match(JINJA_COMMA);
						setState(141);
						jinjaKwArg();
						}
						}
						setState(146);
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
				setState(149);
				jinjaKwArg();
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==JINJA_COMMA) {
					{
					{
					setState(150);
					match(JINJA_COMMA);
					setState(151);
					jinjaKwArg();
					}
					}
					setState(156);
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
		enterRule(_localctx, 20, RULE_jinjaArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
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
		enterRule(_localctx, 22, RULE_jinjaKwArg);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(162);
			match(JINJA_IDENTIFIER);
			setState(163);
			match(JINJA_EQUALS);
			setState(164);
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
		enterRule(_localctx, 24, RULE_htmlText);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
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
		enterRule(_localctx, 26, RULE_styleTag);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(STYLE_OPEN);
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_WORD || _la==CSS_DOT) {
				{
				{
				setState(169);
				cssRule();
				}
				}
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(175);
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
		enterRule(_localctx, 28, RULE_cssRule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			cssSelectorList();
			setState(178);
			match(CSS_OPEN_BRACE);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_WORD) {
				{
				{
				setState(179);
				cssProperty();
				}
				}
				setState(184);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(185);
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
		enterRule(_localctx, 30, RULE_cssSelectorList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			cssSelector();
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CSS_COMMA) {
				{
				{
				setState(188);
				match(CSS_COMMA);
				setState(189);
				cssSelector();
				}
				}
				setState(194);
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
		public List<TerminalNode> CSS_DOT() { return getTokens(JinjaParser.CSS_DOT); }
		public TerminalNode CSS_DOT(int i) {
			return getToken(JinjaParser.CSS_DOT, i);
		}
		public List<TerminalNode> CSS_WORD() { return getTokens(JinjaParser.CSS_WORD); }
		public TerminalNode CSS_WORD(int i) {
			return getToken(JinjaParser.CSS_WORD, i);
		}
		public TerminalNode CSS_COLON() { return getToken(JinjaParser.CSS_COLON, 0); }
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
		enterRule(_localctx, 32, RULE_cssSelector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(198);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case CSS_DOT:
					{
					setState(195);
					match(CSS_DOT);
					setState(196);
					match(CSS_WORD);
					}
					break;
				case CSS_WORD:
					{
					setState(197);
					match(CSS_WORD);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(200); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CSS_WORD || _la==CSS_DOT );
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CSS_COLON) {
				{
				setState(202);
				match(CSS_COLON);
				setState(203);
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
		enterRule(_localctx, 34, RULE_cssProperty);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(CSS_WORD);
			setState(207);
			match(CSS_COLON);
			setState(208);
			valueList();
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SEMICOLON) {
				{
				setState(209);
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
		enterRule(_localctx, 36, RULE_valueList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			cssValue();
			setState(219);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(214);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==CSS_COMMA) {
						{
						setState(213);
						match(CSS_COMMA);
						}
					}

					setState(216);
					cssValue();
					}
					} 
				}
				setState(221);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
		enterRule(_localctx, 38, RULE_cssValue);
		try {
			setState(227);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				_localctx = new WordValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				match(CSS_WORD);
				}
				break;
			case 2:
				_localctx = new NumberValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				match(CSS_NUMBER);
				}
				break;
			case 3:
				_localctx = new ColorValueContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(224);
				match(CSS_COLOR);
				}
				break;
			case 4:
				_localctx = new StringValueContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(225);
				match(CSS_STRING);
				}
				break;
			case 5:
				_localctx = new FunctionValueContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(226);
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
		enterRule(_localctx, 40, RULE_cssFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			match(CSS_WORD);
			setState(230);
			match(CSS_LPAREN);
			setState(231);
			valueList();
			setState(232);
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
		"\u0004\u0001<\u00eb\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0005\u0000"+
		",\b\u0000\n\u0000\f\u0000/\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u00017\b\u0001\u0001\u0002"+
		"\u0001\u0002\u0005\u0002;\b\u0002\n\u0002\f\u0002>\t\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002B\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"L\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\\\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0005\u0005a\b\u0005\n\u0005\f\u0005d\t\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006"+
		"q\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007z\b\u0007\n\u0007\f\u0007}\t\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u0084\b\b\u0001\t\u0004"+
		"\t\u0087\b\t\u000b\t\f\t\u0088\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t"+
		"\u008f\b\t\n\t\f\t\u0092\t\t\u0003\t\u0094\b\t\u0001\t\u0001\t\u0001\t"+
		"\u0005\t\u0099\b\t\n\t\f\t\u009c\t\t\u0001\t\u0003\t\u009f\b\t\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0005\r\u00ab\b\r\n\r\f\r\u00ae\t\r\u0001\r\u0001\r"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00b5\b\u000e\n\u000e"+
		"\f\u000e\u00b8\t\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0005\u000f\u00bf\b\u000f\n\u000f\f\u000f\u00c2\t\u000f\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0004\u0010\u00c7\b\u0010\u000b\u0010\f"+
		"\u0010\u00c8\u0001\u0010\u0001\u0010\u0003\u0010\u00cd\b\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00d3\b\u0011\u0001\u0012"+
		"\u0001\u0012\u0003\u0012\u00d7\b\u0012\u0001\u0012\u0005\u0012\u00da\b"+
		"\u0012\n\u0012\f\u0012\u00dd\t\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u00e4\b\u0013\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0000\u0000\u0015\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(\u0000\u0001\u0002\u0000!!88\u0100\u0000-\u0001\u0000\u0000"+
		"\u0000\u00026\u0001\u0000\u0000\u0000\u0004A\u0001\u0000\u0000\u0000\u0006"+
		"K\u0001\u0000\u0000\u0000\b[\u0001\u0000\u0000\u0000\n]\u0001\u0000\u0000"+
		"\u0000\fp\u0001\u0000\u0000\u0000\u000er\u0001\u0000\u0000\u0000\u0010"+
		"~\u0001\u0000\u0000\u0000\u0012\u009e\u0001\u0000\u0000\u0000\u0014\u00a0"+
		"\u0001\u0000\u0000\u0000\u0016\u00a2\u0001\u0000\u0000\u0000\u0018\u00a6"+
		"\u0001\u0000\u0000\u0000\u001a\u00a8\u0001\u0000\u0000\u0000\u001c\u00b1"+
		"\u0001\u0000\u0000\u0000\u001e\u00bb\u0001\u0000\u0000\u0000 \u00c6\u0001"+
		"\u0000\u0000\u0000\"\u00ce\u0001\u0000\u0000\u0000$\u00d4\u0001\u0000"+
		"\u0000\u0000&\u00e3\u0001\u0000\u0000\u0000(\u00e5\u0001\u0000\u0000\u0000"+
		"*,\u0003\u0002\u0001\u0000+*\u0001\u0000\u0000\u0000,/\u0001\u0000\u0000"+
		"\u0000-+\u0001\u0000\u0000\u0000-.\u0001\u0000\u0000\u0000.0\u0001\u0000"+
		"\u0000\u0000/-\u0001\u0000\u0000\u000001\u0005\u0000\u0000\u00011\u0001"+
		"\u0001\u0000\u0000\u000027\u0003\u001a\r\u000037\u0003\u0006\u0003\u0000"+
		"47\u0003\u0004\u0002\u000057\u0003\u0018\f\u000062\u0001\u0000\u0000\u0000"+
		"63\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u000065\u0001\u0000\u0000"+
		"\u00007\u0003\u0001\u0000\u0000\u00008<\u0005\u0006\u0000\u00009;\u0003"+
		"\u0002\u0001\u0000:9\u0001\u0000\u0000\u0000;>\u0001\u0000\u0000\u0000"+
		"<:\u0001\u0000\u0000\u0000<=\u0001\u0000\u0000\u0000=?\u0001\u0000\u0000"+
		"\u0000><\u0001\u0000\u0000\u0000?B\u0005\u0007\u0000\u0000@B\u0005\b\u0000"+
		"\u0000A8\u0001\u0000\u0000\u0000A@\u0001\u0000\u0000\u0000B\u0005\u0001"+
		"\u0000\u0000\u0000CD\u0005\u0004\u0000\u0000DE\u0003\b\u0004\u0000EF\u0005"+
		"\u001c\u0000\u0000FL\u0001\u0000\u0000\u0000GH\u0005\u0005\u0000\u0000"+
		"HI\u0003\n\u0005\u0000IJ\u0005\u001d\u0000\u0000JL\u0001\u0000\u0000\u0000"+
		"KC\u0001\u0000\u0000\u0000KG\u0001\u0000\u0000\u0000L\u0007\u0001\u0000"+
		"\u0000\u0000MN\u0005\u001e\u0000\u0000N\\\u0005:\u0000\u0000OP\u0005\u001f"+
		"\u0000\u0000P\\\u00058\u0000\u0000Q\\\u0005 \u0000\u0000RS\u0005\"\u0000"+
		"\u0000S\\\u0003\n\u0005\u0000T\\\u0005%\u0000\u0000U\\\u0005&\u0000\u0000"+
		"VW\u0005#\u0000\u0000WX\u00058\u0000\u0000XY\u0005$\u0000\u0000Y\\\u0003"+
		"\n\u0005\u0000Z\\\u0005\'\u0000\u0000[M\u0001\u0000\u0000\u0000[O\u0001"+
		"\u0000\u0000\u0000[Q\u0001\u0000\u0000\u0000[R\u0001\u0000\u0000\u0000"+
		"[T\u0001\u0000\u0000\u0000[U\u0001\u0000\u0000\u0000[V\u0001\u0000\u0000"+
		"\u0000[Z\u0001\u0000\u0000\u0000\\\t\u0001\u0000\u0000\u0000]b\u0003\f"+
		"\u0006\u0000^_\u0005+\u0000\u0000_a\u0003\u0010\b\u0000`^\u0001\u0000"+
		"\u0000\u0000ad\u0001\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001"+
		"\u0000\u0000\u0000c\u000b\u0001\u0000\u0000\u0000db\u0001\u0000\u0000"+
		"\u0000eq\u0003\u000e\u0007\u0000fq\u0005:\u0000\u0000gq\u00059\u0000\u0000"+
		"hq\u0005(\u0000\u0000iq\u0005)\u0000\u0000jq\u0005*\u0000\u0000kl\u0005"+
		"8\u0000\u0000lm\u00054\u0000\u0000mn\u0003\u0012\t\u0000no\u00055\u0000"+
		"\u0000oq\u0001\u0000\u0000\u0000pe\u0001\u0000\u0000\u0000pf\u0001\u0000"+
		"\u0000\u0000pg\u0001\u0000\u0000\u0000ph\u0001\u0000\u0000\u0000pi\u0001"+
		"\u0000\u0000\u0000pj\u0001\u0000\u0000\u0000pk\u0001\u0000\u0000\u0000"+
		"q\r\u0001\u0000\u0000\u0000r{\u00058\u0000\u0000st\u0005,\u0000\u0000"+
		"tz\u00058\u0000\u0000uv\u00056\u0000\u0000vw\u0003\n\u0005\u0000wx\u0005"+
		"7\u0000\u0000xz\u0001\u0000\u0000\u0000ys\u0001\u0000\u0000\u0000yu\u0001"+
		"\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000"+
		"{|\u0001\u0000\u0000\u0000|\u000f\u0001\u0000\u0000\u0000}{\u0001\u0000"+
		"\u0000\u0000~\u0083\u0007\u0000\u0000\u0000\u007f\u0080\u00054\u0000\u0000"+
		"\u0080\u0081\u0003\u0012\t\u0000\u0081\u0082\u00055\u0000\u0000\u0082"+
		"\u0084\u0001\u0000\u0000\u0000\u0083\u007f\u0001\u0000\u0000\u0000\u0083"+
		"\u0084\u0001\u0000\u0000\u0000\u0084\u0011\u0001\u0000\u0000\u0000\u0085"+
		"\u0087\u0003\u0014\n\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u0088"+
		"\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0089"+
		"\u0001\u0000\u0000\u0000\u0089\u0093\u0001\u0000\u0000\u0000\u008a\u008b"+
		"\u0005-\u0000\u0000\u008b\u0090\u0003\u0016\u000b\u0000\u008c\u008d\u0005"+
		"-\u0000\u0000\u008d\u008f\u0003\u0016\u000b\u0000\u008e\u008c\u0001\u0000"+
		"\u0000\u0000\u008f\u0092\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000"+
		"\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091\u0094\u0001\u0000"+
		"\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0093\u008a\u0001\u0000"+
		"\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u009f\u0001\u0000"+
		"\u0000\u0000\u0095\u009a\u0003\u0016\u000b\u0000\u0096\u0097\u0005-\u0000"+
		"\u0000\u0097\u0099\u0003\u0016\u000b\u0000\u0098\u0096\u0001\u0000\u0000"+
		"\u0000\u0099\u009c\u0001\u0000\u0000\u0000\u009a\u0098\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009f\u0001\u0000\u0000"+
		"\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u009d\u009f\u0001\u0000\u0000"+
		"\u0000\u009e\u0086\u0001\u0000\u0000\u0000\u009e\u0095\u0001\u0000\u0000"+
		"\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009f\u0013\u0001\u0000\u0000"+
		"\u0000\u00a0\u00a1\u0003\n\u0005\u0000\u00a1\u0015\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a3\u00058\u0000\u0000\u00a3\u00a4\u0005/\u0000\u0000\u00a4\u00a5"+
		"\u0003\n\u0005\u0000\u00a5\u0017\u0001\u0000\u0000\u0000\u00a6\u00a7\u0005"+
		"\t\u0000\u0000\u00a7\u0019\u0001\u0000\u0000\u0000\u00a8\u00ac\u0005\u0003"+
		"\u0000\u0000\u00a9\u00ab\u0003\u001c\u000e\u0000\u00aa\u00a9\u0001\u0000"+
		"\u0000\u0000\u00ab\u00ae\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000"+
		"\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00af\u0001\u0000"+
		"\u0000\u0000\u00ae\u00ac\u0001\u0000\u0000\u0000\u00af\u00b0\u0005\u000e"+
		"\u0000\u0000\u00b0\u001b\u0001\u0000\u0000\u0000\u00b1\u00b2\u0003\u001e"+
		"\u000f\u0000\u00b2\u00b6\u0005\u0015\u0000\u0000\u00b3\u00b5\u0003\"\u0011"+
		"\u0000\u00b4\u00b3\u0001\u0000\u0000\u0000\u00b5\u00b8\u0001\u0000\u0000"+
		"\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b6\u00b7\u0001\u0000\u0000"+
		"\u0000\u00b7\u00b9\u0001\u0000\u0000\u0000\u00b8\u00b6\u0001\u0000\u0000"+
		"\u0000\u00b9\u00ba\u0005\u0016\u0000\u0000\u00ba\u001d\u0001\u0000\u0000"+
		"\u0000\u00bb\u00c0\u0003 \u0010\u0000\u00bc\u00bd\u0005\u0018\u0000\u0000"+
		"\u00bd\u00bf\u0003 \u0010\u0000\u00be\u00bc\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c2\u0001\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c0"+
		"\u00c1\u0001\u0000\u0000\u0000\u00c1\u001f\u0001\u0000\u0000\u0000\u00c2"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005\u0011\u0000\u0000\u00c4"+
		"\u00c7\u0005\u0010\u0000\u0000\u00c5\u00c7\u0005\u0010\u0000\u0000\u00c6"+
		"\u00c3\u0001\u0000\u0000\u0000\u00c6\u00c5\u0001\u0000\u0000\u0000\u00c7"+
		"\u00c8\u0001\u0000\u0000\u0000\u00c8\u00c6\u0001\u0000\u0000\u0000\u00c8"+
		"\u00c9\u0001\u0000\u0000\u0000\u00c9\u00cc\u0001\u0000\u0000\u0000\u00ca"+
		"\u00cb\u0005\u0014\u0000\u0000\u00cb\u00cd\u0005\u0010\u0000\u0000\u00cc"+
		"\u00ca\u0001\u0000\u0000\u0000\u00cc\u00cd\u0001\u0000\u0000\u0000\u00cd"+
		"!\u0001\u0000\u0000\u0000\u00ce\u00cf\u0005\u0010\u0000\u0000\u00cf\u00d0"+
		"\u0005\u0014\u0000\u0000\u00d0\u00d2\u0003$\u0012\u0000\u00d1\u00d3\u0005"+
		"\u0017\u0000\u0000\u00d2\u00d1\u0001\u0000\u0000\u0000\u00d2\u00d3\u0001"+
		"\u0000\u0000\u0000\u00d3#\u0001\u0000\u0000\u0000\u00d4\u00db\u0003&\u0013"+
		"\u0000\u00d5\u00d7\u0005\u0018\u0000\u0000\u00d6\u00d5\u0001\u0000\u0000"+
		"\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000"+
		"\u0000\u00d8\u00da\u0003&\u0013\u0000\u00d9\u00d6\u0001\u0000\u0000\u0000"+
		"\u00da\u00dd\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000"+
		"\u00db\u00dc\u0001\u0000\u0000\u0000\u00dc%\u0001\u0000\u0000\u0000\u00dd"+
		"\u00db\u0001\u0000\u0000\u0000\u00de\u00e4\u0005\u0010\u0000\u0000\u00df"+
		"\u00e4\u0005\u0012\u0000\u0000\u00e0\u00e4\u0005\u0013\u0000\u0000\u00e1"+
		"\u00e4\u0005\u000f\u0000\u0000\u00e2\u00e4\u0003(\u0014\u0000\u00e3\u00de"+
		"\u0001\u0000\u0000\u0000\u00e3\u00df\u0001\u0000\u0000\u0000\u00e3\u00e0"+
		"\u0001\u0000\u0000\u0000\u00e3\u00e1\u0001\u0000\u0000\u0000\u00e3\u00e2"+
		"\u0001\u0000\u0000\u0000\u00e4\'\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005"+
		"\u0010\u0000\u0000\u00e6\u00e7\u0005\u0019\u0000\u0000\u00e7\u00e8\u0003"+
		"$\u0012\u0000\u00e8\u00e9\u0005\u001a\u0000\u0000\u00e9)\u0001\u0000\u0000"+
		"\u0000\u001a-6<AK[bpy{\u0083\u0088\u0090\u0093\u009a\u009e\u00ac\u00b6"+
		"\u00c0\u00c6\u00c8\u00cc\u00d2\u00d6\u00db\u00e3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}