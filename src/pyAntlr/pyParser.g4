parser grammar pyParser;

options { tokenVocab = pyLexer; }

program
    : statement* EOF
    ;

    statement
        : import_stmt
        | func_def
        | assignment
        | expr_stmt
        | return_stmt
        | route_def
        | render_call
        | if_stmt
        | for_stmt
        ;
// from flask import Flask, render_template, request, redirect, url_for
    import_stmt
        : simple_import
        | from_import
        ;

    simple_import
        : IMPORT dotted_name (AS ID)?
        ;

    from_import
        : FROM dotted_name IMPORT import_list
        ;

    import_list
        : import_item (COMMA import_item)*
        ;

    import_item
        : ID (AS ID)?
        ;

    dotted_name
        : ID (DOT ID)*
        ;

    func_def
        : DEF ID L_PAREN param_list? R_PAREN COLON suite;

    param_list
        : ID (COMMA ID)* ;

    suite
        : statement+;

    assignment
        : APP ASSIGN expr
        | ID ASSIGN expr;

    expr_stmt
        : expr
        ;

    return_stmt
        : RETURN expr?
        ;

    if_stmt
        : IF expr COLON suite (ELSE COLON suite)?
        ;

    for_stmt
        : FOR ID IN expr COLON suite
        ;

    route_def
        : ROUTE L_PAREN route_path (COMMA param_list)? R_PAREN func_def
        ;

    route_path
        : ROUTE_PATH
        ;

    generator_expr
        : L_PAREN ID FOR ID IN expr (IF expr)? R_PAREN
        ;

    expr
        : expr PLUS expr            # AddExpr
        | expr MINUS expr           # SubExpr
        | expr STAR expr            # MulExpr
        | expr DIV expr             # DivExpr
        | atom COLON atom           # ColonExpr
        | MINUS expr                # NegateExpr
        | L_PAREN (expr COMMA?)+ R_PAREN      # ParenExpr
        | L_SQ_B (expr COMMA?)+ R_SQ_B        # SquareExpr
        | L_CUR_B (expr COMMA?)+ R_CUR_B      # CurlyExpr
        | for_stmt                            # forStmt
        | generator_expr            # GeneratorExpr
        | atom                      # atomExpr
    ;

    atom
        : INT               #int
        | FLOAT             #float
        | STRING            #string
        | TRUE              #true
        | FALSE             #false
        | NONE              #none
        | ID                #id
        | call_expr         #callExpr
        | subscript_expr    #subscriptExpr
        ;

    call_expr
        : ID L_PAREN arg_list? R_PAREN
        ;

    arg_list
        : expr (COMMA expr)*
        ;

    subscript_expr
        : ID L_SQ_B expr R_SQ_B
        ;

    render_call : RENDER_TEMPLATE                                   #import_render
    | RENDER_TEMPLATE L_PAREN HTML_FILE (COMMA assignment)* R_PAREN #call_render
    ;