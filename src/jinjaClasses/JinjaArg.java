package jinjaClasses;

public class JinjaArg extends JinjaNode {
    JinjaExpression expression;
    public JinjaExpression getExpression() {
        return expression;
    }
    public void setExpression(JinjaExpression expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "\nJinjaArg{" +
                "\nexpression=" + expression +
                "\n}";
    }
}