lexer grammar JinjaLexer;
@header { package antlr; }

// ========================
// Default Mode (HTML + Entry to Modes)
// ========================

HTML_COMMENT: '<!--' .*? '-->' -> channel(HIDDEN);
DOCTYPE: '<!DOCTYPE' .*? '>' -> skip;
STYLE_OPEN: '<style' .*? '>' -> pushMode(CSS_MODE);

// Jinja entry points
JINJA_BLOCK_START: '{%' -> pushMode(JINJA_MODE);
JINJA_EXPR_START: '{{' -> pushMode(JINJA_MODE);

// HTML tags
HTML_OPEN_TAG : '<' TAG_NAME HTML_ATTRIBUTE* [ \t\r\n]* '>' ;
HTML_CLOSE_TAG : '<' '/' TAG_NAME [ \t\r\n]* '>' ;
HTML_SELF_CLOSING_TAG : '<' TAG_NAME HTML_ATTRIBUTE* [ \t\r\n]* '/' '>' ;

// Modified: HTML text أكثر دقة – يسمح < أو { في النص إذا ما كانوا بداية tag أو block
HTML_TEXT: (~[<{] | '<' ~[a-zA-Z/!{] | '{' ~[%{#])+ ;

// Whitespace في HTML
WS: [ \t\r\n]+ -> channel(HIDDEN);

// ========================
// Modified: HTML Attributes (الإصلاح الرئيسي للخطأ السابق)
// ========================

HTML_ATTRIBUTE
    : [ \t\r\n]+ ATTR_NAME (EQUALS HTML_ATTRIBUTE_VALUE)?
    ;

// Rule عادي (مش fragment) عشان يقدر يستخدم JINJA_EXPR_START
HTML_ATTRIBUTE_VALUE
    : '"' ( JINJA_EXPR_START | ~[\r\n"\\] | '\\' . )* '"'
    | '\'' ( JINJA_EXPR_START | ~[\r\n'\\] | '\\' . )* '\''
    ;

// ========================
// CSS Mode
// ========================

mode CSS_MODE;
CSS_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
STYLE_CLOSE: '</style>' -> popMode;

// Added: Support for quoted strings in CSS (e.g., "Poppins")
CSS_STRING: '"' (~["\\] | '\\' .)* '"' | '\'' (~['\\] | '\\' .)* '\'' ;

// Modified: CSS_WORD أكثر دقة – فقط حروف وأرقام و - _
CSS_WORD: [a-zA-Z_][a-zA-Z0-9_-]* ;
CSS_DOT: '.' ;

// Modified: Support decimals, optional sign, and more units (added 'fr', 's', 'ms')
CSS_NUMBER: '-'? ( [0-9]+ '.' [0-9]* | '.' [0-9]+ | [0-9]+ ) ( 'px' | 'em' | 'rem' | 'vh' | 'vw' | '%' | 'fr' | 's' | 'ms' )? ;
CSS_COLOR: '#' [0-9a-fA-F]+ ;
CSS_COLON: ':';
CSS_OPEN_BRACE: '{' ;
CSS_CLOSE_BRACE: '}' ;
SEMICOLON: ';';
CSS_COMMA: ',' ; // Added for function args
CSS_LPAREN: '(' ; // Added for functions
CSS_RPAREN: ')' ; // Added for functions
CSS_WS: [ \t\r\n]+ -> channel(HIDDEN);

//Jinja Mode
mode JINJA_MODE;
JINJA_BLOCK_END: '%}' -> popMode;
JINJA_EXPR_END: '}}' -> popMode;

// Keywords
JINJA_EXTENDS: 'extends';
JINJA_BLOCK: 'block';
JINJA_ENDBLOCK: 'endblock';
JINJA_FORMAT: 'format';
JINJA_IF: 'if';
JINJA_FOR: 'for';
JINJA_IN: 'in';
JINJA_ELSE: 'else';
JINJA_ENDIF: 'endif';
JINJA_ENDFOR: 'endfor';

// Added: Boolean و None literals
JINJA_TRUE: 'true';
JINJA_FALSE: 'false';
JINJA_NONE: 'none';

// Operators
JINJA_PIPE: '|';
JINJA_DOT: '.';
JINJA_COMMA: ',';
JINJA_COLON: ':';
JINJA_EQUALS: '=';
JINJA_PLUS: '+';
JINJA_MINUS: '-';
JINJA_MULT: '*';
JINJA_DIV: '/';
JINJA_OPAR: '(';
JINJA_CPAR: ')';

// Added: للـ indexing
JINJA_OBRACK: '[';
JINJA_CBRACK: ']';

// Modified: Identifier يدعم Unicode (أحرف من أي لغة)
JINJA_IDENTIFIER: [a-zA-Z_\p{L}] [a-zA-Z0-9_\p{L}]* ;

// Number
JINJA_NUMBER: [0-9]+ ('.' [0-9]+)? ;

// Modified: String صارمة – لا تسمح \r\n إلا إذا escaped
JINJA_STRING : '\'' ( ~['\\] | '\\' . )* '\'' | '"' ( ~["\\] | '\\' . )* '"' ;

JINJA_WS: [ \t\r\n]+ -> skip;
JINJA_COMMENT: '{#' .*? '#}' -> skip;

// ========================
// Fragments
// ========================

fragment TAG_NAME: [a-zA-Z_:] [a-zA-Z0-9_:-]* ;
fragment ATTR_NAME: [a-zA-Z_:] [a-zA-Z0-9_:-]* ;
fragment EQUALS: '=';