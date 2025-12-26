package jinjaAstClasses;

//{{ username | default("Guest") }}
public class FunctionCall extends JinjaPrimary {
    String identifier;
    JinjaCallArgs callArgs;
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public JinjaCallArgs getCallArgs() {
        return callArgs;
    }
    public void setCallArgs(JinjaCallArgs callArgs) {
        this.callArgs = callArgs;
    }
    @Override
    public String toString() {
        return "\nFunctionCall{" +
                "\nidentifier='" + identifier + ' ' +
        ", \ncallArgs=" + callArgs +
                "\n}";
    }
}
