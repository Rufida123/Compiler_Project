package jinjaAstClasses;

//{% for product in product_list %}
public class For extends JinjaStatementHeader {
    String identifier;
    JinjaExpression expression;
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public JinjaExpression getExpression() {
        return expression;
    }
    public void setExpression(JinjaExpression expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "\nFor{" +
                "\nidentifier='" + identifier + ' ' +
        ", \nexpression=" + expression +
                "\n}";
    }
}
