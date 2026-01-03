package PyVisitor;

import PyAstClasses.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class AstPrettyPrinter {

    private final int maxItems = 4;
    private final int maxStrLen = 70;
    private final String IND = "  ";

    /* =========================
       STRICT PRINT (Doctor Mode)
       ========================= */

    public String printStrict(Program program) {
        StringBuilder sb = new StringBuilder();
        if (program == null) return "";

        // root
        sb.append(nodeId(program)).append("\n");

        if (program.getStatements() != null) {
            for (Statement st : program.getStatements()) {
                printNode(sb, st, 1);
            }
        }
        return sb.toString();
    }

    public String formatExpr(PyAstClasses.Expression e) {
        return fmtExpr(e);
    }

    private String fmtExpr(Expression e) {
        e = unwrap(e);
        if (e == null) return "null";

        if (e instanceof IdentifierExpr x) return x.getName();
        if (e instanceof IntExpr x) return String.valueOf(x.getValue());
        if (e instanceof FloatExpr x) return String.valueOf(x.getValue());
        if (e instanceof TrueExpr) return "True";
        if (e instanceof FalseExpr) return "False";
        if (e instanceof NoneExpr) return "None";

        if (e instanceof StringExpr x) return quote(trim(x.getValue()));
        if (e instanceof HtmlFileExpr x) return quote(trim(x.getValue()));

        if (e instanceof ParenExpr x) return "(" + fmtExpr(x.getInner()) + ")";

        if (e instanceof ListLiteralExpr x) return fmtListLiteral(x.getListLiteral());
        if (e instanceof DictLiteralExpr x) return fmtDictLiteral(x.getDictLiteral());

        if (e instanceof GeneratorPrimaryExpr x) return fmtGen(x.getGeneratorExpr());

        if (e instanceof UnaryMinusExpr x) return "-" + fmtExpr(x.getExpr());

        if (e instanceof UnaryPostfixExpr x) return fmtPostfix(x.getExpr());


        if (e.getClass().getSimpleName().equals("BinaryExpr")) {

            return e.toString();
        }

        // CondExpr
        if (e instanceof CondExpr x && x.getCondition() != null) {
            return fmtExpr(x.getThenExpr()) + " if " + fmtExpr(x.getCondition()) + " else " + fmtExpr(x.getElseExpr());
        }

        return e.toString();
    }

    private Expression unwrap(Expression e) {
        // CondExpr
        while (e instanceof CondExpr c && c.getCondition() == null) {
            e = c.getThenExpr();
        }
        // OrPass wrapper
        while (e instanceof OrPassExpr o) {
            e = o.getInner();
            while (e instanceof CondExpr c && c.getCondition() == null) e = c.getThenExpr();
        }
        return e;
    }

    private String fmtPostfix(PostfixExpr p) {
        if (p == null) return "<?>";

        StringBuilder sb = new StringBuilder();
        sb.append(fmtExpr(p.getPrimary()));

        if (p.getOps() != null) {
            for (PostfixOp op : p.getOps()) {
                if (op instanceof AttrPostfix a) {
                    sb.append(".").append(a.getName());
                } else if (op instanceof SubscriptPostfix s) {
                    sb.append("[").append(fmtExpr(s.getIndex())).append("]");
                } else if (op instanceof CallPostfix c) {
                    sb.append("(").append(fmtArgs(c.getArgList())).append(")");
                } else {
                    sb.append(op.toString());
                }
            }
        }
        return sb.toString();
    }

    private String fmtArgs(ArgList argList) {
        if (argList == null || argList.getArgs() == null || argList.getArgs().isEmpty()) return "";
        return argList.getArgs().stream().map(a -> {
            if (a.getName() == null) return fmtExpr(a.getValue());
            return a.getName() + "=" + fmtExpr(a.getValue());
        }).collect(Collectors.joining(", "));
    }

    private String fmtListLiteral(Object listLiteralObj) {
        if (listLiteralObj == null) return "[]";
        try {
            ListLiteral ll = (ListLiteral) listLiteralObj;
            List<Expression> els = ll.getElements();
            if (els == null) return "[]";
            String inside = els.stream().limit(maxItems).map(this::fmtExpr).collect(Collectors.joining(", "));
            if (els.size() > maxItems) inside += ", ... +" + (els.size() - maxItems);
            return "[" + inside + "]";
        } catch (Exception ex) {
            return listLiteralObj.toString();
        }
    }

    private String fmtDictLiteral(DictLiteral d) {
        if (d == null || d.getEntries() == null) return "{}";
        var entries = d.getEntries();
        String inside = entries.stream().limit(maxItems).map(e ->
                fmtExpr(e.getKey()) + ": " + fmtExpr(e.getValue())
        ).collect(Collectors.joining(", "));
        if (entries.size() > maxItems) inside += ", ... +" + (entries.size() - maxItems);
        return "{" + inside + "}";
    }

    private String fmtGen(GeneratorExpr g) {
        if (g == null) return "<gen>";
        String s = g.getYieldName() + " for " + g.getLoopVarName() + " in " + fmtExpr(g.getIterable());
        if (g.getFilter() != null) s += " if " + fmtExpr(g.getFilter());
        return "(" + s + ")";
    }

    /* -------------------- Helpers -------------------- */

    private String fmtRoute(RouteStatement r) {
        String path = r.getRoutePath() != null ? r.getRoutePath().getPath() : "?";
        if (r.getRouteParams() == null || r.getRouteParams().getMethodsList() == null) {
            return quote(path);
        }
        String methods = fmtListLiteral(r.getRouteParams().getMethodsList());
        return quote(path) + " methods=" + methods;
    }

    private String joinDots(DottedName dn) {
        if (dn == null || dn.getParts() == null) return "?";
        return String.join(".", dn.getParts());
    }

    private String joinImportItems(ImportList list) {
        if (list == null || list.getItems() == null) return "";
        return list.getItems().stream()
                .map(ImportItem::getName)
                .collect(Collectors.joining(", "));
    }

    private String quote(String s) { return "'" + s + "'"; }

    private String trim(String s) {
        if (s == null) return "";
        if (s.length() <= maxStrLen) return s;
        return s.substring(0, maxStrLen) + "...";
    }
    /* =========================
       Core node printing
       ========================= */

    private void printNode(StringBuilder sb, Object node, int depth) {
        if (node == null) return;

        indent(sb, depth).append(nodeId(node)).append("\n");
        printChildren(sb, node, depth);
    }

    private void printChildren(StringBuilder sb, Object node, int depth) {
        int d = depth + 1;

        /* ---------- Statements ---------- */

        if (node instanceof ImportStmt s) {
            printNode(sb, s.getDottedName(), d);
            printNode(sb, s.getImportList(), d);
            return;
        }

        if (node instanceof AssignStmt s) {
            // target name (String) => temp IdentifierExpr node (بدون طباعة الاسم)
            printNode(sb, tempId(s.getName(), getLine(s)), d);
            // value expression as tree
            printNode(sb, s.getValue(), d);
            return;
        }

        if (node instanceof RouteStatement s) {
            printNode(sb, s.getRoutePath(), d);
            if (s.getRouteParams() != null) printNode(sb, s.getRouteParams(), d);
            printNode(sb, s.getFuncDef(), d);
            return;
        }

        if (node instanceof FuncDefStatement s) {
            // params are strings => temp IdentifierExpr nodes (بدون طباعة الاسم)
            if (s.getParams() != null) {
                for (String p : s.getParams()) {
                    printNode(sb, tempId(p, getLine(s)), d);
                }
            }
            printNode(sb, s.getBody(), d);
            return;
        }

        if (node instanceof IfStatement s) {
            printNode(sb, s.getCondition(), d);
            printNode(sb, s.getThenSuite(), d);
            if (s.getElseSuite() != null) printNode(sb, s.getElseSuite(), d);
            return;
        }

        if (node instanceof ForStatement s) {
            // loop var name => temp IdentifierExpr
            printNode(sb, tempId(s.getVarName(), getLine(s)), d);
            printNode(sb, s.getExpression(), d);
            printNode(sb, s.getForBlock(), d);
            return;
        }

        if (node instanceof ReturnStmt s) {
            if (s.getReturnArgs() != null) {
                for (Expression e : s.getReturnArgs()) printNode(sb, e, d);
            }
            return;
        }

        if (node instanceof ExprStmt s) {
            printNode(sb, s.getExpr(), d);
            return;
        }

        /* ---------- Suites ---------- */

        if (node instanceof IndentedSuite s) {
            if (s.getStatements() != null) {
                for (Statement st : s.getStatements()) printNode(sb, st, d);
            }
            return;
        }

        if (node instanceof SimpleSuite s) {
            printNode(sb, s.getStatement(), d);
            return;
        }

        /* ---------- Import small nodes ---------- */

        if (node instanceof DottedName dn) {
            // parts are strings; الدكتور بدو "كلاسات"، فبنطبعها كـ IdentifierExpr (بدون قيم)
            if (dn.getParts() != null) {
                for (String part : dn.getParts()) {
                    printNode(sb, tempId(part, getLine(dn)), d);
                }
            }
            return;
        }
        if (node instanceof ImportList il) {
            if (il.getItems() != null) {
                for (ImportItem it : il.getItems()) printNode(sb, it, d);
            }
            return;
        }

        if (node instanceof ImportItem it) {
            // name/alias strings => temp IdentifierExpr (اختياري)
            printNode(sb, tempId(it.getName(), getLine(it)), d);
            if (it.getAlias() != null) printNode(sb, tempId(it.getAlias(), getLine(it)), d);
            return;
        }

        /* ---------- Route small nodes ---------- */

        if (node instanceof RouteParams rp) {
            printNode(sb, rp.getMethodsList(), d);
            return;
        }

        // RoutePath is leaf (string path ما منطبعه)

        /* ---------- Expressions ---------- */

        if (node instanceof CondExpr e) {
            printNode(sb, e.getThenExpr(), d);
            if (e.getCondition() != null) {
                printNode(sb, e.getCondition(), d);
                printNode(sb, e.getElseExpr(), d);
            }
            return;
        }

        if (node instanceof OrPassExpr e) {
            printNode(sb, e.getInner(), d);
            return;
        }

        if (node instanceof ParenExpr e) {
            printNode(sb, e.getInner(), d);
            return;
        }

        if (node instanceof UnaryMinusExpr e) {
            printNode(sb, e.getExpr(), d);
            return;
        }

        if (node instanceof UnaryPostfixExpr e) {
            printNode(sb, e.getExpr(), d);
            return;
        }

        if (node instanceof PostfixExpr p) {
            printNode(sb, p.getPrimary(), d);
            if (p.getOps() != null) {
                for (PostfixOp op : p.getOps()) printNode(sb, op, d);
            }
            return;
        }

        if (node instanceof AttrPostfix) {
            // leaf (attribute name ما منطبعه)
            return;
        }

        if (node instanceof SubscriptPostfix s) {
            printNode(sb, s.getIndex(), d);
            return;
        }

        if (node instanceof CallPostfix c) {
            printNode(sb, c.getArgList(), d);
            return;
        }

        if (node instanceof ArgList a) {
            if (a.getArgs() != null) {
                for (Arg arg : a.getArgs()) printNode(sb, arg, d);
            }
            return;
        }

        if (node instanceof Arg a) {
            // named arg => temp IdentifierExpr (اختياري)
            if (a.getName() != null) printNode(sb, tempId(a.getName(), getLine(a)), d);
            printNode(sb, a.getValue(), d);
            return;
        }

        if (node instanceof ListLiteralExpr le) {
            printNode(sb, le.getListLiteral(), d);
            return;
        }

        if (node instanceof ListLiteral ll) {
            if (ll.getElements() != null) {
                int n = Math.min(ll.getElements().size(), maxItems);
                for (int i = 0; i < n; i++) printNode(sb, ll.getElements().get(i), d);
            }
            return;
        }

        if (node instanceof DictLiteralExpr de) {
            printNode(sb, de.getDictLiteral(), d);
            return;
        }

        if (node instanceof DictLiteral dl) {
            if (dl.getEntries() != null) {
                int n = Math.min(dl.getEntries().size(), maxItems);
                for (int i = 0; i < n; i++) printNode(sb, dl.getEntries().get(i), d);
            }
            return;
        }

        if (node instanceof DictEntry e) {
            printNode(sb, e.getKey(), d);
            printNode(sb, e.getValue(), d);
            return;
        }

        if (node instanceof GeneratorPrimaryExpr g) {
            printNode(sb, g.getGeneratorExpr(), d);
            return;
        }

        if (node instanceof GeneratorExpr g) {// yield/loop vars are strings => temp IdentifierExpr
            printNode(sb, tempId(g.getYieldName(), getLine(g)), d);
            printNode(sb, tempId(g.getLoopVarName(), getLine(g)), d);
            printNode(sb, g.getIterable(), d);
            if (g.getFilter() != null) printNode(sb, g.getFilter(), d);
            return;
        }

        // Leaf expressions: IdentifierExpr / IntExpr / FloatExpr / StringExpr / HtmlFileExpr / TrueExpr / FalseExpr / NoneExpr
        // => no children, so nothing to do.
    }

    /* =========================
       Helpers
       ========================= */

    private IdentifierExpr tempId(String name, int line) {
        IdentifierExpr id = new IdentifierExpr();
        // نخزن الاسم داخلياً إذا احتجته لاحقاً، بس الطباعة ما رح تعرضه
        id.setName(name);
        id.setLineNumber(line);
        return id;
    }

    private String nodeId(Object n) {
        if (n == null) return "null";
        String cls = n.getClass().getSimpleName();
        if (cls == null || cls.isBlank()) cls = "Node";
        int ln = getLine(n);
        return (ln > 0) ? (cls + " @ " + ln) : cls;
    }

    private int getLine(Object n) {
        if (n == null) return -1;
        try {
            Method m = n.getClass().getMethod("getLineNumber");
            Object v = m.invoke(n);
            if (v instanceof Number num) return num.intValue();
        } catch (Exception ignore) {}
        return -1;
    }

    private StringBuilder indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) sb.append(IND);
        return sb;
    }
}