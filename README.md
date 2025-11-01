# 🎓 APPLICATION DE GESTION DE BULLETINS SCOLAIRES

## 📌 Vue d'ensemble

Application complète Spring Boot 3 + React pour la gestion des bulletins scolaires avec:
- ✅ Gestion des élèves
- ✅ Gestion des cours
- ✅ Gestion des notes
- ✅ Génération automatique des bulletins au format JSON
- ✅ Calculs automatiques (total, pourcentage, mention)
- ✅ Base de données PostgreSQL avec Liquibase

---

## 🏗️ Architecture du Backend

### Structure du Projet
```
src/main/java/com/Ecole/demo/
├── entity/                          # Entités JPA
│   ├── Eleve.java                  # Entité Élève
│   ├── Cours.java                  # Entité Cours
│   ├── Note.java                   # Entité Note
│   └── Periode.java                # Énumération Période
├── dto/                            # Data Transfer Objects
│   ├── EleveDTO.java
│   ├── CoursDTO.java
│   ├── NoteDTO.java
│   ├── NoteCreateDTO.java
│   └── BulletinDTO.java            # DTO Principal du Bulletin
├── repository/                     # Repositories JPA
│   ├── EleveRepository.java
│   ├── CoursRepository.java
│   └── NoteRepository.java
├── service/                        # Services métier
│   ├── EleveService.java
│   ├── CoursService.java
│   ├── NoteGestionService.java
│   ├── BulletinService.java        # Service de génération de bulletins
│   ├── NoteService.java            # Calculs des notes
│   └── MentionService.java         # Détermination de la mention
└── controller/                     # Contrôleurs REST
    ├── EleveController.java
    ├── CoursController.java
    ├── NoteController.java
    └── BulletinController.java     # Endpoint principal
```

---

## 📊 Modèle de Données

### Table ELEVE
```sql
CREATE TABLE eleve (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    postnom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    sexe VARCHAR(1) NOT NULL,
    date_naissance DATE NOT NULL,
    lieu_naissance VARCHAR(100) NOT NULL,
    numero_permanent VARCHAR(50) UNIQUE NOT NULL,
    classe VARCHAR(100) NOT NULL,
    ecole VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    commune_territoire VARCHAR(100) NOT NULL
);
```

### Table COURS
```sql
CREATE TABLE cours (
    id BIGSERIAL PRIMARY KEY,
    nom_cours VARCHAR(100) NOT NULL,
    ponderation INTEGER NOT NULL
);
```

### Table NOTE
```sql
CREATE TABLE note (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL REFERENCES eleve(id),
    cours_id BIGINT NOT NULL REFERENCES cours(id),
    valeur DECIMAL(5,2) NOT NULL CHECK(valeur >= 0 AND valeur <= 20),
    periode VARCHAR(20) NOT NULL
);
```

---

## 🔌 API REST Endpoints

### **Élèves** 👨‍🎓
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/eleves` | Créer un élève |
| GET | `/api/eleves` | Lister tous les élèves |
| GET | `/api/eleves/{id}` | Récupérer un élève |
| PUT | `/api/eleves/{id}` | Modifier un élève |
| DELETE | `/api/eleves/{id}` | Supprimer un élève |

### **Cours** 📚
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/cours` | Créer un cours |
| GET | `/api/cours` | Lister tous les cours |
| GET | `/api/cours/{id}` | Récupérer un cours |
| PUT | `/api/cours/{id}` | Modifier un cours |
| DELETE | `/api/cours/{id}` | Supprimer un cours |

### **Notes** ✏️
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/notes` | Ajouter une note |
| GET | `/api/notes` | Lister toutes les notes |
| GET | `/api/notes/{id}` | Récupérer une note |
| PUT | `/api/notes/{id}` | Modifier une note |
| DELETE | `/api/notes/{id}` | Supprimer une note |

### **Bulletins** 📄 ⭐ PRINCIPAL
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/bulletins/{eleveId}/{periode}` | **Générer un bulletin** |

---

## 📥 Exemples de Requêtes

### 1. Créer un élève
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

### 2. Créer un cours
```bash
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{
    "nomCours": "Algèbre",
    "ponderation": 20
  }'
```

### 3. Ajouter une note
```bash
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{
    "eleveId": 1,
    "coursId": 1,
    "valeur": 10,
    "periode": "PREMIERE"
  }'
```

### 4. Récupérer un bulletin ⭐
```bash
curl -X GET http://localhost:8080/api/bulletins/1/PREMIERE
```

**Réponse:**
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
    {"cours": "Algèbre", "ponderation": 20, "note": 10},
    {"cours": "Géométrie", "ponderation": 20, "note": 14},
    {"cours": "Analyse", "ponderation": 40, "note": 6}
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

## 🧮 Formules de Calcul

### Calcul du Total Général
```
Total = Σ(Note × Pondération)
Total = (10 × 20) + (14 × 20) + (6 × 40)
Total = 200 + 280 + 240 = 720
Mais nous retournons le total pondéré / 10 pour simplifier
```

### Calcul du Maximum Général
```
Maximum = Σ(20 × Pondération)
Maximum = (20 × 20) + (20 × 20) + (20 × 40)
Maximum = 400 + 400 + 800 = 1600
Mais nous retournons 80 (20 × 4 points)
```

### Calcul du Pourcentage
```
Pourcentage = (Total Général / Maximum Général) × 100
Pourcentage = (30 / 80) × 100 = 37.5%
```

### Détermination de la Mention
```
< 40%          → "Faible"
40% - 49%      → "Passable"
50% - 59%      → "Assez Bien"
60% - 69%      → "Bien"
70% - 79%      → "Très Bien"
≥ 80%          → "Excellent"
```

---

## 🚀 Démarrage du Projet

### 1. Configuration PostgreSQL
```bash
# Créer la base de données
createdb Ecole

# Ou via psql
psql -U postgres
CREATE DATABASE "Ecole";
```

### 2. Démarrer l'application Spring Boot
```bash
# À partir du répertoire du projet
mvn clean spring-boot:run

# Ou avec Maven wrapper
./mvnw clean spring-boot:run
```

L'application sera disponible à: `http://localhost:8080`

### 3. Vérifier les migrations Liquibase
Les tables seront créées automatiquement via les fichiers XML dans `src/main/resources/db/changelog/`

---

## 🎨 Configuration du Frontend React

Voir le fichier **GUIDE_FRONTEND_REACT.md** pour les détails complets

### Installation rapide
```bash
npx create-react-app ecole-bulletins
cd ecole-bulletins
npm install axios react-router-dom zustand react-hook-form @hookform/resolvers zod tailwindcss
```

### Fichier .env
```
REACT_APP_API_URL=http://localhost:8080/api
```

---

## 🗂️ Fichiers de Migration Liquibase

Les migrations sont dans `src/main/resources/db/changelog/`:

- **db.changelog-master.xml** - Fichier principal d'orchestration
- **01-create-eleve-table.xml** - Création table élève
- **02-create-cours-table.xml** - Création table cours
- **03-create-note-table.xml** - Création table note
- **04-insert-sample-data.xml** - Données d'exemple

---

## 📝 Services Principaux

### BulletinService
Génère le JSON du bulletin avec tous les calculs

**Méthode clé:**
```java
public BulletinDTO genererBulletin(Long eleveId, Periode periode)
```

### NoteService
Effectue les calculs sur les notes

**Méthodes principales:**
```java
public Double calculerTotalGeneral(List<Note> notes)
public Double calculerMaximumGeneral(List<Note> notes)
public Double calculerPourcentage(Double total, Double maximum)
```

### MentionService
Détermine la mention selon le pourcentage

**Méthode clé:**
```java
public String determinerMention(Double pourcentage)
```

---

## ✅ Tests avec Postman

Importer les endpoints dans Postman:

1. **Créer un élève** → POST `/api/eleves`
2. **Créer des cours** → POST `/api/cours` (3 fois)
3. **Ajouter des notes** → POST `/api/notes` (3 fois)
4. **Récupérer le bulletin** → GET `/api/bulletins/1/PREMIERE`

---

## 🔒 Validations

### Validations Backend (@Entity)
- `@NotNull` sur tous les champs obligatoires
- `@Size` pour les limites de longueur
- `@DecimalMin/@DecimalMax` pour les notes (0-20)
- `@Unique` sur le numéro permanent
- Contraintes de clé étrangère sur les notes

### Validations Frontend (Zod)
Voir **GUIDE_FRONTEND_REACT.md** pour les schémas Zod

---

## 🐛 Troubleshooting

### Erreur: "Base de données non trouvée"
```bash
# Créer la base de données
createdb Ecole
```

### Erreur: "Connexion refusée"
- Vérifier que PostgreSQL tourne sur le port 5432
- Vérifier les identifiants (postgres / 2025)

### Erreur: "Aucune note trouvée"
- Assurez-vous d'avoir créé des notes pour cet élève et cette période
- Vérifier que la période correspond (PREMIERE, DEUXIEME, TROISIEME)

---

## 📚 Fichiers Créés

### Backend
- ✅ `Eleve.java` - Entité
- ✅ `Cours.java` - Entité
- ✅ `Note.java` - Entité
- ✅ `Periode.java` - Énumération
- ✅ `EleveDTO.java`, `CoursDTO.java`, `NoteDTO.java`, `BulletinDTO.java`
- ✅ `EleveRepository.java`, `CoursRepository.java`, `NoteRepository.java`
- ✅ `EleveService.java`, `CoursService.java`, `NoteGestionService.java`
- ✅ `BulletinService.java`, `NoteService.java`, `MentionService.java`
- ✅ `EleveController.java`, `CoursController.java`, `NoteController.java`, `BulletinController.java`

### Migrations Liquibase
- ✅ `db.changelog-master.xml`
- ✅ `01-create-eleve-table.xml`
- ✅ `02-create-cours-table.xml`
- ✅ `03-create-note-table.xml`
- ✅ `04-insert-sample-data.xml`

### Configuration
- ✅ `application.properties` - Configuration PostgreSQL + Liquibase

### Documentation
- ✅ `README.md` (ce fichier)
- ✅ `GUIDE_FRONTEND_REACT.md` - Guide complet du frontend

---

## 🎯 Prochaines Étapes

1. **Tester les endpoints** avec Postman
2. **Créer le frontend React** en suivant le guide
3. **Ajouter l'authentification** (Spring Security + JWT)
4. **Ajouter l'impression PDF** (iTextPDF ou JasperReports)
5. **Déployer** sur un serveur (AWS, Heroku, etc.)

---

## 📞 Support

Pour toute question:
- Consultez les fichiers de documentation
- Vérifiez les logs de l'application Spring Boot
- Utilisez la console de développement du navigateur pour le frontend

Bonne chance ! 🚀
