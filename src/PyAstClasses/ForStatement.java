package PyAstClasses;

public class ForStatement extends Statement {
    private String varName;
    private Expression expression;
    private Suite forBlock;

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Suite getForBlock() {
        return forBlock;
    }

    public void setForBlock(Suite forBlock) {
        this.forBlock = forBlock;
    }

    @Override
    public String toString() {
        return "\nForStmt{\n" +
                "varName='" + varName + '\n' +
                ", expression=" + expression +
                "\n, forBlock=" + forBlock +
                "\n}";
    }
}
