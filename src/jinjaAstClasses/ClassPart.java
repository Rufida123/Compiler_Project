package jinjaAstClasses;

public class ClassPart extends SelectorPart {
    String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    @Override
    public String toString() {
        return "\nClassSelector{" +
                "\nword='" + word + ' ' +
        "\n}";
    }
}
