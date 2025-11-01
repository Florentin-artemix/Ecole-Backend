# 🧪 TEST API - MISE À JOUR AVEC UTILISATEURS

## 📋 Nouveaux Endpoints Utilisateurs

### 1️⃣ Créer un Utilisateur (Professeur)
```http
POST /api/utilisateurs
Content-Type: application/json

{
  "nomComplet": "Dr. Jean Mukendi",
  "role": "PROFESSEUR",
  "telephone": "+243123456789",
  "email": "jean.mukendi@umoja.edu",
  "motDePasse": "professeur123"
}
```

**Réponse (201 Created):**
```json
{
  "id": 1,
  "nomComplet": "Dr. Jean Mukendi",
  "role": "PROFESSEUR",
  "telephone": "+243123456789",
  "email": "jean.mukendi@umoja.edu",
  "actif": true
}
```

---

### 2️⃣ Récupérer tous les Utilisateurs
```http
GET /api/utilisateurs
```

**Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "nomComplet": "Dr. Jean Mukendi",
    "role": "PROFESSEUR",
    "telephone": "+243123456789",
    "email": "jean.mukendi@umoja.edu",
    "actif": true
  },
  {
    "id": 2,
    "nomComplet": "Mme. Marie Kalonda",
    "role": "PROFESSEUR",
    "telephone": "+243987654321",
    "email": "marie.kalonda@umoja.edu",
    "actif": true
  }
]
```

---

### 3️⃣ Récupérer les Utilisateurs par Rôle
```http
GET /api/utilisateurs/role/PROFESSEUR
```

**Réponse (200 OK):** Liste des professeurs uniquement

```http
GET /api/utilisateurs/role/ADMIN
GET /api/utilisateurs/role/PARENT
GET /api/utilisateurs/role/PERCEPTEUR
```

---

### 4️⃣ Récupérer un Utilisateur Spécifique
```http
GET /api/utilisateurs/1
```

---

### 5️⃣ Modifier un Utilisateur
```http
PUT /api/utilisateurs/1
Content-Type: application/json

{
  "nomComplet": "Dr. Jean Mukendi (Modifié)",
  "role": "PROFESSEUR",
  "telephone": "+243123456789",
  "email": "jean.mukendi@umoja.edu",
  "motDePasse": "nouveaumotdepasse123"
}
```

---

### 6️⃣ Supprimer un Utilisateur
```http
DELETE /api/utilisateurs/1
```

**Réponse (204 No Content)**

---

## 🎓 COURS - Endpoints Mis à Jour

### 1️⃣ Créer un Cours (AVEC PROFESSEUR)
```http
POST /api/cours
Content-Type: application/json

{
  "nomCours": "Algèbre",
  "ponderation": 20,
  "professeurId": 1
}
```

**Réponse (201 Created):**
```json
{
  "id": 1,
  "nomCours": "Algèbre",
  "ponderation": 20,
  "professeurNom": "Dr. Jean Mukendi",
  "professeurId": 1
}
```

---

### 2️⃣ Récupérer tous les Cours
```http
GET /api/cours
```

**Réponse (200 OK):**
```json
[
  {
    "id": 1,
    "nomCours": "Algèbre",
    "ponderation": 20,
    "professeurNom": "Dr. Jean Mukendi",
    "professeurId": 1
  },
  {
    "id": 2,
    "nomCours": "Géométrie",
    "ponderation": 20,
    "professeurNom": "Dr. Jean Mukendi",
    "professeurId": 1
  },
  {
    "id": 3,
    "nomCours": "Analyse",
    "ponderation": 40,
    "professeurNom": "Mme. Marie Kalonda",
    "professeurId": 2
  }
]
```

---

### 3️⃣ Modifier un Cours (AVEC PROFESSEUR)
```http
PUT /api/cours/1
Content-Type: application/json

{
  "nomCours": "Algèbre Avancée",
  "ponderation": 25,
  "professeurId": 2
}
```

---

## 📊 SCÉNARIO COMPLET DE TEST - AVEC UTILISATEURS

### Étape 1: Créer 2 Professeurs
```bash
# Professeur 1
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Dr. Jean Mukendi",
    "role": "PROFESSEUR",
    "telephone": "+243123456789",
    "email": "jean.mukendi@umoja.edu",
    "motDePasse": "professeur123"
  }'

# Professeur 2
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Mme. Marie Kalonda",
    "role": "PROFESSEUR",
    "telephone": "+243987654321",
    "email": "marie.kalonda@umoja.edu",
    "motDePasse": "professeur123"
  }'
```

---

### Étape 2: Récupérer les Professeurs
```bash
curl http://localhost:8080/api/utilisateurs/role/PROFESSEUR
```

Résultat: Les IDs des professeurs (ex: 1 et 2)

---

### Étape 3: Créer 3 Cours Avec Professeurs
```bash
# Algèbre - Professeur 1
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{
    "nomCours": "Algèbre",
    "ponderation": 20,
    "professeurId": 1
  }'

# Géométrie - Professeur 1
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{
    "nomCours": "Géométrie",
    "ponderation": 20,
    "professeurId": 1
  }'

# Analyse - Professeur 2
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{
    "nomCours": "Analyse",
    "ponderation": 40,
    "professeurId": 2
  }'
```

---

### Étape 4: Créer un Élève
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

---

### Étape 5: Ajouter 3 Notes
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

---

### Étape 6: Récupérer le Bulletin Complet ⭐
```bash
curl http://localhost:8080/api/bulletins/1/PREMIERE
```

**Réponse Complète:**
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

## 📝 Tous les Types d'Utilisateurs

### 1. ADMIN (Administrateur)
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Admin Système",
    "role": "ADMIN",
    "telephone": "+243999999999",
    "email": "admin@umoja.edu",
    "motDePasse": "admin123"
  }'
```

### 2. PROFESSEUR
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Dr. Jean Mukendi",
    "role": "PROFESSEUR",
    "telephone": "+243123456789",
    "email": "professeur@umoja.edu",
    "motDePasse": "prof123"
  }'
```

### 3. PARENT
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Parent Kabongo",
    "role": "PARENT",
    "telephone": "+243777777777",
    "email": "parent@umoja.edu",
    "motDePasse": "parent123"
  }'
```

### 4. PERCEPTEUR (Collecteur de frais)
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Mr. Peter Kasongo",
    "role": "PERCEPTEUR",
    "telephone": "+243888888888",
    "email": "percepteur@umoja.edu",
    "motDePasse": "percepteur123"
  }'
```

---

## 🔄 PÉRIODES MISES À JOUR

Vous pouvez maintenant utiliser ces 5 périodes:

```bash
# 1ère période
curl http://localhost:8080/api/bulletins/1/PREMIERE

# 2e période
curl http://localhost:8080/api/bulletins/1/DEUXIEME

# 3e période
curl http://localhost:8080/api/bulletins/1/TROISIEME

# Examen premier semestre
curl http://localhost:8080/api/bulletins/1/EXAMEN_PREMIER_SEMESTRE

# Examen second semestre
curl http://localhost:8080/api/bulletins/1/EXAMEN_SECOND_SEMESTRE
```

---

## ✅ CHECKLIST FINALE

- [ ] Créer 2 professeurs
- [ ] Créer 1 administrateur
- [ ] Créer 1 parent
- [ ] Créer 1 percepteur
- [ ] Vérifier les professeurs: `GET /api/utilisateurs/role/PROFESSEUR`
- [ ] Créer 3 cours avec les professeurs
- [ ] Vérifier les cours: `GET /api/cours`
- [ ] Créer un élève
- [ ] Ajouter 3 notes pour PREMIERE
- [ ] Récupérer le bulletin PREMIERE
- [ ] Ajouter des notes pour EXAMEN_PREMIER_SEMESTRE
- [ ] Récupérer le bulletin EXAMEN_PREMIER_SEMESTRE

---

## 🐛 Erreurs Courantes

| Erreur | Cause | Solution |
|--------|-------|----------|
| 400 Bad Request | Email déjà utilisé | Utiliser un email unique |
| 404 Not Found | Professeur n'existe pas | Vérifier l'ID du professeur |
| 500 Server Error | Rôle invalide | Utiliser: ADMIN, PROFESSEUR, PARENT, PERCEPTEUR |

---

Bon testing ! 🚀
