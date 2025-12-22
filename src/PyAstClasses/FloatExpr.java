package PyAstClasses;

public class FloatExpr extends PrimaryExpr {
        private double value;

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        @Override public String toString() { return "Float(" + value + ")"; }


}
