-- ========================================
-- RÉINITIALISATION DES SÉQUENCES POSTGRESQL
-- ========================================
-- Ce script réinitialise toutes les séquences des tables
-- pour éviter les erreurs de clé dupliquée
-- ========================================

-- 1. Réinitialiser la séquence de la table UTILISATEUR
SELECT setval('utilisateur_id_seq', (SELECT COALESCE(MAX(id), 0) FROM utilisateur));

-- 2. Réinitialiser la séquence de la table ELEVE
SELECT setval('eleve_id_seq', (SELECT COALESCE(MAX(id), 0) FROM eleve));

-- 3. Réinitialiser la séquence de la table COURS
SELECT setval('cours_id_seq', (SELECT COALESCE(MAX(id), 0) FROM cours));

-- 4. Réinitialiser la séquence de la table NOTE
SELECT setval('note_id_seq', (SELECT COALESCE(MAX(id), 0) FROM note));

-- ========================================
-- VÉRIFICATION DES SÉQUENCES
-- ========================================
-- Exécutez ces commandes pour vérifier que les séquences sont bien réinitialisées

SELECT 'utilisateur_id_seq' AS sequence_name, last_value FROM utilisateur_id_seq;
SELECT 'eleve_id_seq' AS sequence_name, last_value FROM eleve_id_seq;
SELECT 'cours_id_seq' AS sequence_name, last_value FROM cours_id_seq;
SELECT 'note_id_seq' AS sequence_name, last_value FROM note_id_seq;

-- ========================================
-- ALTERNATIVE: SUPPRESSION DES DONNÉES (SI NÉCESSAIRE)
-- ========================================
-- ⚠️ ATTENTION: Ces commandes suppriment TOUTES les données!
-- Décommentez seulement si vous voulez repartir à zéro

-- DELETE FROM note;
-- DELETE FROM cours;
-- DELETE FROM eleve;
-- DELETE FROM utilisateur;

-- Puis réinitialiser les séquences à 1:
-- ALTER SEQUENCE utilisateur_id_seq RESTART WITH 1;
-- ALTER SEQUENCE eleve_id_seq RESTART WITH 1;
-- ALTER SEQUENCE cours_id_seq RESTART WITH 1;
-- ALTER SEQUENCE note_id_seq RESTART WITH 1;
