package PyAstClasses;

import java.util.ArrayList;

public class FuncDefStatement extends Statement {
    private String name;
    private ArrayList<String> params = new ArrayList<>();
    private Suite body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public Suite getBody() {
        return body;
    }

    public void setBody(Suite body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "\nFuncDefStmt{\nname='" + name +
                "',\nparams=" + params +
                ",\nbody=" + body + "\n}";
    }
}
