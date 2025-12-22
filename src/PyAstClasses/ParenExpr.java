package PyAstClasses;


public class ParenExpr extends PrimaryExpr {
    private Expression inner;

    public Expression getInner() { return inner; }
    public void setInner(Expression inner) { this.inner = inner; }

    @Override public String toString() { return "Paren(" + inner + ")"; }
}

