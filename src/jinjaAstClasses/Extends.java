package jinjaAstClasses;

//{% extends "base.html" %}
public class Extends extends JinjaStatementHeader {
    String string;

    public String getString() {
        return string;
    }
    public void setString(String string) {
        this.string = string;
    }
    @Override
    public String toString() {
        return "\nExtends{" +
                "\nstring='" + string + '\'' +
                "\n}";
    }
}
