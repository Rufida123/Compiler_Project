package PyClasses;

public class IntExpr extends PrimaryExpr {
    private long value;
    private int lineNumber;

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }

    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    @Override public String toString() { return "Int(" + value + ")"; }
}