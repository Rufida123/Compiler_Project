package jinjaAstClasses;

public class WordValue extends CssValue {
    String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    @Override
    public String toString() {
        return "\nWordValue{" +
                "\nword='" + word + ' ' +
        "\n}";
    }
}