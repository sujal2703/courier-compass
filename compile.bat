@echo off
cd "%~dp0New folder"
call "%~dp0New folder\maven\apache-maven-3.9.6\bin\mvn.cmd" clean compile
pause
