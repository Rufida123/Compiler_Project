package PyAstClasses;

public class ImportItem extends ImportStatement {
    private String name;
    private String alias; // nullable

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    @Override
    public String toString() {
        return (alias == null)
                ? ("\nImportItem{name='" + name + "'}")
                : ("\nImportItem{name='" + name + "', alias='" + alias + "'}");
    }
}

