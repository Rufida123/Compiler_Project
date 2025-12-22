// ImportStmt.java
package PyAstClasses;

import java.util.ArrayList;

public class ImportStmt extends ImportStatement {

        private DottedName dottedName;
        private ImportList importList;

        public DottedName getDottedName() { return dottedName; }
        public void setDottedName(DottedName dottedName) { this.dottedName = dottedName; }

        public ImportList getImportList() { return importList; }
        public void setImportList(ImportList importList) { this.importList = importList; }

        @Override
        public String toString() {
            return "\nImportStmt{" +
                    "\ndottedName=" + dottedName +
                    ",\nimportList=" + importList +
                    "\n}";
        }


}
