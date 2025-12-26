package jinjaAstClasses;

//{{ user.profile.name }}
public class AccessExpr extends JinjaPrimary {
    JinjaIdentifierChain chain;
    public JinjaIdentifierChain getChain() {
        return chain;
    }
    public void setChain(JinjaIdentifierChain chain) {
        this.chain = chain;
    }
    @Override
    public String toString() {
        return chain.toString();
    }
}