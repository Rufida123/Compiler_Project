package PyAstClasses;

public class AttrPostfix extends PostfixOp {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() { return "AttrPostfix(." + name + ")"; }
}
