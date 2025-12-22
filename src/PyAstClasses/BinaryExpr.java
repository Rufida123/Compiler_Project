package PyAstClasses;

public class BinaryExpr extends Expression {
    private String op;
    private Expression left;
    private Expression right;

    public String getOp() { return op; }
    public void setOp(String op) { this.op = op; }

    public Expression getLeft() { return left; }
    public void setLeft(Expression left) { this.left = left; }

    public Expression getRight() { return right; }
    public void setRight(Expression right) { this.right = right; }

    @Override
    public String toString() {
        return "Binary(" + op + ", " + left + ", " + right + ")";
    }
}
