package PyAstClasses;

import java.util.ArrayList;

public class CallExpr extends Expression {
    private Expression callee;
    private ArrayList<Arg> args = new ArrayList<>();

    public Expression getCallee() { return callee; }
    public void setCallee(Expression callee) { this.callee = callee; }

    public ArrayList<Arg> getArgs() { return args; }
    public void setArgs(ArrayList<Arg> args) { this.args = args; }

    @Override
    public String toString() { return "\nCall(" + callee + ", args=" + args + ")"; }
}
