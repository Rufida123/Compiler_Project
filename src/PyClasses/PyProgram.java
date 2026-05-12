package PyClasses;

import java.util.ArrayList;
import java.util.List;
import ast.MainProgram;

public abstract class PyProgram extends MainProgram {
    protected int lineNumber = -1;
    private List<Statement> statements = new ArrayList<>();

    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

    public List<Statement> getStatements() { return statements; }
    public void setStatements(List<Statement> statements) { this.statements = statements; }

    public void addStatement(Statement s) { this.statements.add(s); }


    @Override
    public String toString() {
        return "\nProgram{" +
                ", \nlineNumber=" + lineNumber +
                ", \nstatement=" + statements +
                "\n}";
    }
}
