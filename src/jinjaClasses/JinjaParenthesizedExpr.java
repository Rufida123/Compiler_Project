package jinjaClasses;

/** Preserves a parenthesized Jinja expression. */
public class JinjaParenthesizedExpr extends JinjaPrimary {
    private JinjaExpression expression;
    public JinjaExpression getExpression() { return expression; }
    public void setExpression(JinjaExpression expression) { this.expression = expression; }
    @Override public String toString() { return "(" + expression + ")"; }
}
