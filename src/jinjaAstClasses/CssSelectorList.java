package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
public class CssSelectorList {
    List<CssSelector> selectors = new ArrayList<>();
    public List<CssSelector> getSelectors() {
        return selectors;
    }
    public void setSelectors(List<CssSelector> selectors) {
        this.selectors = selectors;
    }
    @Override
    public String toString() {
        return "\nCssSelectorList{" +
                "\nselectors=" + selectors +
                "\n}";
    }
}