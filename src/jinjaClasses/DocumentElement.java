package jinjaClasses;

public abstract class DocumentElement {
    protected int line = -1;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    // اختياري: helper لـ toString()
    protected String lineInfo() {
        return line != -1 ? " [line: " + line + "]" : "";
    }
}