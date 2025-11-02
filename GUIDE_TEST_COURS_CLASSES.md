# Guide de Test API - Gestion des Cours et Classes

## Vue d'ensemble
Ce guide démontre comment créer des cours rattachés à des classes. Un même cours (ex: Mathématiques) peut être dispensé dans différentes classes (1ère, 2ème, 3ème) avec des pondérations différentes.

---

## 1. CRÉER DES CLASSES

### POST /api/classes - Créer la classe "1ère"
**URL:** `http://localhost:8080/api/classes`
**Method:** POST
**Headers:** 
```
Content-Type: application/json
```
**Body (JSON):**
```json
{
  "nom": "1ère",
  "description": "Première année du secondaire"
}
```

### POST /api/classes - Créer la classe "2ème"
**URL:** `http://localhost:8080/api/classes`
**Method:** POST
**Body (JSON):**
```json
{
  "nom": "2ème",
  "description": "Deuxième année du secondaire"
}
```

### POST /api/classes - Créer la classe "3ème"
**URL:** `http://localhost:8080/api/classes`
**Method:** POST
**Body (JSON):**
```json
{
  "nom": "3ème",
  "description": "Troisième année du secondaire"
}
```

### POST /api/classes - Créer la classe "4ème"
**URL:** `http://localhost:8080/api/classes`
**Method:** POST
**Body (JSON):**
```json
{
  "nom": "4ème",
  "description": "Quatrième année du secondaire"
}
```

---

## 2. RÉCUPÉRER LES CLASSES

### GET /api/classes - Obtenir toutes les classes
**URL:** `http://localhost:8080/api/classes`
**Method:** GET

**Réponse attendue (sans référence circulaire):**
```json
[
  {
    "id": 1,
    "nom": "1ère",
    "description": "Première année du secondaire"
  },
  {
    "id": 2,
    "nom": "2ème",
    "description": "Deuxième année du secondaire"
  },
  {
    "id": 3,
    "nom": "3ème",
    "description": "Troisième année du secondaire"
  },
  {
    "id": 4,
    "nom": "4ème",
    "description": "Quatrième année du secondaire"
  }
]
```

**Note:** Les cours ne sont PAS inclus dans cette réponse pour éviter les références circulaires. Pour obtenir les cours d'une classe spécifique, utilisez l'endpoint GET /api/cours.

---

## 3. CRÉER UN PROFESSEUR (si nécessaire)

### POST /api/utilisateurs - Créer un professeur
**URL:** `http://localhost:8080/api/utilisateurs`
**Method:** POST
**Body (JSON):**
```json
{
  "nom": "Kabongo",
  "postnom": "Jean",
  "prenom": "Pierre",
  "email": "prof.math@ecole.cd",
  "motDePasse": "password123",
  "role": "PROFESSEUR"
}
```

**Note:** Notez l'ID du professeur créé (ex: 1) pour l'utiliser dans la création des cours.

---

## 4. CRÉER DES COURS RATTACHÉS AUX CLASSES

### POST /api/cours - Mathématiques en 1ère (Pondération: 3)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Headers:** 
```
Content-Type: application/json
```
**Body (JSON):**
```json
{
  "nomCours": "Mathématiques",
  "ponderation": 3,
  "classeId": 1,
  "professeurId": 1
}
```

**Réponse attendue:**
```json
{
  "id": 1,
  "nomCours": "Mathématiques",
  "ponderation": 3,
  "classeId": 1,
  "classeNom": "1ère",
  "professeurNom": "Kabongo Jean Pierre",
  "professeurId": 1
}
```

### POST /api/cours - Mathématiques en 2ème (Pondération: 4)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Mathématiques",
  "ponderation": 4,
  "classeId": 2,
  "professeurId": 1
}
```

### POST /api/cours - Mathématiques en 3ème (Pondération: 5)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Mathématiques",
  "ponderation": 5,
  "classeId": 3,
  "professeurId": 1
}
```

### POST /api/cours - Français en 1ère (Pondération: 3)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Français",
  "ponderation": 3,
  "classeId": 1,
  "professeurId": 1
}
```

### POST /api/cours - Français en 2ème (Pondération: 4)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Français",
  "ponderation": 4,
  "classeId": 2,
  "professeurId": 1
}
```

### POST /api/cours - Sciences en 4ème (Pondération: 5)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Sciences",
  "ponderation": 5,
  "classeId": 4,
  "professeurId": 1
}
```

### POST /api/cours - Histoire-Géographie en 3ème (Pondération: 2)
**URL:** `http://localhost:8080/api/cours`
**Method:** POST
**Body (JSON):**
```json
{
  "nomCours": "Histoire-Géographie",
  "ponderation": 2,
  "classeId": 3,
  "professeurId": 1
}
```

---

## 5. RÉCUPÉRER LES COURS

### GET /api/cours - Obtenir tous les cours
**URL:** `http://localhost:8080/api/cours`
**Method:** GET

**Réponse attendue:**
```json
[
  {
    "id": 1,
    "nomCours": "Mathématiques",
    "ponderation": 3,
    "classeId": 1,
    "classeNom": "1ère",
    "professeurNom": "Kabongo Jean Pierre",
    "professeurId": 1
  },
  {
    "id": 2,
    "nomCours": "Mathématiques",
    "ponderation": 4,
    "classeId": 2,
    "classeNom": "2ème",
    "professeurNom": "Kabongo Jean Pierre",
    "professeurId": 1
  },
  {
    "id": 3,
    "nomCours": "Mathématiques",
    "ponderation": 5,
    "classeId": 3,
    "classeNom": "3ème",
    "professeurNom": "Kabongo Jean Pierre",
    "professeurId": 1
  }
]
```

### GET /api/cours/{id} - Obtenir un cours spécifique
**URL:** `http://localhost:8080/api/cours/1`
**Method:** GET

---

## 6. METTRE À JOUR UN COURS

### PUT /api/cours/{id} - Modifier la pondération
**URL:** `http://localhost:8080/api/cours/1`
**Method:** PUT
**Body (JSON):**
```json
{
  "nomCours": "Mathématiques",
  "ponderation": 4,
  "classeId": 1,
  "professeurId": 1
}
```

---

## 7. SUPPRIMER UN COURS

### DELETE /api/cours/{id}
**URL:** `http://localhost:8080/api/cours/1`
**Method:** DELETE

**Réponse attendue:** Status 204 No Content

---

## Collection Postman - Importation

Vous pouvez créer une collection Postman et importer ces requêtes. Voici la structure JSON de la collection :

```json
{
  "info": {
    "name": "Gestion Cours et Classes",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Classes",
      "item": [
        {
          "name": "Créer Classe",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"nom\": \"1ère\",\n  \"description\": \"Première année du secondaire\"\n}"
            },
            "url": {
              "raw": "http://localhost:8080/api/classes",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "classes"]
            }
          }
        },
        {
          "name": "Obtenir toutes les classes",
          "request": {
            "method": "GET",
            "url": {
              "raw": "http://localhost:8080/api/classes",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "classes"]
            }
          }
        }
      ]
    },
    {
      "name": "Cours",
      "item": [
        {
          "name": "Créer Cours",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"nomCours\": \"Mathématiques\",\n  \"ponderation\": 3,\n  \"classeId\": 1,\n  \"professeurId\": 1\n}"
            },
            "url": {
              "raw": "http://localhost:8080/api/cours",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "cours"]
            }
          }
        },
        {
          "name": "Obtenir tous les cours",
          "request": {
            "method": "GET",
            "url": {
              "raw": "http://localhost:8080/api/cours",
              "protocol": "http",
              "host": ["localhost"],
              "port": "8080",
              "path": ["api", "cours"]
            }
          }
        }
      ]
    }
  ]
}
```

---

## Notes importantes

1. **Ordre de création:** Créez toujours dans cet ordre :
   - Classes
   - Professeurs
   - Cours

2. **IDs:** Remplacez les IDs (classeId, professeurId) par les valeurs réelles retournées par vos créations.

3. **Même cours, classes différentes:** Vous pouvez créer "Mathématiques" pour la 1ère, 2ème, 3ème avec des pondérations différentes.

4. **Pondération:** Plus la classe est avancée, plus la pondération peut être élevée (3 pour 1ère, 4 pour 2ème, 5 pour 3ème, etc.)
