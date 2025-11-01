# Guide Système Parent-Élève

## 📋 Vue d'ensemble

Le système Parent-Élève permet de créer et gérer les relations entre les parents (utilisateurs avec le rôle PARENT) et leurs enfants élèves. Chaque parent peut avoir plusieurs enfants, et chaque enfant peut avoir plusieurs parents (père, mère, tuteur, etc.).

## 🎯 Fonctionnalités

- ✅ Créer une relation parent-enfant
- ✅ Un parent peut avoir plusieurs enfants
- ✅ Un enfant peut avoir plusieurs parents/tuteurs
- ✅ Voir tous les enfants d'un parent avec leurs classes et écoles
- ✅ Voir tous les parents d'un élève
- ✅ Spécifier le lien de parenté (Père, Mère, Tuteur, Oncle, Tante, etc.)

## 🔧 API Endpoints

### 1. Créer une relation parent-enfant unique
**POST** `/api/parent-eleve`

```json
{
  "parentId": 1,
  "eleveId": 1,
  "lienParente": "Père"
}
```

### 2. Créer plusieurs relations en batch (RECOMMANDÉ)
**POST** `/api/parent-eleve/batch`

```json
[
  {
    "parentId": 1,
    "eleveId": 1,
    "lienParente": "Père"
  },
  {
    "parentId": 1,
    "eleveId": 2,
    "lienParente": "Père"
  }
]
```

### 3. Récupérer un parent avec tous ses enfants
**GET** `/api/parent-eleve/parent/{parentId}`

**Réponse exemple :**
```json
{
  "parentId": 1,
  "nomComplet": "Jean Mukendi",
  "email": "j.mukendi@gmail.com",
  "telephone": "+243812345678",
  "enfants": [
    {
      "eleveId": 1,
      "nomComplet": "Mukendi Trésor Junior",
      "sexe": "M",
      "dateNaissance": "2010-05-15",
      "classe": "6ème Littéraire",
      "ecole": "Lycée Excellence Kinshasa",
      "lienParente": "Père"
    },
    {
      "eleveId": 2,
      "nomComplet": "Mukendi Grace",
      "sexe": "F",
      "dateNaissance": "2012-08-20",
      "classe": "4ème Scientifique",
      "ecole": "Lycée Excellence Kinshasa",
      "lienParente": "Père"
    }
  ]
}
```

### 4. Récupérer les enfants d'un parent (liste détaillée)
**GET** `/api/parent-eleve/parent/{parentId}/enfants`

### 5. Récupérer les parents d'un élève
**GET** `/api/parent-eleve/eleve/{eleveId}/parents`

**Réponse exemple :**
```json
[
  {
    "id": 1,
    "parentId": 1,
    "parentNom": "Jean Mukendi",
    "parentEmail": "j.mukendi@gmail.com",
    "parentTelephone": "+243812345678",
    "eleveId": 1,
    "eleveNom": "Mukendi Trésor Junior",
    "eleveClasse": "6ème Littéraire",
    "eleveEcole": "Lycée Excellence Kinshasa",
    "lienParente": "Père"
  },
  {
    "id": 2,
    "parentId": 2,
    "parentNom": "Marie Tshala",
    "parentEmail": "m.tshala@gmail.com",
    "parentTelephone": "+243823456789",
    "eleveId": 1,
    "eleveNom": "Mukendi Trésor Junior",
    "eleveClasse": "6ème Littéraire",
    "eleveEcole": "Lycée Excellence Kinshasa",
    "lienParente": "Mère"
  }
]
```

### 6. Modifier une relation
**PUT** `/api/parent-eleve/{id}`

### 7. Supprimer une relation
**DELETE** `/api/parent-eleve/{id}`

## 📝 Exemples de Tests Postman

### Test 1 : Créer plusieurs relations parent-enfant
1. **Méthode** : POST
2. **URL** : `http://localhost:8080/api/parent-eleve/batch`
3. **Headers** : `Content-Type: application/json`
4. **Body** : Utilisez le fichier `test_parent_eleve_batch.json`

```json
[
  {
    "parentId": 1,
    "eleveId": 1,
    "lienParente": "Père"
  },
  {
    "parentId": 1,
    "eleveId": 2,
    "lienParente": "Père"
  },
  {
    "parentId": 2,
    "eleveId": 1,
    "lienParente": "Mère"
  }
]
```

### Test 2 : Voir tous les enfants d'un parent
1. **Méthode** : GET
2. **URL** : `http://localhost:8080/api/parent-eleve/parent/1`

➡️ Vous verrez tous les enfants du parent avec leurs classes et écoles !

### Test 3 : Voir les parents d'un élève
1. **Méthode** : GET
2. **URL** : `http://localhost:8080/api/parent-eleve/eleve/1/parents`

## 🎓 Workflow Complet

### Étape 1 : Créer un utilisateur PARENT
**POST** `/api/utilisateurs`
```json
{
  "nomComplet": "Jean Mukendi",
  "role": "PARENT",
  "telephone": "+243812345678",
  "email": "j.mukendi@gmail.com",
  "motDePasse": "Parent123!"
}
```

### Étape 2 : Créer un ou plusieurs élèves
**POST** `/api/eleves`

### Étape 3 : Lier le parent à ses enfants
**POST** `/api/parent-eleve/batch`
```json
[
  {
    "parentId": 1,
    "eleveId": 1,
    "lienParente": "Père"
  },
  {
    "parentId": 1,
    "eleveId": 2,
    "lienParente": "Père"
  }
]
```

### Étape 4 : Consulter les informations
**GET** `/api/parent-eleve/parent/1`

## 📊 Types de Lien de Parenté Suggérés

- **Père**
- **Mère**
- **Tuteur**
- **Tutrice**
- **Oncle**
- **Tante**
- **Grand-père**
- **Grand-mère**
- **Frère aîné**
- **Sœur aînée**

## 🗄️ Structure de la Base de Données

Table `parent_eleve` :
```sql
CREATE TABLE parent_eleve (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    eleve_id BIGINT NOT NULL,
    lien_parente VARCHAR(50),
    FOREIGN KEY (parent_id) REFERENCES utilisateur(id),
    FOREIGN KEY (eleve_id) REFERENCES eleve(id)
);
```

## ⚠️ Validations Importantes

1. **L'utilisateur doit avoir le rôle PARENT** pour être lié à un élève
2. **Pas de doublons** : une relation parent-enfant ne peut être créée qu'une seule fois
3. **Les IDs doivent exister** : le parent et l'élève doivent exister dans la base de données

## 💡 Cas d'Usage

### Cas 1 : Famille avec plusieurs enfants
Un parent (Jean) a 2 enfants dans la même école :
```json
[
  {"parentId": 1, "eleveId": 1, "lienParente": "Père"},
  {"parentId": 1, "eleveId": 2, "lienParente": "Père"}
]
```

### Cas 2 : Enfant avec plusieurs tuteurs
Un élève (Trésor) a un père et une mère :
```json
[
  {"parentId": 1, "eleveId": 1, "lienParente": "Père"},
  {"parentId": 2, "eleveId": 1, "lienParente": "Mère"}
]
```

### Cas 3 : Tuteur légal
Un enfant sous tutelle :
```json
[
  {"parentId": 5, "eleveId": 3, "lienParente": "Tuteur"}
]
```

## 🚀 Avantages du Système

1. **Vision complète** : Un parent voit tous ses enfants, leurs classes et écoles
2. **Flexible** : Supporte plusieurs parents par enfant
3. **Traçable** : Le type de lien est enregistré (père, mère, tuteur)
4. **Sécurisé** : Seuls les utilisateurs avec le rôle PARENT peuvent être liés
5. **Batch processing** : Créer plusieurs relations en une seule requête

## 📱 Utilisation pour un Portail Parent

Le parent peut maintenant :
- ✅ Se connecter avec son compte PARENT
- ✅ Voir la liste de tous ses enfants
- ✅ Voir les classes et écoles de chaque enfant
- ✅ Consulter les bulletins de chaque enfant
- ✅ Suivre les notes de chaque enfant

## 🔗 Endpoints Liés Utiles

- `/api/bulletins/eleve/{eleveId}/periode/{periode}` - Voir le bulletin d'un enfant
- `/api/notes/eleve/{eleveId}` - Voir les notes d'un enfant
- `/api/paiements/eleve/{eleveId}` - Voir les paiements d'un enfant
