package jinjaAstClasses;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;

public class Document {
    List<ProgramElement> htmlElements = new ArrayList<>();
    protected int line = -1;

    public List<ProgramElement> getHtmlElements() {
        return htmlElements;
    }

    public void setHtmlElements(List<ProgramElement> htmlElements) {
        this.htmlElements = htmlElements;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "\nDocument [line: " + line + "] {" +
                "\nhtmlElements=" + htmlElements.stream()
                .filter(Objects::nonNull)
                .filter(e -> !(e instanceof HtmlText && ((HtmlText) e).getText().trim().isEmpty()))
                .collect(Collectors.toList()) +
                "\n}";
    }
}