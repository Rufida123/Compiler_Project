package PyAstClasses;
import java.util.ArrayList;
import java.util.List;
public class ImportList extends ImportStatement {
    private List<ImportItem> items = new ArrayList<>();

    public List<ImportItem> getItems() { return items; }
    public void setItems(List<ImportItem> items) { this.items = items; }
    public void addItem(ImportItem item) { this.items.add(item); }

    @Override
    public String toString() {
        return "\nImportList{" +
                "\nitems=" + items +
                "\n}";
    }
}
