@echo off
REM Script pour exécuter la migration PostgreSQL
REM Remplacez les valeurs ci-dessous par vos paramètres de connexion

set PGHOST=localhost
set PGPORT=5432
set PGDATABASE=ecole_db
set PGUSER=postgres

echo Execution de la migration...
psql -h %PGHOST% -p %PGPORT% -U %PGUSER% -d %PGDATABASE% -f migration_add_classe_to_cours.sql

echo.
echo Migration terminee!
pause
