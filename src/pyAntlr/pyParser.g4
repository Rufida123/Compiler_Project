parser grammar pyParser;

options { tokenVocab = pyLexer; }


// ================= البرنامج الرئيسي =================
program
    : (statement | NEWLINE)* EOF
    ;

identifier
    : ID
    | APP
    | RENDER_TEMPLATE
    | REQUEST
    | REDIRECT
    | URL_FOR
    ;
// نوع الجملة
statement
    : NEWLINE? import_stmt       #ImportStatement
    | NEWLINE? assignment        #AssignmentStatement
    | NEWLINE? return_stmt       #ReturnStatement
    | NEWLINE? expr_stmt         #ExprStatement
    | NEWLINE? route_def                #RouteStatement
    | NEWLINE? func_def                 #FuncDefStatement
    | NEWLINE? if_stmt                  #IfStatement
    | NEWLINE? for_stmt                 #ForStatement
    ;


// ================= import =================

import_stmt
    : (FROM dotted_name)* IMPORT import_list
    ;

dotted_name
    : ID (DOT ID)*
    ;

import_list
    : import_item (COMMA import_item)*
    ;

import_item
    :  identifier (AS ID)?
    ;

// ================= تعاريف و جمل =================

// app = Flask(__name__)
// products = [...]
assignment
    : (APP | ID) ASSIGN expr
    ;

// def index(...):
func_def
    : DEF ID L_PAREN param_list? R_PAREN COLON suite
    ;

param_list
    : ID (COMMA ID)*
    ;

// Block داخل الدالة أو الـ if
suite
    : NEWLINE+ INDENT (statement | NEWLINE)+ DEDENT   #IndentedSuite
    | statement                                      #SimpleSuite
    ;


// ================= route + decorator =================

// @app.route('/add', methods=[...])
// def add_product(): ...
route_def
    : ROUTE L_PAREN route_path (COMMA NEWLINE? route_params)? R_PAREN NEWLINE func_def
    ;

route_path
    : ROUTE_PATH
    | STRING
    ;

route_params
    : METHODS ASSIGN list_literal
    ;

// ================= if / for / return / expr =================

if_stmt
    : IF expr COLON suite (ELSE COLON suite)?
    ;

for_stmt
    : FOR ID IN expr COLON suite
    ;

return_stmt
    : RETURN return_args?
    ;

return_args
    : expr (COMMA expr)*     // يدعم: return "msg", 404
    ;

expr_stmt
    : expr
    ;

// ================= التعابير Expressions =================

expr
    : orExpr (IF orExpr ELSE orExpr)?      #CondExpr
    ;

orExpr
    : equalityExpr                         #OrPassExpr
    ;

// ==, !=, is
equalityExpr
    : relationalExpr ((EQ | NEQ | IS) relationalExpr)*
    ;

// <, >
relationalExpr
    : additiveExpr ((LT | GT) additiveExpr)*
    ;

// +, -
additiveExpr
    : multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*
    ;

// *, /
multiplicativeExpr
    : unaryExpr ((STAR | DIV) unaryExpr)*
    ;

// -x
unaryExpr
    : MINUS unaryExpr                                   #UnaryMinusExpr
    | postfixExpr                                      #UnaryPostfixExpr
    ;

// x(), x[y], x.y (سلسلة)
postfixExpr
    : primaryExpr (postfixOp)*
    ;

postfixOp
    : L_PAREN arg_list? R_PAREN                         #CallPostfix
    | L_SQ_B expr R_SQ_B                                #SubscriptPostfix
    | DOT ID                                            #AttrPostfix
    ;

// القيم الأساسية + list/dict + generator
primaryExpr
    : INT                                               #IntLiteralExpr
    | FLOAT                                             #FloatLiteralExpr
    | STRING                                            #StringLiteralExpr
    | HTML_FILE                                         #HtmlFileLiteralExpr
    | TRUE                                              #TrueLiteralExpr
    | FALSE                                             #FalseLiteralExpr
    | NONE                                              #NoneLiteralExpr
    | identifier                                        #IdentifierExpr
    | list_literal                                      #ListLiteralExpr
    | dict_literal                                      #DictLiteralExpr
    | generator_expr                                    #GeneratorPrimaryExpr
    | L_PAREN expr R_PAREN                              #ParenExpr
    ;

// (p for p in products if p['id'] == product_id)
generator_expr
    : NEWLINE? L_PAREN NEWLINE? ID FOR ID IN expr (IF expr)? NEWLINE? R_PAREN
    ;

// ================= list / dict literals =================

list_literal
    : NEWLINE? L_SQ_B NEWLINE?(expr (COMMA NEWLINE? expr)*)? NEWLINE? R_SQ_B
    ;

dict_literal
    : NEWLINE? L_CUR_B NEWLINE?(dict_entry (COMMA NEWLINE? dict_entry)*)? NEWLINE? R_CUR_B
    ;

dict_entry
    : expr COLON expr
    ;

// ================= arguments =================

// يدعم positional و keyword args:
// render_template('index.html', products=products)
arg
    : expr
    | ID ASSIGN expr
    ;

arg_list
    : arg (COMMA NEWLINE? arg)*
    ;
