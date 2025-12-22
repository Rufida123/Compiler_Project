package PyAstClasses;


public class GeneratorPrimaryExpr extends PrimaryExpr {
    private GeneratorExpr generatorExpr;

    public GeneratorExpr getGeneratorExpr() { return generatorExpr; }
    public void setGeneratorExpr(GeneratorExpr generatorExpr) { this.generatorExpr = generatorExpr; }

    @Override public String toString() { return "GeneratorPrimary(" + generatorExpr + ")"; }
}
