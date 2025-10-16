@echo off
echo Starting AI Career Advisor with Auto JavaFX Setup...
echo.

REM Create temp directory for JavaFX
if not exist "temp\javafx" mkdir temp\javafx

REM Download JavaFX if not exists
if not exist "temp\javafx\javafx-controls-17.0.2.jar" (
    echo Downloading JavaFX libraries...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/openjfx/javafx-controls/17.0.2/javafx-controls-17.0.2.jar' -OutFile 'temp\javafx\javafx-controls-17.0.2.jar'"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/openjfx/javafx-fxml/17.0.2/javafx-fxml-17.0.2.jar' -OutFile 'temp\javafx\javafx-fxml-17.0.2.jar'"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/openjfx/javafx-graphics/17.0.2/javafx-graphics-17.0.2.jar' -OutFile 'temp\javafx\javafx-graphics-17.0.2.jar'"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/openjfx/javafx-base/17.0.2/javafx-base-17.0.2.jar' -OutFile 'temp\javafx\javafx-base-17.0.2.jar'"
)

REM Run with downloaded JavaFX
java -cp "target\classes;temp\javafx\*" com.example.aicareeradvisor.Launcher

echo.
echo Application finished.
pause
