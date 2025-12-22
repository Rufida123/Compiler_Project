package PyAstClasses;

public class AttrExpr extends Expression {
    private Expression base;
    private String name;

    public Expression getBase() { return base; }
    public void setBase(Expression base) { this.base = base; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "\nAttr(" + base + "." + name + ")"; }
}
