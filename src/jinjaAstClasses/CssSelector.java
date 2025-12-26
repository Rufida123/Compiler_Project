package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;

public class CssSelector {
    List<SelectorPart> parts = new ArrayList<>();
    CssPseudo pseudo;
    public List<SelectorPart> getParts() {
        return parts;
    }
    public void setParts(List<SelectorPart> parts) {
        this.parts = parts;
    }
    public CssPseudo getPseudo() {
        return pseudo;
    }
    public void setPseudo(CssPseudo pseudo) {
        this.pseudo = pseudo;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nCssSelector{");
        sb.append("\n  parts=").append(parts);
        if (pseudo != null) {
            sb.append(", \n  pseudo=").append(pseudo);
        }
        sb.append("\n}");
        return sb.toString();
    }
}
