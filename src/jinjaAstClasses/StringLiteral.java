package jinjaAstClasses;

//{{ "Hello World" }}
public class StringLiteral extends JinjaPrimary {
    String string;
    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }
    @Override
    public String toString() {
        return "\nStringLiteral{" +
                "\nstring='" + string + ' ' +
        "\n}";
    }
}
