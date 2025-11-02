-- Migration pour ajouter la relation Classe aux Cours existants
-- Date: 2025-11-02

-- Étape 1: Créer la table classe si elle n'existe pas
CREATE TABLE IF NOT EXISTS classe (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

-- Étape 2: Insérer des classes par défaut si elles n'existent pas
INSERT INTO classe (nom, description) 
VALUES 
    ('1ère', 'Première année du secondaire'),
    ('2ème', 'Deuxième année du secondaire'),
    ('3ème', 'Troisième année du secondaire'),
    ('4ème', 'Quatrième année du secondaire'),
    ('5ème', 'Cinquième année du secondaire'),
    ('6ème', 'Sixième année du secondaire')
ON CONFLICT (nom) DO NOTHING;

-- Étape 3: Ajouter la colonne classe_id comme NULLABLE (temporairement)
ALTER TABLE cours 
ADD COLUMN IF NOT EXISTS classe_id BIGINT;

-- Étape 4: Mettre à jour tous les cours existants avec la première classe par défaut
UPDATE cours 
SET classe_id = (SELECT id FROM classe WHERE nom = '1ère' LIMIT 1)
WHERE classe_id IS NULL;

-- Étape 5: Maintenant rendre la colonne NOT NULL
ALTER TABLE cours 
ALTER COLUMN classe_id SET NOT NULL;

-- Étape 6: Ajouter la contrainte de clé étrangère
ALTER TABLE cours 
ADD CONSTRAINT fk_cours_classe 
FOREIGN KEY (classe_id) 
REFERENCES classe(id);

-- Vérification
SELECT 'Migration terminée avec succès!' as message;
SELECT COUNT(*) as nombre_classes FROM classe;
SELECT COUNT(*) as nombre_cours_avec_classe FROM cours WHERE classe_id IS NOT NULL;
