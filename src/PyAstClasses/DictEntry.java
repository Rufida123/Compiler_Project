package PyAstClasses;


public class DictEntry extends Program {
    private Expression key;
    private Expression value;

    public Expression getKey() { return key; }
    public void setKey(Expression key) { this.key = key; }

    public Expression getValue() { return value; }
    public void setValue(Expression value) { this.value = value; }

    @Override
    public String toString() {
        return "\nDictEntry{" +
                "\nkey=" + key +
                ",\nvalue=" + value +
                "\n}";
    }
}

