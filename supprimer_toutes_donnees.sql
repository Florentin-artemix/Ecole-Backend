-- ========================================
-- SUPPRESSION COMPLÈTE DES DONNÉES DE TEST
-- ========================================
-- ⚠️ ATTENTION: Ce script supprime TOUTES les données
-- Utilisez-le uniquement pour repartir à zéro
-- ========================================

-- Supprimer dans l'ordre inverse des dépendances (du plus dépendant au moins dépendant)

-- 1. Supprimer toutes les notes
DELETE FROM note;

-- 2. Supprimer tous les cours
DELETE FROM cours;

-- 3. Supprimer tous les élèves
DELETE FROM eleve;

-- 4. Supprimer tous les utilisateurs
DELETE FROM utilisateur;

-- ========================================
-- RÉINITIALISER LES SÉQUENCES À 1
-- ========================================

ALTER SEQUENCE note_id_seq RESTART WITH 1;
ALTER SEQUENCE cours_id_seq RESTART WITH 1;
ALTER SEQUENCE eleve_id_seq RESTART WITH 1;
ALTER SEQUENCE utilisateur_id_seq RESTART WITH 1;

-- ========================================
-- VÉRIFICATION
-- ========================================

SELECT 'Utilisateurs restants: ' || COUNT(*) FROM utilisateur;
SELECT 'Élèves restants: ' || COUNT(*) FROM eleve;
SELECT 'Cours restants: ' || COUNT(*) FROM cours;
SELECT 'Notes restantes: ' || COUNT(*) FROM note;

SELECT 'utilisateur_id_seq' AS sequence_name, last_value FROM utilisateur_id_seq;
SELECT 'eleve_id_seq' AS sequence_name, last_value FROM eleve_id_seq;
SELECT 'cours_id_seq' AS sequence_name, last_value FROM cours_id_seq;
SELECT 'note_id_seq' AS sequence_name, last_value FROM note_id_seq;

-- ========================================
-- MESSAGE
-- ========================================
-- Toutes les tables sont vides et les séquences commencent à 1
-- Vous pouvez maintenant insérer vos données de test
