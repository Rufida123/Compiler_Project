package jinjaClasses;

/** A binary Jinja expression such as product.price + tax. */
public class JinjaBinaryExpr extends JinjaPrimary {
    private JinjaPrimary left;
    private String operator;
    private JinjaPrimary right;

    public JinjaPrimary getLeft() { return left; }
    public void setLeft(JinjaPrimary left) { this.left = left; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public JinjaPrimary getRight() { return right; }
    public void setRight(JinjaPrimary right) { this.right = right; }

    @Override public String toString() { return "(" + left + " " + operator + " " + right + ")"; }
}
