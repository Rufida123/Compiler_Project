package PyAstClasses;

public class StringExpr extends PrimaryExpr {
    private String value;

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override public String toString() { return "String(" + value + ")"; }
}

