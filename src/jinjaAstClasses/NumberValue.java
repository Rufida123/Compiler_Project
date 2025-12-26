package jinjaAstClasses;

public class NumberValue extends CssValue {
    String number;
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    @Override
    public String toString() {
        return "\nNumberValue{" +
                "\nnumber='" + number + ' ' +
        "\n}";
    }
}
