@echo off
echo Setting up Maven environment...
set MAVEN_HOME=%~dp0maven\apache-maven-3.9.6
set PATH=%PATH%;%MAVEN_HOME%\bin

echo.
echo Compiling and running JDBC connectivity test...
call mvn clean compile exec:java -Dexec.mainClass="com.example.aicareeradvisor.JdbcConnectivityTest" -q

pause
