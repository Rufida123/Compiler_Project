package PyAstClasses;
import java.util.ArrayList;
import java.util.List;
public class ReturnStmt extends Statement {
    private List<Expression> returnArgs = new ArrayList<>(); // nullable/empty

    public List<Expression> getReturnArgs() { return returnArgs; }
    public void setReturnArgs(List<Expression> returnArgs) { this.returnArgs = returnArgs; }
    public void addReturnArg(Expression e) { this.returnArgs.add(e); }

    @Override
    public String toString() {
        return "\nReturnStatement{" +
                "\nreturnArgs=" + returnArgs +
                "\n}";
    }
}
