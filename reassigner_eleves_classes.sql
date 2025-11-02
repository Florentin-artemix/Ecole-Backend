-- Script de réassignation des élèves aux bonnes classes
-- Basé sur les données existantes dans votre base
-- Date: 2 Novembre 2025

-- ============================================================
-- ÉTAPE 1: Créer les classes si elles n'existent pas
-- ============================================================

INSERT INTO classe (nom, description) VALUES
    ('6e Scientifique', 'Sixième année section scientifique'),
    ('8eme', 'Huitième année'),
    ('1ère', 'Première année du secondaire'),
    ('2ème', 'Deuxième année du secondaire'),
    ('3ème', 'Troisième année du secondaire'),
    ('4ème', 'Quatrième année du secondaire'),
    ('5ème', 'Cinquième année du secondaire'),
    ('6ème', 'Sixième année du secondaire')
ON CONFLICT (nom) DO NOTHING;

-- ============================================================
-- ÉTAPE 2: Réassigner les élèves selon leurs numéros permanents
-- ============================================================

-- Tous les élèves de "6e Scientifique"
UPDATE eleve 
SET classe_id = (SELECT id FROM classe WHERE nom = '6e Scientifique')
WHERE numero_permanent IN (
    'KIN2008001',  -- Mukendi Jean Pierre
    'LUB2008002',  -- Tshala Marie Grace
    'GOM2008003',  -- Kabamba Joseph Daniel
    'MAT2008004',  -- Nsimba Claire Joelle
    'BUK2008005',  -- Lukaku Emmanuel Marc
    'KAN2008006',  -- Mbuyi Rachel Sarah
    'KIS2008007',  -- Ilunga Patrick Albert
    'MBU2008008',  -- Kalala Esther Divine
    'KOL2008009',  -- Nkongolo David Samuel
    'MBA2008010'   -- Mutombo Angelique Deborah
);

-- L'élève de "8eme"
UPDATE eleve 
SET classe_id = (SELECT id FROM classe WHERE nom = '8eme')
WHERE numero_permanent = '568906';  -- Esther Mashauri Olive

-- ============================================================
-- ÉTAPE 3: Vérification
-- ============================================================

-- Afficher la distribution des élèves par classe
SELECT 
    c.nom as classe,
    COUNT(e.id) as nombre_eleves,
    STRING_AGG(e.nom || ' ' || e.prenom, ', ') as eleves
FROM classe c
LEFT JOIN eleve e ON e.classe_id = c.id
GROUP BY c.id, c.nom
HAVING COUNT(e.id) > 0
ORDER BY c.nom;

-- Afficher tous les élèves avec leur classe
SELECT 
    e.id,
    e.nom,
    e.postnom,
    e.prenom,
    e.numero_permanent,
    c.nom as classe,
    e.ecole_id
FROM eleve e
JOIN classe c ON e.classe_id = c.id
ORDER BY c.nom, e.nom;

-- Rapport final
SELECT 
    '✅ Réassignation terminée!' as message,
    (SELECT COUNT(*) FROM eleve WHERE classe_id = (SELECT id FROM classe WHERE nom = '6e Scientifique')) as eleves_6e_scientifique,
    (SELECT COUNT(*) FROM eleve WHERE classe_id = (SELECT id FROM classe WHERE nom = '8eme')) as eleves_8eme,
    (SELECT COUNT(*) FROM eleve) as total_eleves;
