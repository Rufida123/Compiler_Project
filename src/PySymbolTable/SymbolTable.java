 package PySymbolTable;

import PyVisitor.AstPrettyPrinter;
import PyAstClasses.Expression;

import java.util.*;

public class SymbolTable {

    //
    private final Map<String, Symbol> table = new LinkedHashMap<>();

    //Stack for scopes
    private final Deque<String> scopeStack = new ArrayDeque<>();
    private final List<DuplicateDef> duplicates = new ArrayList<>();

    public SymbolTable() {
        initGlobal();
    }

    // ================= Scopes =================

    public void initGlobal() {
        table.clear();
        duplicates.clear();
        scopeStack.clear();
        scopeStack.push("Global");
    }

    public void enterScope(String label) {
        scopeStack.push(label);
    }

    public void exitScope() {
        if (scopeStack.size() > 1) scopeStack.pop();
    }

    public String currentScopePath() {
        // stack top-first
        List<String> parts = new ArrayList<>(scopeStack);
        Collections.reverse(parts);
        return String.join(" -> ", parts);
    }

    private String scopedKey(String name) {
        return currentScopePath() + "::" + name;
    }

    // ================= Add / Define =================


    public void addSymbol(String name, Object value, String type) {
        addSymbol(name, value, type, -1);
    }

    //with line
    public void addSymbol(String name, Object value, String type, int line) {
        String key = scopedKey(name);

        // duplicate
        if (table.containsKey(key)) {
            Symbol old = table.get(key);
            old.duplicateLines.add(line);
            duplicates.add(new DuplicateDef(name, currentScopePath(), old.lineDefined, line));
            return;
        }

        table.put(key, new Symbol(name, value, type, line, currentScopePath()));
    }

    public boolean containsKey(String name) {
        return table.containsKey(scopedKey(name));
    }

    public Object getValue(String name) {
        Symbol s = table.get(scopedKey(name));
        return (s != null) ? s.value : null;
    }

    public List<DuplicateDef> getDuplicates() {
        return duplicates;
    }

    public void clear() {
        initGlobal();
    }


    // ================= Printing =================

    public void printSymbolTable(AstPrettyPrinter printer) {
        System.out.println("Symbol Table:");
        System.out.printf("+---------------------------+------------------------------+----------------------+--------+----------------------------------------+%n");
        System.out.printf("| Name                      | Value                        | Type                 | Line   | Scope                        |%n");
        System.out.printf("+---------------------------+------------------------------+----------------------+--------+----------------------------------------+%n");

        for (Symbol s : table.values()) {
            String valueStr = formatValue(s.value, printer);
            System.out.printf("| %-25s | %-28s | %-20s | %-6s | %-40s |%n",
                    ellipsize(s.name, 25),
                    ellipsize(valueStr, 28),
                    ellipsize(s.type, 20),
                    (s.lineDefined > 0 ? s.lineDefined : ""),
                    ellipsize(s.scopePath, 40)
            );
        }

        System.out.printf("+---------------------------+------------------------------+----------------------+--------+----------------------------------------+%n");

        if (!duplicates.isEmpty()) {
            System.out.println("\nDuplicates (same-scope redefinitions):");
            for (DuplicateDef d : duplicates) System.out.println("- " + d);
        }
    }

    private static final Set<String> VAR_TYPES = new HashSet<>(
            Arrays.asList("Assignment", "Parameter", "ForVar")
    );

    private boolean isVariable(Symbol s) {
        return s != null && VAR_TYPES.contains(s.type);
    }

    public void printVariablesOnly(AstPrettyPrinter printer) {
        System.out.println("Symbol Table (Variables Only):");
        System.out.printf("+---------------------------+------------------------------+------------+--------+----------------------------------------+%n");
        System.out.printf("| Name                      | Value                        | VarType    | Line   | Scope                                  |%n");
        System.out.printf("+---------------------------+------------------------------+------------+--------+----------------------------------------+%n");

        for (Symbol s : table.values()) {
            if (!isVariable(s)) continue;

            String valueStr = ellipsize(formatValue(s.value, printer), 28);

            System.out.printf("| %-25s | %-28s | %-10s | %-6s | %-38s |%n",
                    ellipsize(s.name, 25),
                    valueStr,
                    ellipsize(s.type, 10),
                    (s.lineDefined > 0 ? s.lineDefined : ""),
                    ellipsize(s.scopePath, 38)
            );
        }

        System.out.printf("+---------------------------+------------------------------+------------+--------+----------------------------------------+%n");

        if (!duplicates.isEmpty()) {
            System.out.println("\nDuplicates (variables only):");
            for (DuplicateDef d : duplicates) {
                System.out.println("- " + d);
            }
        }
    }
    private String formatValue(Object value, AstPrettyPrinter printer) {
        if (value == null) return "null";
        if (value instanceof Expression e) return printer.formatExpr(e);
        return String.valueOf(value);
    }
    private String ellipsize(String s, int max) {
        if (s == null) return "null";
        if (s.length() <= max) return s;
        return s.substring(0, max - 3) + "...";
    }
    // ================= Data classes =================

    public static class Symbol {
        public final String name;
        public final Object value;
        public final String type;
        public final int lineDefined;
        public final String scopePath;
        public final List<Integer> duplicateLines = new ArrayList<>();

        public Symbol(String name, Object value, String type, int lineDefined, String scopePath) {
            this.name = name;
            this.value = value;
            this.type = type;
            this.lineDefined = lineDefined;
            this.scopePath = scopePath;
        }
    }

    public static class DuplicateDef {
        public final String name;
        public final String scopePath;
        public final int firstLine;
        public final int duplicateLine;

        public DuplicateDef(String name, String scopePath, int firstLine, int duplicateLine) {
            this.name = name;
            this.scopePath = scopePath;
            this.firstLine = firstLine;
            this.duplicateLine = duplicateLine;
        }

        @Override
        public String toString() {
            return "Duplicate '" + name + "' in [" + scopePath + "] first@" + firstLine + " again@" + duplicateLine;
        }
    }
}