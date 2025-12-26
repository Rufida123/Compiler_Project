package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SelfClosingTag extends HtmlTag {
    private String tagName;
    private List<HtmlAttribute> attributes = new ArrayList<>();

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

    @Override
    public String toString() {
        return "\nSelfClosingTag" + lineInfo() + " {" +
                "\ntagName='" + tagName + '\'' +
                ", \nattributes=" + attributes.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()) +
                "\n}";
    }
}