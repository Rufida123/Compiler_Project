package PyAstClasses;

public class RoutePath extends Program {
    private String path; // من ROUTE_PATH أو STRING

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    @Override
    public String toString() {
        return "\nRoutePath{" +
                "\npath=" + path +
                "\n}";
    }
}
