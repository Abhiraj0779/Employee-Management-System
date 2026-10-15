@echo off
cd /d "%~dp0"
echo Starting the application...
mvn spring-boot:run
echo.
pause
