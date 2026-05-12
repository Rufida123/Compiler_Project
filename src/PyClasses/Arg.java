package PyClasses;

public class Arg extends PyProgram {
    private String name;       // null للـ positional
    private Expression value;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Expression getValue() { return value; }
    public void setValue(Expression value) { this.value = value; }

    @Override
    public String toString() {
        return "\nArg{" +
                (name != null ? "\nname='" + name + '\'' + "," : "") +
                "\nvalue=" + value +
                "\n}";
    }
}
