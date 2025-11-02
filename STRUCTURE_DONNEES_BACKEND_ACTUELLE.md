# 🔄 MISE À JOUR IMPORTANTE - Structure des Données Backend
## Guide d'Intégration Frontend - Version Actuelle du Projet

---

## ⚠️ ATTENTION : Le Projet a Évolué

Ce document présente la **structure exacte et actuelle** des données renvoyées par le backend.
Les exemples ci-dessous correspondent à ce que vous recevrez réellement de l'API.

**Date de mise à jour** : 2 Novembre 2025  
**Version Backend** : Production actuelle

---

## 📊 Structure Actuelle des Données par Endpoint

### 1. GET /api/classes - Liste des Classes

**URL** : `http://localhost:8080/api/classes`

**Ce que le backend renvoie ACTUELLEMENT** :

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
  }
]
```

**Interface TypeScript** :
```typescript
interface ClasseDTO {
  id: number;
  nom: string;
  description: string;
}
```

**Points clés** :
- ✅ Pas de référence circulaire
- ✅ Pas de liste de cours incluse
- ✅ Structure simple et propre

---

### 2. GET /api/cours - Liste des Cours

**URL** : `http://localhost:8080/api/cours`

**Ce que le backend renvoie ACTUELLEMENT** :

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
    "nomCours": "Français",
    "ponderation": 3,
    "classeId": 1,
    "classeNom": "1ère",
    "professeurNom": "Mukendi Marie Claire",
    "professeurId": 2
  }
]
```

**Interface TypeScript** :
```typescript
interface CoursDTO {
  id: number;
  nomCours: string;
  ponderation: number;
  classeId: number;        // ID de la classe
  classeNom: string;       // Nom de la classe (ex: "1ère")
  professeurNom: string;   // Nom complet du professeur
  professeurId: number;    // ID du professeur
}
```

**Points clés** :
- ✅ Inclut les informations de la classe (ID + nom)
- ✅ Inclut les informations du professeur (ID + nom complet)
- ✅ Un même cours peut apparaître plusieurs fois avec des classes différentes
- ✅ Chaque occurrence peut avoir une pondération différente

---

### 3. GET /api/eleves - Liste des Élèves

**URL** : `http://localhost:8080/api/eleves`

**Ce que le backend renvoie ACTUELLEMENT** :

```json
[
  {
    "id": 1,
    "nom": "Mukendi",
    "postnom": "Joseph",
    "prenom": "Emmanuel",
    "sexe": "M",
    "dateNaissance": "2010-05-15",
    "lieuNaissance": "Kinshasa",
    "numeroPermanent": "EL2024001",
    "classe": "1ère",
    "ecole": {
      "id": 1,
      "nomEcole": "Institut Technique Bosangani",
      "codeEcole": "ITB001",
      "ville": "Kinshasa",
      "commune_territoire": "Gombe",
      "adresse": "Avenue de la Liberté",
      "telephone": "+243 123 456 789",
      "email": "contact@bosangani.cd",
      "devise": "Excellence et Discipline"
    },
    "code": "2024001",
    "ville": "Kinshasa",
    "commune_territoire": "Gombe"
  }
]
```

**Interface TypeScript** :
```typescript
interface EleveDTO {
  id: number;
  nom: string;
  postnom: string;
  prenom: string;
  sexe: string;              // "M" ou "F"
  dateNaissance: string;     // Format: "YYYY-MM-DD"
  lieuNaissance: string;
  numeroPermanent: string;
  classe: string;            // Nom de la classe (ex: "1ère")
  ecole: EcoleDTO;          // Objet école complet
  code?: string;
  ville?: string;
  commune_territoire?: string;
}

interface EcoleDTO {
  id: number;
  nomEcole: string;
  codeEcole: string;
  ville: string;
  commune_territoire: string;
  adresse: string;
  telephone: string;
  email: string;
  devise: string;
}
```

---

### 4. GET /api/notes - Liste des Notes

**URL** : `http://localhost:8080/api/notes`

**Ce que le backend renvoie ACTUELLEMENT** :

```json
[
  {
    "id": 1,
    "eleveId": 1,
    "eleveNom": "Mukendi Joseph Emmanuel",
    "coursId": 1,
    "coursNom": "Mathématiques",
    "ponderation": 3,
    "valeur": 15.5,
    "periode": "PREMIERE_PERIODE"
  },
  {
    "id": 2,
    "eleveId": 1,
    "eleveNom": "Mukendi Joseph Emmanuel",
    "coursId": 3,
    "coursNom": "Français",
    "ponderation": 3,
    "valeur": 14.0,
    "periode": "PREMIERE_PERIODE"
  }
]
```

**Interface TypeScript** :
```typescript
interface NoteDTO {
  id: number;
  eleveId: number;
  eleveNom: string;        // Nom complet de l'élève
  coursId: number;
  coursNom: string;        // Nom du cours
  ponderation: number;     // Pondération du cours
  valeur: number;          // Note sur 20
  periode: Periode;        // Enum de la période
}

enum Periode {
  PREMIERE_PERIODE = "PREMIERE_PERIODE",
  DEUXIEME_PERIODE = "DEUXIEME_PERIODE",
  TROISIEME_PERIODE = "TROISIEME_PERIODE",
  QUATRIEME_PERIODE = "QUATRIEME_PERIODE",
  EXAMEN = "EXAMEN"
}
```

---

### 5. GET /api/utilisateurs - Liste des Utilisateurs

**URL** : `http://localhost:8080/api/utilisateurs`

**Ce que le backend renvoie ACTUELLEMENT** :

```json
[
  {
    "id": 1,
    "nomComplet": "Kabongo Jean Pierre",
    "role": "PROFESSEUR",
    "telephone": "+243 999 123 456",
    "email": "kabongo.jean@ecole.cd",
    "actif": true
  },
  {
    "id": 2,
    "nomComplet": "Mukendi Marie Claire",
    "role": "PROFESSEUR",
    "telephone": "+243 999 654 321",
    "email": "mukendi.marie@ecole.cd",
    "actif": true
  },
  {
    "id": 3,
    "nomComplet": "Admin Système",
    "role": "ADMIN",
    "telephone": "+243 999 000 000",
    "email": "admin@ecole.cd",
    "actif": true
  }
]
```

**Interface TypeScript** :
```typescript
interface UtilisateurDTO {
  id: number;
  nomComplet: string;      // Nom complet concaténé
  role: Role;              // ADMIN, PROFESSEUR, PARENT
  telephone: string;
  email: string;
  actif: boolean;
}

enum Role {
  ADMIN = "ADMIN",
  PROFESSEUR = "PROFESSEUR",
  PARENT = "PARENT"
}
```

---

### 6. GET /api/bulletins/eleve/{eleveId}/periode/{periode}

**URL** : `http://localhost:8080/api/bulletins/eleve/1/periode/PREMIERE_PERIODE`

**Ce que le backend renvoie ACTUELLEMENT** :

```json
{
  "nomComplet": "Mukendi Joseph Emmanuel",
  "sexe": "M",
  "dateNaissance": "2010-05-15",
  "lieuNaissance": "Kinshasa",
  "numeroPermanent": "EL2024001",
  "classe": "1ère",
  "ecole": {
    "id": 1,
    "nomEcole": "Institut Technique Bosangani",
    "codeEcole": "ITB001",
    "ville": "Kinshasa",
    "commune_territoire": "Gombe",
    "adresse": "Avenue de la Liberté",
    "telephone": "+243 123 456 789",
    "email": "contact@bosangani.cd",
    "devise": "Excellence et Discipline"
  },
  "periode": "Première Période",
  "numeroPeriode": "1",
  "Code": "2024001",
  "ville": "Kinshasa",
  "commune_territoire": "Gombe",
  "notes": [
    {
      "id": 1,
      "eleveId": 1,
      "eleveNom": "Mukendi Joseph Emmanuel",
      "coursId": 1,
      "coursNom": "Mathématiques",
      "ponderation": 3,
      "valeur": 15.5,
      "periode": "PREMIERE_PERIODE"
    },
    {
      "id": 2,
      "eleveId": 1,
      "eleveNom": "Mukendi Joseph Emmanuel",
      "coursId": 3,
      "coursNom": "Français",
      "ponderation": 3,
      "valeur": 14.0,
      "periode": "PREMIERE_PERIODE"
    }
  ],
  "totalGeneral": 29.5,
  "maximumGeneral": 40.0,
  "pourcentage": 73.75,
  "mention": "DISTINCTION"
}
```

**Interface TypeScript** :
```typescript
interface BulletinDTO {
  nomComplet: string;
  sexe: string;
  dateNaissance: string;
  lieuNaissance: string;
  numeroPermanent: string;
  classe: string;
  ecole: EcoleDTO;
  periode: string;              // Nom de la période (ex: "Première Période")
  numeroPeriode: string;        // Numéro (ex: "1")
  Code: string;                 // Code élève
  ville: string;
  commune_territoire: string;
  notes: NoteDTO[];
  totalGeneral: number;         // Somme des notes
  maximumGeneral: number;       // Total des pondérations
  pourcentage?: number;         // Pourcentage
  mention?: string;             // EXCELLENCE, DISTINCTION, etc.
}
```

---

## 🔧 Données à Envoyer au Backend

### 1. POST /api/classes - Créer une Classe

**Body à envoyer** :
```json
{
  "nom": "1ère",
  "description": "Première année du secondaire"
}
```

**Ce que vous recevrez en retour** :
```json
{
  "id": 1,
  "nom": "1ère",
  "description": "Première année du secondaire"
}
```

---

### 2. POST /api/cours - Créer un Cours

**⚠️ IMPORTANT : Le champ `classeId` est OBLIGATOIRE**

**Body à envoyer** :
```json
{
  "nomCours": "Mathématiques",
  "ponderation": 3,
  "classeId": 1,
  "professeurId": 1
}
```

**Ce que vous recevrez en retour** :
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

**Validation** :
- ✅ `nomCours` : obligatoire, 1-100 caractères
- ✅ `ponderation` : obligatoire, nombre entier positif
- ✅ `classeId` : obligatoire, doit exister en base
- ✅ `professeurId` : obligatoire, doit être un utilisateur avec role PROFESSEUR

---

### 3. POST /api/eleves - Créer un Élève

**Body à envoyer** :
```json
{
  "nom": "Mukendi",
  "postnom": "Joseph",
  "prenom": "Emmanuel",
  "sexe": "M",
  "dateNaissance": "2010-05-15",
  "lieuNaissance": "Kinshasa",
  "numeroPermanent": "EL2024001",
  "classe": "1ère",
  "ecole": {
    "id": 1
  },
  "code": "2024001",
  "ville": "Kinshasa",
  "commune_territoire": "Gombe"
}
```

**Note** : Pour `ecole`, vous envoyez juste l'objet avec l'ID, le backend retournera l'objet complet.

---

### 4. POST /api/notes - Créer une Note

**Body à envoyer** :
```json
{
  "eleveId": 1,
  "coursId": 1,
  "valeur": 15.5,
  "periode": "PREMIERE_PERIODE"
}
```

**Ce que vous recevrez en retour** :
```json
{
  "id": 1,
  "eleveId": 1,
  "eleveNom": "Mukendi Joseph Emmanuel",
  "coursId": 1,
  "coursNom": "Mathématiques",
  "ponderation": 3,
  "valeur": 15.5,
  "periode": "PREMIERE_PERIODE"
}
```

**Périodes disponibles** :
- `PREMIERE_PERIODE`
- `DEUXIEME_PERIODE`
- `TROISIEME_PERIODE`
- `QUATRIEME_PERIODE`
- `EXAMEN`

---

### 5. POST /api/utilisateurs - Créer un Utilisateur/Professeur

**Body à envoyer** :
```json
{
  "nom": "Kabongo",
  "postnom": "Jean",
  "prenom": "Pierre",
  "email": "kabongo.jean@ecole.cd",
  "motDePasse": "password123",
  "telephone": "+243 999 123 456",
  "role": "PROFESSEUR"
}
```

**Ce que vous recevrez en retour** :
```json
{
  "id": 1,
  "nomComplet": "Kabongo Jean Pierre",
  "role": "PROFESSEUR",
  "telephone": "+243 999 123 456",
  "email": "kabongo.jean@ecole.cd",
  "actif": true
}
```

**Rôles disponibles** :
- `ADMIN`
- `PROFESSEUR`
- `PARENT`

---

## 💻 Exemples d'Intégration Frontend React

### Exemple 1 : Afficher la liste des cours groupés par classe

```typescript
import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface CoursDTO {
  id: number;
  nomCours: string;
  ponderation: number;
  classeId: number;
  classeNom: string;
  professeurNom: string;
  professeurId: number;
}

const CoursParClasse: React.FC = () => {
  const [cours, setCours] = useState<CoursDTO[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCours = async () => {
      try {
        const response = await axios.get<CoursDTO[]>('http://localhost:8080/api/cours');
        setCours(response.data);
      } catch (error) {
        console.error('Erreur:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCours();
  }, []);

  // Grouper les cours par classe
  const coursParClasse = cours.reduce((acc, cours) => {
    const classe = cours.classeNom;
    if (!acc[classe]) {
      acc[classe] = [];
    }
    acc[classe].push(cours);
    return acc;
  }, {} as Record<string, CoursDTO[]>);

  if (loading) return <div>Chargement...</div>;

  return (
    <div>
      <h1>Cours par Classe</h1>
      {Object.entries(coursParClasse).map(([classe, coursList]) => (
        <div key={classe} style={{ marginBottom: '30px' }}>
          <h2>Classe: {classe}</h2>
          <table>
            <thead>
              <tr>
                <th>Cours</th>
                <th>Pondération</th>
                <th>Professeur</th>
              </tr>
            </thead>
            <tbody>
              {coursList.map(c => (
                <tr key={c.id}>
                  <td>{c.nomCours}</td>
                  <td>{c.ponderation}</td>
                  <td>{c.professeurNom}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ))}
    </div>
  );
};

export default CoursParClasse;
```

---

### Exemple 2 : Formulaire de création de cours avec validation

```typescript
import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface ClasseDTO {
  id: number;
  nom: string;
  description: string;
}

interface UtilisateurDTO {
  id: number;
  nomComplet: string;
  role: string;
}

interface CoursFormData {
  nomCours: string;
  ponderation: number;
  classeId: number | null;
  professeurId: number | null;
}

const CoursForm: React.FC = () => {
  const [classes, setClasses] = useState<ClasseDTO[]>([]);
  const [professeurs, setProfesseurs] = useState<UtilisateurDTO[]>([]);
  const [formData, setFormData] = useState<CoursFormData>({
    nomCours: '',
    ponderation: 1,
    classeId: null,
    professeurId: null
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const loadData = async () => {
      try {
        const [classesRes, utilisateursRes] = await Promise.all([
          axios.get<ClasseDTO[]>('http://localhost:8080/api/classes'),
          axios.get<UtilisateurDTO[]>('http://localhost:8080/api/utilisateurs')
        ]);

        setClasses(classesRes.data);
        // Filtrer uniquement les professeurs
        setProfesseurs(utilisateursRes.data.filter(u => u.role === 'PROFESSEUR'));
      } catch (error) {
        console.error('Erreur de chargement:', error);
        alert('Erreur lors du chargement des données');
      }
    };

    loadData();
  }, []);

  const validateForm = (): boolean => {
    const newErrors: Record<string, string> = {};

    if (!formData.nomCours.trim()) {
      newErrors.nomCours = 'Le nom du cours est obligatoire';
    } else if (formData.nomCours.length > 100) {
      newErrors.nomCours = 'Le nom ne peut pas dépasser 100 caractères';
    }

    if (!formData.ponderation || formData.ponderation < 1) {
      newErrors.ponderation = 'La pondération doit être au moins 1';
    }

    if (!formData.classeId) {
      newErrors.classeId = 'Veuillez sélectionner une classe';
    }

    if (!formData.professeurId) {
      newErrors.professeurId = 'Veuillez sélectionner un professeur';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setLoading(true);

    try {
      const response = await axios.post('http://localhost:8080/api/cours', {
        nomCours: formData.nomCours,
        ponderation: formData.ponderation,
        classeId: formData.classeId,
        professeurId: formData.professeurId
      });

      console.log('Cours créé:', response.data);
      alert(`Cours "${response.data.nomCours}" créé avec succès pour la classe ${response.data.classeNom}!`);

      // Réinitialiser le formulaire
      setFormData({
        nomCours: '',
        ponderation: 1,
        classeId: null,
        professeurId: null
      });
      setErrors({});
    } catch (error: any) {
      console.error('Erreur:', error);
      if (error.response?.data?.message) {
        alert(`Erreur: ${error.response.data.message}`);
      } else {
        alert('Erreur lors de la création du cours');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '20px' }}>
      <h2>Créer un Nouveau Cours</h2>

      <form onSubmit={handleSubmit}>
        {/* Nom du cours */}
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Nom du cours *
          </label>
          <input
            type="text"
            value={formData.nomCours}
            onChange={(e) => setFormData({ ...formData, nomCours: e.target.value })}
            style={{
              width: '100%',
              padding: '8px',
              border: errors.nomCours ? '2px solid red' : '1px solid #ccc',
              borderRadius: '4px'
            }}
            placeholder="Ex: Mathématiques, Français, Sciences..."
          />
          {errors.nomCours && (
            <span style={{ color: 'red', fontSize: '12px' }}>{errors.nomCours}</span>
          )}
        </div>

        {/* Classe */}
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Classe *
          </label>
          <select
            value={formData.classeId || ''}
            onChange={(e) => setFormData({ ...formData, classeId: parseInt(e.target.value) })}
            style={{
              width: '100%',
              padding: '8px',
              border: errors.classeId ? '2px solid red' : '1px solid #ccc',
              borderRadius: '4px'
            }}
          >
            <option value="">-- Sélectionnez une classe --</option>
            {classes.map(classe => (
              <option key={classe.id} value={classe.id}>
                {classe.nom} - {classe.description}
              </option>
            ))}
          </select>
          {errors.classeId && (
            <span style={{ color: 'red', fontSize: '12px' }}>{errors.classeId}</span>
          )}
        </div>

        {/* Pondération */}
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Pondération *
          </label>
          <input
            type="number"
            min="1"
            max="10"
            value={formData.ponderation}
            onChange={(e) => setFormData({ ...formData, ponderation: parseInt(e.target.value) })}
            style={{
              width: '100%',
              padding: '8px',
              border: errors.ponderation ? '2px solid red' : '1px solid #ccc',
              borderRadius: '4px'
            }}
          />
          {errors.ponderation && (
            <span style={{ color: 'red', fontSize: '12px' }}>{errors.ponderation}</span>
          )}
          <small style={{ color: '#666' }}>
            Plus la classe est avancée, plus la pondération peut être élevée
          </small>
        </div>

        {/* Professeur */}
        <div style={{ marginBottom: '20px' }}>
          <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>
            Professeur *
          </label>
          <select
            value={formData.professeurId || ''}
            onChange={(e) => setFormData({ ...formData, professeurId: parseInt(e.target.value) })}
            style={{
              width: '100%',
              padding: '8px',
              border: errors.professeurId ? '2px solid red' : '1px solid #ccc',
              borderRadius: '4px'
            }}
          >
            <option value="">-- Sélectionnez un professeur --</option>
            {professeurs.map(prof => (
              <option key={prof.id} value={prof.id}>
                {prof.nomComplet}
              </option>
            ))}
          </select>
          {errors.professeurId && (
            <span style={{ color: 'red', fontSize: '12px' }}>{errors.professeurId}</span>
          )}
        </div>

        {/* Bouton */}
        <button
          type="submit"
          disabled={loading}
          style={{
            width: '100%',
            padding: '12px',
            backgroundColor: loading ? '#ccc' : '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            fontSize: '16px',
            cursor: loading ? 'not-allowed' : 'pointer'
          }}
        >
          {loading ? 'Création en cours...' : 'Créer le Cours'}
        </button>
      </form>
    </div>
  );
};

export default CoursForm;
```

---

### Exemple 3 : Hook personnalisé pour gérer les cours

```typescript
import { useState, useEffect } from 'react';
import axios from 'axios';

interface CoursDTO {
  id: number;
  nomCours: string;
  ponderation: number;
  classeId: number;
  classeNom: string;
  professeurNom: string;
  professeurId: number;
}

interface UseCoursResult {
  cours: CoursDTO[];
  loading: boolean;
  error: string | null;
  refetch: () => void;
  getCoursParClasse: (classeId: number) => CoursDTO[];
  getCoursParNom: (nomCours: string) => CoursDTO[];
}

export const useCours = (): UseCoursResult => {
  const [cours, setCours] = useState<CoursDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchCours = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await axios.get<CoursDTO[]>('http://localhost:8080/api/cours');
      setCours(response.data);
    } catch (err: any) {
      setError(err.message || 'Erreur lors du chargement des cours');
      console.error('Erreur:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCours();
  }, []);

  const getCoursParClasse = (classeId: number): CoursDTO[] => {
    return cours.filter(c => c.classeId === classeId);
  };

  const getCoursParNom = (nomCours: string): CoursDTO[] => {
    return cours.filter(c => 
      c.nomCours.toLowerCase().includes(nomCours.toLowerCase())
    );
  };

  return {
    cours,
    loading,
    error,
    refetch: fetchCours,
    getCoursParClasse,
    getCoursParNom
  };
};

// Utilisation dans un composant
const MonComposant: React.FC = () => {
  const { cours, loading, error, getCoursParClasse } = useCours();

  if (loading) return <div>Chargement...</div>;
  if (error) return <div>Erreur: {error}</div>;

  const coursClasse1 = getCoursParClasse(1);

  return (
    <div>
      <h2>Cours de la 1ère</h2>
      {coursClasse1.map(c => (
        <div key={c.id}>
          {c.nomCours} (Pond: {c.ponderation}) - Prof: {c.professeurNom}
        </div>
      ))}
    </div>
  );
};
```

---

## 📋 Checklist de Migration Frontend

Assurez-vous d'avoir fait ces modifications dans votre code frontend :

### ✅ Modifications Obligatoires

- [ ] **Ajout du champ `classeId`** dans tous les formulaires de création/modification de cours
- [ ] **Chargement de la liste des classes** au démarrage de l'application
- [ ] **Mise à jour des interfaces TypeScript** avec les nouveaux champs (`classeId`, `classeNom`)
- [ ] **Affichage de la classe** dans les listes de cours
- [ ] **Groupement des cours par classe** dans l'interface (optionnel mais recommandé)
- [ ] **Validation côté frontend** : vérifier que `classeId` est fourni avant soumission

### ✅ Bonnes Pratiques

- [ ] Créer un hook ou service réutilisable pour les opérations CRUD sur les cours
- [ ] Implémenter un cache local pour les classes (rarement modifiées)
- [ ] Afficher des messages d'erreur clairs quand une classe n'est pas sélectionnée
- [ ] Permettre de filtrer les cours par classe dans l'interface
- [ ] Afficher la pondération à côté de chaque cours dans les listes

---

## 🚨 Erreurs Courantes à Éviter

### Erreur 1 : Oublier le champ `classeId`

❌ **Mauvais** :
```typescript
const newCours = {
  nomCours: "Mathématiques",
  ponderation: 3,
  professeurId: 1
  // classeId manquant !
};
```

✅ **Correct** :
```typescript
const newCours = {
  nomCours: "Mathématiques",
  ponderation: 3,
  classeId: 1,        // ✅ Obligatoire
  professeurId: 1
};
```

---

### Erreur 2 : Confondre `classe` (string) et `classeId` (number)

Dans `EleveDTO`, le champ `classe` est un **string** :
```typescript
{
  "classe": "1ère"  // String
}
```

Dans `CoursDTO`, le champ `classeId` est un **number** :
```typescript
{
  "classeId": 1  // Number (ID)
}
```

---

### Erreur 3 : Ne pas filtrer les professeurs

❌ **Mauvais** : Afficher tous les utilisateurs dans le sélecteur de professeur

✅ **Correct** :
```typescript
const professeurs = utilisateurs.filter(u => u.role === 'PROFESSEUR');
```

---

## 📊 Tableau Récapitulatif des Endpoints

| Endpoint | Méthode | Body requis | Retourne |
|----------|---------|-------------|----------|
| `/api/classes` | GET | - | `ClasseDTO[]` |
| `/api/classes/{id}` | GET | - | `ClasseDTO` |
| `/api/classes` | POST | `ClasseDTO` | `ClasseDTO` |
| `/api/classes/{id}` | PUT | `ClasseDTO` | `ClasseDTO` |
| `/api/cours` | GET | - | `CoursDTO[]` (avec `classeId` et `classeNom`) |
| `/api/cours/{id}` | GET | - | `CoursDTO` |
| `/api/cours` | POST | `{ nomCours, ponderation, classeId, professeurId }` | `CoursDTO` |
| `/api/cours/{id}` | PUT | `{ nomCours, ponderation, classeId, professeurId }` | `CoursDTO` |
| `/api/eleves` | GET | - | `EleveDTO[]` (avec objet `ecole` complet) |
| `/api/notes` | GET | - | `NoteDTO[]` (avec noms des élèves et cours) |
| `/api/utilisateurs` | GET | - | `UtilisateurDTO[]` (avec `nomComplet`) |

---

## 🎯 Résumé des Changements Clés

### Ce qui a changé dans le backend :

1. **Nouvelle entité `Classe`** avec endpoints CRUD complets
2. **`CoursDTO` enrichi** avec `classeId` et `classeNom`
3. **Aucune référence circulaire** grâce aux DTOs
4. **Retours API cohérents** avec informations complètes mais sans imbrication infinie

### Ce que le frontend doit faire :

1. ✅ **Charger les classes** au démarrage
2. ✅ **Inclure `classeId`** dans la création/modification de cours
3. ✅ **Afficher `classeNom`** dans les listes de cours
4. ✅ **Filtrer par classe** si besoin
5. ✅ **Gérer les erreurs** de validation

---

## 📞 Contact et Support

Ce document reflète la **structure exacte et actuelle** du backend.

**Tous les exemples JSON sont réels** et correspondent à ce que l'API retourne maintenant.

Pour toute question, référez-vous aux DTOs dans le code source :
- `dto/ClasseDTO.java`
- `dto/CoursDTO.java`
- `dto/EleveDTO.java`
- `dto/NoteDTO.java`
- `dto/UtilisateurDTO.java`

---

**Date** : 2 Novembre 2025  
**Version Backend** : Production actuelle avec système de classes intégré
