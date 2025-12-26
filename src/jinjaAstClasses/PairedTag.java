package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PairedTag extends HtmlTag {
    private String tagName;
    private List<HtmlAttribute> attributes = new ArrayList<>();
    private List<ProgramElement> children = new ArrayList<>();

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<HtmlAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<HtmlAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<ProgramElement> getChildren() {
        return children;
    }

    public void setChildren(List<ProgramElement> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "\nPairedTag" + lineInfo() + " {" +
                "\ntagName='" + tagName + '\'' +
                ", \nattributes=" + attributes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()) +
                ", \nchildren=" + children.stream()
                .filter(Objects::nonNull)
                .filter(e -> !(e instanceof HtmlText && ((HtmlText) e).getText().trim().isEmpty()))
                .collect(Collectors.toList()) +
                "\n}";
    }
}