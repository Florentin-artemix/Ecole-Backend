# 🎓 Système de Configuration d'École - Documentation Complète

## 📋 Résumé des Modifications

### ✅ Fichiers Créés (Backend)

1. **Entité**
   - `Ecole.java` - Entité principale avec tous les champs (nom, code, ville, commune, adresse, téléphone, email, devise)

2. **Repository**
   - `EcoleRepository.java` - Interface JPA avec méthodes findByCodeEcole() et existsByCodeEcole()

3. **DTOs**
   - `EcoleDTO.java` - DTO pour les réponses
   - `EcoleCreateUpdateDTO.java` - DTO pour la création/modification avec validation

4. **Service**
   - `EcoleService.java` - Logique métier complète

5. **Controller**
   - `EcoleController.java` - API REST avec CORS activé

6. **Migration SQL**
   - `migration_ecole.sql` - Script de migration de la base de données

---

## 🔧 Modifications des Fichiers Existants

### 1. **Entité Eleve** ✅
   - Remplacé `private String ecole` par `private Ecole ecole` (relation @ManyToOne)
   - Ajouté l'annotation `@JoinColumn(name = "ecole_id", nullable = false)`

### 2. **DTOs mis à jour** ✅
   - `EleveDTO` - Champ `ecole` changé de String à EcoleDTO
   - `EnfantDTO` - Champ `ecole` changé de String à EcoleDTO
   - `ParentEleveDTO` - Champ `eleveEcole` changé de String à EcoleDTO
   - `BulletinDTO` - Champ `ecole` changé de String à EcoleDTO

### 3. **Services mis à jour** ✅
   - `EleveService` - Gère maintenant la conversion Ecole ↔ EcoleDTO
   - `ParentEleveService` - Convertit Ecole en EcoleDTO dans EnfantDTO et ParentEleveDTO
   - `BulletinService` - Convertit Ecole en EcoleDTO dans BulletinDTO

### 4. **Calcul du Bulletin Corrigé** ✅
   - **Total général** = Somme des notes (10 + 25 + 4 = 39)
   - **Maximum général** = Somme des pondérations (20 + 40 + 10 = 70)
   - **Pourcentage** = (39 × 100) / 70 = 55.7%

---

## 🌐 API Endpoints - École

### Base URL: `http://localhost:8080/api/ecole`

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/api/ecole` | Récupérer les infos de l'école (première configurée) |
| GET | `/api/ecole/all` | Récupérer toutes les écoles |
| GET | `/api/ecole/{id}` | Récupérer une école par ID |
| POST | `/api/ecole` | Créer une nouvelle école |
| PUT | `/api/ecole/{id}` | Mettre à jour une école |
| DELETE | `/api/ecole/{id}` | Supprimer une école |

---

## 📝 Exemple de Requête POST

```json
{
  "nomEcole": "Institut Umoja",
  "codeEcole": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira",
  "adresse": "Avenue de la Paix, n°123",
  "telephone": "+243 123 456 789",
  "email": "info@institutumoja.cd",
  "devise": "Éduquer pour transformer"
}
```

---

## 📝 Exemple de Réponse GET

```json
{
  "id": 1,
  "nomEcole": "Institut Umoja",
  "codeEcole": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira",
  "adresse": "Avenue de la Paix, n°123",
  "telephone": "+243 123 456 789",
  "email": "info@institutumoja.cd",
  "devise": "Éduquer pour transformer"
}
```

---

## 🗄️ Migration de la Base de Données

### Étapes à suivre :

1. **Exécuter le script SQL** : `migration_ecole.sql`
   - Crée la table `ecole`
   - Ajoute la colonne `ecole_id` dans la table `eleve`
   - Insère une école par défaut
   - Lie tous les élèves existants à cette école

2. **Après vérification**, décommenter la ligne suivante dans le script :
   ```sql
   ALTER TABLE eleve DROP COLUMN ecole;
   ```
   Pour supprimer l'ancienne colonne `ecole` (String)

---

## 📊 Nouveau Format du Bulletin

### Avant (Problématique)
```json
{
  "ecole": "Institut Umoja",
  "totalGeneral": 300,
  "maximumGeneral": 400,
  "pourcentage": 75
}
```

### Après (Correct)
```json
{
  "ecole": {
    "id": 1,
    "nomEcole": "Institut Umoja",
    "codeEcole": "EP1234",
    "ville": "Bukavu",
    "commune_territoire": "Bagira",
    "adresse": "Avenue de la Paix, n°123",
    "telephone": "+243 123 456 789",
    "email": "info@institutumoja.cd",
    "devise": "Éduquer pour transformer"
  },
  "totalGeneral": 39,
  "maximumGeneral": 70,
  "pourcentage": 55.71
}
```

---

## 🚀 Démarrage

### 1. Exécuter la migration SQL
```bash
mysql -u root -p nom_de_votre_base < migration_ecole.sql
```

### 2. Redémarrer l'application Spring Boot
```bash
cd "C:\Users\NERIA FLORENTIN\Downloads\demo"
mvnw.cmd spring-boot:run
```

### 3. Tester l'API
```bash
# Créer une école
curl -X POST http://localhost:8080/api/ecole \
  -H "Content-Type: application/json" \
  -d '{
    "nomEcole": "Institut Umoja",
    "codeEcole": "EP1234",
    "ville": "Bukavu",
    "commune_territoire": "Bagira",
    "devise": "Éduquer pour transformer"
  }'

# Récupérer les infos de l'école
curl http://localhost:8080/api/ecole
```

---

## ✅ Validation

Tous les fichiers ont été compilés avec succès, aucune erreur détectée ! ✨

### Fichiers validés :
- ✅ Ecole.java (Entité)
- ✅ Eleve.java (Relation ManyToOne mise à jour)
- ✅ EcoleRepository.java
- ✅ EcoleService.java
- ✅ EcoleController.java
- ✅ Tous les DTOs (EcoleDTO, EleveDTO, EnfantDTO, ParentEleveDTO, BulletinDTO)
- ✅ Tous les Services (EleveService, ParentEleveService, BulletinService)
- ✅ NoteService.java (Calcul du bulletin corrigé)

---

## 🎯 Avantages du Nouveau Système

1. **Données normalisées** : Une seule source de vérité pour les infos de l'école
2. **Facilité de maintenance** : Modifier l'école une seule fois au lieu de mettre à jour tous les élèves
3. **Données riches** : Le bulletin contient maintenant toutes les infos de l'école (téléphone, email, devise, etc.)
4. **Évolutivité** : Facilite l'ajout de nouvelles écoles (système multi-écoles)
5. **Calcul correct** : Les totaux et pourcentages sont maintenant réalistes

---

## 📌 Notes Importantes

- Le CORS est activé pour `http://localhost:5173` (Vite/React)
- Les validations sont en place (email, taille des champs, etc.)
- Le code école est unique (contrainte UNIQUE en base de données)
- La relation Eleve ↔ Ecole utilise `FetchType.EAGER` pour charger automatiquement les données

---

## 🐛 Résolution de Problèmes

### Si vous avez des erreurs de compilation :
```bash
mvnw.cmd clean install -DskipTests
```

### Si la migration échoue :
Vérifiez que l'ancienne colonne `ecole` existe encore dans la table `eleve` avant de la supprimer.

---

**Créé le : 11 janvier 2025**
**Statut : ✅ Prêt pour la production**
