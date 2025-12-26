package jinjaAstClasses;

public class CssProperty {
    String word;
    ValueList valueList;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public ValueList getValueList() {
        return valueList;
    }
    public void setValueList(ValueList valueList) {
        this.valueList = valueList;
    }
    @Override
    public String toString() {
        return "\nCssProperty{" +
                "\nword='" + word + ' ' +
        ", \nvalueList=" + valueList +
                "\n}";
    }
}
