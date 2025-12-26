package jinjaAstClasses;

//{{ 42 }}
public class NumberLiteral extends JinjaPrimary {
    String number;

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    @Override
    public String toString() {
        return "\nNumberLiteral{" +
                "\nnumber='" + number + ' ' +
        "\n}";
    }
}
