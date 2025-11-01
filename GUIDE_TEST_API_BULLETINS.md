# Guide de test de l'API Bulletins

## Endpoint disponible

### GET /api/bulletins/{eleveId}/{periode}

Récupère le bulletin d'un élève pour une période donnée.

## Exemples de requêtes dans Postman

### 1. Bulletin de la 1ère période
```
GET http://localhost:8080/api/bulletins/1/PREMIERE
```

### 2. Bulletin de la 2e période
```
GET http://localhost:8080/api/bulletins/1/DEUXIEME
```

### 3. Bulletin de la 3e période
```
GET http://localhost:8080/api/bulletins/1/TROISIEME
```

### 4. Bulletin de l'examen du premier semestre
```
GET http://localhost:8080/api/bulletins/1/EXAMEN_PREMIER_SEMESTRE
```

### 5. Bulletin de l'examen du second semestre
```
GET http://localhost:8080/api/bulletins/1/EXAMEN_SECOND_SEMESTRE
```

## Paramètres

- **eleveId**: L'ID de l'élève (numérique, ex: 1, 2, 3)
- **periode**: La période (chaîne de caractères):
  - PREMIERE
  - DEUXIEME
  - TROISIEME
  - EXAMEN_PREMIER_SEMESTRE
  - EXAMEN_SECOND_SEMESTRE

## Méthode HTTP
**GET** (pas POST!)

## Headers nécessaires
Aucun header spécial n'est requis pour cette requête.

## Exemple de réponse attendue

```json
{
  "nomComplet": "Kabongo Florent Jean",
  "sexe": "M",
  "dateNaissance": "2008-04-12",
  "lieuNaissance": "Bukavu",
  "numeroPermanent": "12345",
  "classe": "3e Scientifique",
  "ecole": "Institut Umoja",
  "periode": "1ère période",
  "numeroPeriode": "PREMIERE",
  "Code": "EP1234",
  "ville": "Bukavu",
  "commune_territoire": "Bagira",
  "notes": [
    {
      "id": 1,
      "eleveId": 1,
      "eleveNom": "Kabongo Florent Jean",
      "coursId": 1,
      "coursNom": "Mathématiques",
      "ponderation": 5,
      "valeur": 15.5,
      "periode": "PREMIERE"
    },
    {
      "id": 2,
      "eleveId": 1,
      "eleveNom": "Kabongo Florent Jean",
      "coursId": 2,
      "coursNom": "Français",
      "ponderation": 4,
      "valeur": 14.0,
      "periode": "PREMIERE"
    }
  ],
  "totalGeneral": 133.5,
  "maximumGeneral": 200.0,
  "pourcentage": 66.75,
  "mention": "Bien",
  "conduite": "Bon",
  "place_nbreEleve": "25/26"
}
```

## Notes importantes

1. ⚠️ Assurez-vous que l'élève existe dans la base de données
2. ⚠️ Assurez-vous que l'élève a des notes pour la période demandée
3. ⚠️ Utilisez **GET** et non POST
4. ⚠️ Les périodes doivent être en MAJUSCULES et correspondre exactement aux valeurs de l'énumération
