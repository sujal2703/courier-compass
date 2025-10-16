@echo off
echo Starting AI Career Advisor Application...
echo.

REM Set JavaFX module path
set JAVA_FX_PATH=C:\Program Files\Java\javafx-17.0.2\lib

REM Check if JavaFX exists
if not exist "%JAVA_FX_PATH%" (
    echo ERROR: JavaFX not found at %JAVA_FX_PATH%
    echo Please download JavaFX 17 SDK from https://openjfx.io/
    echo Extract it to C:\Program Files\Java\javafx-17.0.2\
    pause
    exit /b 1
)

REM Run the application with JavaFX modules
java --module-path "%JAVA_FX_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "target\classes" com.example.aicareeradvisor.Launcher

echo.
echo Application finished.
pause
