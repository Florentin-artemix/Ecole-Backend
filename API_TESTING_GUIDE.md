# 🧪 TEST API - GESTION DE BULLETINS SCOLAIRES

## 📋 Guide de Test Complet

Ce fichier contient tous les exemples de requêtes HTTP pour tester l'API backend.

Vous pouvez utiliser:
- **Postman** (interface graphique)
- **cURL** (ligne de commande)
- **REST Client** (VS Code extension)
- **Thunder Client** (VS Code extension)

---

## 🔧 Configuration Base

**URL Base:** `http://localhost:8080/api`

**Headers par défaut:**
```
Content-Type: application/json
Accept: application/json
```

---

## 👨‍🎓 ÉLÈVES - Endpoints de Gestion

### 1️⃣ Créer un élève
```http
POST /api/eleves
Content-Type: application/json

{
  "nom": "Kabongo",
  "postnom": "Florent",
  "prenom": "",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira"
}
```

**Réponse (201 Created):**
```json
{
  "id": 1,
  "nom": "Kabongo",
  "postnom": "Florent",
  "prenom": "",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira"
}
```

---

### 2️⃣ Récupérer tous les élèves
```http
GET /api/eleves
```

**Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "nom": "Kabongo",
    "postnom": "Florent",
    "prenom": "",
    "sexe": "M",
    "dateNaissance": "2008-04-12",
    "lieuNaissance": "Bukavu",
    "numeroPermanent": "12345",
    "classe": "3e Scientifique",
    "ecole": "Institut Umoja",
    "code": "EP1234",
    "ville": "Bukavu",
    "commune_territoire": "Bagira"
  }
]
```

---

### 3️⃣ Récupérer un élève spécifique
```http
GET /api/eleves/1
```

**Réponse (200 OK):**
```json
{
  "id": 1,
  "nom": "Kabongo",
  "postnom": "Florent",
  "prenom": "",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira"
}
```

---

### 4️⃣ Modifier un élève
```http
PUT /api/eleves/1
Content-Type: application/json

{
  "nom": "Kabongo",
  "postnom": "Florent",
  "prenom": "Jean",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "Terminale Scientifique",
  "ecole": "Institut Umoja",
  "code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira"
}
```

**Réponse (200 OK):** Élève modifié

---

### 5️⃣ Supprimer un élève
```http
DELETE /api/eleves/1
```

**Réponse (204 No Content):** Aucun contenu

---

## 📚 COURS - Endpoints de Gestion

### 1️⃣ Créer un cours
```http
POST /api/cours
Content-Type: application/json

{
  "nomCours": "Algèbre",
  "ponderation": 20
}
```

**Réponse (201 Created):**
```json
{
  "id": 1,
  "nomCours": "Algèbre",
  "ponderation": 20
}
```

---

### 2️⃣ Créer le 2ème cours
```http
POST /api/cours
Content-Type: application/json

{
  "nomCours": "Géométrie",
  "ponderation": 20
}
```

**Réponse:**
```json
{
  "id": 2,
  "nomCours": "Géométrie",
  "ponderation": 20
}
```

---

### 3️⃣ Créer le 3ème cours
```http
POST /api/cours
Content-Type: application/json

{
  "nomCours": "Analyse",
  "ponderation": 40
}
```

**Réponse:**
```json
{
  "id": 3,
  "nomCours": "Analyse",
  "ponderation": 40
}
```

---

### 4️⃣ Récupérer tous les cours
```http
GET /api/cours
```

**Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "nomCours": "Algèbre",
    "ponderation": 20
  },
  {
    "id": 2,
    "nomCours": "Géométrie",
    "ponderation": 20
  },
  {
    "id": 3,
    "nomCours": "Analyse",
    "ponderation": 40
  }
]
```

---

### 5️⃣ Récupérer un cours spécifique
```http
GET /api/cours/1
```

---

### 6️⃣ Modifier un cours
```http
PUT /api/cours/1
Content-Type: application/json

{
  "nomCours": "Algèbre Linéaire",
  "ponderation": 25
}
```

---

### 7️⃣ Supprimer un cours
```http
DELETE /api/cours/1
```

---

## ✏️ NOTES - Endpoints de Gestion

### 1️⃣ Ajouter une note - Algèbre
```http
POST /api/notes
Content-Type: application/json

{
  "eleveId": 1,
  "coursId": 1,
  "valeur": 10,
  "periode": "PREMIERE"
}
```

**Réponse (201 Created):**
```json
{
  "id": 1,
  "eleve": {
    "id": 1,
    "nom": "Kabongo",
    "postnom": "Florent",
    ...
  },
  "cours": {
    "id": 1,
    "nomCours": "Algèbre",
    "ponderation": 20
  },
  "valeur": 10,
  "periode": "PREMIERE"
}
```

---

### 2️⃣ Ajouter une note - Géométrie
```http
POST /api/notes
Content-Type: application/json

{
  "eleveId": 1,
  "coursId": 2,
  "valeur": 14,
  "periode": "PREMIERE"
}
```

---

### 3️⃣ Ajouter une note - Analyse
```http
POST /api/notes
Content-Type: application/json

{
  "eleveId": 1,
  "coursId": 3,
  "valeur": 6,
  "periode": "PREMIERE"
}
```

---

### 4️⃣ Récupérer toutes les notes
```http
GET /api/notes
```

**Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "eleve": {...},
    "cours": {...},
    "valeur": 10,
    "periode": "PREMIERE"
  },
  {
    "id": 2,
    "eleve": {...},
    "cours": {...},
    "valeur": 14,
    "periode": "PREMIERE"
  },
  {
    "id": 3,
    "eleve": {...},
    "cours": {...},
    "valeur": 6,
    "periode": "PREMIERE"
  }
]
```

---

### 5️⃣ Récupérer une note spécifique
```http
GET /api/notes/1
```

---

### 6️⃣ Modifier une note
```http
PUT /api/notes/1
Content-Type: application/json

{
  "eleveId": 1,
  "coursId": 1,
  "valeur": 12,
  "periode": "PREMIERE"
}
```

---

### 7️⃣ Supprimer une note
```http
DELETE /api/notes/1
```

---

## 📄 BULLETINS - Endpoint Principal ⭐

### 🎯 Récupérer le Bulletin Complet
```http
GET /api/bulletins/1/PREMIERE
```

**Réponse (200 OK) - FORMAT JSON COMPLET:**
```json
{
  "nomComplet": "Kabongo Florent",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "periode": "1ère période",
  "Code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira",
  "notes": [
    {
      "cours": "Algèbre",
      "ponderation": 20,
      "note": 10
    },
    {
      "cours": "Géométrie",
      "ponderation": 20,
      "note": 14
    },
    {
      "cours": "Analyse",
      "ponderation": 40,
      "note": 6
    }
  ],
  "totalGeneral": 30,
  "maximumGeneral": 80,
  "pourcentage": 37.5,
  "mention": "Faible",
  "conduite": "Bon",
  "place_nbreEleve": "25/26"
}
```

---

### 📊 Explication des Calculs

**Pour cet exemple:**

1. **Total Général:**
   - (10 × 20) + (14 × 20) + (6 × 40) = 200 + 280 + 240 = 720
   - Normalisé: 720 / 24 = 30

2. **Maximum Général:**
   - (20 × 20) + (20 × 20) + (20 × 40) = 400 + 400 + 800 = 1600
   - Normalisé: 1600 / 20 = 80

3. **Pourcentage:**
   - (30 / 80) × 100 = 37.5%

4. **Mention:**
   - 37.5% < 40% → **"Faible"**

---

## 🔄 Autres Périodes

### Tester la 2ème période
```http
GET /api/bulletins/1/DEUXIEME
```

### Tester la 3ème période
```http
GET /api/bulletins/1/TROISIEME
```

---

## 📝 Scénario Complet de Test

### Étape 1: Créer un élève
```bash
curl -X POST http://localhost:8080/api/eleves \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Kabongo",
    "postnom": "Florent",
    "prenom": "",
    "sexe": "M",
    "dateNaissance": "2008-04-12",
    "lieuNaissance": "Bukavu",
    "numeroPermanent": "12345",
    "classe": "3e Scientifique",
    "ecole": "Institut Umoja",
    "code": "EP1234",
    "ville": "Bukavu",
    "commune_territoire": "Bagira"
  }'
```

### Étape 2: Créer 3 cours
```bash
# Algèbre
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{"nomCours": "Algèbre", "ponderation": 20}'

# Géométrie
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{"nomCours": "Géométrie", "ponderation": 20}'

# Analyse
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{"nomCours": "Analyse", "ponderation": 40}'
```

### Étape 3: Ajouter 3 notes
```bash
# Note Algèbre = 10
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "eleveId": 1,
    "coursId": 1,
    "valeur": 10,
    "periode": "PREMIERE"
  }'

# Note Géométrie = 14
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "eleveId": 1,
    "coursId": 2,
    "valeur": 14,
    "periode": "PREMIERE"
  }'

# Note Analyse = 6
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "eleveId": 1,
    "coursId": 3,
    "valeur": 6,
    "periode": "PREMIERE"
  }'
```

### Étape 4: Récupérer le bulletin
```bash
curl -X GET http://localhost:8080/api/bulletins/1/PREMIERE
```

---

## ✅ Checklist de Vérification

- [ ] Élève créé avec succès
- [ ] 3 cours créés avec succès
- [ ] 3 notes ajoutées avec succès
- [ ] Bulletin généré avec succès
- [ ] Total Général = 30 ✓
- [ ] Maximum Général = 80 ✓
- [ ] Pourcentage = 37.5% ✓
- [ ] Mention = "Faible" ✓
- [ ] Conduite = "Bon" ✓
- [ ] Place = "25/26" ✓

---

## 🐛 Codes d'Erreur Possibles

| Code | Signification | Solution |
|------|---------------|----------|
| 200 | OK | Tout va bien ✓ |
| 201 | Created | Ressource créée ✓ |
| 204 | No Content | Suppression réussie ✓ |
| 400 | Bad Request | Vérifier le format JSON |
| 404 | Not Found | L'ID n'existe pas |
| 409 | Conflict | Numéro permanent déjà existant |
| 500 | Server Error | Erreur serveur, vérifier les logs |

---

## 📊 Test Avancé - Plusieurs Élèves

### Créer un 2ème élève
```http
POST /api/eleves
Content-Type: application/json

{
  "nom": "Mukwaya",
  "postnom": "Jean",
  "prenom": "Claude",
  "sexe": "M",
  "dateNaissance": "2007-08-20",
  "lieuNaissance": "Uvira",
  "numeroPermanent": "67890",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "code": "EP5678",
  "ville": "Uvira",
  "commune_territoire": "Fizi"
}
```

### Ajouter des notes pour le 2ème élève
```http
POST /api/notes
Content-Type: application/json

{
  "eleveId": 2,
  "coursId": 1,
  "valeur": 15,
  "periode": "PREMIERE"
}
```

### Récupérer le bulletin du 2ème élève
```http
GET /api/bulletins/2/PREMIERE
```

---

## 🎓 Test des Mentions

Testez les différentes mentions en variant les notes:

### Mention: "Faible" (< 40%)
Notes: 6, 8, 5 → Mention: "Faible"

### Mention: "Passable" (40-50%)
Notes: 10, 12, 8 → Mention: "Passable"

### Mention: "Assez Bien" (50-60%)
Notes: 12, 14, 10 → Mention: "Assez Bien"

### Mention: "Bien" (60-70%)
Notes: 14, 16, 12 → Mention: "Bien"

### Mention: "Très Bien" (70-80%)
Notes: 16, 18, 15 → Mention: "Très Bien"

### Mention: "Excellent" (≥ 80%)
Notes: 18, 19, 20 → Mention: "Excellent"

---

## 💾 Export Postman Collection

Pour importer dans Postman, créez une collection avec toutes les requêtes ci-dessus.

**Format Postman v2.1:**
```json
{
  "info": {
    "name": "Gestion Bulletins Scolaires",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Élèves",
      "item": [
        {
          "name": "Créer Élève",
          "request": {
            "method": "POST",
            "url": "http://localhost:8080/api/eleves"
          }
        }
      ]
    }
  ]
}
```

---

Bon testing ! 🚀
