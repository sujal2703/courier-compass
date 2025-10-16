@echo off
echo Starting AI Career Advisor Application...
echo.

REM Set JavaFX module path (adjust path as needed)
set JAVA_FX_PATH=C:\Program Files\Java\javafx-17.0.2\lib

REM Run the application
java --module-path "%JAVA_FX_PATH%" --add-modules javafx.controls,javafx.fxml -cp "target/classes" com.example.aicareeradvisor.Launcher

echo.
echo Application finished.
pause
