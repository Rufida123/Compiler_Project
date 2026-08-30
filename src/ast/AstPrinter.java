package ast;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Readable indented dump of either AST: for each node its kind, its position,
 * its scalar fields, then its children one level deeper.
 *
 * <pre>
 * AssignStmt (line 25, col 0)
 *   name = "products"
 *   value:
 *     PostfixExpr (line 25, col 11)
 *       primary:
 *         IdentifierExpr (line 25, col 11)
 *           name = "load_products_from_json"
 * </pre>
 *
 * Reached polymorphically through {@link MainProgram#print(int)}, which any node
 * may override; the reflective default guarantees no node prints as a blank.
 */
public final class AstPrinter {

    private AstPrinter() {}

    public static String render(MainProgram node, int indent) {
        StringBuilder out = new StringBuilder();
        node(node, out, indent);
        return out.toString();
    }

    private static void node(MainProgram value, StringBuilder out, int indent) {
        pad(out, indent);
        out.append(nameOf(value.getClass()));
        int line = position(value, true);
        int column = position(value, false);
        if (line >= 0 || column >= 0) out.append(" (line ").append(line).append(", col ").append(column).append(')');
        out.append('\n');

        for (Field field : fieldsOf(value.getClass())) {
            Object member;
            try {
                field.setAccessible(true);
                member = field.get(value);
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                continue;
            }
            child(field.getName(), member, out, indent + 1);
        }
    }

    private static void child(String label, Object value, StringBuilder out, int indent) {
        if (value == null) {
            pad(out, indent); out.append(label).append(" = null\n");
        } else if (value instanceof MainProgram child) {
            pad(out, indent); out.append(label).append(":\n");
            node(child, out, indent + 1);
        } else if (value instanceof List<?> items) {
            pad(out, indent); out.append(label).append(" [").append(items.size()).append("]");
            if (items.isEmpty()) { out.append('\n'); return; }
            out.append(":\n");
            for (Object item : items) {
                if (item instanceof MainProgram child) node(child, out, indent + 1);
                else { pad(out, indent + 1); out.append(scalar(item)).append('\n'); }
            }
        } else if (value instanceof Map<?,?> entries) {
            pad(out, indent); out.append(label).append(" {").append(entries.size()).append("}:\n");
            for (Map.Entry<?,?> entry : entries.entrySet()) child(String.valueOf(entry.getKey()), entry.getValue(), out, indent + 1);
        } else {
            pad(out, indent); out.append(label).append(" = ").append(scalar(value)).append('\n');
        }
    }

    private static String scalar(Object value) {
        if (value instanceof String text) return '"' + text.replace("\n", "\\n").replace("\"", "\\\"") + '"';
        return String.valueOf(value);
    }

    private static List<Field> fieldsOf(Class<?> type) {
        List<Class<?>> chain = new ArrayList<>();
        for (Class<?> current = type; current != null && current != Object.class; current = current.getSuperclass()) {
            chain.add(0, current);
        }
        List<Field> fields = new ArrayList<>();
        for (Class<?> current : chain) {
            for (Field field : current.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) continue;
                String name = field.getName();
                if (name.equals("line") || name.equals("column") || name.equals("lineNumber")) continue;
                if (isInheritedProgramList(type, field)) continue;
                fields.add(field);
            }
        }
        return fields;
    }

    private static int position(MainProgram value, boolean wantLine) {
        if (value instanceof jinjaClasses.JinjaNode node) return wantLine ? node.getLine() : node.getColumn();
        if (value instanceof PyClasses.PyProgram node)    return wantLine ? node.getLineNumber() : node.getColumn();
        return -1;
    }

    private static void pad(StringBuilder out, int indent) {
        out.append("  ".repeat(Math.max(0, indent)));
    }
    /** Anonymous subclasses (the program root is one) report an empty simple name. */
    private static String nameOf(Class<?> type) {
        while (type != null && type.getSimpleName().isEmpty()) type = type.getSuperclass();
        return type == null ? "Node" : type.getSimpleName();
    }

    /**
     * PyProgram is both the program root and the base of every Python node, so
     * every node inherits its {@code statements} list.  Only the root actually
     * uses it; elsewhere it is noise.
     */
    private static boolean isInheritedProgramList(Class<?> owner, java.lang.reflect.Field field) {
        if (!field.getName().equals("statements")) return false;
        if (!field.getDeclaringClass().equals(PyClasses.PyProgram.class)) return false;
        return !(owner.equals(PyClasses.PyProgram.class)
                 || PyClasses.PyProgram.class.equals(owner.getSuperclass()));
    }
}
