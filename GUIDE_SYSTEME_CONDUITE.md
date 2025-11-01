# Guide du Système de Conduite

## 📋 Vue d'ensemble

Le système de conduite permet maintenant à **chaque professeur** d'attribuer une évaluation de conduite à chaque élève pour chaque période. La conduite affichée sur le bulletin sera **la conduite la plus fréquemment attribuée** par l'ensemble des professeurs.

## 🎯 Types de Conduite Disponibles

1. **EXCELLENT** - Excellent
2. **TRES_BON** - Très Bon
3. **BON** - Bon
4. **ASSEZ_BON** - Assez Bon
5. **PASSABLE** - Passable
6. **MEDIOCRE** - Médiocre
7. **MAUVAIS** - Mauvais

## 📊 Comment ça fonctionne ?

### Exemple pratique :

Si un élève reçoit les évaluations suivantes pour la 1ère période :
- Prof de Maths : **BON**
- Prof de Français : **TRES_BON**
- Prof de Physique : **BON**
- Prof d'Histoire : **BON**

➡️ Le bulletin affichera : **"Bon"** (car c'est la conduite la plus répétée : 3 fois)

Si aucun professeur n'a évalué la conduite d'un élève, le bulletin affichera : **"Non évalué"**

## 🔧 API Endpoints

### 1. Créer une conduite unique
**POST** `/api/conduites`

```json
{
  "eleveId": 1,
  "professeurId": 2,
  "typeConduite": "BON",
  "periode": "PREMIERE",
  "commentaire": "Élève respectueux et attentif"
}
```

### 2. Créer plusieurs conduites en batch (RECOMMANDÉ)
**POST** `/api/conduites/batch`

Utilisez le fichier `test_conduites_batch.json` fourni :

```json
[
  {
    "eleveId": 1,
    "professeurId": 1,
    "typeConduite": "BON",
    "periode": "PREMIERE",
    "commentaire": "Élève respectueux"
  },
  {
    "eleveId": 1,
    "professeurId": 2,
    "typeConduite": "TRES_BON",
    "periode": "PREMIERE",
    "commentaire": "Excellente participation"
  }
]
```

### 3. Récupérer les conduites d'un élève pour une période
**GET** `/api/conduites/eleve/{eleveId}/periode/{periode}`

Exemple : `/api/conduites/eleve/1/periode/PREMIERE`

### 4. Récupérer la conduite la plus fréquente (pour le bulletin)
**GET** `/api/conduites/eleve/{eleveId}/periode/{periode}/most-frequent`

Exemple : `/api/conduites/eleve/1/periode/PREMIERE`

Retourne directement : `"Bon"` ou `"Non évalué"`

### 5. Récupérer les conduites attribuées par un professeur
**GET** `/api/conduites/professeur/{professeurId}/periode/{periode}`

Exemple : `/api/conduites/professeur/2/periode/PREMIERE`

### 6. Modifier une conduite
**PUT** `/api/conduites/{id}`

```json
{
  "eleveId": 1,
  "professeurId": 2,
  "typeConduite": "EXCELLENT",
  "periode": "PREMIERE",
  "commentaire": "Commentaire modifié"
}
```

### 7. Supprimer une conduite
**DELETE** `/api/conduites/{id}`

## 📝 Test dans Postman

### Étape 1 : Créer plusieurs conduites
1. **Méthode** : POST
2. **URL** : `http://localhost:8080/api/conduites/batch`
3. **Headers** : `Content-Type: application/json`
4. **Body** : Copiez le contenu de `test_conduites_batch.json`

### Étape 2 : Générer le bulletin
1. **Méthode** : GET
2. **URL** : `http://localhost:8080/api/bulletins/eleve/1/periode/PREMIERE`

➡️ La conduite sera maintenant calculée automatiquement !

## 🎓 Workflow Recommandé

### Pour chaque période :

1. **Chaque professeur** attribue une conduite à ses élèves via POST `/api/conduites` ou `/api/conduites/batch`

2. **Le système calcule automatiquement** la conduite la plus fréquente lors de la génération du bulletin

3. **Le bulletin affiche** la conduite majoritaire attribuée par les professeurs

## 📊 Exemple de Scénario Complet

### Scénario : Élève Jean Mukendi (ID: 1) - 1ère Période

**Évaluations des professeurs :**
```
Prof Maths (ID: 2)      → BON
Prof Français (ID: 3)   → TRES_BON  
Prof Physique (ID: 4)   → BON
Prof Histoire (ID: 5)   → BON
Prof Anglais (ID: 6)    → ASSEZ_BON
```

**Résultat :** Le bulletin affichera **"Bon"** (3 occurrences)

## 🗄️ Structure de la Base de Données

Une nouvelle table `conduite` a été créée :

```sql
CREATE TABLE conduite (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    professeur_id BIGINT NOT NULL,
    type_conduite VARCHAR(50) NOT NULL,
    periode VARCHAR(50) NOT NULL,
    commentaire VARCHAR(500),
    FOREIGN KEY (eleve_id) REFERENCES eleve(id),
    FOREIGN KEY (professeur_id) REFERENCES utilisateur(id)
);
```

## ⚠️ Points Importants

1. **Chaque professeur** peut attribuer UNE conduite par élève par période
2. **La conduite sur le bulletin** est calculée dynamiquement (la plus fréquente)
3. Si **égalité** entre plusieurs conduites, la première dans l'ordre alphabétique est choisie
4. Si **aucune conduite** n'est attribuée, le bulletin affiche "Non évalué"
5. Les **commentaires** de chaque professeur sont sauvegardés mais pas affichés sur le bulletin (peuvent être consultés via l'API)

## 🚀 Prochaines Étapes

1. Ajustez les IDs dans `test_conduites_batch.json` selon vos données
2. Testez la création de conduites via Postman
3. Générez un bulletin pour vérifier que la conduite s'affiche correctement
4. Chaque professeur peut maintenant attribuer des conduites à ses élèves !
