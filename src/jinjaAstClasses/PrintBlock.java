package jinjaAstClasses;

public class PrintBlock extends JinjaBlock {
    JinjaExpression jinjaExpression;
    public JinjaExpression getJinjaExpression() {
        return jinjaExpression;
    }
    public void setJinjaExpression(JinjaExpression jinjaExpression) {
        this.jinjaExpression = jinjaExpression;
    }
    @Override
    public String toString() {
        return "\nPrintBlock" + lineInfo() + " {" +
                "\njinjaExpression=" + jinjaExpression +
                "\n}";
    }
}