package PyAstClasses;

public class UnaryMinusExpr extends UnaryExpr {
    private Expression expr; // unaryExpr عملياً

    public Expression getExpr() { return expr; }
    public void setExpr(Expression expr) { this.expr = expr; }

    @Override
    public String toString() { return "UnaryMinus(" + expr + ")"; }
}
