package PyAstClasses;

public class ListLiteralExpr extends PrimaryExpr {
        private ListLiteral listLiteral;

        public ListLiteral getListLiteral() { return listLiteral; }
        public void setListLiteral(ListLiteral listLiteral) { this.listLiteral = listLiteral; }

        @Override public String toString() { return "ListLiteralExpr(" + listLiteral + ")"; }

}
