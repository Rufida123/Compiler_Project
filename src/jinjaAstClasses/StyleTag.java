package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StyleTag extends ProgramElement {
    String tagName;
    List<CssRule> cssRules = new ArrayList<>();

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<CssRule> getCssRules() {
        return cssRules;
    }

    public void setCssRules(List<CssRule> cssRules) {
        this.cssRules = cssRules;
    }

    @Override
    public String toString() {
        return "\nStyleTag" + lineInfo() + " {" +
                "\ncssRules=" + cssRules.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()) +
                "\n}";
    }
}