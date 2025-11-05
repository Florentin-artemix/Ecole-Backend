@echo off
echo ========================================
echo DEMARRAGE APPLICATION AVEC LIQUIBASE
echo ========================================
echo.

cd /d C:\Users\NERIA FLORENTIN\Downloads\demo

echo [1/3] Recherche de Java...
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo ERREUR: Java n'est pas installe ou n'est pas dans le PATH
    echo Veuillez installer Java 21 ou configurer JAVA_HOME
    pause
    exit /b 1
)

echo [2/3] Nettoyage du projet...
call mvnw.cmd clean

echo.
echo [3/3] Demarrage de l'application...
echo.
echo ========================================
echo LIQUIBASE VA EXECUTER AUTOMATIQUEMENT :
echo ========================================
echo  1. Suppression de toutes les tables
echo  2. Creation des nouvelles tables
echo  3. Insertion des donnees de test
echo.
echo L'application sera disponible sur: http://localhost:8080
echo.
echo Patientez pendant le demarrage...
echo ========================================
echo.

call mvnw.cmd spring-boot:run

pause
