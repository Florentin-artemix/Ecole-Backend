-- Migration pour créer la table Conduite et assurer la compatibilité avec le système de notation
-- Date: 2025-01-12
-- Base de données: PostgreSQL

-- ============================================
-- 1. CRÉATION DE LA TABLE CONDUITE
-- ============================================

-- Supprimer la table conduite si elle existe (pour permettre une ré-exécution du script)
DROP TABLE IF EXISTS conduite CASCADE;

-- Créer la table conduite
CREATE TABLE conduite (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    professeur_id BIGINT NOT NULL,
    type_conduite VARCHAR(20) NOT NULL,
    periode VARCHAR(20) NOT NULL,
    commentaire VARCHAR(500),
    CONSTRAINT fk_conduite_eleve FOREIGN KEY (eleve_id) REFERENCES eleve(id) ON DELETE CASCADE,
    CONSTRAINT fk_conduite_professeur FOREIGN KEY (professeur_id) REFERENCES utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT chk_type_conduite CHECK (type_conduite IN ('EXCELLENT', 'TRES_BON', 'BON', 'ASSEZ_BON', 'PASSABLE', 'MEDIOCRE', 'MAUVAIS')),
    CONSTRAINT chk_conduite_periode CHECK (periode IN ('PREMIERE', 'DEUXIEME', 'TROISIEME', 'QUATRIEME', 'CINQUIEME', 'SIXIEME', 'PREMIER_TRIMESTRE', 'DEUXIEME_TRIMESTRE', 'TROISIEME_TRIMESTRE', 'PREMIER_SEMESTRE', 'DEUXIEME_SEMESTRE'))
);

-- Créer des index pour améliorer les performances
CREATE INDEX idx_conduite_eleve_id ON conduite(eleve_id);
CREATE INDEX idx_conduite_professeur_id ON conduite(professeur_id);
CREATE INDEX idx_conduite_periode ON conduite(periode);
CREATE INDEX idx_conduite_eleve_periode ON conduite(eleve_id, periode);

-- ============================================
-- 2. VÉRIFICATION DE LA TABLE NOTE
-- ============================================

-- S'assurer que la table note existe avec la bonne structure
-- Si elle n'existe pas, la créer
CREATE TABLE IF NOT EXISTS note (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    cours_id BIGINT NOT NULL,
    valeur DECIMAL(5,2) NOT NULL,
    periode VARCHAR(20) NOT NULL,
    CONSTRAINT fk_note_eleve FOREIGN KEY (eleve_id) REFERENCES eleve(id) ON DELETE CASCADE,
    CONSTRAINT fk_note_cours FOREIGN KEY (cours_id) REFERENCES cours(id) ON DELETE CASCADE,
    CONSTRAINT chk_note_valeur CHECK (valeur >= 0 AND valeur <= 100),
    CONSTRAINT chk_note_periode CHECK (periode IN ('PREMIERE', 'DEUXIEME', 'TROISIEME', 'QUATRIEME', 'CINQUIEME', 'SIXIEME', 'PREMIER_TRIMESTRE', 'DEUXIEME_TRIMESTRE', 'TROISIEME_TRIMESTRE', 'PREMIER_SEMESTRE', 'DEUXIEME_SEMESTRE'))
);

-- Créer des index pour la table note si elle vient d'être créée
CREATE INDEX IF NOT EXISTS idx_note_eleve_id ON note(eleve_id);
CREATE INDEX IF NOT EXISTS idx_note_cours_id ON note(cours_id);
CREATE INDEX IF NOT EXISTS idx_note_periode ON note(periode);
CREATE INDEX IF NOT EXISTS idx_note_eleve_periode ON note(eleve_id, periode);

-- ============================================
-- 3. INSERTION DES DONNÉES DE TEST (NOTES)
-- ============================================

-- Nettoyer les données existantes si nécessaire (ATTENTION: Cette ligne supprime toutes les notes)
-- TRUNCATE TABLE note RESTART IDENTITY CASCADE;

-- Insérer les données de notes fournies
-- Note: Assurez-vous que les eleve_id et cours_id correspondent à des enregistrements existants

INSERT INTO note (id, eleve_id, cours_id, valeur, periode) VALUES
(1, 1, 1, 16.50, 'PREMIERE'),
(2, 1, 2, 15.00, 'PREMIERE'),
(3, 1, 3, 14.50, 'PREMIERE'),
(4, 1, 4, 13.00, 'PREMIERE'),
(5, 1, 5, 12.00, 'PREMIERE'),
(6, 1, 6, 14.00, 'PREMIERE'),
(7, 1, 7, 13.50, 'PREMIERE'),
(8, 1, 8, 15.00, 'PREMIERE'),
(9, 1, 9, 16.00, 'PREMIERE'),
(10, 1, 10, 17.00, 'PREMIERE'),
(11, 2, 1, 18.50, 'PREMIERE'),
(12, 2, 2, 17.00, 'PREMIERE'),
(13, 2, 3, 18.00, 'PREMIERE'),
(14, 2, 4, 16.50, 'PREMIERE'),
(15, 2, 5, 17.00, 'PREMIERE'),
(16, 2, 6, 16.00, 'PREMIERE'),
(17, 2, 7, 15.50, 'PREMIERE'),
(18, 2, 8, 17.50, 'PREMIERE'),
(19, 2, 9, 18.00, 'PREMIERE'),
(20, 2, 10, 19.00, 'PREMIERE'),
(21, 3, 1, 12.00, 'PREMIERE')
ON CONFLICT (id) DO NOTHING; -- Éviter les erreurs si les données existent déjà

-- Réinitialiser la séquence pour la table note
SELECT setval('note_id_seq', (SELECT MAX(id) FROM note), true);

-- ============================================
-- 4. INSERTION DE DONNÉES DE TEST (CONDUITES)
-- ============================================

-- Exemples de conduites pour tester le système
-- Ces données sont optionnelles et peuvent être commentées

INSERT INTO conduite (eleve_id, professeur_id, type_conduite, periode, commentaire) VALUES
-- Conduites pour l'élève 1 (période PREMIERE)
((SELECT id FROM eleve LIMIT 1 OFFSET 0), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1), 'BON', 'PREMIERE', 'Élève attentif et participatif'),
((SELECT id FROM eleve LIMIT 1 OFFSET 0), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1 OFFSET 1), 'TRES_BON', 'PREMIERE', 'Excellent comportement en classe'),
((SELECT id FROM eleve LIMIT 1 OFFSET 0), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1 OFFSET 2), 'BON', 'PREMIERE', 'Respectueux des règles'),

-- Conduites pour l'élève 2 (période PREMIERE)
((SELECT id FROM eleve LIMIT 1 OFFSET 1), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1), 'EXCELLENT', 'PREMIERE', 'Leader positif dans le groupe'),
((SELECT id FROM eleve LIMIT 1 OFFSET 1), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1 OFFSET 1), 'TRES_BON', 'PREMIERE', 'Participation active'),
((SELECT id FROM eleve LIMIT 1 OFFSET 1), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1 OFFSET 2), 'EXCELLENT', 'PREMIERE', 'Modèle pour les autres'),

-- Conduites pour l'élève 3 (période PREMIERE)
((SELECT id FROM eleve LIMIT 1 OFFSET 2), (SELECT id FROM utilisateur WHERE role = 'PROFESSEUR' LIMIT 1), 'ASSEZ_BON', 'PREMIERE', 'Peut mieux faire')
ON CONFLICT DO NOTHING;

-- ============================================
-- 5. VÉRIFICATIONS ET RAPPORTS
-- ============================================

-- Afficher le nombre de notes insérées
DO $$
DECLARE
    note_count INTEGER;
    conduite_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO note_count FROM note;
    SELECT COUNT(*) INTO conduite_count FROM conduite;
    
    RAISE NOTICE 'Migration terminée avec succès !';
    RAISE NOTICE '===========================================';
    RAISE NOTICE 'Nombre total de notes: %', note_count;
    RAISE NOTICE 'Nombre total de conduites: %', conduite_count;
    RAISE NOTICE '===========================================';
END $$;

-- Afficher un aperçu des données
SELECT 
    'NOTES' as type,
    COUNT(*) as total,
    COUNT(DISTINCT eleve_id) as eleves_distincts,
    COUNT(DISTINCT periode) as periodes_distinctes
FROM note
UNION ALL
SELECT 
    'CONDUITES' as type,
    COUNT(*) as total,
    COUNT(DISTINCT eleve_id) as eleves_distincts,
    COUNT(DISTINCT periode) as periodes_distinctes
FROM conduite;

-- ============================================
-- 6. COMMENTAIRES ET INSTRUCTIONS
-- ============================================

-- IMPORTANT: Ce script va:
-- 1. Créer la table 'conduite' pour gérer les évaluations de comportement
-- 2. S'assurer que la table 'note' existe avec la bonne structure
-- 3. Insérer les données de notes fournies
-- 4. Insérer des exemples de conduites (optionnel)
-- 5. Créer les index nécessaires pour optimiser les performances

-- AVANT D'EXÉCUTER CE SCRIPT:
-- - Assurez-vous que les tables 'eleve', 'cours', et 'utilisateur' existent
-- - Vérifiez que les eleve_id (1, 2, 3) existent dans la table eleve
-- - Vérifiez que les cours_id (1-10) existent dans la table cours
-- - Si vous voulez nettoyer les données existantes, décommentez la ligne TRUNCATE

-- APRÈS L'EXÉCUTION:
-- - Vérifiez que les données sont bien insérées
-- - Testez l'API de notation avec conduite
-- - Les professeurs peuvent maintenant noter la conduite en même temps que les notes académiques
