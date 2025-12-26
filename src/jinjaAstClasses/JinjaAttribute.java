package jinjaAstClasses;

public class JinjaAttribute extends HtmlAttribute {
    public JinjaBlock jinjaBlock;

    public JinjaBlock getJinjaBlock() {
        return jinjaBlock;
    }

    public void setJinjaBlock(JinjaBlock jinjaBlock) {
        this.jinjaBlock = jinjaBlock;
    }

    @Override
    public String toString() {
        return "\nJinjaAttribute{" +
                "\njinjaBlock=" + jinjaBlock +
                "\n}";
    }
}
