package jinjaAstClasses;

public class PlainValue extends AttributeValue {
    private String text;  // TAG_ATTR_VALUE

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "\nPlainValue{" +
                "\ntext='" + text + '\'' +
                "\n}";
    }
}
