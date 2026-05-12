package jinjaClasses;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;
import ast.MainProgram;

public class JinjaProgram extends MainProgram {
    List<DocumentElement> htmlElements = new ArrayList<>();
    protected int line = -1;

    public List<DocumentElement> getHtmlElements() {
        return htmlElements;
    }

    public void setHtmlElements(List<DocumentElement> htmlElements) {
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