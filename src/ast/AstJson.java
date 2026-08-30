package ast;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Serialises either AST as a real, recursive JSON tree.
 *
 * <p>Every node becomes an object carrying its kind and its position metadata,
 * followed by its declared fields:</p>
 *
 * <pre>{"node":"AssignStmt","line":25,"column":0,"name":"products","value":{...}}</pre>
 *
 * <p>Fields are read reflectively rather than by 117 hand-written methods, so a
 * node added later cannot silently drop out of the dump — and the position
 * metadata is guaranteed to be present for every node.</p>
 */
public final class AstJson {

    private AstJson() {}

    /** Serialises one node (or any value) to compact JSON. */
    public static String of(Object value) {
        StringBuilder out = new StringBuilder();
        write(value, out, 0);
        return out.toString();
    }

    /** Serialises one node to indented, human-diffable JSON. */
    public static String pretty(Object value) {
        StringBuilder out = new StringBuilder();
        write(value, out, 1);
        return out.toString();
    }

    private static void write(Object value, StringBuilder out, int indent) {
        if (value == null)                    { out.append("null"); return; }
        if (value instanceof String text)     { string(text, out); return; }
        if (value instanceof Character text)  { string(String.valueOf(text), out); return; }
        if (value instanceof Boolean flag)    { out.append(flag); return; }
        if (value instanceof Number number)   { number(number, out); return; }
        if (value instanceof List<?> list)    { list(list, out, indent); return; }
        if (value instanceof Map<?,?> map)    { map(map, out, indent); return; }
        if (value instanceof MainProgram node){ node(node, out, indent); return; }
        string(String.valueOf(value), out);
    }

    private static void node(MainProgram value, StringBuilder out, int indent) {
        out.append('{');
        newline(out, indent);
        out.append("\"node\":");
        string(nameOf(value.getClass()), out);
        out.append(',');
        newline(out, indent);
        out.append("\"line\":").append(lineOf(value)).append(',');
        newline(out, indent);
        out.append("\"column\":").append(columnOf(value));

        for (Field field : fieldsOf(value.getClass())) {
            Object member;
            try {
                field.setAccessible(true);
                member = field.get(value);
            } catch (ReflectiveOperationException | RuntimeException ignored) {
                continue;
            }
            out.append(',');
            newline(out, indent);
            string(field.getName(), out);
            out.append(':');
            write(member, out, indent == 0 ? 0 : indent + 1);
        }
        newline(out, indent - 1);
        out.append('}');
    }

    /** Declared instance fields from the base class downwards, excluding position metadata. */
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

    /** Position metadata: both ASTs expose it, under historically different names. */
    private static int lineOf(MainProgram value) {
        if (value instanceof jinjaClasses.JinjaNode node) return node.getLine();
        if (value instanceof PyClasses.PyProgram node)    return node.getLineNumber();
        return -1;
    }

    private static int columnOf(MainProgram value) {
        if (value instanceof jinjaClasses.JinjaNode node) return node.getColumn();
        if (value instanceof PyClasses.PyProgram node)    return node.getColumn();
        return -1;
    }

    private static void list(List<?> values, StringBuilder out, int indent) {
        out.append('[');
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) out.append(',');
            newline(out, indent);
            write(values.get(i), out, indent == 0 ? 0 : indent + 1);
        }
        if (!values.isEmpty()) newline(out, indent - 1);
        out.append(']');
    }

    private static void map(Map<?,?> values, StringBuilder out, int indent) {
        out.append('{');
        boolean first = true;
        for (Map.Entry<?,?> entry : values.entrySet()) {
            if (!first) out.append(',');
            first = false;
            newline(out, indent);
            string(String.valueOf(entry.getKey()), out);
            out.append(':');
            write(entry.getValue(), out, indent == 0 ? 0 : indent + 1);
        }
        if (!values.isEmpty()) newline(out, indent - 1);
        out.append('}');
    }

    private static void number(Number value, StringBuilder out) {
        double d = value.doubleValue();
        if (Double.isNaN(d) || Double.isInfinite(d)) { out.append("null"); return; }
        if (value instanceof Double || value instanceof Float) out.append(d == Math.rint(d) ? String.valueOf((long) d) : String.valueOf(d));
        else out.append(value);
    }

    private static void string(String text, StringBuilder out) {
        out.append('"');
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '"'  -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                case '\b' -> out.append("\\b");
                case '\f' -> out.append("\\f");
                default   -> { if (c < 0x20) out.append(String.format("\\u%04x", (int) c)); else out.append(c); }
            }
        }
        out.append('"');
    }

    private static void newline(StringBuilder out, int indent) {
        if (indent <= 0) return;
        out.append('\n');
        out.append("  ".repeat(indent));
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
