package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
public class ValueList {
    List<CssValue> values = new ArrayList<>();
    public List<CssValue> getValues() {
        return values;
    }
    public void setValues(List<CssValue> values) {
        this.values = values;
    }
    @Override
    public String toString() {
        return "\nValueList{" +
                "\nvalues=" + values +
                "\n}";
    }
}