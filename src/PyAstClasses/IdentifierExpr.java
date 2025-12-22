package PyAstClasses;

public class IdentifierExpr extends PrimaryExpr {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override public String toString() { return "Id(" + name + ")"; }
}
