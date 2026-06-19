package semantic;

/**
 * ENHANCED SEMANTIC ANALYZER
 * Adds 5+ additional semantic checks:
 * 1. Undefined function call
 * 2. Wrong number of function parameters
 * 3.Variable redefinition (duplicate definition)
 * 4. Missing return statement in function
 * 5.Invalid import
 */

import PyClasses.*;
import java.util.*;

public class EnhancedSemanticAnalyzer extends SemanticAnalyzer {

    // Track function definitions
    private Map<String, FunctionDefinition> functionDefs = new LinkedHashMap<>();
    private Set<String> importedModules = new LinkedHashSet<>();
    private Set<String> definedVariables = new LinkedHashSet<>();
    private String currentFunction = null;

    public static class FunctionDefinition {
        public String name;
        public int paramCount;
        public int lineNumber;
        public boolean hasReturn;

        public FunctionDefinition(String name, int paramCount, int lineNumber) {
            this.name = name;
            this.paramCount = paramCount;
            this.lineNumber = lineNumber;
            this.hasReturn = false;
        }
    }

    /**
     * ERROR 1: Undefined function call
     * متلاً: len_custom() لما ما تكون معرّفة
     */
    public void checkUndefinedFunction(String functionName, int lineNumber, String filePath) {
        if (!isBuiltinFunction(functionName) && !functionDefs.containsKey(functionName)) {
            report(filePath, lineNumber, functionName,
                    "Undefined function '" + functionName + "'. "
                            + "Did you forget to define it?");
        }
    }

    /**
     * ERROR 2: Wrong number of function parameters
     * متلاً: def greet(name, age): ... 
     *        greet("Ali")  ← Missing parameter!
     */
    public void checkFunctionArguments(String functionName, int argCount,
                                      int lineNumber, String filePath) {
        if (!functionDefs.containsKey(functionName)) return;

        FunctionDefinition def = functionDefs.get(functionName);
        if (def.paramCount != argCount) {
            report(filePath, lineNumber, functionName,
                    "Function '" + functionName + "' expects "
                            + def.paramCount + " argument(s) but got " + argCount + ". "
                            + "Check the function definition at line " + def.lineNumber);
        }
    }

    /**
     * ERROR 3: Variable redefinition
     * متلاً: name = "Ali"
     *        name = "Omar"  ← second definition (warning)
     */
    public void checkVariableRedefinition(String variableName, int lineNumber, String filePath) {
        if (definedVariables.contains(variableName)) {
            report(filePath, lineNumber, variableName,
                    "Warning: Variable '" + variableName + "' is being redefined. "
                            + "Did you mean to use a different name or update an existing variable?");
        } else {
            definedVariables.add(variableName);
        }
    }

    /**
     * ERROR 4: Missing return statement
     * متلاً: def get_user():
     *            print("no return!")  ← Missing return
     */
    public void checkMissingReturn(String functionName, boolean hasReturn, int lineNumber, String filePath) {
        if (!hasReturn && !isVoidFunction(functionName)) {
            report(filePath, lineNumber, functionName,
                    "Function '" + functionName + "' does not have a return statement. "
                            + "If this is intentional, it will return None");
        }
    }

    /**
     * ERROR 5: Invalid import
     * متلاً: import nonexistent_module
     *        from flask import unknownFunction
     */
    public void checkInvalidImport(String moduleName, String itemName, int lineNumber, String filePath) {
        Set<String> validModules = new HashSet<>(Arrays.asList(
                "flask", "os", "sys", "json", "datetime", "random", "math"
        ));

        if (!validModules.contains(moduleName)) {
            report(filePath, lineNumber, moduleName,
                    "Unknown module '" + moduleName + "'. "
                            + "Make sure the module name is correct. "
                            + "Valid modules: " + validModules);
        }

        if (itemName != null) {
            Map<String, Set<String>> flaskExports = new HashMap<>();
            flaskExports.put("flask", new HashSet<>(Arrays.asList(
                    "Flask", "render_template", "request", "redirect", "url_for"
            )));

            Set<String> exports = flaskExports.get(moduleName);
            if (exports != null && !exports.contains(itemName)) {
                report(filePath, lineNumber, itemName,
                        "Module '" + moduleName + "' has no attribute '" + itemName + "'. "
                                + "Available: " + exports);
            }
        }
    }

    /**
     * ✅ ERROR 6: Attribute doesn't exist on type
     * متلاً: name = "Ali"
     *        name.uppercase()  ← should be upper()
     */
    public void checkInvalidAttribute(String variableName, String attribute,
                                     String type, int lineNumber, String filePath) {
        Map<String, Set<String>> typeAttributes = new HashMap<>();
        typeAttributes.put("str", new HashSet<>(Arrays.asList(
                "upper", "lower", "strip", "split", "replace", "startswith", "endswith"
        )));
        typeAttributes.put("list", new HashSet<>(Arrays.asList(
                "append", "extend", "insert", "remove", "pop", "clear", "sort", "reverse"
        )));
        typeAttributes.put("dict", new HashSet<>(Arrays.asList(
                "keys", "values", "items", "get", "pop", "update", "clear"
        )));

        Set<String> validAttrs = typeAttributes.get(type);
        if (validAttrs != null && !validAttrs.contains(attribute)) {
            report(filePath, lineNumber, attribute,
                    "Type '" + type + "' has no method '" + attribute + "'. "
                            + "Did you mean one of: " + validAttrs);
        }
    }

    /**
     * ERROR 7: List/Dict index type error
     * متلاً: my_list = [1, 2, 3]
     *        my_list["zero"]  ← should be integer!
     */
    public void checkIndexType(String containerType, String indexType,
                              int lineNumber, String filePath) {
        if ("list".equals(containerType) && !"int".equals(indexType)) {
            report(filePath, lineNumber, null,
                    "Type Error: List indices must be integers, not '" + indexType + "'");
        }
        if ("dict".equals(containerType) && "none".equals(indexType)) {
            report(filePath, lineNumber, null,
                    "Type Error: Dict keys cannot be None");
        }
    }

    /**
     * ERROR 8: Function used as variable
     * متلاً: print = "hello"  ← redefining a built-in function!
     */
    public void checkBuiltinRedefinition(String name, int lineNumber, String filePath) {
        if (isBuiltinFunction(name)) {
            report(filePath, lineNumber, name,
                    "Error: Cannot redefine built-in function '" + name + "'. "
                            + "This will break all calls to " + name + "()");
        }
    }

    // ─────────────────────────────────────────────────────────
    // Helper methods
    // ─────────────────────────────────────────────────────────

    private boolean isBuiltinFunction(String name) {
        Set<String> builtins = new HashSet<>(Arrays.asList(
                "print", "len", "range", "int", "str", "float", "list", "dict",
                "sum", "max", "min", "sorted", "reversed", "enumerate", "zip"
        ));
        return builtins.contains(name);
    }

    private boolean isVoidFunction(String name) {
        // Functions that typically don't return a value
        Set<String> voidFuncs = new HashSet<>(Arrays.asList(
                "print", "setup", "init", "configure"
        ));
        return voidFuncs.contains(name);
    }

    public void registerFunction(String name, int paramCount, int lineNumber) {
        functionDefs.put(name, new FunctionDefinition(name, paramCount, lineNumber));
    }

    public void setCurrentFunction(String name) {
        currentFunction = name;
    }

    public String getCurrentFunction() {
        return currentFunction;
    }

    private void report(String filePath, int lineNumber, String variable, String message) {
        // Inherit from SemanticAnalyzer's reporting
        System.err.println("Semantic Error in " + filePath + " at line " + lineNumber + ": " + message);
    }
}
