lexer grammar pyLexer;

FROM        : 'from';
IMPORT      : 'import';
AS          : 'as';
DEF         : 'def';
RETURN      : 'return';
IF          : 'if';
ELSE        : 'else';
NONE        : 'None';
FOR         : 'for';
IN          : 'in';
TRUE        : 'True';
FALSE       : 'False';

APP         : 'app';
ROUTE       : '@app.route';
RUN         : 'app.run';
RENDER_TEMPLATE : 'render_template';
REQUEST     : 'request';
REDIRECT    : 'redirect';
URL_FOR     : 'url_for';

ROUTE_PATH
    : '\'' '/' (~['\r\n])*? '\''
    | '"'  '/' (~["\r\n])*? '"'
    ;

METHODS : 'methods';

HTTP_METHOD : 'GET' | 'POST' | 'PUT' | 'DELETE';

HTML_FILE
    : '\'' (ID | [0-9] | '_' | '-' | '.')+ '.html' '\''
    | '"' (ID | [0-9] | '_' | '-' | '.')+ '.html' '"'
    ;

URL : 'http' 's'? '://' (~[ \t"'<>])+;

INT     : '-'? [0-9]+;
FLOAT   : '-'? [0-9]+ '.' [0-9]+;
STRING  : '"' (~["\\] | '\\' .)* '"'
        | '\'' (~['\\] | '\\' .)* '\'';

ID : [a-zA-Z_][a-zA-Z0-9_]*;

ASSIGN      : '=';
PLUS        : '+';
MINUS       : '-';
STAR        : '*';
DIV         : '/';
EQ          : '==';
NEQ         : '!=';
LT          : '<';
GT          : '>';
COLON       : ':';
COMMA       : ',';
DOT         : '.';
L_PAREN      : '(';
R_PAREN      : ')';
L_CUR_B      : '{';
R_CUR_B      : '}';
L_SQ_B      : '[';
R_SQ_B      : ']';

WS : [ \t\r\n]+ -> skip;
COMMENT : '#' ~[\r\n]* -> skip;
