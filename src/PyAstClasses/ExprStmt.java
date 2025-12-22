package PyAstClasses;

public class ExprStmt extends Statement {
        private Expression expr;
        public Expression getExpr() { return expr; }
        public void setExpr(Expression expr) { this.expr = expr; }

        @Override
        public String toString() {
            return "\nExprStatement{" +
                    "\nexpr=" + expr +
                    "\n}";
        }
}


