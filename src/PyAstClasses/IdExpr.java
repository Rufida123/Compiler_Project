package PyAstClasses;

public class IdExpr extends Expression {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "\nId(" + name + ")"; }
}
