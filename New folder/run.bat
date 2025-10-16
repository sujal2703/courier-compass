@echo off
echo Setting up Maven environment...
set MAVEN_HOME=%~dp0maven\apache-maven-3.9.6
set PATH=%PATH%;%MAVEN_HOME%\bin

echo.
echo Running Maven compile...
call mvn clean compile

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed. Please check the errors above.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.
echo Running the application...
call mvn javafx:run

pause
