package jinjaAstClasses;

public class DotAccess extends Access {
    String identifier;  // the property name, e.g. "profile"
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    @Override
    public String toString() {
        return "\nDotAccess{" +
                "\nidentifier='" + identifier + ' ' +
        "\n}";
    }
}
