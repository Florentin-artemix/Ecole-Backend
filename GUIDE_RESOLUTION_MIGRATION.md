# GUIDE DE RÉSOLUTION - Erreur Migration Classe

## ⚠️ Problème
Votre base de données contient déjà des cours, et Hibernate ne peut pas ajouter la colonne `classe_id NOT NULL` car les enregistrements existants n'ont pas de valeur pour cette colonne.

## ✅ Solution Rapide

### Option 1 : Exécuter le Script de Migration (RECOMMANDÉ)

**Étape 1 : Arrêter l'application Spring Boot**

**Étape 2 : Exécuter le script SQL**

Ouvrez un terminal PostgreSQL (pgAdmin ou ligne de commande) et exécutez :

```bash
psql -U postgres -d votre_base_de_donnees -f migration_add_classe_to_cours.sql
```

OU utilisez le fichier `run_migration.bat` après avoir modifié les paramètres de connexion.

**Étape 3 : Redémarrer l'application**

---

### Option 2 : Exécution Manuelle dans pgAdmin

Ouvrez pgAdmin, connectez-vous à votre base de données et exécutez ce script SQL :

```sql
-- 1. Créer la table classe
CREATE TABLE IF NOT EXISTS classe (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

-- 2. Insérer des classes par défaut
INSERT INTO classe (nom, description) 
VALUES 
    ('1ère', 'Première année du secondaire'),
    ('2ème', 'Deuxième année du secondaire'),
    ('3ème', 'Troisième année du secondaire'),
    ('4ème', 'Quatrième année du secondaire'),
    ('5ème', 'Cinquième année du secondaire'),
    ('6ème', 'Sixième année du secondaire')
ON CONFLICT (nom) DO NOTHING;

-- 3. Ajouter la colonne classe_id comme NULLABLE
ALTER TABLE cours 
ADD COLUMN IF NOT EXISTS classe_id BIGINT;

-- 4. Mettre à jour tous les cours existants
UPDATE cours 
SET classe_id = (SELECT id FROM classe WHERE nom = '1ère' LIMIT 1)
WHERE classe_id IS NULL;

-- 5. Rendre la colonne NOT NULL
ALTER TABLE cours 
ALTER COLUMN classe_id SET NOT NULL;

-- 6. Ajouter la contrainte de clé étrangère
ALTER TABLE cours 
ADD CONSTRAINT fk_cours_classe 
FOREIGN KEY (classe_id) 
REFERENCES classe(id);
```

---

### Option 3 : Supprimer et Recréer (Si vous n'avez pas de données importantes)

Si vos cours en base ne sont pas importants, vous pouvez :

```sql
-- ATTENTION : Cela supprime toutes les données !
DROP TABLE IF EXISTS note CASCADE;
DROP TABLE IF EXISTS cours CASCADE;
DROP TABLE IF EXISTS classe CASCADE;
```

Puis redémarrez l'application, Hibernate recréera tout proprement.

---

## 📝 Vérification

Après avoir exécuté la migration, vérifiez que tout fonctionne :

```sql
-- Vérifier les classes
SELECT * FROM classe;

-- Vérifier les cours avec leurs classes
SELECT c.id, c.nom_cours, c.ponderation, cl.nom as classe
FROM cours c
JOIN classe cl ON c.classe_id = cl.id;
```

---

## 🚀 Après la Migration

Une fois la migration effectuée, vous pouvez :

1. Redémarrer votre application Spring Boot
2. Utiliser les nouveaux endpoints pour gérer les classes
3. Créer de nouveaux cours liés à des classes spécifiques
4. Réassigner les cours existants aux bonnes classes si nécessaire

---

## 📌 Note Important

Tous les cours existants seront automatiquement assignés à la classe "1ère" par défaut. Vous devrez probablement les réassigner aux bonnes classes via l'API :

```
PUT http://localhost:8080/api/cours/{id}
{
  "nomCours": "Mathématiques",
  "ponderation": 4,
  "classeId": 2,
  "professeurId": 1
}
```
