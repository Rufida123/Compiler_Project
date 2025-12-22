package PyAstClasses;

public class IntExpr extends PrimaryExpr {
    private long value;

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }

    @Override public String toString() { return "Int(" + value + ")"; }
}
