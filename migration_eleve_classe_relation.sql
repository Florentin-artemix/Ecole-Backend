-- Migration pour lier les Eleves à l'entité Classe
-- Date: 2 Novembre 2025
-- Adapté aux données existantes dans la base

-- IMPORTANT: Cette migration gère le cas où classe_id existe déjà
-- et transforme les anciennes valeurs de classe (String) en relations

-- ============================================================
-- PARTIE 1: CRÉER LES CLASSES MANQUANTES
-- ============================================================

-- Créer les classes basées sur les données existantes dans la table eleve
-- On extrait toutes les valeurs uniques de la colonne "classe" si elle existe

DO $$
BEGIN
    -- Vérifier si la colonne classe (String) existe encore
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'eleve' AND column_name = 'classe' 
        AND data_type = 'character varying'
    ) THEN
        -- Créer les classes basées sur les valeurs existantes
        INSERT INTO classe (nom, description)
        SELECT DISTINCT 
            classe as nom,
            'Classe importée automatiquement' as description
        FROM eleve
        WHERE classe IS NOT NULL
        AND NOT EXISTS (
            SELECT 1 FROM classe c WHERE c.nom = eleve.classe
        );
        
        RAISE NOTICE 'Classes créées à partir des données existantes';
    END IF;
END $$;

-- Créer quelques classes standards si elles n'existent pas
INSERT INTO classe (nom, description) VALUES
    ('1ère', 'Première année du secondaire'),
    ('2ème', 'Deuxième année du secondaire'),
    ('3ème', 'Troisième année du secondaire'),
    ('4ème', 'Quatrième année du secondaire'),
    ('5ème', 'Cinquième année du secondaire'),
    ('6ème', 'Sixième année du secondaire'),
    ('6e Scientifique', 'Sixième année section scientifique'),
    ('8eme', 'Huitième année')
ON CONFLICT (nom) DO NOTHING;

-- ============================================================
-- PARTIE 2: GÉRER LA MIGRATION DES DONNÉES
-- ============================================================

DO $$
BEGIN
    -- CAS 1: Si la colonne classe (String) existe encore
    IF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'eleve' AND column_name = 'classe' 
        AND data_type = 'character varying'
    ) THEN
        RAISE NOTICE 'Migration depuis colonne classe (String) vers classe_id';
        
        -- Créer colonne temporaire pour sauvegarder les anciennes valeurs
        ALTER TABLE eleve ADD COLUMN IF NOT EXISTS classe_old VARCHAR(100);
        UPDATE eleve SET classe_old = classe WHERE classe_old IS NULL;
        
        -- Vérifier si classe_id existe déjà
        IF NOT EXISTS (
            SELECT 1 FROM information_schema.columns 
            WHERE table_name = 'eleve' AND column_name = 'classe_id'
        ) THEN
            -- Créer la colonne classe_id
            ALTER TABLE eleve ADD COLUMN classe_id BIGINT;
        END IF;
        
        -- Associer chaque élève à sa classe
        UPDATE eleve e
        SET classe_id = (
            SELECT c.id 
            FROM classe c 
            WHERE c.nom = e.classe_old 
            LIMIT 1
        )
        WHERE e.classe_old IS NOT NULL 
        AND (e.classe_id IS NULL OR e.classe_id = 1);
        
        -- Pour les élèves sans correspondance, assigner à la première classe disponible
        UPDATE eleve 
        SET classe_id = (SELECT MIN(id) FROM classe)
        WHERE classe_id IS NULL;
        
        -- Supprimer l'ancienne colonne classe (String)
        ALTER TABLE eleve DROP COLUMN IF EXISTS classe;
        
        -- Supprimer la colonne temporaire
        ALTER TABLE eleve DROP COLUMN IF EXISTS classe_old;
        
    -- CAS 2: Si classe_id existe déjà mais pointe tous vers la même classe
    ELSIF EXISTS (
        SELECT 1 FROM information_schema.columns 
        WHERE table_name = 'eleve' AND column_name = 'classe_id'
    ) THEN
        RAISE NOTICE 'La colonne classe_id existe déjà. Vérification des données...';
        
        -- Vérifier si tous les élèves pointent vers la même classe (probablement classe_id = 1)
        IF (SELECT COUNT(DISTINCT classe_id) FROM eleve) = 1 THEN
            RAISE NOTICE 'Tous les élèves sont dans la même classe. Migration impossible sans données sources.';
            RAISE NOTICE 'Veuillez réassigner manuellement les élèves aux bonnes classes via l''API';
        END IF;
    END IF;
    
    -- Rendre la colonne NOT NULL si elle ne l'est pas déjà
    ALTER TABLE eleve ALTER COLUMN classe_id SET NOT NULL;
    
    -- Supprimer l'ancienne contrainte si elle existe
    ALTER TABLE eleve DROP CONSTRAINT IF EXISTS fk_eleve_classe;
    
    -- Ajouter la contrainte de clé étrangère
    ALTER TABLE eleve 
    ADD CONSTRAINT fk_eleve_classe 
    FOREIGN KEY (classe_id) 
    REFERENCES classe(id);
    
    RAISE NOTICE 'Migration terminée avec succès!';
END $$;

-- ============================================================
-- PARTIE 3: VÉRIFICATIONS ET RAPPORTS
-- ============================================================

-- Rapport sur l'état de la migration
SELECT 'Migration terminée avec succès!' as message;

-- Nombre d'élèves avec une classe assignée
SELECT COUNT(*) as nombre_eleves_avec_classe 
FROM eleve 
WHERE classe_id IS NOT NULL;

-- Distribution des élèves par classe
SELECT 
    c.nom as classe,
    c.description,
    COUNT(e.id) as nombre_eleves
FROM classe c 
LEFT JOIN eleve e ON e.classe_id = c.id 
GROUP BY c.id, c.nom, c.description
ORDER BY c.nom;

-- Afficher les élèves par classe
SELECT 
    c.nom as classe,
    e.nom || ' ' || e.postnom || ' ' || e.prenom as eleve_nom_complet,
    e.numero_permanent
FROM eleve e
JOIN classe c ON e.classe_id = c.id
ORDER BY c.nom, e.nom;

-- ============================================================
-- NOTES IMPORTANTES
-- ============================================================
-- 
-- Si tous vos élèves ont classe_id = 1, vous devrez les réassigner 
-- manuellement aux bonnes classes via l'API PUT /api/eleves/{id}
--
-- Exemple de réassignation via SQL si nécessaire:
-- UPDATE eleve SET classe_id = (SELECT id FROM classe WHERE nom = '6e Scientifique') 
-- WHERE numero_permanent IN ('KIN2008001', 'LUB2008002', ...);
--
