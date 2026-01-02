package jinjaSymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    // البنية: Stack of scopes - كل scope هو Map
    private final Stack<Map<String, Symbol>> scopes;
    private final List<String> scopeNames; // أسماء السكوبات (للطباعة)
    private int currentScopeLevel;

    // Constructor
    public SymbolTable() {
        this.scopes = new Stack<>();
        this.scopeNames = new ArrayList<>();
        this.currentScopeLevel = -1;
        openScope("global"); // السكوب العالمي
    }

    // === عمليات إدارة السكوبات ===

    /**
     * فتح سكوب جديد
     * @param scopeName اسم السكوب (للتوضيح عند الطباعة)
     */
    public void openScope(String scopeName) {
        currentScopeLevel++;
        scopes.push(new HashMap<>());
        scopeNames.add(scopeName);
    }

    /**
     * فتح سكوب جديد بدون اسم
     */
    public void openScope() {
        openScope("scope_" + (currentScopeLevel + 1));
    }

    /**
     * إغلاق السكوب الحالي
     * @return الرموز التي تمت إزالتها
     */
    public List<Symbol> closeScope() {
        if (currentScopeLevel <= 0) {
            throw new IllegalStateException("Cannot close global scope");
        }

        Map<String, Symbol> removedScope = scopes.pop();
        scopeNames.remove(currentScopeLevel);
        currentScopeLevel--;

        return new ArrayList<>(removedScope.values());
    }

    /**
     * الحصول على مستوى السكوب الحالي
     */
    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }

    /**
     * الحصول على اسم السكوب الحالي
     */
    public String getCurrentScopeName() {
        return scopeNames.get(currentScopeLevel);
    }

    // === عمليات إدارة الرموز ===

    /**
     * إضافة رمز جديد (مع التحقق من التكرار في السكوب الحالي)
     * @param symbol الرمز المراد إضافته
     * @return true إذا تمت الإضافة بنجاح، false إذا كان مكرراً
     */
    public boolean add(Symbol symbol) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(symbol.getName())) {
            return false;
        }
        currentScope.put(symbol.getName(), symbol);
        return true;
    }

    /**
     * إضافة رمز جديد (نسخة مبسطة)
     */
    public boolean add(String name, String type) {
        // استخدم المُنشئ الجديد مع مستوى السكوب الحالي ورقم سطر -1 (غير محدد)
        return add(new Symbol(name, type, currentScopeLevel, -1));
    }

    /**
     * البحث عن رمز (من الداخل إلى الخارج - الـ scope الحالي ثم الأب)
     * @param name اسم الرمز
     * @return الرمز إذا وجد، أو null
     */
    public Symbol lookup(String name) {
        // البحث من السكوب الحالي إلى السكوب العالمي
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol symbol = scopes.get(i).get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

    /**
     * البحث عن رمز في السكوب الحالي فقط (لا يشمل الآباء)
     */
    public Symbol lookupInCurrentScope(String name) {
        return scopes.peek().get(name);
    }

    /**
     * التحقق من وجود رمز في أي سكوب
     */
    public boolean isDefined(String name) {
        return lookup(name) != null;
    }

    /**
     * التحقق من وجود رمز في السكوب الحالي فقط
     */
    public boolean isDefinedInCurrentScope(String name) {
        return scopes.peek().containsKey(name);
    }

    /**
     * حذف رمز من السكوب الحالي
     */
    public Symbol removeFromCurrentScope(String name) {
        return scopes.peek().remove(name);
    }

    /**
     * حذف رمز من أي سكوب (أول ظهور من الأعلى)
     */
    public Symbol remove(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Symbol removed = scopes.get(i).remove(name);
            if (removed != null) {
                return removed;
            }
        }
        return null;
    }

    /**
     * الحصول على جميع الرموز في السكوب الحالي
     */
    public List<Symbol> getCurrentScopeSymbols() {
        return new ArrayList<>(scopes.peek().values());
    }

    /**
     * الحصول على جميع الرموز من جميع السكوبات
     */
    public List<Symbol> getAllSymbols() {
        List<Symbol> allSymbols = new ArrayList<>();
        for (int i = 0; i < scopes.size(); i++) {
            allSymbols.addAll(scopes.get(i).values());
        }
        return allSymbols;
    }

    /**
     * إحصائيات الرموز
     */
    public String getStatistics() {
        int totalSymbols = 0;
        StringBuilder stats = new StringBuilder();
        stats.append("=== Symbol Table Statistics ===\n");

        for (int i = 0; i < scopes.size(); i++) {
            int scopeSize = scopes.get(i).size();
            totalSymbols += scopeSize;
            stats.append(String.format("Scope %d (%s): %d symbols\n",
                    i, scopeNames.get(i), scopeSize));
        }

        stats.append(String.format("Total symbols: %d\n", totalSymbols));
        stats.append("=============================\n");
        return stats.toString();
    }

    // === الطباعة والتصدير ===

    /**
     * طباعة جدول الرموز بشكل منظم
     */
    public void printTable() {
        System.out.println("\n" + getFormattedTable());
    }

    /**
     * الحصول على جدول الرموز بشكل منظم كـ String
     */
    public String getFormattedTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== SYMBOL TABLE ===\n");
        int level = 0;
        for (Map<String, Symbol> scope : scopes) {
            sb.append("\nScope Level ").append(level++).append(" [").append(scopeNames.get(level-1)).append("]:\n");
            sb.append("-".repeat(40)).append("\n");
            if (scope.isEmpty()) {
                sb.append(" (empty)\n");
            } else {
                int index = 1;
                for (Symbol symbol : scope.values()) {
                    sb.append(String.format(" %2d. %s\n", index++, symbol));
                }
            }
        }
        sb.append("=".repeat(40)).append("\n");
        return sb.toString();
    }

    /**
     * طريقة toString مختصرة
     */
    @Override
    public String toString() {
        return String.format("SymbolTable[scopes=%d, symbols=%d]",
                scopes.size(), getAllSymbols().size());
    }

    // === طرق مساعدة ===

    /**
     * تصدير الرموز كـ JSON (للتوسع المستقبلي)
     */
    public String toJson() {
        return "{\"scopes\": " + scopes.size() + ", \"symbols\": " + getAllSymbols().size() + "}";
    }

    /**
     * تصدير الرموز كـ CSV (للتوسع المستقبلي)
     */
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append("Scope Level,Scope Name,Symbol Name,Symbol Type,Line\n");

        for (int i = 0; i < scopes.size(); i++) {
            for (Symbol symbol : scopes.get(i).values()) {
                csv.append(String.format("%d,%s,%s,%s,%d\n",
                        i, scopeNames.get(i),
                        symbol.getName(), symbol.getType(),
                        symbol.getLineNumber()));
            }
        }
        return csv.toString();
    }

    /**
     * تنظيف جدول الرموز بالكامل
     */
    public void clear() {
        scopes.clear();
        scopeNames.clear();
        currentScopeLevel = -1;
        openScope("global");
    }
    // === طرق مساعدة للوصول إلى السكوب العالمي ===

    /**
     * إضافة رمز إلى السكوب العالمي مباشرة
     * @param symbol الرمز المراد إضافته
     * @return true إذا تمت الإضافة بنجاح
     */
    public boolean addToGlobalScope(Symbol symbol) {
        if (scopes.isEmpty()) {
            return false;
        }

        // السكوب العالمي هو الأول دائمًا (index 0)
        Map<String, Symbol> globalScope = scopes.get(0);

        // التحقق من عدم التكرار
        if (globalScope.containsKey(symbol.getName())) {
            return false;
        }

        // إضافة الرمز مع تعديل مستوى السكوب ليكون 0
        Symbol globalSymbol = new Symbol(symbol.getName(), symbol.getType(), 0, symbol.getLineNumber());
        globalScope.put(symbol.getName(), globalSymbol);
        return true;
    }

    /**
     * الحصول على خريطة الرموز في مستوى سكوب معين
     * @param level مستوى السكوب المطلوب
     * @return خريطة الرموز في ذلك المستوى
     */
    public Map<String, Symbol> getScope(int level) {
        if (level >= 0 && level < scopes.size()) {
            return scopes.get(level);
        }
        return null;
    }
}