package PyClasses;

public class StringExpr extends PrimaryExpr {
    private String value;
    private int lineNumber;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    @Override public String toString() { return "String(" + value + ")"; }
}