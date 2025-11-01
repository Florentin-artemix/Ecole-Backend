-- Migration pour ajouter la table Ecole et modifier la table Eleve
-- Date: 2025-01-11

-- 0. Supprimer les objets existants s'ils existent (pour permettre une ré-exécution du script)
-- Supprimer la contrainte de clé étrangère si elle existe
ALTER TABLE eleve DROP CONSTRAINT IF EXISTS fk_eleve_ecole;

-- Supprimer la colonne ecole_id si elle existe
ALTER TABLE eleve DROP COLUMN IF EXISTS ecole_id;

-- Supprimer la table ecole si elle existe
DROP TABLE IF EXISTS ecole CASCADE;

-- 1. Créer la table Ecole
CREATE TABLE ecole (
    id BIGSERIAL PRIMARY KEY,
    nom_ecole VARCHAR(200) NOT NULL,
    code_ecole VARCHAR(50) NOT NULL UNIQUE,
    ville VARCHAR(100) NOT NULL,
    commune_territoire VARCHAR(100) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(100),
    devise VARCHAR(255)
);

-- 2. Ajouter la colonne ecole_id dans la table eleve (temporairement nullable)
ALTER TABLE eleve ADD COLUMN ecole_id BIGINT NULL;

-- 3. Ajouter la contrainte de clé étrangère
ALTER TABLE eleve ADD CONSTRAINT fk_eleve_ecole 
    FOREIGN KEY (ecole_id) REFERENCES ecole(id);

-- 4. Créer un index sur ecole_id pour améliorer les performances
CREATE INDEX idx_eleve_ecole_id ON eleve(ecole_id);

-- 5. Insérer une école par défaut (exemple)
INSERT INTO ecole (nom_ecole, code_ecole, ville, commune_territoire, adresse, telephone, email, devise)
VALUES ('Institut Umoja', 'EP1234', 'Bukavu', 'Bagira', 'Avenue de la Paix', '+243 123 456 789', 'info@institutumoja.cd', 'Éduquer pour transformer');

-- 6. Mettre à jour tous les élèves existants pour les lier à l'école par défaut
UPDATE eleve SET ecole_id = (SELECT id FROM ecole LIMIT 1) WHERE ecole_id IS NULL;

-- 7. Rendre la colonne ecole_id obligatoire
ALTER TABLE eleve ALTER COLUMN ecole_id SET NOT NULL;

-- 8. Supprimer l'ancienne colonne ecole (String) si elle existe encore
-- ALTER TABLE eleve DROP COLUMN ecole;

-- Note: Décommenter la ligne ci-dessus après avoir vérifié que tout fonctionne correctement
