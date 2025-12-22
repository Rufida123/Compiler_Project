package PyAstClasses;
import java.util.ArrayList;
import java.util.List;

public class ListLiteral extends Program {
    private List<Expression> elements = new ArrayList<>();

    public List<Expression> getElements() { return elements; }
    public void setElements(List<Expression> elements) { this.elements = elements; }
    public void addElement(Expression e) { this.elements.add(e); }

    @Override
    public String toString() {
        return "\nListLiteral{" +
                "\nelements=" + elements +
                "\n}";
    }
}
