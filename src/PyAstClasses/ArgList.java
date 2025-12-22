package PyAstClasses;
import java.util.ArrayList;
import java.util.List;

public class ArgList extends Program {
    private List<Arg> args = new ArrayList<>();

    public List<Arg> getArgs() { return args; }
    public void setArgs(List<Arg> args) { this.args = args; }
    public void addArg(Arg a) { this.args.add(a); }

    @Override
    public String toString() {
        return "\nArgList{" +
                "\nargs=" + args +
                "\n}";
    }
}
