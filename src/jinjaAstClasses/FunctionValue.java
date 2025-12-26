package jinjaAstClasses;

public class FunctionValue extends CssValue {
    CssFunction function;
    public CssFunction getFunction() {
        return function;
    }
    public void setFunction(CssFunction function) {
        this.function = function;
    }
    @Override
    public String toString() {
        return "\nFunctionValue{" +
                "\nfunction=" + function +
                "\n}";
    }
}