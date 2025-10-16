@echo off
echo Starting AI Career Advisor Application...
echo.

REM Try to run without JavaFX modules first (in case they're built-in)
echo Attempting to run application...
java -cp "target\classes" com.example.aicareeradvisor.Launcher

if %errorlevel% neq 0 (
    echo.
    echo JavaFX not found. Please install JavaFX or use Java 8-11.
    echo.
    echo Alternative solutions:
    echo 1. Install Java 8 or 11 (which includes JavaFX)
    echo 2. Download JavaFX SDK from https://openjfx.io/
    echo 3. Use an IDE like IntelliJ IDEA or Eclipse
    echo.
)

pause
