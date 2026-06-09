package PyClasses;

public class FloatExpr extends PrimaryExpr {
        private double value;
        private int lineNumber;

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        public int getLineNumber() { return lineNumber; }
        public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }

        @Override public String toString() { return "Float(" + value + ")"; }
}