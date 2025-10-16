@echo off
echo Starting AI Career Advisor from IDE...
echo.

REM Try to run with JavaFX modules from Java installation
java --module-path "%JAVA_HOME%\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "target\classes" com.example.aicareeradvisor.Launcher

if %errorlevel% neq 0 (
    echo.
    echo Trying without modules...
    java -cp "target\classes" com.example.aicareeradvisor.Launcher
)

echo.
pause
