@echo off
echo Starting AI Career Advisor Application...
echo.

REM Compile first
echo Compiling application...
call mvn compile -q

REM Run the application
echo Starting application...
java -cp "target/classes" com.example.aicareeradvisor.Launcher

echo.
echo Application finished.
pause
