package codegen;

import jinjaClasses.*;
import java.util.*;

/** Renders the supported Jinja AST to static HTML.  It deliberately consumes
 * AST nodes, never the original template source. */
public class JinjaRenderer {
    private final Map<String, JinjaProgram> templates;
    private final Map<String, String> routes;

    public JinjaRenderer(Map<String, JinjaProgram> templates, Map<String, String> routes) {
        this.templates = templates;
        this.routes = routes;
    }

    public String render(String name, Map<String, Object> context) {
        JinjaProgram page = templates.get(name);
        if (page == null) throw new IllegalArgumentException("Template not found in AST: " + name);
        Map<String, List<DocumentElement>> blocks = blocksOf(page.getHtmlElements());
        JinjaProgram base = extendedTemplate(page);
        return renderElements(base == null ? page.getHtmlElements() : base.getHtmlElements(),
                new HashMap<>(context), blocks, 0, null).text;
    }

    private JinjaProgram extendedTemplate(JinjaProgram page) {
        for (DocumentElement e : page.getHtmlElements()) if (header(e) instanceof Extends ex) {
            String n = unquote(ex.getString());
            return templates.get(n);
        }
        return null;
    }
    private Map<String,List<DocumentElement>> blocksOf(List<DocumentElement> es) {
        Map<String,List<DocumentElement>> result = new HashMap<>();
        for (int i=0;i<es.size();i++) if (header(es.get(i)) instanceof BlockStart b) {
            Slice s = renderElements(es, new HashMap<>(), Map.of(), i+1, BlockEnd.class);
            result.put(b.getIdentifier(), es.subList(i+1, s.next)); i = s.next;
        }
        return result;
    }
    private record Slice(String text, int next) {}
    private Slice renderElements(List<DocumentElement> es, Map<String,Object> c,
                                 Map<String,List<DocumentElement>> overrides, int from, Class<?> stop) {
        StringBuilder out = new StringBuilder();
        for (int i=from;i<es.size();i++) {
            DocumentElement e=es.get(i);
            Object h=header(e); if (stop != null && stop.isInstance(h)) return new Slice(out.toString(), i);
            if (e instanceof Doctype d) out.append(d.getText());
            else if (e instanceof HtmlText t) out.append(t.getText());
            else if (e instanceof PrintBlock p) out.append(string(value(p.getJinjaExpression(), c)));
            else if (e instanceof StyleTag style) out.append(css(style));
            else if (e instanceof PairedTag t) { out.append('<').append(t.getTagName()); attrs(out,t.getAttributes(),c); out.append('>'); out.append(renderElements(t.getChildren(),c,overrides,0,null).text); out.append("</").append(t.getTagName()).append('>'); }
            else if (e instanceof SelfClosingTag t) { out.append('<').append(t.getTagName()); attrs(out,t.getAttributes(),c); out.append(" />"); }
            else if (h instanceof For f) {
                Slice body=renderElements(es,c,overrides,i+1,EndFor.class); Object xs=value(f.getExpression(),c);
                if (xs instanceof Iterable<?> it) for(Object x:it) { Map<String,Object> child=new HashMap<>(c); child.put(f.getIdentifier(),x); out.append(renderElements(es,child,overrides,i+1,EndFor.class).text); }
                i=body.next;
            } else if (h instanceof If iff) {
                Slice yes=renderElements(es,c,overrides,i+1,Else.class); int end=yes.next;
                Slice no = end<es.size() && header(es.get(end)) instanceof Else ? renderElements(es,c,overrides,end+1,EndIf.class) : yes;
                if (truth(value(iff.getExpression(),c))) out.append(yes.text); else if (end<es.size() && header(es.get(end)) instanceof Else) out.append(no.text);
                i=no.next;
            } else if (h instanceof BlockStart b) {
                Slice own=renderElements(es,c,overrides,i+1,BlockEnd.class); List<DocumentElement> use=overrides.getOrDefault(b.getIdentifier(),es.subList(i+1,own.next));
                out.append(renderElements(use,c,overrides,0,null).text); i=own.next;
            } else if (h instanceof Extends || h instanceof Else || h instanceof EndFor || h instanceof EndIf || h instanceof BlockEnd) { }
        }
        return new Slice(out.toString(), es.size());
    }
    private static Object header(DocumentElement e) { return e instanceof ControlBlock c ? c.getJinjaStatementHeader() : e; }
    private void attrs(StringBuilder out,List<HtmlAttribute> as,Map<String,Object> c) {
        for(HtmlAttribute a:as) if(a instanceof NormalAttribute n) { out.append(' ').append(n.getKey()); if(n.getValue()!=null) { out.append("=\""); if(n.getValue() instanceof PlainValue p) out.append(p.getText()); else if(n.getValue() instanceof JinjaValueExpr j && j.getJinjaBlock() instanceof PrintBlock pb) out.append(string(value(pb.getJinjaExpression(),c))); out.append('"'); } }
    }
    private String css(StyleTag style) {
        StringBuilder b = new StringBuilder("<style>");
        for (CssRule r : style.getCssRules()) {
            List<CssSelector> selectors = r.getSelectorList().getSelectors();
            for (int i = 0; i < selectors.size(); i++) {
                if (i > 0) b.append(", ");
                CssSelector s = selectors.get(i);
                for (SelectorPart p : s.getParts()) {
                    if (p.isDescendant()) b.append(' ');
                    if (p instanceof ClassPart x) b.append('.').append(x.getWord());
                    else if (p instanceof TagPart x) b.append(x.getWord());
                }
                if (s.getPseudo() != null) b.append(':').append(s.getPseudo().getWord());
            }
            b.append(" {");
            for (CssProperty p : r.getProperties()) {
                b.append(p.getWord()).append(": ").append(values(p.getValueList())).append("; ");
            }
            b.append('}');
        }
        return b.append("</style>").toString();
    }

    /** Joins a value list, restoring the source separators. */
    private String values(ValueList list) {
        StringBuilder b = new StringBuilder();
        List<CssValue> vs = list.getValues();
        for (int i = 0; i < vs.size(); i++) {
            if (i > 0) b.append(vs.get(i).isCommaBefore() ? ", " : " ");
            b.append(cssValue(vs.get(i)));
        }
        return b.toString();
    }

    private String cssValue(CssValue v){if(v instanceof StringValue x)return x.getString();if(v instanceof NumberValue x)return x.getNumber();if(v instanceof ColorValue x)return x.getColor();if(v instanceof WordValue x)return x.getWord();if(v instanceof FunctionValue x){return x.getFunction().getWord()+"("+values(x.getFunction().getValueList())+")";}return "";}
    private Object value(JinjaExpression e,Map<String,Object> c) { Object v=primary(e.getPrimary(),c); for(JinjaFilter f:e.getFilters()) v=filter(f,v,c); return v; }
    private Object primary(JinjaPrimary p,Map<String,Object> c) {
        if(p instanceof StringLiteral s) return unquote(s.getString()); if(p instanceof NumberLiteral n) return Double.valueOf(n.getNumber()); if(p instanceof TrueLiteral) return true; if(p instanceof FalseLiteral) return false; if(p instanceof NoneLiteral) return null;
        if(p instanceof JinjaParenthesizedExpr x) return value(x.getExpression(),c);
        if(p instanceof AccessExpr x) return chain(x.getChain(),c);
        if(p instanceof JinjaBinaryExpr b) return binary(b.getOperator(),primary(b.getLeft(),c),primary(b.getRight(),c));
        if(p instanceof FunctionCall f) return call(f,c);
        return null;
    }
    private Object chain(JinjaIdentifierChain q,Map<String,Object> c) { Object v=c.get(q.getIdentifier()); for(Access a:q.getAccesses()) { if(a instanceof DotAccess d) v=member(v,d.getIdentifier()); else if(a instanceof IndexAccess x) v=member(v,value(x.getExpression(),c)); } return v; }
    private Object member(Object v,Object key) { if(v instanceof Map<?,?> m) return m.get(String.valueOf(key)); if(v instanceof List<?> l && key instanceof Number n) return l.get(n.intValue()); return null; }
    private Object binary(String op,Object a,Object b) { if("~".equals(op)) return string(a)+string(b); if("==".equals(op)) return Objects.equals(a,b); if("!=".equals(op)) return !Objects.equals(a,b); if(a instanceof Number x&&b instanceof Number y) { double l=x.doubleValue(),r=y.doubleValue(); return switch(op){case "+"->l+r;case "-"->l-r;case "*"->l*r;case "/"->l/r;case "<"->l<r;case ">"->l>r;case "<="->l<=r;case ">="->l>=r;default->null;}; } return null; }
    private Object call(FunctionCall f,Map<String,Object> c) { if("url_for".equals(f.getIdentifier())) { String endpoint=""; Map<String,Object> args=args(f.getCallArgs(),c); Object p=args.remove("$0"); if(p!=null) endpoint=String.valueOf(p); String path=routes.getOrDefault(endpoint,"#"+endpoint); for(var e:args.entrySet()) path=path.replace("<int:"+e.getKey()+">",string(e.getValue())).replace("<"+e.getKey()+">",string(e.getValue())); return path; } return null; }
    private Map<String,Object> args(JinjaCallArgs a,Map<String,Object> c) { Map<String,Object> r=new HashMap<>(); if(a instanceof CallMixedArgs m) { int i=0; for(JinjaArg x:m.getPosArgs()) r.put("$"+(i++),value(x.getExpression(),c)); for(JinjaKwArg x:m.getKwArgs()) r.put(x.getIdentifier(),value(x.getExpression(),c)); } else if(a instanceof CallKwArgs k) for(JinjaKwArg x:k.getKwArgs()) r.put(x.getIdentifier(),value(x.getExpression(),c)); return r; }
    private Object filter(JinjaFilter f,Object v,Map<String,Object> c) { String n=f.getName(); if("upper".equals(n))return string(v).toUpperCase(); if("lower".equals(n))return string(v).toLowerCase(); if("format".equals(n)) { Object fmt=args(f.getArgs(),c).get("$0"); try{return String.format(String.valueOf(v),fmt);}catch(Exception ignored){return v;} } return v; }
    private static boolean truth(Object x){return x instanceof Boolean b?b:x!=null && !(x instanceof Number n&&n.doubleValue()==0) && !"".equals(x);}
    private static String string(Object x){ if(x==null)return ""; if(x instanceof Number n && n.doubleValue()==Math.rint(n.doubleValue()))return String.valueOf(n.longValue()); return String.valueOf(x); }
    private static String unquote(String x){return x!=null&&x.length()>=2&&((x.startsWith("\"")&&x.endsWith("\""))||(x.startsWith("'")&&x.endsWith("'")))?x.substring(1,x.length()-1):x;}
}
