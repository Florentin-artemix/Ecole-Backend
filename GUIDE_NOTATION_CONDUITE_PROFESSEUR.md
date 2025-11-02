# Guide : Notation de la Conduite par les Professeurs

## Vue d'ensemble

Les professeurs peuvent maintenant attribuer une note de conduite à un élève **en même temps** qu'ils donnent une note académique. Cette fonctionnalité permet d'évaluer à la fois les performances académiques et le comportement de l'élève dans un seul formulaire.

## Énumération TypeConduite

Les valeurs possibles pour la conduite sont :
- `EXCELLENT` - Excellent
- `TRES_BON` - Très Bon
- `BON` - Bon
- `ASSEZ_BON` - Assez Bon
- `PASSABLE` - Passable
- `MEDIOCRE` - Médiocre
- `MAUVAIS` - Mauvais

## Utilisation de l'API

### 1. Créer une note AVEC conduite

**Endpoint :** `POST /api/notes`

**Corps de la requête :**
```json
{
  "eleveId": 1,
  "coursId": 5,
  "valeur": 75.5,
  "periode": "PREMIER_TRIMESTRE",
  "typeConduite": "BON",
  "commentaireConduite": "Élève attentif et participatif en classe"
}
```

**Résultat :** 
- Une note académique est créée
- Une évaluation de conduite est automatiquement créée et associée au professeur du cours

### 2. Créer une note SANS conduite

**Endpoint :** `POST /api/notes`

**Corps de la requête :**
```json
{
  "eleveId": 1,
  "coursId": 5,
  "valeur": 75.5,
  "periode": "PREMIER_TRIMESTRE"
}
```

**Résultat :** 
- Seule la note académique est créée
- Aucune évaluation de conduite n'est créée

### 3. Créer plusieurs notes avec conduite (Batch)

**Endpoint :** `POST /api/notes/batch`

**Corps de la requête :**
```json
[
  {
    "eleveId": 1,
    "coursId": 5,
    "valeur": 75.5,
    "periode": "PREMIER_TRIMESTRE",
    "typeConduite": "BON",
    "commentaireConduite": "Bon comportement général"
  },
  {
    "eleveId": 2,
    "coursId": 5,
    "valeur": 82.0,
    "periode": "PREMIER_TRIMESTRE",
    "typeConduite": "TRES_BON",
    "commentaireConduite": "Excellent comportement et participation active"
  },
  {
    "eleveId": 3,
    "coursId": 5,
    "valeur": 68.0,
    "periode": "PREMIER_TRIMESTRE"
  }
]
```

**Résultat :**
- Les notes de l'élève 1 et 2 auront des conduites associées
- La note de l'élève 3 n'aura pas de conduite (champs optionnels)

### 4. Mettre à jour une note avec conduite

**Endpoint :** `PUT /api/notes/{id}`

**Corps de la requête :**
```json
{
  "eleveId": 1,
  "coursId": 5,
  "valeur": 80.0,
  "periode": "PREMIER_TRIMESTRE",
  "typeConduite": "TRES_BON",
  "commentaireConduite": "Amélioration notable du comportement"
}
```

**Note :** Si vous mettez à jour une note et ajoutez un `typeConduite`, une **nouvelle** évaluation de conduite sera créée (pas de mise à jour de l'ancienne).

## Fonctionnement Interne

1. **Professeur automatiquement identifié** : Lorsqu'un professeur crée une note pour un cours, le système récupère automatiquement l'identifiant du professeur associé au cours.

2. **Création automatique de la conduite** : Si les champs `typeConduite` et optionnellement `commentaireConduite` sont fournis, le système crée automatiquement une entrée dans la table `conduite` avec :
   - L'élève concerné
   - Le professeur du cours
   - Le type de conduite
   - La période
   - Le commentaire (optionnel)

3. **Indépendance des données** : Les notes et les conduites restent des entités séparées dans la base de données, ce qui permet :
   - De consulter toutes les conduites d'un élève
   - De calculer la conduite la plus fréquente pour le bulletin
   - De gérer les conduites indépendamment des notes

## API de Conduite (toujours disponible)

Les professeurs peuvent toujours créer des évaluations de conduite indépendamment des notes :

**Endpoint :** `POST /api/conduites`

**Corps de la requête :**
```json
{
  "eleveId": 1,
  "professeurId": 3,
  "typeConduite": "BON",
  "periode": "PREMIER_TRIMESTRE",
  "commentaire": "Bon comportement général dans le couloir"
}
```

## Avantages

1. **Gain de temps** : Le professeur peut noter la conduite en même temps que la note académique
2. **Simplicité** : Un seul formulaire pour deux évaluations
3. **Flexibilité** : La conduite reste optionnelle
4. **Traçabilité** : Toutes les évaluations de conduite sont conservées avec leur professeur et période

## Exemples d'utilisation Frontend

### Exemple React - Formulaire de création de note

```jsx
const [noteData, setNoteData] = useState({
  eleveId: '',
  coursId: '',
  valeur: '',
  periode: 'PREMIER_TRIMESTRE',
  typeConduite: '', // Optionnel
  commentaireConduite: '' // Optionnel
});

const conduiteOptions = [
  { value: 'EXCELLENT', label: 'Excellent' },
  { value: 'TRES_BON', label: 'Très Bon' },
  { value: 'BON', label: 'Bon' },
  { value: 'ASSEZ_BON', label: 'Assez Bon' },
  { value: 'PASSABLE', label: 'Passable' },
  { value: 'MEDIOCRE', label: 'Médiocre' },
  { value: 'MAUVAIS', label: 'Mauvais' }
];

const handleSubmit = async (e) => {
  e.preventDefault();
  
  // Préparer les données (enlever les champs vides)
  const dataToSend = { ...noteData };
  if (!dataToSend.typeConduite) {
    delete dataToSend.typeConduite;
    delete dataToSend.commentaireConduite;
  }
  
  const response = await fetch('http://localhost:8080/api/notes', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(dataToSend)
  });
  
  if (response.ok) {
    alert('Note et conduite créées avec succès !');
  }
};

return (
  <form onSubmit={handleSubmit}>
    {/* Champs pour la note */}
    <input 
      type="number" 
      placeholder="Note" 
      value={noteData.valeur}
      onChange={(e) => setNoteData({...noteData, valeur: e.target.value})}
    />
    
    {/* Section optionnelle pour la conduite */}
    <div style={{marginTop: '20px', padding: '10px', border: '1px solid #ddd'}}>
      <h4>Évaluation de la conduite (optionnel)</h4>
      <select 
        value={noteData.typeConduite}
        onChange={(e) => setNoteData({...noteData, typeConduite: e.target.value})}
      >
        <option value="">-- Pas d'évaluation --</option>
        {conduiteOptions.map(opt => (
          <option key={opt.value} value={opt.value}>{opt.label}</option>
        ))}
      </select>
      
      {noteData.typeConduite && (
        <textarea
          placeholder="Commentaire sur la conduite (optionnel)"
          value={noteData.commentaireConduite}
          onChange={(e) => setNoteData({...noteData, commentaireConduite: e.target.value})}
        />
      )}
    </div>
    
    <button type="submit">Créer la note</button>
  </form>
);
```

## Consultation des Conduites

Pour voir toutes les conduites d'un élève pour une période :

**Endpoint :** `GET /api/conduites/eleve/{eleveId}/periode/{periode}`

**Exemple :** `GET /api/conduites/eleve/1/periode/PREMIER_TRIMESTRE`

Pour voir la conduite la plus fréquente (utilisée dans le bulletin) :

**Endpoint :** `GET /api/conduites/eleve/{eleveId}/periode/{periode}/most-frequent`

**Exemple :** `GET /api/conduites/eleve/1/periode/PREMIER_TRIMESTRE/most-frequent`

**Réponse :** `"Bon"` (la conduite la plus souvent attribuée)
