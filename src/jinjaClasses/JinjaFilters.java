package jinjaClasses;

import java.util.Set;

/**
 * The single source of truth for which Jinja filters this compiler supports.
 *
 * <p>Both sides read it, so they cannot drift apart:</p>
 * <ul>
 *   <li>{@code semantic.SemanticAnalyzer} rejects anything outside this set
 *       (E-J-10), during analysis, before generation starts;</li>
 *   <li>{@code codegen.JinjaRenderer} implements exactly this set.</li>
 * </ul>
 *
 * <p>Previously an unrecognised filter was a silent pass-through at render time,
 * so {@code {{ x | trim }}} quietly emitted the untrimmed value.  A filter added
 * to the renderer must be added here, and vice versa.</p>
 */
public final class JinjaFilters {

    private JinjaFilters() {}

    public static final Set<String> SUPPORTED = Set.of(
            "upper", "lower", "format", "string", "trim",
            "replace", "int", "float", "list", "length", "default"
    );

    public static boolean isSupported(String name) {
        return name != null && SUPPORTED.contains(name);
    }

    /** Sorted, for error messages. */
    public static String supportedList() {
        return SUPPORTED.stream().sorted().toList().toString();
    }
}
