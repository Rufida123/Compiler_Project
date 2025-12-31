package PySymbolTable;

import PyVisitor.AstPrettyPrinter;
import PyAstClasses.Expression;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

    private final Map<String, Symbol> table = new LinkedHashMap<>();

    public void addSymbol(String name, Object value, String type) {
        table.put(name, new Symbol(name, value, type));
    }

    public boolean containsKey(String name) {
        return table.containsKey(name);
    }

    public Object getValue(String name) {
        Symbol s = table.get(name);
        return (s != null) ? s.value : null;
    }

    public void clear() { table.clear(); }

    public void printSymbolTable(AstPrettyPrinter printer) {
        System.out.println("Symbol Table:");
        System.out.printf("+---------------------------+------------------------------+----------------------+\n");
        System.out.printf("| Name                      | Value                        | Type                 |\n");
        System.out.printf("+---------------------------+------------------------------+----------------------+\n");

        for (Symbol s : table.values()) {
            String valueStr = formatValue(s.value, printer);
            valueStr = ellipsize(valueStr, 80);

            System.out.printf("| %-25s | %-28s | %-20s |\n",
                    ellipsize(s.name, 25),
                    ellipsize(valueStr, 28),
                    ellipsize(s.type, 20)
            );
        }

        System.out.printf("+---------------------------+------------------------------+----------------------+\n");
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

    static class Symbol {
        String name;
        Object value;
        String type;

        Symbol(String name, Object value, String type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }
    }
}
