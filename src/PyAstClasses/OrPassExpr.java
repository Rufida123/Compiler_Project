package PyAstClasses;

public class OrPassExpr extends Expression{
    private Expression inner;

    public Expression getInner() { return inner; }
    public void setInner(Expression inner) { this.inner = inner; }

    @Override
    public String toString() { return "OrPass(" + inner + ")"; }
}
