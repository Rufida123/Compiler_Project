package jinjaAstClasses;

public class JinjaValueExpr extends AttributeValue {
    private JinjaBlock jinjaBlock;  // Usually a PrintBlock

    public JinjaBlock getJinjaBlock() {
        return jinjaBlock;
    }

    public void setJinjaBlock(JinjaBlock jinjaBlock) {
        this.jinjaBlock = jinjaBlock;
    }

    @Override
    public String toString() {
        return "\nJinjaValueExpr{" +
                "\njinjaBlock=" + jinjaBlock +
                "\n}";
    }
}
