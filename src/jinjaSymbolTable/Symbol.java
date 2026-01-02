package jinjaSymbolTable;

public class Symbol {
    private final String name;
    private final String type; // "block", "variable", "selector"
    private final int scopeLevel;  // أضف هذا الحقل
    private final int lineNumber;  // أضف هذا الحقل

    // المُنشئ القديم (للتوافق)
    public Symbol(String name, String type) {
        this(name, type, -1, -1);
    }

    // المُنشئ الجديد مع مستوى السكوب ورقم السطر
    public Symbol(String name, String type, int scopeLevel, int lineNumber) {
        this.name = name;
        this.type = type;
        this.scopeLevel = scopeLevel;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    // أضف هذه الدوال الجديدة
    public int getScopeLevel() {
        return scopeLevel;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return type + ": " + name + " [scope: " + scopeLevel + ", line: " + lineNumber + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return name.equals(symbol.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}