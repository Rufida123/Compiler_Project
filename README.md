# Compiler_Project

A university compilers project: a compiler for a Flask + Jinja2 subset, written in Java with ANTLR 4.13.2.

It lexes and parses Python (Flask subset), Jinja2, HTML and CSS; builds two ASTs; runs semantic
analysis and type checking on both sides; and generates static HTML from the Jinja AST using
context data taken from the Python AST.

```
app.py + data/products.json            templates/*.html
        |                                     |
   pyLexer / pyParser                JinjaLexer / JinjaParser
        |                                     |
   Python Parse Tree                   Jinja Parse Tree
        |                                     |
     Visitor.java                        Visitor.java
        |                                     |
    Python AST  ---> Semantic Analysis <--- Jinja AST
        |            + Type Checking          |
   AstContextExtractor                        |
        |                                     |
   Context Data  ------> JinjaRenderer <------+
                              |
                        output/*.html
```

## Requirements

* JDK 17 or newer (`javac -version`)
* Python 3.12 (only needed for the executor fallback and for running the Flask demo)
* `dependencies/antlr-4.13.2-complete.jar` — already in the repository

## One-time setup (Windows / PowerShell)

```powershell
# 1. Python environment (not committed; create it once per machine)
py -3.12 -m venv .venv
.venv\Scripts\python.exe -m pip install -r generated_app\requirements.txt

# 2. Compile the compiler
New-Item -ItemType Directory -Force .build\classes | Out-Null
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp dependencies\antlr-4.13.2-complete.jar -d .build\classes $sources

# 3. Compile the integration tests
javac -encoding UTF-8 -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" -d .build\classes tests\*.java
```

The compiler finds a Python interpreter in this order, and reports every option it tried if none works:

1. `.venv\Scripts\python.exe` (or `.venv/bin/python`) in the project root
2. the `COMPILER_PYTHON` environment variable
3. `py -3.12`
4. `python3`

## Run the compiler

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main
start output\index.html
```

With explicit paths:

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main `
     app.py templates output compiler_output
```

Outputs:

| Path | Contents |
| --- | --- |
| `output\index.html`, `add_product.html`, `product_detail.html`, `test.html` | generated from the Jinja AST |
| `output\app.py`, `output\style.css`, `output\script.js`, `output\data\` | copied byte-for-byte |
| `compiler_output\ast_python.json`, `ast_jinja.json` | recursive JSON trees; every node has `node`, `line`, `column` |
| `compiler_output\semantic_report.txt` | semantic + type diagnostics, or "No semantic/type errors." |
| `compiler_output\generation_log.txt` | which AST nodes produced each template's context (`ast` or `executor`) |

If parsing fails, or if any non-`Warning:` diagnostic is reported, generation is skipped and the
previous `output/` is left untouched.

## Print the ASTs and the symbol table

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main --print-ast
```

Prints the whole Python tree, every Jinja tree, and the symbol table for each file to the console,
and writes the same content to:

```
compiler_output\ast_python.txt
compiler_output\ast_jinja.txt
compiler_output\symbol_table.txt
```

The symbol table lists every scope that was opened — global, each `func_*`, each `suite_*`, and each
Jinja `block_*` / `for_loop` — with each entry's name, kind and declaration line.

## Run the Flask demo

```powershell
.venv\Scripts\python.exe app.py
```

Then open <http://127.0.0.1:5000/>. The app supports listing, adding, viewing and deleting products;
adds and deletes are written straight back to `data\products.json`.

## Regeneration cycle

Static pages in `output/` are snapshots. After changing data or templates, re-run the compiler:

```powershell
# 1. change data through the UI (add or delete a product), or edit data\products.json / templates\
# 2. stop Flask with Ctrl+C, then regenerate
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main
# 3. refresh output\index.html in the browser (Ctrl+F5)
```

Running the compiler never modifies `data\products.json` — route probing during context extraction
is side-effect free.

## Run the tests

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" AstContextExtractorTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" AstMetadataTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" JinjaValidationIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" EnhancedSemanticReportIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" PersistentProductsIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" CodeGeneratorIntegrationTest
py -3.12 -m py_compile app.py output\app.py
```

Every test writes into its own temp directory, so the suite never touches `output/` or
`compiler_output/`.

## Regenerate the ANTLR sources

`grammars/*.g4` are authoritative. Never hand-edit anything under `src/pyAntlr` or `src/jinjaAntlr`.

```powershell
Set-Location grammars
java -jar ..\dependencies\antlr-4.13.2-complete.jar -encoding UTF-8 -visitor -listener -o ..\src\jinjaAntlr JinjaLexer.g4 JinjaParser.g4
java -jar ..\dependencies\antlr-4.13.2-complete.jar -encoding UTF-8 -visitor -listener -o ..\src\pyAntlr   pyLexer.g4  pyParser.g4
Set-Location ..
```

## Adding a language feature

```
authoritative grammar -> regenerate ANTLR -> AST node -> Visitor
    -> semantic / type validation -> renderer -> integration test
```

## Further reading

* `COMPILER_FLOW.md` — architecture and execution path in detail
* `PROJECT_AUDIT.md` — requirement-by-requirement audit with file/line evidence
