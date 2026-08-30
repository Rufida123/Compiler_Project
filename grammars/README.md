# Grammars

`grammars/*.g4` are the **only** authoritative grammars. Everything under `src/pyAntlr/` and
`src/jinjaAntlr/` is ANTLR-generated output — never edit it by hand.

```powershell
Set-Location grammars
java -jar ..\dependencies\antlr-4.13.2-complete.jar -encoding UTF-8 -visitor -listener -o ..\src\jinjaAntlr JinjaLexer.g4 JinjaParser.g4
java -jar ..\dependencies\antlr-4.13.2-complete.jar -encoding UTF-8 -visitor -listener -o ..\src\pyAntlr   pyLexer.g4  pyParser.g4
Set-Location ..
```
