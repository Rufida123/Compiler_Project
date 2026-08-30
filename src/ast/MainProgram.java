package ast;

/** Common root of both ASTs; gives every node a polymorphic dump and JSON form. */
public class MainProgram {

    /**
     * Readable dump of this node and its children, indented by {@code indent}
     * levels.  Overridable per node; the default is reflective so a node added
     * later still prints.
     */
    public String print(int indent) { return AstPrinter.render(this, indent); }

    /** Whole-tree dump starting at this node. */
    public String printTree() { return print(0); }

    /** Recursive JSON tree for this node and its children. */
    public String toJson() { return AstJson.of(this); }

    /** Indented variant, used for the compiler_output artefacts. */
    public String toPrettyJson() { return AstJson.pretty(this); }
}
