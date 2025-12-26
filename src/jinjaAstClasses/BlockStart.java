package jinjaAstClasses;

//{% block header %}
public class BlockStart extends JinjaStatementHeader {
    String identifier;
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "\nBlockStart{" +
                "\nidentifier='" + identifier + '\'' +
                "\n}";
    }
}
