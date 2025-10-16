@echo off
echo Starting AI Career Advisor Application...
echo.

REM Create temp directory for JavaFX
if not exist "temp\javafx" mkdir temp\javafx

REM Download JavaFX SDK directly
echo Downloading JavaFX SDK...
if not exist "temp\javafx-sdk.zip" (
    echo Please wait, downloading JavaFX SDK (this may take a few minutes)...
    powershell -Command "Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/17.0.2/openjfx-17.0.2_windows-x64_bin-sdk.zip' -OutFile 'temp\javafx-sdk.zip'"
)

REM Extract JavaFX SDK
if not exist "temp\javafx-sdk" (
    echo Extracting JavaFX SDK...
    powershell -Command "Expand-Archive -Path 'temp\javafx-sdk.zip' -DestinationPath 'temp\' -Force"
)

REM Set JavaFX module path
set JAVA_FX_PATH=%~dp0temp\javafx-sdk\javafx-sdk-17.0.2\lib

REM Run the application with JavaFX modules
echo Starting application...
java --module-path "%JAVA_FX_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "target\classes" com.example.aicareeradvisor.Launcher

echo.
echo Application finished.
pause
