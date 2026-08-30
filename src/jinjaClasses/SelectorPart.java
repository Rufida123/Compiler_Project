package jinjaClasses;

public abstract class SelectorPart extends JinjaNode {

    /**
     * True when whitespace separated this part from the previous one, i.e. this
     * is a CSS descendant combinator (".nav-links a") rather than a compound
     * selector (".nav-links.a").  Decided from the lexer's token offsets, since
     * CSS_WS is on the hidden channel.
     */
    private boolean descendant;

    public boolean isDescendant() { return descendant; }
    public void setDescendant(boolean descendant) { this.descendant = descendant; }
}
