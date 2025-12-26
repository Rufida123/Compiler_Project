package jinjaAstClasses;

public class JinjaFilter {
    String name;
    JinjaCallArgs args;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public JinjaCallArgs getArgs() {
        return args;
    }
    public void setArgs(JinjaCallArgs args) {
        this.args = args;
    }
    @Override
    public String toString() {
        return "\nJinjaFilter{" +
                "\nname='" + name + ' ' +
        ", \nargs=" + args +
                "\n}";
    }
}
