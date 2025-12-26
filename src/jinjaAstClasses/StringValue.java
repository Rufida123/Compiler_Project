package jinjaAstClasses;

public class StringValue extends CssValue {
    String string;
    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }
    @Override
    public String toString() {
        return "\nStringValue{" +
                "\nstring='" + string + ' ' +
        "\n}";
    }
}
