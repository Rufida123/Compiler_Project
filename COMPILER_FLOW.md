# مرجع مشروع المترجمات: البنية ومسار التنفيذ

هذا الملف يصف الحالة الفعلية الحالية للمشروع: ما الذي يدخله المستخدم، كيف تُبنى أشجار AST، كيف تتم الفحوص الدلالية والنوعية، كيف تُستخرج بيانات Python، وكيف تتحول قوالب Jinja إلى HTML ثابت.

المشروع يدعم subset محدداً من Python/Flask وJinja، وليس كل اللغتين.

## 1. الهدف النهائي

المسار الأساسي المنفذ هو:

```text
app.py + data/products.json
        ↓
Python Lexer / Parser
        ↓
Python Parse Tree
        ↓
Visitor.java
        ↓
Python AST
        ↓
SemanticAnalyzer + TypeChecker + EnhancedSemanticAnalyzer
        ↓
AstContextExtractor   (تنفيذ Python 3.12 fallback فقط)
        ↓
Context Data

templates/*.html أو templates/*.jinja
        ↓
Jinja Lexer / Parser
        ↓
Jinja Parse Tree
        ↓
Visitor.java
        ↓
Jinja AST
        ↓
SemanticAnalyzer + TypeChecker
        ↓
JinjaRenderer
        ↓
HTML ثابت في output/
```

إذا وُجد parsing error أو semantic/type error، لا تبدأ مرحلة توليد HTML.

## 2. ملفات الإدخال الافتراضية

نقطة التشغيل هي:

```text
src/app/Main.java
```

وعند تشغيلها دون arguments تستخدم:

```text
app.py                 برنامج Flask
data/products.json     مخزن المنتجات الدائم
templates/             قوالب Jinja وHTML
output/                الصفحات النهائية
compiler_output/       AST والتقارير
```

يدعم ماسح القوالب الامتدادين:

```text
.html
.jinja
```

يمكن تمرير مسارات مخصصة بهذا الترتيب:

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main input.py templates_dir output_dir compiler_output_dir
```

## 3. ملفات Grammar وANTLR

ملفات grammar المصدرية المعتمدة هي:

```text
grammars/pyLexer.g4
grammars/pyParser.g4
grammars/JinjaLexer.g4
grammars/JinjaParser.g4
```

أما الملفات الموجودة في:

```text
src/pyAntlr/
src/jinjaAntlr/
```

فهي ملفات Java مولدة من ANTLR ولا يجب تعديلها يدوياً.

أمر إعادة توليد Jinja ANTLR هو:

```powershell
Set-Location grammars
java -jar ..\dependencies\antlr-4.13.2-complete.jar -visitor -listener -o ..\src\jinjaAntlr JinjaLexer.g4 JinjaParser.g4
```

## 4. مرحلة Parsing

`Main.java` ينشئ:

- `pyLexer` و`pyParser` لملف Python.
- `JinjaLexer` و`JinjaParser` لكل قالب.

يوجد ANTLR error listener مخصص يجمع أخطاء lexer/parser ويعرض:

```text
filename:line:column message
```

عند وجود خطأ parsing:

- لا تُستخدم parse tree جزئية بصمت.
- لا يُبنى AST ناقص.
- لا يعمل Semantic Analysis أو Code Generation.
- يبقى الخرج السابق دون استبدال.

## 5. بناء AST

`src/visitor/Visitor.java` يحوّل parse trees إلى كائنات Java.

### Python AST

العقد موجودة في `src/PyClasses/`، ومن أهمها:

- `PyProgram`: جذر البرنامج.
- `ImportStmt`: imports.
- `AssignStmt`: الإسناد.
- `RouteStatement`: Flask route مع path وmethods والدالة.
- `FuncDefStatement`: تعريف الدوال والمعاملات والجسم.
- `ReturnStmt`: return.
- `IfStatement` و`ForStatement`.
- `PostfixExpr`: calls وindexing وattribute access.
- `ListLiteralExpr` و`DictLiteralExpr`.
- `BinaryExpr` و`UnaryExpr` و`CondExpr`.

### Jinja AST

العقد موجودة في `src/jinjaClasses/`، ومن أهمها:

- `JinjaProgram`: جذر القالب.
- `PairedTag` و`SelfClosingTag` و`HtmlText`.
- `PrintBlock`: تعبيرات `{{ ... }}`.
- `ControlBlock`: تعليمات `{% ... %}`.
- `For` و`If` و`Else`.
- `Extends` و`BlockStart` و`BlockEnd`.
- `AccessExpr`: مثل `product.price` و`products[0]`.
- `FunctionCall`: مثل `url_for(...)`.
- `JinjaFilter`.
- `JinjaBinaryExpr` و`JinjaParenthesizedExpr`.

## 6. التحليل الدلالي

`src/semantic/SemanticAnalyzer.java` ينفذ:

- تسجيل imports والمتغيرات والدوال والنطاقات.
- اكتشاف المتغيرات غير المعرفة.
- جمع سياقات `render_template`.
- بناء registry لمسارات Flask.
- تسجيل endpoint وroute path وHTTP methods والمعاملات الديناميكية.
- التحقق من أن endpoint المستخدم في Jinja `url_for` موجود.
- التحقق من تمرير كل dynamic route parameter.
- رفض معاملات route المجهولة.
- اعتبار Python built-ins المستخدمة حالياً، ومنها `open` و`__file__`.

`src/semantic/EnhancedSemanticAnalyzer.java` يجتاز Python AST أيضاً ويضيف إلى
التقرير النهائي:

- استدعاء دالة غير معرفة أو غير مستوردة.
- عدد arguments غير مطابق لتعريف الدالة.
- import من module غير مدعوم أو Flask export غير مدعوم.
- إعادة تعريف built-in.
- attribute غير متوافق مع نوع `str` أو `list` أو `dict` عندما يكون النوع معروفاً.
- index غير متوافق مع نوع الحاوية.
- مفتاح dict بقيمة `None`.

> ملاحظة: هذه الفحوصات الثلاث كانت مكتوبة لكنها لم تكن تعمل إطلاقاً
> (`inferSimpleType` لم تكن ترى خلال `PostfixExpr`)، وتم إصلاحها وإثباتها باختبارات.
- تعريف الدالة نفسها أكثر من مرة.
- تحذير عند إسناد المتغير أكثر من مرة في النطاق نفسه.
- تحذير عند وجود دالة لا تحتوي `return` ويُتوقع أن تعيد قيمة.

الأخطاء الإضافية تدخل في قرار منع التوليد. الرسائل التي تبدأ بـ `Warning:`
تظهر في console و`semantic_report.txt` لكنها لا تمنع التوليد.

مثال:

```jinja2
{{ url_for('product_detail', product_id=product.id) }}
```

يُفحص مقابل route المسجل في Python AST، وليس مقابل اسم hardcoded.

## 7. فحص الأنواع

`src/semantic/TypeChecker.java` يفحص الأنواع الأساسية:

```text
int, float, str, bool, list, dict, none, unknown
```

في Python يفحص العمليات، بعض استدعاءات الدوال، `len`، `render_template` و`url_for`.

في Jinja يدعم ويفحص:

```text
+  -  *  /  ~
== != < > <= >=
```

أمثلة:

```jinja2
{{ product.price + tax }}       {# صالح إن كان الطرفان رقميين #}
{{ product.price >= 100 }}      {# مقارنة رقمية #}
{{ product.price + " USD" }}    {# خطأ number + string #}
{{ product.price ~ " USD" }}    {# دمج نصي صالح #}
```

كما يستنتج أنواع filters أساسية مثل:

```text
string, upper, lower, trim, replace, format
int, float, list
```

## 8. التخزين الدائم للمنتجات

مصدر المنتجات الأساسي هو:

```text
data/products.json
```

عند بدء Flask ينفذ `app.py`:

```python
products = load_products_from_json()
```

وعند إضافة منتج عبر POST:

```text
قراءة بيانات form
    ↓
إنشاء new_product
    ↓
products.append(new_product)
    ↓
save_products_to_json(products)
    ↓
redirect إلى index
```

`save_products_to_json` يستخدم:

```python
json.dump(products, data_file, indent=2, ensure_ascii=False)
```

لذلك تبقى المنتجات محفوظة بعد إيقاف Flask.

الحذف الدائم منفذ الآن: `delete_product` يزيل المنتج حسب `id`، يستدعي
`save_products_to_json(products)`، ثم `redirect(url_for("index"))`.

## 9. استخراج Context Data (من Python AST أولاً)

المسار الافتراضي هو `src/codegen/AstContextExtractor.java`، وهو يبني Context Data بالمشي على
Python AST دون تنفيذ البرنامج:

- القيم الحرفية (str/int/float/bool/list/dict) تُقَيّم من عقد AST مباشرة.
- الأسماء تُحل عبر `AssignStmt` على مستوى الموديول (constant folding).
- `os.path.join` و`os.path.dirname` و`__file__` تُطوى لاسترجاع مسار ملف البيانات من AST.
- نمط `load_products_from_json()` يُكتشف بنيوياً (`open(<path>)` ثم `json.load(<handle>)`)،
  ثم يُقرأ ملف JSON المطوي مساره.

`src/codegen/PythonContextExecutor.java` بقي كـ fallback صريح فقط، للقوالب التي يوجد في
سياقها تعبير لا يمكن طيّه (مثل generator expression في `product_detail`).
`generation_log.txt` يسجل لكل قالب: `Context source [name]: ast` أو `: executor`.

العلم `-Dcompiler.astContext=false` يعيد السلوك القديم (executor لكل شيء).

يُحل مفسر Python بالترتيب: `.venv\Scripts\python.exe` ثم `COMPILER_PYTHON` ثم `py -3.12` ثم `python3`.

التنفيذ بلا آثار جانبية: يجري التقاط globals قبل استدعاء الـ routes، ويُستبدل `open`
بنسخة ترفض الكتابة، لأن route الحذف يكتب فعلياً إلى `products.json`.

التنفيذ يستخدم Flask stub صغيراً:

- لا يبدأ development server.
- لا يحتاج استيراد Flask أثناء مرحلة التوليد.
- يسجل استدعاءات `render_template`.
- يسمح بتنفيذ تهيئة البيانات والدوال ضمن subset المشروع.

أولوية مصدر `products`:

1. إذا وُجد `data/products.json` بجانب ملف Python، يقرأه المولد مباشرة ويعتبره المصدر المعتمد.
2. إذا لم يوجد، يستخدم قيمة `products` الناتجة من تنفيذ مصدر Python، حفاظاً على توافق الملفات القديمة التي تحتوي list literal.

هذا التنفيذ مخصص لملفات المشروع الموثوقة؛ لأنه ينفذ Python فعلياً.

## 10. توليد HTML من Jinja AST

`src/codegen/JinjaRenderer.java` يقرأ Jinja AST ولا ينسخ النص الأصلي للقالب.

يدعم في التوليد:

- HTML tags والنصوص والattributes.
- تعويض `{{ variable }}`.
- attribute access وindexing.
- `for`.
- `if / else`.
- `extends` و`block`.
- العمليات الحسابية والمقارنات و`~`.
- filters أساسية.
- `url_for` باستخدام route registry.
- تمثيل CSS الموجود في AST.

`src/codegen/StaticSiteGenerator.java`:

- يعيد إنشاء `output/` بعد نجاح التحليل.
- يولد ملف `.html` لكل قالب صفحة.
- لا يولد layout مستقل إذا كان مستخدماً عبر `extends`.
- ينسخ `app.py` كما هو بوصفه ملفاً داعماً.
- ينسخ `data/` إلى `output/data/`.
- ينسخ `style.css` و`script.js` دون معالجة إذا وُجدا بجانب `app.py` أو داخل `templates/`.

الخرج الحالي:

```text
output/
├── app.py
├── data/
│   └── products.json
├── index.html
├── add_product.html
├── product_detail.html
└── test.html
```

ملفات HTML هنا snapshots ثابتة. عمليات POST وإضافة المنتجات تتم من Flask، ثم يجب إعادة تشغيل المترجم لتحديث هذه snapshots.

## 11. ملفات تقارير المترجم

`src/codegen/CompilerArtifactWriter.java` ينشئ:

```text
compiler_output/
├── ast_python.json      شجرة JSON حقيقية متداخلة
├── ast_jinja.json       شجرة JSON لكل قالب
├── ast_python.txt       طباعة مقروءة منسقة
├── ast_jinja.txt        طباعة مقروءة لكل قالب
├── symbol_table.txt     كل النطاقات ومدخلاتها
├── semantic_report.txt
└── generation_log.txt
```

كل عقدة في ملفات JSON تحمل `node` و`line` و`column`.

- `ast_python.json`: تمثيل Python AST.
- `ast_jinja.json`: AST لكل قالب.
- `semantic_report.txt`: أخطاء semantic/type أو رسالة نجاح.
- `generation_log.txt`: وقت التوليد ومسارات الإدخال والخرج وعدد القوالب.

## 11ب. الطباعة (Print)

كل عقدة في الشجرتين ترث `print(int indent)` من `ast.MainProgram`، وهي قابلة للإعادة
وتطبع: نوع العقدة ثم `line:col` ثم حقولها ثم أبناءها بإزاحة أعمق.
`printTree()` يطبع الشجرة كاملة، و`SymbolTable.print()` يعرض كل نطاق فُتح أثناء المشي
(global، `func_*`، `suite_*`، `block_*`، `for_loop`) مع اسم كل مدخل ونوعه وسطره —
النطاقات تُحفظ بعد إغلاقها لذلك تبقى معاملات الدوال ظاهرة.

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main --print-ast
```

يطبع الشجرتين وجدول الرموز على الـ console ويكتب ملفات `.txt` في `compiler_output/`.

## 12. دورة التشغيل أمام المشرف

### تجهيز Python مرة واحدة

```powershell
py -3.12 -m venv .venv
.venv\Scripts\python.exe -m pip install -r generated_app\requirements.txt
```

لا يتم تثبيت الحزم عالمياً.

### تجميع Java

```powershell
New-Item -ItemType Directory -Force .build\classes | Out-Null
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -cp dependencies\antlr-4.13.2-complete.jar -d .build\classes $sources
```

### التوليد الأول

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main
start output\index.html
```

### إضافة منتج

```powershell
.venv\Scripts\python.exe app.py
```

ثم افتح:

```text
http://127.0.0.1:5000/add
```

بعد الإضافة يتغير `data/products.json` مباشرة.

### إعادة التوليد اليدوية

أوقف Flask باستخدام `Ctrl+C`، ثم:

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" app.Main
start output\index.html
```

إذا كانت الصفحة مفتوحة، استخدم `Ctrl+F5`. سيظهر المنتج المحفوظ في HTML الجديد.

هذا هو تطبيق شرط المشروع:

```text
أي تعديل في البيانات أو الواجهة
        ↓
إعادة render
        ↓
تزامن output/ مع المصدر
```

## 13. الفرق بين output وgenerated_app

`output/` هو مسار التسليم الحالي المطلوب:

- HTML مولد من Jinja AST.
- app.py وملفات الدعم منسوخة.
- data منسوخة.

`generated_app/` ناتج قديم لمسار `CodeGenerator.java` الذي يولد تطبيق Flask. بقي للحفاظ على التوافق والاختبارات القديمة، لكنه ليس الخرج الأساسي الذي ينشئه `Main.java` الآن.

إصدارات `generated_app/requirements.txt` بقيت دون تغيير:

```text
Flask==2.3.0
Jinja2==3.1.2
Werkzeug==2.3.0
```

## 14. الاختبارات والتحقق المنفذ

الاختبارات الحالية:

- `JinjaValidationIntegrationTest`: parsing وJinja semantic/type و`url_for`.
- `CodeGeneratorIntegrationTest`: يحافظ على اختبار المولد القديم.
- `PersistentProductsIntegrationTest`: التخزين الدائم وإعادة التوليد.
- `EnhancedSemanticReportIntegrationTest`: أخطاء وتحذيرات المحلل الإضافي، بما فيها
  الفحوصات الثلاث التي أُصلحت، مع حالة سلبية تثبت عدم وجود false positives.
- `AstContextExtractorTest`: طي القوائم والقواميس، حل الأسماء، نمط json.load، وتفعيل الـ fallback.
- `AstMetadataTest`: كل عقدة في الشجرتين تحمل line وcolumn (723 + 1054 عقدة).

اختبار التخزين الدائم يتحقق من:

- إضافة منتج داخل fixture مؤقت.
- تحديث `products.json`.
- إعادة تشغيل المترجم.
- ظهور المنتج داخل `output/index.html`.
- عدم وجود semantic/type errors.
- fallback عند غياب JSON.
- نسخ CSS وJavaScript byte-for-byte.
- نسخ data بجانب `output/app.py`.
- صحة Python syntax.

أوامر الفحص:

```powershell
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" PersistentProductsIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" EnhancedSemanticReportIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" JinjaValidationIntegrationTest
java -cp ".build\classes;dependencies\antlr-4.13.2-complete.jar" CodeGeneratorIntegrationTest
py -3.12 -m py_compile app.py output\app.py
```

آخر حالة تم التحقق منها:

```text
Java compilation: PASS
Python 3.12 syntax: PASS
Persistent products test: PASS
Enhanced semantic report test: PASS
Jinja validation test: PASS
Legacy CodeGenerator test: PASS
Semantic/type report: No errors
```

لم يُثبت أو يُحدث أي package أثناء تنفيذ هذه التعديلات.

## 15. القيود الحالية

- اللغة المدعومة subset وليست Python/Jinja كاملة.
- صفحات `output/*.html` ثابتة ولا تعالج POST بنفسها.
- روابط Flask الديناميكية تحتاج تشغيل Flask؛ أما ملفات HTML فيمكن فتحها مباشرة كصفحات مولدة.
- إعادة render بعد الإضافة يدوية ومقصودة في هذا التصميم.
- Jinja macros وincludes وميزات متقدمة أخرى ليست ضمن grammar الحالية.
- filters غير المعروفة قد تبقى في AST دون استنتاج نوع دقيق.
- مفسر CSS يعيد بناء ما تمثله CSS AST، ويحافظ الآن على descendant combinator
  وفواصل قوائم القيم (تم التحقق: 32 قاعدة تطابق المصدر تماماً)؛ لكنه ليس بعمومية متصفح كامل.
- parsing errors تظهر في console، بينما ملفات التقارير الكاملة تُكتب بعد نجاح parsing وبناء AST.

## 16. قاعدة تطوير أي ميزة جديدة

عند إضافة تركيب جديد إلى اللغة، المسار الصحيح هو:

```text
authoritative grammar
→ regenerate ANTLR
→ AST node
→ Visitor
→ Semantic/Type validation
→ Renderer/Generator
→ Integration tests
```

لا يجب تعديل Java files المولدة من ANTLR يدوياً.

## 17. الحالة بعد جولة الإصلاحات

ملخص ما تغير عن النسخة السابقة من هذا الملف:

| الموضوع | قبل | بعد |
| --- | --- | --- |
| Context Data | تنفيذ `app.py` في subprocess | `AstContextExtractor` يطوي من Python AST؛ executor fallback فقط |
| موقع العقدة | Python فقط وبدون column | line + column لكل عقدة في الشجرتين |
| أخطاء Python الحاجبة | 13 | 16 |
| أخطاء Jinja الحاجبة | 7 | 9 |
| الحذف | لا يحذف | يحذف ويحفظ |
| `ast_*.json` | نص مهرّب داخل سلسلة واحدة | شجرة JSON حقيقية |
| جدول الرموز | غير مستخدم في التحليل الدلالي | مصدر التعريفات لـ E-PY-01 وE-J-01 وE-PY-03 |
| `<!DOCTYPE html>` | يُحذف (skip) | token → عقدة `Doctype` → يُطبع |
| مفسر Python | `py -3.12` ثابت | `.venv` ← `COMPILER_PYTHON` ← `py -3.12` ← `python3` |

التفاصيل الكاملة مع الأدلة وأرقام الأسطر في `PROJECT_AUDIT.md`.
