package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;

//{{ user.profile.picture.url }}
//{{ user.settings["theme"] }}
public class JinjaIdentifierChain {
    String identifier; // The first/base name, e.g. "user"
    List<Access> accesses = new ArrayList<>(); // All subsequent .prop or [expr]
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public List<Access> getAccesses() {
        return accesses;
    }
    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(identifier);
        for (Access access : accesses) {
            if (access instanceof DotAccess) {
                sb.append(".").append(((DotAccess) access).getIdentifier());
            } else if (access instanceof IndexAccess) {
                sb.append("[").append(((IndexAccess) access).getExpression()).append("]");
            }
        }
        return sb.toString();
    }
}