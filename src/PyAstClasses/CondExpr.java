package PyAstClasses;

public class CondExpr extends Expression {
    private Expression thenExpr;
    private Expression condition;
    private Expression elseExpr;

    public Expression getThenExpr() { return thenExpr; }
    public void setThenExpr(Expression thenExpr) { this.thenExpr = thenExpr; }

    public Expression getCondition() { return condition; }
    public void setCondition(Expression condition) { this.condition = condition; }

    public Expression getElseExpr() { return elseExpr; }
    public void setElseExpr(Expression elseExpr) { this.elseExpr = elseExpr; }

     @Override
    public String toString() {
        if (condition == null) return "CondExpr(" + thenExpr + ")";
        return "CondExpr(" + thenExpr + " if " + condition + " else " + elseExpr + ")";
    }
}
