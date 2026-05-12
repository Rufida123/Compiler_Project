package PyClasses;

public class DictLiteralExpr extends PrimaryExpr {
    private DictLiteral dictLiteral;

    public DictLiteral getDictLiteral() { return dictLiteral; }
    public void setDictLiteral(DictLiteral dictLiteral) { this.dictLiteral = dictLiteral; }

    @Override
    public String toString() {
        return "\nDictLiteralExpr{" +
                "\ndictLiteral=" + dictLiteral +
                "\n}";
    }
}

