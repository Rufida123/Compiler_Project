package sharedSymbolTable;

import java.util.*;

/**
 * Unified SymbolTable — merges jinjaSymbolTable.SymbolTable and
 * PySymbolTable.SymbolTable into a single class.
 *
 * API summary
 * ───────────
 *  Scope management (Jinja-style):  openScope / closeScope
 *  Scope management (Python-style): enterScope / exitScope   (aliases)
 *  Re-initialise:                   initGlobal / clear       (both reset to fresh global scope)
 *  Symbol insertion (Jinja-style):  add(Symbol) / add(name, type)
 *  Symbol insertion (Python-style): addSymbol(name, type [, line] [, value])
 *  Lookup:                          lookup / lookupInCurrentScope / isDefined / isDefinedInCurrentScope
 *  Removal:                         remove / removeFromCurrentScope
 *  Global-scope access:             addToGlobalScope / getScope(level)
 *  Output:                          printTable / getFormattedTable / getStatistics / toJson / toCsv
 */
public class SymbolTable {

    // ── Internal state ──────────────────────────────────────────────────────────

    private final Stack<Map<String, Symbol>> scopes     = new Stack<>();
    private final List<String>               scopeNames = new ArrayList<>();
    private int currentScopeLevel = -1;

    /** Every scope ever opened, in open order, kept alive after it is closed so
     *  the printed table can still show nested scopes and their parameters. */
    private final List<Snapshot> history = new ArrayList<>();

    /** One recorded scope: its nesting level, its name and its symbols. */
    public record Snapshot(int level, String name, Map<String, Symbol> symbols) {}

    // ── Construction / reset ────────────────────────────────────────────────────

    public SymbolTable() {
        initGlobal();
    }

    /**
     * Resets the table to a single empty global scope.
     * Called automatically in the constructor; can also be called by visitors
     * that need a clean slate (e.g. when visiting a second file).
     */
    public void initGlobal() {
        scopes.clear();
        scopeNames.clear();
        history.clear();
        currentScopeLevel = -1;
        openScope("global");
    }

    /** Alias for {@link #initGlobal()} — preserves the Jinja-style name. */
    public void clear() {
        initGlobal();
    }

    // ── Scope management ────────────────────────────────────────────────────────

    /** Opens a new named scope and increments the scope level. */
    public void openScope(String scopeName) {
        currentScopeLevel++;
        Map<String, Symbol> frame = new LinkedHashMap<>();
        scopes.push(frame);
        scopeNames.add(scopeName);
        history.add(new Snapshot(currentScopeLevel, scopeName, frame));
    }

    /** Opens an anonymous scope (auto-named "scope_N"). */
    public void openScope() {
        openScope("scope_" + (currentScopeLevel + 1));
    }

    /**
     * Closes the current scope.
     * Jinja callers use this variant; it returns the symbols that were removed.
     *
     * @throws IllegalStateException if you attempt to close the global scope.
     */
    public List<Symbol> closeScope() {
        if (currentScopeLevel <= 0) {
            throw new IllegalStateException("Cannot close the global scope.");
        }
        Map<String, Symbol> removed = scopes.pop();
        scopeNames.remove(currentScopeLevel);
        currentScopeLevel--;
        return new ArrayList<>(removed.values());
    }

    /**
     * Opens a new named scope.
     * Python-style alias for {@link #openScope(String)}.
     */
    public void enterScope(String label) {
        openScope(label);
    }

    /**
     * Closes the current scope, silently ignoring an attempt to pop the global scope.
     * Python-style alias — the Python visitor intentionally protects the global scope.
     */
    public void exitScope() {
        if (currentScopeLevel <= 0) return;   // protect global scope
        scopes.pop();
        scopeNames.remove(currentScopeLevel);
        currentScopeLevel--;
    }

    /** Returns the current nesting depth (0 = global). */
    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }

    /** Returns the name of the current scope. */
    public String getCurrentScopeName() {
        return scopeNames.get(currentScopeLevel);
    }

    // ── Symbol insertion ────────────────────────────────────────────────────────

    /**
     * Inserts a pre-built {@link Symbol} into the current scope.
     *
     * @return {@code true} on success; {@code false} if the name is already
     *         defined in the <em>current</em> scope (duplicates are rejected).
     */
    public boolean add(Symbol symbol) {
        Map<String, Symbol> current = scopes.peek();
        if (current.containsKey(symbol.getName())) return false;
        current.put(symbol.getName(), symbol);
        return true;
    }

    /**
     * Convenience overload: builds a {@link Symbol} from name + type and inserts
     * it into the current scope (line number set to −1).
     * Jinja-style shorthand.
     */
    public boolean add(String name, String type) {
        return add(new Symbol(name, type, currentScopeLevel, -1));
    }

    /**
     * Inserts a symbol (name + type) into the current scope with a known line number.
     * Python-style variant.
     */
    public void addSymbol(String name, String type, int line) {
        add(new Symbol(name, type, currentScopeLevel, line));
    }

    /**
     * Inserts a symbol (name + type) into the current scope; line number unknown.
     * Python-style variant.
     */
    public void addSymbol(String name, String type) {
        add(new Symbol(name, type, currentScopeLevel, -1));
    }

    /**
     * Inserts a symbol with an additional {@code value} parameter.
     * The value is intentionally ignored in this implementation (mirroring the
     * original PySymbolTable design), but the signature is preserved so existing
     * Python-visitor call-sites compile without changes.
     */
    public void addSymbol(String name, Object value, String type, int line) {
        add(new Symbol(name, type, currentScopeLevel, line));
    }

    /**
     * Inserts a symbol directly into the <em>global</em> (level-0) scope,
     * regardless of the current nesting depth.
     *
     * @return {@code true} on success; {@code false} if the name is already
     *         present in the global scope or if the table is uninitialised.
     */
    public boolean addToGlobalScope(Symbol symbol) {
        if (scopes.isEmpty()) return false;
        Map<String, Symbol> global = scopes.get(0);
        if (global.containsKey(symbol.getName())) return false;
        Symbol gs = new Symbol(symbol.getName(), symbol.getType(), 0, symbol.getLineNumber());
        global.put(gs.getName(), gs);
        return true;
    }

    // ── Lookup ──────────────────────────────────────────────────────────────────

    /**
     * Searches from the innermost scope outward.
     *
     * @return the {@link Symbol}, or {@code null} if not found in any scope.
     */
    public Symbol lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol s = scopes.get(i).get(name);
            if (s != null) return s;
        }
        return null;
    }

    /** Searches only the current (innermost) scope. */
    public Symbol lookupInCurrentScope(String name) {
        return scopes.peek().get(name);
    }

    /** Returns {@code true} if the name is defined in any reachable scope. */
    public boolean isDefined(String name) {
        return lookup(name) != null;
    }

    /** Returns {@code true} if the name is defined in the current scope only. */
    public boolean isDefinedInCurrentScope(String name) {
        return scopes.peek().containsKey(name);
    }

    // ── Removal ─────────────────────────────────────────────────────────────────

    /** Removes and returns the symbol from the current scope, or {@code null}. */
    public Symbol removeFromCurrentScope(String name) {
        return scopes.peek().remove(name);
    }

    /**
     * Removes the first occurrence of the name, searching inward→outward.
     *
     * @return the removed {@link Symbol}, or {@code null} if not found.
     */
    public Symbol remove(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol removed = scopes.get(i).remove(name);
            if (removed != null) return removed;
        }
        return null;
    }

    // ── Bulk accessors ──────────────────────────────────────────────────────────

    /** Returns all symbols in the current scope as a new list. */
    public List<Symbol> getCurrentScopeSymbols() {
        return new ArrayList<>(scopes.peek().values());
    }

    /** Returns all symbols across every scope as a flat list. */
    public List<Symbol> getAllSymbols() {
        List<Symbol> all = new ArrayList<>();
        for (Map<String, Symbol> scope : scopes) all.addAll(scope.values());
        return all;
    }

    /**
     * Returns the raw scope map at a given nesting level, or {@code null} if the
     * level is out of range.  Useful for cross-language cross-reference checks.
     */
    public Map<String, Symbol> getScope(int level) {
        return (level >= 0 && level < scopes.size()) ? scopes.get(level) : null;
    }

    // ── Output ──────────────────────────────────────────────────────────────────

    /** Every scope ever opened, in open order (closed scopes included). */
    public List<Snapshot> getAllScopes() {
        return Collections.unmodifiableList(history);
    }

    /** Prints the full table to {@code System.out}. */
    public void printTable() {
        System.out.println(print());
    }

    /**
     * Full table: every scope opened while walking the file, with each entry's
     * name, kind and declaration line.  Unlike {@link #getFormattedTable()} this
     * survives scope closing, so function parameters and Jinja loop variables
     * are still visible after the walk has finished.
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SYMBOL TABLE ===\n");
        for (Snapshot scope : history) {
            sb.append("\nScope Level ").append(scope.level())
              .append(" [").append(scope.name()).append("]:\n");
            sb.append("-".repeat(60)).append("\n");
            if (scope.symbols().isEmpty()) {
                sb.append("  (empty)\n");
            } else {
                int idx = 1;
                for (Symbol symbol : scope.symbols().values()) {
                    sb.append(String.format("  %2d. %-26s kind=%-14s line=%d%n",
                            idx++, symbol.getName(), symbol.getType(), symbol.getLineNumber()));
                }
            }
        }
        sb.append("=".repeat(60)).append("\n");
        return sb.toString();
    }

    /** Returns a human-readable, multi-line table as a {@link String}. */
    public String getFormattedTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SYMBOL TABLE ===\n");
        int level = 0;
        for (Map<String, Symbol> scope : scopes) {
            String name = scopeNames.get(level);
            sb.append("\nScope Level ").append(level).append(" [").append(name).append("]:\n");
            sb.append("-".repeat(50)).append("\n");
            if (scope.isEmpty()) {
                sb.append("  (empty)\n");
            } else {
                int idx = 1;
                for (Symbol s : scope.values()) {
                    sb.append(String.format("  %2d. %s%n", idx++, s));
                }
            }
            level++;
        }
        sb.append("=".repeat(50)).append("\n");
        return sb.toString();
    }

    /** Returns per-scope and total symbol counts as a {@link String}. */
    public String getStatistics() {
        int total = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("=== Symbol Table Statistics ===\n");
        for (Snapshot scope : history) {
            int count = scope.symbols().size();
            total += count;
            sb.append(String.format("Scope %d (%s): %d symbol(s)%n",
                    scope.level(), scope.name(), count));
        }
        sb.append("Total symbols: ").append(total).append("\n");
        sb.append("===============================\n");
        return sb.toString();
    }

    // ── Export helpers ──────────────────────────────────────────────────────────

    /** Minimal JSON summary (scope count + symbol count). */
    public String toJson() {
        return "{\"scopes\": " + scopes.size()
                + ", \"symbols\": " + getAllSymbols().size() + "}";
    }

    /** Full CSV export of every symbol across all scopes. */
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append("Scope Level,Scope Name,Symbol Name,Symbol Type,Line\n");
        for (int i = 0; i < scopes.size(); i++) {
            for (Symbol s : scopes.get(i).values()) {
                csv.append(String.format("%d,%s,%s,%s,%d%n",
                        i, scopeNames.get(i),
                        s.getName(), s.getType(), s.getLineNumber()));
            }
        }
        return csv.toString();
    }

    // ── Object overrides ────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("SymbolTable[scopes=%d, symbols=%d]",
                scopes.size(), getAllSymbols().size());
    }
}