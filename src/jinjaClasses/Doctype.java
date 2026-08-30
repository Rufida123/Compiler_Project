package jinjaClasses;

/** The document type declaration, e.g. {@code <!DOCTYPE html>}. */
public class Doctype extends DocumentElement {

    private String text;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    @Override
    public String toString() {
        return "\nDoctype" + lineInfo() + " {" +
                "\ntext='" + text + '\'' +
                "\n}";
    }
}
