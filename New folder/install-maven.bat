@echo off
echo Installing Maven for AI Career Advisor...
echo.

REM Download Maven
echo Downloading Maven...
powershell -Command "Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' -OutFile 'maven.zip'"

REM Extract Maven
echo Extracting Maven...
powershell -Command "Expand-Archive -Path 'maven.zip' -DestinationPath '.\maven' -Force"

REM Set environment variable
echo Setting up environment...
setx MAVEN_HOME "%CD%\maven\apache-maven-3.9.6"
setx PATH "%PATH%;%CD%\maven\apache-maven-3.9.6\bin"

echo.
echo Maven installed successfully!
echo Please restart your command prompt and run: .\run-app.bat
pause
