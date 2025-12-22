package PyAstClasses;

public class AssignStmt extends Statement {
    private String name;       // APP أو ID
    private Expression value;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Expression getValue() { return value; }
    public void setValue(Expression value) { this.value = value; }

    @Override
    public String toString() {
        return "\nAssignmentStatement{" +
                "\nname='" + name + '\'' +
                ",\nvalue=" + value +
                "\n}";
    }
}
