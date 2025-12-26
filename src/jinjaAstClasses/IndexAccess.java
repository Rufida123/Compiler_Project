package jinjaAstClasses;

////{{ user.settings["theme"] }}
public class IndexAccess extends Access {
    JinjaExpression expression;  // anything inside the brackets
    public JinjaExpression getExpression() {
        return expression;
    }
    public void setExpression(JinjaExpression expression) {
        this.expression = expression;
    }
    @Override
    public String toString() {
        return "\nIndexAccess{" +
                "\nexpression=" + expression +
                "\n}";
    }
}
