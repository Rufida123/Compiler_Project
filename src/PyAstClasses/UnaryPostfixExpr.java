package PyAstClasses;

public class UnaryPostfixExpr extends UnaryExpr {
    private PostfixExpr expr;

    public PostfixExpr getExpr() { return expr; }
    public void setExpr(PostfixExpr expr) { this.expr = expr; }

    @Override
    public String toString() { return "UnaryPostfix(" + expr + ")"; }
}

