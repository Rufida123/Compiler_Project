package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
public class CallMixedArgs extends JinjaCallArgs {
    List<JinjaArg> posArgs = new ArrayList<>();
    List<JinjaKwArg> kwArgs = new ArrayList<>();
    public List<JinjaArg> getPosArgs() {
        return posArgs;
    }
    public void setPosArgs(List<JinjaArg> posArgs) {
        this.posArgs = posArgs;
    }
    public List<JinjaKwArg> getKwArgs() {
        return kwArgs;
    }
    public void setKwArgs(List<JinjaKwArg> kwArgs) {
        this.kwArgs = kwArgs;
    }
    @Override
    public String toString() {
        return "\nCallMixedArgs{" +
                "\nposArgs=" + posArgs +
                ", \nkwArgs=" + kwArgs +
                "\n}";
    }
}