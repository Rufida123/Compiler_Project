package PySymbolTable;

import java.util.*;

public class Scope {
    private final String label;
    private final int line;
    private final Scope parent;

    private final Map<String, SymbolTable.Symbol> symbols = new LinkedHashMap<>();
    private final List<Scope> children = new ArrayList<>();

    public Scope(String label, int line, Scope parent) {
        this.label = label;
        this.line = line;
        this.parent = parent;
        if (parent != null) parent.children.add(this);
    }

    public String getLabel() { return label; }
    public int getLine() { return line; }
    public Scope getParent() { return parent; }

    public Map<String, SymbolTable.Symbol> getSymbols() { return symbols; }
    public List<Scope> getChildren() { return children; }

    public String getPath() {
        if (parent == null) return label;
        return parent.getPath() + " -> " + label;
    }

    public boolean containsLocal(String name) {
        return symbols.containsKey(name);
    }

    public SymbolTable.Symbol getLocal(String name) {
        return symbols.get(name);
    }

    public void putLocal(String name, SymbolTable.Symbol sym) {
        symbols.put(name, sym);
    }

    public SymbolTable.Symbol lookup(String name) {
        for (Scope s = this; s != null; s = s.parent) {
            SymbolTable.Symbol found = s.symbols.get(name);
            if (found != null) return found;
        }
        return null;
    }
}