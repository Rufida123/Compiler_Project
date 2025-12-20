parser grammar JinjaParser;
@header { package antlr; }
options { tokenVocab = JinjaLexer; }

document : htmlElement* EOF ;

// ترتيب العناصر لتقليل ambiguity
htmlElement : styleTag | jinjaBlock | htmlTag | htmlText ;

// HTML tags (paired أو self-closing)
htmlTag : HTML_OPEN_TAG htmlElement* HTML_CLOSE_TAG #PairedTag
        | HTML_SELF_CLOSING_TAG #SelfClosingTag ;

// Jinja blocks: إما control statement أو print expression
jinjaBlock : JINJA_BLOCK_START jinjaStatementHeader JINJA_BLOCK_END #ControlBlock
           | JINJA_EXPR_START jinjaExpression JINJA_EXPR_END #PrintBlock ;

// الـ control statements داخل {% ... %}
jinjaStatementHeader : JINJA_EXTENDS JINJA_STRING #Extends
                     | JINJA_BLOCK JINJA_IDENTIFIER #BlockStart
                     | JINJA_ENDBLOCK #BlockEnd
                     | JINJA_IF jinjaExpression #If
                     | JINJA_ELSE #Else
                     | JINJA_ENDIF #EndIf
                     | JINJA_FOR JINJA_IDENTIFIER JINJA_IN jinjaExpression #For
                     | JINJA_ENDFOR #EndFor ;

// Jinja expressions: primary + pipes/filters فقط (كما في تصميمك الأصلي مع تحسينات)
jinjaExpression : jinjaPrimary (JINJA_PIPE jinjaFilter)*;

// القيم الأساسية (primary)
jinjaPrimary : jinjaIdentifierChain #AccessExpr
             | JINJA_STRING #StringLiteral
             | JINJA_NUMBER #NumberLiteral
             | JINJA_TRUE #TrueLiteral // Added: boolean true
             | JINJA_FALSE #FalseLiteral // Added: boolean false
             | JINJA_NONE #NoneLiteral // Added: none/null
             | JINJA_IDENTIFIER JINJA_OPAR jinjaCallArgs JINJA_CPAR #FunctionCall ;

// Identifier chain مع دعم indexing: obj.attr[0]['key'].method
jinjaIdentifierChain : JINJA_IDENTIFIER ( (JINJA_DOT JINJA_IDENTIFIER) | (JINJA_OBRACK jinjaExpression JINJA_CBRACK) )*;

// Filter call (identifier أو format مع أو بدون arguments)
jinjaFilter : (JINJA_IDENTIFIER | JINJA_FORMAT) (JINJA_OPAR jinjaCallArgs JINJA_CPAR)?;

// Call arguments: positional أولاً ثم keyword (أكثر دقة وشبه Jinja الحقيقي)
jinjaCallArgs : jinjaArg+ (JINJA_COMMA jinjaKwArg (JINJA_COMMA jinjaKwArg)*)? #CallMixedArgs
              | jinjaKwArg (JINJA_COMMA jinjaKwArg)* #CallKwArgs
              | #EmptyArgs ;

jinjaArg : jinjaExpression;

jinjaKwArg : JINJA_IDENTIFIER JINJA_EQUALS jinjaExpression;

// HTML plain text
htmlText : HTML_TEXT;

// <style> tag مع CSS داخلها
styleTag : STYLE_OPEN cssRule* STYLE_CLOSE;

// CSS rule (updated for selector lists)
cssRule : cssSelectorList CSS_OPEN_BRACE cssProperty* CSS_CLOSE_BRACE;

// CSS selector list (comma-separated selectors)
cssSelectorList : cssSelector (CSS_COMMA cssSelector)* ;

// Single CSS selector
cssSelector : (CSS_DOT CSS_WORD | CSS_WORD)+ (CSS_COLON CSS_WORD)? ;

// CSS property (updated to handle value lists with optional commas)
cssProperty : CSS_WORD CSS_COLON valueList SEMICOLON?;

// Value list supporting space-separated or comma-separated
valueList : cssValue (CSS_COMMA? cssValue)* ;

// CSS value types (added support for functions)
cssValue : CSS_WORD #WordValue
         | CSS_NUMBER #NumberValue
         | CSS_COLOR #ColorValue
         | CSS_STRING #StringValue
         | cssFunction #FunctionValue
         ;

// CSS function (recursive for nesting)
cssFunction : CSS_WORD CSS_LPAREN valueList CSS_RPAREN ;