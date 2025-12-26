package jinjaAstClasses;

public class NormalAttribute extends HtmlAttribute {
    public String key;
    public AttributeValue value; //null if no =value

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AttributeValue getValue() {
        return value;
    }

    public void setValue(AttributeValue value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\nNormalAttribute{" +
                "\nkey='" + key + '\'' +
                (value != null ? ", \nvalue=" + value : "") +
                "\n}";
    }
}
