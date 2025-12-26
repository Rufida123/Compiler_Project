package jinjaAstClasses;

//{% if user.is_authenticated %}
public class If extends JinjaStatementHeader {
    JinjaExpression expression;
    public JinjaExpression getExpression() {
        return expression;
    }
    public void setExpression(JinjaExpression expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "\nIf{" +
                "\nexpression=" + expression +
                "\n}";
    }
}