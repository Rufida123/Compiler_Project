package jinjaAstClasses;

public class ControlBlock extends JinjaBlock {
    JinjaStatementHeader jinjaStatementHeader;
    public JinjaStatementHeader getJinjaStatementHeader() {
        return jinjaStatementHeader;
    }
    public void setJinjaStatementHeader(JinjaStatementHeader jinjaStatementHeader) {
        this.jinjaStatementHeader = jinjaStatementHeader;
    }
    @Override
    public String toString() {
        return "\nControlBlock" + lineInfo() + " {" +
                "\njinjaStatementHeader=" + jinjaStatementHeader +
                "\n}";
    }
}
