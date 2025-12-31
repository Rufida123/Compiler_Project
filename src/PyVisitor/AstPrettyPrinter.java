package PyVisitor;

import PyAstClasses.*;

import java.util.List;
import java.util.stream.Collectors;

public class AstPrettyPrinter {

    private final int maxItems = 4;     // max num showen frpm list/dict
    private final int maxStrLen = 70;   //cut long text
    private final String IND = "  ";

    public String print(Program program) {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeId(program)).append("\n");
        //  statements
        for (Statement st : program.getStatements()) {
            printStmt(sb, st, 1);
        }
        return sb.toString();
    }

    /* -------------------- Statements -------------------- */

    private void printStmt(StringBuilder sb, Statement st, int depth) {
        if (st == null) return;

        if (st instanceof ImportStmt s) {
            indent(sb, depth).append(nodeId(s)).append(" import from ")
                    .append(joinDots(s.getDottedName()))
                    .append(": ")
                    .append(joinImportItems(s.getImportList()))
                    .append("\n");
            return;
        }

        if (st instanceof AssignStmt s) {
            indent(sb, depth).append(nodeId(s)).append(" ")
                    .append(s.getName()).append(" = ")
                    .append(fmtExpr(s.getValue()))
                    .append("\n");
            return;
        }

        if (st instanceof RouteStatement s) {
            indent(sb, depth).append(nodeId(s))
                    .append(" route ").append(fmtRoute(s))
                    .append("\n");

            printFunc(sb, s.getFuncDef(), depth + 1);
            return;
        }

        if (st instanceof FuncDefStatement s) {
            printFunc(sb, s, depth);
            return;
        }

        if (st instanceof IfStatement s) {
            indent(sb, depth).append(nodeId(s)).append(" if ")
                    .append(fmtExpr(s.getCondition()))
                    .append("\n");
            printSuite(sb, s.getThenSuite(), depth + 1);
            if (s.getElseSuite() != null) {
                indent(sb, depth).append("else\n");
                printSuite(sb, s.getElseSuite(), depth + 1);
            }
            return;
        }

        if (st instanceof ForStatement s) {
            indent(sb, depth).append(nodeId(s)).append(" for ")
                    .append(s.getVarName()).append(" in ")
                    .append(fmtExpr(s.getExpression()))
                    .append("\n");
            printSuite(sb, s.getForBlock(), depth + 1);
            return;
        }

        if (st instanceof ReturnStmt s) {
            indent(sb, depth).append(nodeId(s)).append(" return");
            if (s.getReturnArgs() != null && !s.getReturnArgs().isEmpty()) {
                sb.append(" ")
                        .append(s.getReturnArgs().stream().map(this::fmtExpr).collect(Collectors.joining(", ")));
            }
            sb.append("\n");
            return;
        }

        if (st instanceof ExprStmt s) {
            indent(sb, depth).append(nodeId(s)).append(" ")
                    .append(fmtExpr(s.getExpr()))
                    .append("\n");
            return;
        }

        // fallback
        indent(sb, depth).append(nodeId(st)).append(" ").append(st.getClass().getSimpleName()).append("\n");
    }

    private void printFunc(StringBuilder sb, FuncDefStatement f, int depth) {
        indent(sb, depth).append(nodeId(f)).append(" def ")
                .append(f.getName()).append("(")
                .append(f.getParams() == null ? "" : String.join(", ", f.getParams()))
                .append(")\n");
        printSuite(sb, f.getBody(), depth + 1);
    }

    private void printSuite(StringBuilder sb, Suite suite, int depth) {
        if (suite == null) return;

        if (suite instanceof IndentedSuite s) {
            for (Statement st : s.getStatements()) {
                printStmt(sb, st, depth);
            }
            return;
        }

        if (suite instanceof SimpleSuite s) {
            printStmt(sb, s.getStatement(), depth);
            return;
        }

        indent(sb, depth).append(nodeId(suite)).append(" ").append(suite.getClass().getSimpleName()).append("\n");
    }

    /* -------------------- Expressions (Compact) -------------------- */
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

    private String nodeId(Program n) {
        if (n == null) return "null";
        int ln = n.getLineNumber();

        String cls = n.getClass().getSimpleName();
        if (cls == null || cls.isBlank()) cls = "Program";

        return (ln > 0) ? (cls + " @ " + ln) : cls;
    }

    private StringBuilder indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) sb.append(IND);
        return sb;
    }

    private String quote(String s) { return "'" + s + "'"; }

    private String trim(String s) {
        if (s == null) return "";
        if (s.length() <= maxStrLen) return s;
        return s.substring(0, maxStrLen) + "...";
    }
}
