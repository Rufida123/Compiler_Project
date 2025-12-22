package PyAstClasses;
import java.util.ArrayList;
import java.util.List;

public class PostfixExpr extends Expression {
    private PrimaryExpr primary;
    private List<PostfixOp> ops = new ArrayList<>();

    public PrimaryExpr getPrimary() { return primary; }
    public void setPrimary(PrimaryExpr primary) { this.primary = primary; }

    public List<PostfixOp> getOps() { return ops; }
    public void setOps(List<PostfixOp> ops) { this.ops = ops; }
    public void addOp(PostfixOp op) { this.ops.add(op); }

    @Override
    public String toString() {
        return "PostfixExpr(" + primary + ", ops=" + ops + ")";
    }
}

