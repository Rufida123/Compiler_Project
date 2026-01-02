parser grammar JinjaParser;
@header { package antlr; }
options { tokenVocab = JinjaLexer; }

document : programElement* EOF ;

programElement
    : styleTag
    | jinjaBlock
    | htmlTag
    | htmlText
    ;

// --- HTML Tag Structure ---

htmlTag
    : TAG_OPEN TAG_ID htmlAttribute* TAG_CLOSE programElement* TAG_CLOSE_START TAG_ID TAG_CLOSE  #PairedTag
    | TAG_OPEN TAG_ID htmlAttribute* TAG_SLASH_CLOSE #SelfClosingTag
    ;

htmlAttribute
    : TAG_ID (TAG_EQUALS attributeValue)? #NormalAttribute
    | jinjaBlock                          #JinjaAttribute
    ;

attributeValue
    : TAG_ATTR_VALUE #PlainValue
    | jinjaBlock     #JinjaValueExpr
    ;

// --- Jinja Blocks ---

jinjaBlock
    : (JINJA_BLOCK_START | TAG_JINJA_BLOCK_START) jinjaStatementHeader JINJA_BLOCK_END #ControlBlock
    | (JINJA_EXPR_START | TAG_JINJA_EXPR_START) jinjaExpression JINJA_EXPR_END        #PrintBlock
    ;

jinjaStatementHeader
    : JINJA_EXTENDS JINJA_STRING                  #Extends
    | JINJA_BLOCK JINJA_IDENTIFIER                #BlockStart
    | JINJA_ENDBLOCK                              #BlockEnd
    | JINJA_IF jinjaExpression                    #If
    | JINJA_ELSE                                  #Else
    | JINJA_ENDIF                                 #EndIf
    | JINJA_FOR JINJA_IDENTIFIER JINJA_IN jinjaExpression #For
    | JINJA_ENDFOR                                #EndFor
    ;

jinjaExpression : jinjaPrimary (JINJA_PIPE jinjaFilter)*;

jinjaPrimary
    : jinjaIdentifierChain #AccessExpr
    | JINJA_STRING         #StringLiteral
    | JINJA_NUMBER         #NumberLiteral
    | JINJA_TRUE           #TrueLiteral
    | JINJA_FALSE          #FalseLiteral
    | JINJA_NONE           #NoneLiteral
    | JINJA_IDENTIFIER JINJA_OPAR jinjaCallArgs JINJA_CPAR #FunctionCall
    ;

jinjaIdentifierChain : JINJA_IDENTIFIER ( (JINJA_DOT JINJA_IDENTIFIER) | (JINJA_OBRACK jinjaExpression JINJA_CBRACK) )*;
jinjaFilter : (JINJA_IDENTIFIER | JINJA_FORMAT) (JINJA_OPAR jinjaCallArgs JINJA_CPAR)?;
jinjaCallArgs
    : jinjaArg+ (JINJA_COMMA jinjaKwArg (JINJA_COMMA jinjaKwArg)*)? #CallMixedArgs
    | jinjaKwArg (JINJA_COMMA jinjaKwArg)*                          #CallKwArgs
    |                                                               #EmptyArgs
    ;

jinjaArg : jinjaExpression;
jinjaKwArg : JINJA_IDENTIFIER JINJA_EQUALS jinjaExpression;

htmlText : HTML_TEXT;

// --- CSS Rules ---
styleTag : STYLE_OPEN cssRule* STYLE_CLOSE;
cssRule : cssSelectorList CSS_OPEN_BRACE cssProperty* CSS_CLOSE_BRACE;
cssSelectorList : cssSelector (CSS_COMMA cssSelector)* ;

cssSelector : selectorPart+ (CSS_COLON CSS_WORD)? ;

selectorPart
    : CSS_DOT CSS_WORD  #ClassPart
    | CSS_WORD          #TagPart
    ;
cssProperty : CSS_WORD CSS_COLON valueList SEMICOLON?;
valueList : cssValue (CSS_COMMA? cssValue)* ;

cssValue
    : CSS_WORD     #WordValue
    | CSS_NUMBER   #NumberValue
    | CSS_COLOR    #ColorValue
    | CSS_STRING   #StringValue
    | cssFunction  #FunctionValue
    ;

cssFunction : CSS_WORD CSS_LPAREN valueList CSS_RPAREN ;