package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
public class CssRule {
    CssSelectorList selectorList;
    List<CssProperty> properties = new ArrayList<>();
    public CssSelectorList getSelectorList() {
        return selectorList;
    }
    public void setSelectorList(CssSelectorList selectorList) {
        this.selectorList = selectorList;
    }
    public List<CssProperty> getProperties() {
        return properties;
    }
    public void setProperties(List<CssProperty> properties) {
        this.properties = properties;
    }
    @Override
    public String toString() {
        return "\nCssRule{" +
                "\nselectorList=" + selectorList +
                ", \nproperties=" + properties +
                "\n}";
    }
}
