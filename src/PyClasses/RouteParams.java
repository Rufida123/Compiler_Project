package PyClasses;

public class RouteParams extends PyProgram {
    private ListLiteral methodsList; // list_literal

    public ListLiteral getMethodsList() { return methodsList; }
    public void setMethodsList(ListLiteral methodsList) { this.methodsList = methodsList; }

    @Override
    public String toString() {
        return "\nRouteParams{" +
                "\nmethodsList=" + methodsList +
                "\n}";
    }
}

