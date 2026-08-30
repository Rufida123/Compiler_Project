package jinjaClasses;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;

public class JinjaProgram extends JinjaNode {
    List<DocumentElement> htmlElements = new ArrayList<>();

    public List<DocumentElement> getHtmlElements() {
        return htmlElements;
    }

    public void setHtmlElements(List<DocumentElement> htmlElements) {
        this.htmlElements = htmlElements;
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