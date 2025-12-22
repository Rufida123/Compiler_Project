package PyAstClasses;

public class IfStatement extends Statement {
        private Expression condition;
        private Suite thenSuite;
        private Suite elseSuite; // nullable

        public Expression getCondition() { return condition; }
        public void setCondition(Expression condition) { this.condition = condition; }

        public Suite getThenSuite() { return thenSuite; }
        public void setThenSuite(Suite thenSuite) { this.thenSuite = thenSuite; }

        public Suite getElseSuite() { return elseSuite; }
        public void setElseSuite(Suite elseSuite) { this.elseSuite = elseSuite; }

        @Override
        public String toString() {
            return "\nIfStatement{" +
                    "\ncondition=" + condition +
                    ",\nthenSuite=" + thenSuite +
                    ",\nelseSuite=" + elseSuite +
                    "\n}";
        }


}
