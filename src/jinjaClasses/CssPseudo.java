package jinjaClasses;

public class CssPseudo extends JinjaNode {
    String word;
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    @Override
    public String toString() {
        return "\nCssPseudo{" +
                "\nword='" + word + ' ' +
        "\n}";
    }
}
