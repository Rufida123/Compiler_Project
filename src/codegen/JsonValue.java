package codegen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal JSON reader shared by the AST context extractor and the Python
 * context executor.  It exists so the project keeps a single dependency-free
 * JSON implementation instead of two copies.
 *
 * Numbers are always returned as {@link Double}; objects as {@link LinkedHashMap};
 * arrays as {@link ArrayList}.  That matches what the renderer expects.
 */
public final class JsonValue {

    private final String source;
    private int index;

    private JsonValue(String source) { this.source = source; }

    /** Parses a complete JSON document. */
    public static Object read(String text) {
        return new JsonValue(text).value();
    }

    private Object value() {
        skipWhitespace();
        char c = source.charAt(index);
        if (c == '{') return object();
        if (c == '[') return array();
        if (c == '"') return string();
        if (source.startsWith("true", index))  { index += 4; return Boolean.TRUE; }
        if (source.startsWith("false", index)) { index += 5; return Boolean.FALSE; }
        if (source.startsWith("null", index))  { index += 4; return null; }
        int start = index;
        while (index < source.length() && "-+.0123456789eE".indexOf(source.charAt(index)) >= 0) index++;
        return Double.valueOf(source.substring(start, index));
    }

    private Map<String,Object> object() {
        Map<String,Object> result = new LinkedHashMap<>();
        index++;                       // consume '{'
        skipWhitespace();
        while (source.charAt(index) != '}') {
            String key = string();
            skipWhitespace();
            index++;                   // consume ':'
            result.put(key, value());
            skipWhitespace();
            if (source.charAt(index) == ',') { index++; skipWhitespace(); }
        }
        index++;                       // consume '}'
        return result;
    }

    private List<Object> array() {
        List<Object> result = new ArrayList<>();
        index++;                       // consume '['
        skipWhitespace();
        while (source.charAt(index) != ']') {
            result.add(value());
            skipWhitespace();
            if (source.charAt(index) == ',') { index++; skipWhitespace(); }
        }
        index++;                       // consume ']'
        return result;
    }

    private String string() {
        StringBuilder text = new StringBuilder();
        index++;                       // consume opening quote
        while (source.charAt(index) != '"') {
            char c = source.charAt(index++);
            if (c != '\\') { text.append(c); continue; }
            char escape = source.charAt(index++);
            switch (escape) {
                case 'n' -> text.append('\n');
                case 'r' -> text.append('\r');
                case 't' -> text.append('\t');
                case 'b' -> text.append('\b');
                case 'f' -> text.append('\f');
                case 'u' -> { text.append((char) Integer.parseInt(source.substring(index, index + 4), 16)); index += 4; }
                default  -> text.append(escape);
            }
        }
        index++;                       // consume closing quote
        return text.toString();
    }

    private void skipWhitespace() {
        while (index < source.length() && Character.isWhitespace(source.charAt(index))) index++;
    }
}
