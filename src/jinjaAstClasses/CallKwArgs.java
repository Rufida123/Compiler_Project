package jinjaAstClasses;

import java.util.ArrayList;
import java.util.List;
public class CallKwArgs extends JinjaCallArgs {
    List<JinjaKwArg> kwArgs = new ArrayList<>();
    public List<JinjaKwArg> getKwArgs() {
        return kwArgs;
    }
    public void setKwArgs(List<JinjaKwArg> kwArgs) {
        this.kwArgs = kwArgs;
    }
    @Override
    public String toString() {
        return "\nCallKwArgs{" +
                "\nkwArgs=" + kwArgs +
                "\n}";
    }
}
