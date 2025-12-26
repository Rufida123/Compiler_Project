package jinjaAstClasses;

public class HtmlText extends ProgramElement {
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        return "\nHtmlText" + lineInfo() + " {" +
                "\ntext='" + text.trim() + '\'' +
                "\n}";
    }
}