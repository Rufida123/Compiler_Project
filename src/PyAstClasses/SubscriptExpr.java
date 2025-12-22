package PyAstClasses;

public class SubscriptExpr extends Expression {
    private Expression base;
    private Expression index;

    public Expression getBase() { return base; }
    public void setBase(Expression base) { this.base = base; }

    public Expression getIndex() { return index; }
    public void setIndex(Expression index) { this.index = index; }

    @Override
    public String toString() { return "\nSubscript(" + base + "[" + index + "])"; }
}
