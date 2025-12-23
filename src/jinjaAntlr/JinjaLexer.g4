lexer grammar JinjaLexer;
@header { package antlr; }

// ========================
// Default Mode (HTML Text)
// ========================

JINJA_BLOCK_START: '{%' -> pushMode(JINJA_MODE);
JINJA_EXPR_START: '{{' -> pushMode(JINJA_MODE);

TAG_OPEN: '<' -> pushMode(TAG_MODE);
TAG_CLOSE_START: '</' -> pushMode(TAG_MODE);

HTML_COMMENT: '<!--' .*? '-->' -> channel(HIDDEN);
DOCTYPE: '<!DOCTYPE' .*? '>' -> skip;
STYLE_OPEN: '<style' .*? '>' -> pushMode(CSS_MODE);

// Text that isn't a tag or Jinja start
HTML_TEXT: (~[<{] | '<' ~[a-zA-Z/!{] | '{' ~[%{#])+ ;
WS: [ \t\r\n]+ -> channel(HIDDEN);

// ========================
// Tag Mode (Inside < ... >)
// ========================
mode TAG_MODE;

TAG_CLOSE: '>' -> popMode;
TAG_SLASH_CLOSE: '/>' -> popMode;

TAG_ID: [a-zA-Z_:] [a-zA-Z0-9_:-]* ;
TAG_EQUALS: '=';

TAG_ATTR_VALUE: '"' ( ~["\\{] | '\\' . | JINJA_START_IN_QUOTE )* '"'
               | '\'' ( ~['\\{] | '\\' . | JINJA_START_IN_QUOTE )* '\'' ;

fragment JINJA_START_IN_QUOTE: '{{' | '{%';

// Allow Jinja entry from inside a tag
TAG_JINJA_BLOCK_START: '{%' -> pushMode(JINJA_MODE);
TAG_JINJA_EXPR_START: '{{' -> pushMode(JINJA_MODE);

TAG_WS: [ \t\r\n]+ -> skip;

// ========================
// CSS Mode
// ========================
mode CSS_MODE;
STYLE_CLOSE: '</style>' -> popMode;
CSS_COMMENT: '/*' .*? '*/' -> channel(HIDDEN);
CSS_STRING: '"' (~["\\] | '\\' .)* '"' | '\'' (~['\\] | '\\' .)* '\'' ;
CSS_WORD: [a-zA-Z_][a-zA-Z0-9_-]* ;
CSS_DOT: '.' ;
CSS_NUMBER: '-'? ( [0-9]+ '.' [0-9]* | '.' [0-9]+ | [0-9]+ ) ( 'px' | 'em' | 'rem' | 'vh' | 'vw' | '%' | 'fr' | 's' | 'ms' )? ;
CSS_COLOR: '#' [0-9a-fA-F]+ ;
CSS_COLON: ':';
CSS_OPEN_BRACE: '{' ;
CSS_CLOSE_BRACE: '}' ;
SEMICOLON: ';';
CSS_COMMA: ',' ;
CSS_LPAREN: '(' ;
CSS_RPAREN: ')' ;
CSS_WS: [ \t\r\n]+ -> channel(HIDDEN);

// ========================
// Jinja Mode
// ========================
mode JINJA_MODE;
JINJA_BLOCK_END: '%}' -> popMode;
JINJA_EXPR_END: '}}' -> popMode;

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
JINJA_TRUE: 'true';
JINJA_FALSE: 'false';
JINJA_NONE: 'none';

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
JINJA_OBRACK: '[';
JINJA_CBRACK: ']';

JINJA_IDENTIFIER: [a-zA-Z_\p{L}] [a-zA-Z0-9_\p{L}]* ;
JINJA_NUMBER: [0-9]+ ('.' [0-9]+)? ;
JINJA_STRING : '\'' ( ~['\\] | '\\' . )* '\'' | '"' ( ~["\\] | '\\' . )* '"' ;
JINJA_WS: [ \t\r\n]+ -> skip;
JINJA_COMMENT: '{#' .*? '#}' -> skip;