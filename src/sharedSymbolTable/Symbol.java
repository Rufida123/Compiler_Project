package sharedSymbolTable;

/**
 * Unified Symbol class — used by both the Jinja and Python symbol tables.
 * Replaces jinjaSymbolTable.Symbol and the inner PySymbolTable.SymbolTable.Symbol.
 */
public class Symbol {

    private final String name;
    private final String type;       // e.g. "block", "variable", "selector", "function", "parameter", "loop_variable"
    private final int scopeLevel;
    private final int lineNumber;

    // ── Constructors ────────────────────────────────────────────────────────────

    /** Convenience constructor (scope and line unknown). */
    public Symbol(String name, String type) {
        this(name, type, -1, -1);
    }

    /** Full constructor. */
    public Symbol(String name, String type, int scopeLevel, int lineNumber) {
        this.name       = name;
        this.type       = type;
        this.scopeLevel = scopeLevel;
        this.lineNumber = lineNumber;
    }

    // ── Getters ─────────────────────────────────────────────────────────────────

    public String getName()     { return name; }
    public String getType()     { return type; }
    public int getScopeLevel()  { return scopeLevel; }
    public int getLineNumber()  { return lineNumber; }

    // ── Object overrides ────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return type + ": " + name
                + " [scope: " + scopeLevel
                + ", line: "  + lineNumber + "]";
    }

    /** Equality is based on name only (consistent with original Jinja design). */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol s = (Symbol) o;
        return name.equals(s.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}