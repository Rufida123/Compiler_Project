package PyAstClasses;

import java.util.ArrayList;
import java.util.List;

public class DictLiteral extends Program {
    private List<DictEntry> entries = new ArrayList<>();

    public List<DictEntry> getEntries() { return entries; }
    public void setEntries(List<DictEntry> entries) { this.entries = entries; }
    public void addEntry(DictEntry e) { this.entries.add(e); }

    @Override
    public String toString() {
        return "\nDictLiteral{" +
                "\nentries=" + entries +
                "\n}";
    }
}

