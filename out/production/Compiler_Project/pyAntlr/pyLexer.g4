lexer grammar pyLexer;

tokens { INDENT, DEDENT }
@members {
    java.util.Deque<Integer> indents = new java.util.ArrayDeque<>();
    java.util.LinkedList<org.antlr.v4.runtime.Token> pending = new java.util.LinkedList<>();
    private boolean initialized = false;

    private int opened = 0;

    @Override
    public org.antlr.v4.runtime.Token nextToken() {

        if (!initialized) {
            indents.push(0);
            initialized = true;
        }

        if (!pending.isEmpty()) {
            return pending.pollFirst();
        }

        org.antlr.v4.runtime.Token t = super.nextToken();
        int tt = t.getType();


        if (tt == L_PAREN || tt == L_SQ_B || tt == L_CUR_B) opened++;
        else if (tt == R_PAREN || tt == R_SQ_B || tt == R_CUR_B) opened = Math.max(0, opened - 1);


        if (tt == org.antlr.v4.runtime.Token.EOF) {
            while (indents.size() > 1) {
                indents.pop();
                pending.add(makeToken(DEDENT));
            }
            return pending.isEmpty() ? t : pending.pollFirst();
        }


        if (tt == NEWLINE) {

            // داخل () [] {} : تجاهل الـNEWLINE بالكامل
            if (opened > 0) {
                return nextToken();
            }

            // أضف NEWLINE
            pending.add(t);

            // إذا السطر التالي فاضي/تعليق/EOF لا تغيّر indentation
            int la = _input.LA(1);
            if (la == '\n' || la == '\r' || la == '#' || la == org.antlr.v4.runtime.IntStream.EOF) {
                return pending.pollFirst();
            }

            // احسب المسافات من نص NEWLINE (لأنه يحتوي المسافات بعده)
            String txt = t.getText().replace("\r", "");
            int spaces = 0;
            for (int i = txt.length() - 1; i >= 0; i--) {
                char c = txt.charAt(i);
                if (c == ' ' || c == '\t') spaces++;
                else break;
            }

            int current = indents.peek();

            if (spaces > current) {
                indents.push(spaces);
                pending.add(makeToken(INDENT));
            } else if (spaces < current) {
                while (indents.size() > 1 && indents.peek() > spaces) {
                    indents.pop();
                    pending.add(makeToken(DEDENT));
                }
            }

            return pending.pollFirst();
        }

        return t;
    }

   private org.antlr.v4.runtime.Token makeToken(int type) {
       org.antlr.v4.runtime.misc.Pair<
               org.antlr.v4.runtime.TokenSource,
               org.antlr.v4.runtime.CharStream
       > source = new org.antlr.v4.runtime.misc.Pair<>(this, _input);

       org.antlr.v4.runtime.CommonToken token =
               new org.antlr.v4.runtime.CommonToken(
                       source,
                       type,
                       org.antlr.v4.runtime.Token.DEFAULT_CHANNEL,
                       -1,
                       -1
               );

       token.setText("");
       token.setLine(getLine());
       token.setCharPositionInLine(getCharPositionInLine());
       return token;
   }

}


FROM        : 'from';
IMPORT      : 'import';
AS          : 'as';
DEF         : 'def';
RETURN      : 'return';
IF          : 'if';
ELSE        : 'else';
NONE        : 'None';
TRUE        : 'True';
FALSE       : 'False';
FOR         : 'for';
IN          : 'in';
IS          : 'is';

APP             : 'app';
ROUTE           : '@app.route';
//RUN             : 'app.run';
RENDER_TEMPLATE : 'render_template';
REQUEST         : 'request';
REDIRECT        : 'redirect';
URL_FOR         : 'url_for';


ROUTE_PATH
    : '\'' '/' (~['\r\n])* '\''
    | '"'  '/' (~["\r\n])* '"'
    ;
METHODS         : 'methods';

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

NEWLINE
    : ('\r'? '\n') [ \t]*
    ;

WS: [ \t]+ -> skip;

COMMENT : '#' ~[\r\n]* -> skip;
