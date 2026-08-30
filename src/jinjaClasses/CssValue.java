package jinjaClasses;

public abstract class CssValue extends JinjaNode {

    /** True when a comma separated this value from the previous one. */
    private boolean commaBefore;

    public boolean isCommaBefore() { return commaBefore; }
    public void setCommaBefore(boolean commaBefore) { this.commaBefore = commaBefore; }

    @Override
    public abstract String toString();
}
