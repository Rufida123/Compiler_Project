package PyAstClasses;
import java.util.ArrayList;
import java.util.List;

public class IndentedSuite extends Suite {
    private List<Statement> statements = new ArrayList<>();

    public List<Statement> getStatements() { return statements; }
    public void setStatements(List<Statement> statements) { this.statements = statements; }
    public void addStatement(Statement s) { this.statements.add(s); }

    @Override
    public String toString() {
        return "\nIndentedSuite{" +
                "\nstatements=" + statements +
                "\n}";
    }
}

