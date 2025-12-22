package PyAstClasses;

public class SubscriptPostfix extends PostfixOp {
    private Expression index;

    public Expression getIndex() { return index; }
    public void setIndex(Expression index) { this.index = index; }

    @Override
    public String toString() { return "SubscriptPostfix([" + index + "])"; }
}

