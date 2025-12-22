package PyAstClasses;
public class SimpleSuite extends Suite {
    private Statement statement;

    public Statement getStatement() { return statement; }
    public void setStatement(Statement statement) { this.statement = statement; }

    @Override
    public String toString() {
        return "\nSimpleSuite{" +
                "\nstatement=" + statement +
                "\n}";
    }
}

