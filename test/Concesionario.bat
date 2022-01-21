@echo off
cls
chcp 65001 > nul
cd %~dp0
cd ..
"%JAVA_HOME%\bin\java.exe" -Dfile.encoding=UTF-8 -classpath .\bin Principal
cd %~dp0
chcp 850 > nul