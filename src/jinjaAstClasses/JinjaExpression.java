package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;

//{{ product.price | float | round(2) }}
public class JinjaExpression {
    JinjaPrimary primary;
    List<JinjaFilter> filters = new ArrayList<>();
    public JinjaPrimary getPrimary() {
        return primary;
    }
    public void setPrimary(JinjaPrimary primary) {
        this.primary = primary;
    }
    public List<JinjaFilter> getFilters() {
        return filters;
    }
    public void setFilters(List<JinjaFilter> filters) {
        this.filters = filters;
    }
    @Override
    public String toString() {
        return "\nJinjaExpression{" +
                "\nprimary=" + primary +
                ", \nfilters=" + filters +
                "\n}";
    }
}
