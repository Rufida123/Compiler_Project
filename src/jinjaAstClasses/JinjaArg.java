package jinjaAstClasses;

public class JinjaArg {
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