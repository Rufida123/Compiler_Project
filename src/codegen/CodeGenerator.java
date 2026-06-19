package codegen;

import PyClasses.*;
import jinjaClasses.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * CODE GENERATOR - Phase 3 of Compiler
 * Converts Python AST + Jinja AST → Flask Application Code
 */
public class CodeGenerator {

    private final String outputDir;
    private final StringBuilder flaskAppCode = new StringBuilder();

    public CodeGenerator(String outputDir) {
        this.outputDir = outputDir;
    }

    public void generate(PyProgram pyProgram, Map<String, JinjaProgram> jinjaTemplates) {
        try {
            // ✅ Create directories automatically
            Files.createDirectories(Paths.get(outputDir));
            Files.createDirectories(Paths.get(outputDir, "templates"));

            initializeFlaskApp();

            // Process Python AST
            if (pyProgram != null && pyProgram.getStatements() != null) {
                for (Statement stmt : pyProgram.getStatements()) {
                    if (stmt != null) {
                        generateFromStatement(stmt);
                    }
                }
            }

            // Copy templates
            copyTemplates(jinjaTemplates);

            // Write files
            writeAppFile();
            writeConfigFile();
            writeRequirementsFile();

            System.out.println("\n✅ Flask app generated successfully!");

        } catch (IOException e) {
            System.err.println("❌ Error generating app: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeFlaskApp() {
        flaskAppCode.append("from flask import Flask, render_template, request, redirect, url_for\n");
        flaskAppCode.append("from config import Config\n");
        flaskAppCode.append("import os\n\n");
        flaskAppCode.append("app = Flask(__name__)\n");
        flaskAppCode.append("app.config.from_object(Config)\n\n");
    }

    private void generateFromStatement(Statement statement) {
        if (statement == null) return;

        // Handle different statement types
        if (statement instanceof RouteStatement) {
            RouteStatement route = (RouteStatement) statement;
            generateRoute(route);
        } else if (statement instanceof FuncDefStatement) {
            FuncDefStatement funcDef = (FuncDefStatement) statement;
            generateFunction(funcDef);
        }
    }

    private void generateRoute(RouteStatement route) {
        if (route == null) return;

        String path = "/";
        if (route.getRoutePath() != null) {
            path = route.getRoutePath().getPath();
        }

        flaskAppCode.append("@app.route('").append(path).append("')\n");

        FuncDefStatement funcDef = route.getFuncDef();
        if (funcDef != null) {
            generateFunction(funcDef);
        }
    }

    private void generateFunction(FuncDefStatement funcDef) {
        if (funcDef == null) return;

        String funcName = funcDef.getName();
        List<String> params = funcDef.getParams();

        flaskAppCode.append("def ").append(funcName).append("(");
        if (params != null && !params.isEmpty()) {
            flaskAppCode.append(String.join(", ", params));
        }
        flaskAppCode.append("):\n");

        Suite body = funcDef.getBody();
        if (body != null) {
            if (body instanceof IndentedSuite) {
                List<Statement> stmts = ((IndentedSuite) body).getStatements();
                if (stmts != null) {
                    for (Statement stmt : stmts) {
                        generateBodyStatement(stmt, "    ");
                    }
                }
            } else if (body instanceof SimpleSuite) {
                Statement stmt = ((SimpleSuite) body).getStatement();
                generateBodyStatement(stmt, "    ");
            }
        }

        flaskAppCode.append("\n");
    }

    private void generateBodyStatement(Statement statement, String indent) {
        if (statement == null) return;

        if (statement instanceof ReturnStmt) {
            flaskAppCode.append(indent).append("return ");
            List<Expression> args = ((ReturnStmt) statement).getReturnArgs();
            if (args != null && !args.isEmpty()) {
                generateExpression(args.get(0));
            }
            flaskAppCode.append("\n");

        } else if (statement instanceof ExprStmt) {
            flaskAppCode.append(indent);
            generateExpression(((ExprStmt) statement).getExpr());
            flaskAppCode.append("\n");
        }
    }

    private void generateExpression(Expression expr) {
        if (expr == null) {
            flaskAppCode.append("None");
            return;
        }

        if (expr instanceof StringExpr) {
            flaskAppCode.append("'").append(((StringExpr) expr).getValue()).append("'");
        } else if (expr instanceof IntExpr) {
            flaskAppCode.append(((IntExpr) expr).getValue());
        } else if (expr instanceof IdentifierExpr) {
            flaskAppCode.append(((IdentifierExpr) expr).getName());
        } else if (expr instanceof PostfixExpr) {
            PostfixExpr postfix = (PostfixExpr) expr;
            generateExpression(postfix.getPrimary());

            List<PostfixOp> ops = postfix.getOps();
            if (ops != null) {
                for (PostfixOp op : ops) {
                    if (op instanceof CallPostfix) {
                        flaskAppCode.append("()");
                    }
                }
            }
        }
    }

    private void copyTemplates(Map<String, JinjaProgram> jinjaTemplates) {
        if (jinjaTemplates == null || jinjaTemplates.isEmpty()) {
            return;
        }

        for (String templatePath : jinjaTemplates.keySet()) {
            try {
                String fileName = new File(templatePath).getName();
                Path targetPath = Paths.get(outputDir, "templates", fileName);

                Files.copy(
                        Paths.get(templatePath),
                        targetPath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println("✅ Copied: templates/" + fileName);

            } catch (IOException e) {
                System.err.println("⚠️  Could not copy " + templatePath);
            }
        }
    }

    private void writeAppFile() {
        flaskAppCode.append("\nif __name__ == '__main__':\n");
        flaskAppCode.append("    app.run(debug=True)\n");

        try {
            Files.createDirectories(Paths.get(outputDir));
            Files.write(
                    Paths.get(outputDir, "app.py"),
                    flaskAppCode.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
            System.out.println("✅ Generated: app.py");
        } catch (IOException e) {
            System.err.println("❌ Error writing app.py");
        }
    }

    private void writeConfigFile() {
        StringBuilder config = new StringBuilder();
        config.append("import os\n\n");
        config.append("class Config:\n");
        config.append("    SECRET_KEY = 'dev-key'\n");
        config.append("    TEMPLATES_AUTO_RELOAD = True\n");

        try {
            Files.createDirectories(Paths.get(outputDir));
            Files.write(
                    Paths.get(outputDir, "config.py"),
                    config.toString().getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
            System.out.println("✅ Generated: config.py");
        } catch (IOException e) {
            System.err.println("❌ Error writing config.py");
        }
    }

    private void writeRequirementsFile() {
        String req = "Flask==2.3.0\nJinja2==3.1.2\nWerkzeug==2.3.0\n";

        try {
            Files.createDirectories(Paths.get(outputDir));
            Files.write(
                    Paths.get(outputDir, "requirements.txt"),
                    req.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );
            System.out.println("✅ Generated: requirements.txt");
        } catch (IOException e) {
            System.err.println("❌ Error writing requirements.txt");
        }
    }
}
