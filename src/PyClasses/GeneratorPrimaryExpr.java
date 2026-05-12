package PyClasses;


public class GeneratorPrimaryExpr extends PrimaryExpr {
    private GeneratorExpr generatorExpr;

    public GeneratorExpr getGeneratorExpr() { return generatorExpr; }
    public void setGeneratorExpr(GeneratorExpr generatorExpr) { this.generatorExpr = generatorExpr; }

    @Override
    public String toString() {
        return "\nGeneratorPrimaryExpr{" +
                "\ngeneratorExpr=" + generatorExpr +
                "\n}";
    }
}
