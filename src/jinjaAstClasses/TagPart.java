package jinjaAstClasses;

public class TagPart extends SelectorPart {
    String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    @Override
    public String toString() {
        return "\nTagSelector{" +
                "\nword='" + word + ' ' +
        "\n}";
    }
}
