-- Script de nettoyage de la base de données Ecole
-- À exécuter dans pgAdmin ou psql avant de redémarrer l'application

-- Supprimer la table de tracking Liquibase
DROP TABLE IF EXISTS databasechangelog CASCADE;
DROP TABLE IF EXISTS databasechangeloglock CASCADE;

-- Supprimer les tables créées (dans l'ordre inverse des dépendances)
DROP TABLE IF EXISTS paiement CASCADE;
DROP TABLE IF EXISTS derogation CASCADE;
DROP TABLE IF EXISTS suivi_paiement CASCADE;
DROP TABLE IF EXISTS motif_paiement CASCADE;
DROP TABLE IF EXISTS conduite CASCADE;
DROP TABLE IF EXISTS note CASCADE;
DROP TABLE IF EXISTS parent_eleve CASCADE;
DROP TABLE IF EXISTS cours CASCADE;
DROP TABLE IF EXISTS eleve CASCADE;
DROP TABLE IF EXISTS utilisateur CASCADE;
DROP TABLE IF EXISTS classe CASCADE;
DROP TABLE IF EXISTS ecole CASCADE;

-- Vérifier que toutes les tables ont été supprimées
SELECT tablename FROM pg_tables WHERE schemaname = 'public';
