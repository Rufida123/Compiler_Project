// DottedName.java
package PyAstClasses;

import java.util.ArrayList;
import java.util.List;

public class DottedName extends ImportStatement {
        private List<String> parts = new ArrayList<>();

        public List<String> getParts() { return parts; }
        public void setParts(List<String> parts) { this.parts = parts; }
        public void addPart(String p) { this.parts.add(p); }

        @Override
        public String toString() {
            return "\nDottedName{" +
                    "\nparts=" + parts +
                    "\n}";
        }
}


