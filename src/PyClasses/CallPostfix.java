package PyClasses;

public class CallPostfix extends PostfixOp {
    private ArgList argList; // nullable

    public ArgList getArgList() { return argList; }
    public void setArgList(ArgList argList) { this.argList = argList; }

    @Override
    public String toString() {
        return "\nCallPostfix{" +
                "\nargList=" + argList +
                "\n}";
    }
}

