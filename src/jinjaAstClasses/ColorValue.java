package jinjaAstClasses;

public class ColorValue extends CssValue {
    String color;
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String toString() {
        return "\nColorValue{" +
                "\ncolor='" + color + ' ' +
        "\n}";
    }
}