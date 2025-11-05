@echo off
echo ============================================
echo MIGRATION : SYSTEME DE GESTION DES PAIEMENTS
echo ============================================
echo.

set /p DB_NAME="Nom de la base de donnees: "
set /p DB_USER="Utilisateur PostgreSQL (defaut: postgres): "
if "%DB_USER%"=="" set DB_USER=postgres

echo.
echo Execution de la migration...
echo.

psql -U %DB_USER% -d %DB_NAME% -f migration_systeme_paiement.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ============================================
    echo Migration executee avec succes !
    echo ============================================
    echo.
    echo Tables creees:
    echo   - motif_paiement
    echo   - paiement
    echo   - suivi_paiement
    echo   - derogation
    echo.
    echo Colonne ajoutee a la table eleve:
    echo   - statut_paiement_global
    echo.
    echo Vous pouvez maintenant redemarrer l'application Spring Boot
    echo.
) else (
    echo.
    echo ============================================
    echo ERREUR lors de la migration !
    echo ============================================
    echo Verifiez les informations de connexion
    echo.
)

pause
