package jinjaClasses;

import ast.MainProgram;

/**
 * Common base of every Jinja/HTML/CSS AST node.
 *
 * <p>It exists so the course requirement "every node stores its position as
 * node metadata" holds for the whole tree: {@code line} and {@code column} are
 * declared once here and stamped by the visitor from the ANTLR token.</p>
 */
public abstract class JinjaNode extends MainProgram {

    protected int line   = -1;
    protected int column = -1;

    public int getLine()   { return line; }
    public int getColumn() { return column; }

    public void setLine(int line)     { this.line = line; }
    public void setColumn(int column) { this.column = column; }

    /** Suffix used by every {@code toString()} so dumps show the position. */
    protected String lineInfo() {
        if (line == -1) return "";
        return column >= 0 ? " [line: " + line + ", col: " + column + "]" : " [line: " + line + "]";
    }
}
