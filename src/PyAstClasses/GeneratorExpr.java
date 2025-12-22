package PyAstClasses;

public class GeneratorExpr extends Program {
    private String yieldName;
    private String loopVarName;
    private Expression iterable;
    private Expression filter; // nullable

    public String getYieldName() { return yieldName; }
    public void setYieldName(String yieldName) { this.yieldName = yieldName; }

    public String getLoopVarName() { return loopVarName; }
    public void setLoopVarName(String loopVarName) { this.loopVarName = loopVarName; }

    public Expression getIterable() { return iterable; }
    public void setIterable(Expression iterable) { this.iterable = iterable; }

    public Expression getFilter() { return filter; }
    public void setFilter(Expression filter) { this.filter = filter; }

    @Override
    public String toString() {
        return "Gen(" + yieldName + " for " + loopVarName + " in " + iterable +
                (filter != null ? (" if " + filter) : "") + ")";
    }
}
