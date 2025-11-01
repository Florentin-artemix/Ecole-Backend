# 📚 GUIDE FRONTEND REACT - GESTION DE BULLETINS SCOLAIRES

## 📋 Table des Matières
1. [Architecture du Projet](#architecture)
2. [Structure des Composants](#structure)
3. [Configuration des Services API](#api)
4. [Détails des Endpoints](#endpoints)
5. [Exemples de Code React](#exemples)
6. [Gestion d'État](#etat)
7. [Validations](#validations)

---

## 🏗️ Architecture du Projet {#architecture}

### Stack Technologique Recommandée
```
Frontend:
- React 18+
- Axios ou Fetch API
- React Router v6
- Zustand ou Redux (gestion d'état)
- Tailwind CSS ou Material-UI (styling)
- React Hook Form (formulaires)
- Zod ou Yup (validation)
```

### Structure du Dossier Recommandée
```
src/
├── components/
│   ├── common/
│   │   ├── Header.jsx
│   │   ├── Sidebar.jsx
│   │   └── Loading.jsx
│   ├── eleves/
│   │   ├── EleveList.jsx
│   │   ├── EleveForm.jsx
│   │   ├── EleveDetail.jsx
│   │   └── EleveDelete.jsx
│   ├── cours/
│   │   ├── CoursList.jsx
│   │   ├── CoursForm.jsx
│   │   └── CoursDelete.jsx
│   ├── notes/
│   │   ├── NoteList.jsx
│   │   ├── NoteForm.jsx
│   │   ├── NoteDelete.jsx
│   │   └── NoteByEleve.jsx
│   └── bulletins/
│       ├── BulletinDetail.jsx
│       ├── BulletinSearch.jsx
│       ├── BulletinPrint.jsx
│       └── BulletinDownload.jsx
├── services/
│   ├── api.js
│   ├── eleveService.js
│   ├── coursService.js
│   ├── noteService.js
│   └── bulletinService.js
├── hooks/
│   ├── useEleves.js
│   ├── useCours.js
│   ├── useNotes.js
│   └── useBulletin.js
├── store/
│   ├── eleveStore.js
│   ├── coursStore.js
│   ├── noteStore.js
│   └── bulletinStore.js
├── utils/
│   ├── validation.js
│   ├── formatters.js
│   └── constants.js
├── pages/
│   ├── ElevesPage.jsx
│   ├── CoursPage.jsx
│   ├── NotesPage.jsx
│   ├── BulletinsPage.jsx
│   └── Dashboard.jsx
└── App.jsx
```

---

## 🎨 Structure des Composants {#structure}

### 1. **Composant EleveList** - Affichage de la Liste des Élèves

```jsx
// components/eleves/EleveList.jsx
import React, { useState, useEffect } from 'react';
import { useEleves } from '../../hooks/useEleves';
import EleveForm from './EleveForm';
import { PencilIcon, TrashIcon, PlusIcon } from '@heroicons/react/24/outline';

export default function EleveList() {
  const { eleves, loading, error, createEleve, updateEleve, deleteEleve, fetchEleves } = useEleves();
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchEleves();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cet élève ?')) {
      await deleteEleve(id);
    }
  };

  if (loading) return <div>Chargement...</div>;
  if (error) return <div className="text-red-600">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Gestion des Élèves</h1>
        <button
          onClick={() => {
            setEditingId(null);
            setShowForm(!showForm);
          }}
          className="bg-blue-600 text-white px-4 py-2 rounded flex items-center gap-2"
        >
          <PlusIcon className="w-5 h-5" />
          Ajouter un élève
        </button>
      </div>

      {showForm && (
        <EleveForm
          onSubmit={editingId ? updateEleve : createEleve}
          onClose={() => setShowForm(false)}
          editingId={editingId}
        />
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {eleves.map(eleve => (
          <div key={eleve.id} className="bg-white rounded-lg shadow p-6">
            <h3 className="text-xl font-semibold">{eleve.nom} {eleve.postnom}</h3>
            <p className="text-gray-600">{eleve.classe}</p>
            <p className="text-sm text-gray-500">N°: {eleve.numeroPermanent}</p>
            <div className="flex gap-2 mt-4">
              <button
                onClick={() => {
                  setEditingId(eleve.id);
                  setShowForm(true);
                }}
                className="flex items-center gap-1 bg-yellow-500 text-white px-3 py-1 rounded text-sm"
              >
                <PencilIcon className="w-4 h-4" />
              </button>
              <button
                onClick={() => handleDelete(eleve.id)}
                className="flex items-center gap-1 bg-red-600 text-white px-3 py-1 rounded text-sm"
              >
                <TrashIcon className="w-4 h-4" />
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
```

### 2. **Composant BulletinDetail** - Affichage du Bulletin

```jsx
// components/bulletins/BulletinDetail.jsx
import React, { useState, useEffect } from 'react';
import { useBulletin } from '../../hooks/useBulletin';
import { useParams } from 'react-router-dom';
import BulletinPrint from './BulletinPrint';

export default function BulletinDetail() {
  const { eleveId, periode } = useParams();
  const { bulletin, loading, error, fetchBulletin } = useBulletin();
  const [showPrint, setShowPrint] = useState(false);

  useEffect(() => {
    fetchBulletin(eleveId, periode);
  }, [eleveId, periode]);

  if (loading) return <div className="text-center py-8">Chargement du bulletin...</div>;
  if (error) return <div className="text-red-600 text-center py-8">{error}</div>;
  if (!bulletin) return <div className="text-center py-8">Aucun bulletin trouvé</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="bg-white rounded-lg shadow-lg p-8">
        {/* En-tête du bulletin */}
        <div className="text-center mb-8 border-b-2 pb-6">
          <h1 className="text-3xl font-bold">{bulletin.ecole}</h1>
          <p className="text-gray-600">Code: {bulletin.Code}</p>
          <p className="text-gray-600">{bulletin.ville}, {bulletin.commune_territoire}</p>
        </div>

        {/* Informations de l'élève */}
        <div className="grid grid-cols-2 gap-4 mb-8 p-4 bg-gray-50 rounded">
          <div>
            <p className="font-semibold">Nom Complet:</p>
            <p>{bulletin.nomComplet}</p>
          </div>
          <div>
            <p className="font-semibold">Sexe:</p>
            <p>{bulletin.sexe === 'M' ? 'Masculin' : 'Féminin'}</p>
          </div>
          <div>
            <p className="font-semibold">Date de Naissance:</p>
            <p>{new Date(bulletin.dateNaissance).toLocaleDateString('fr-FR')}</p>
          </div>
          <div>
            <p className="font-semibold">Lieu de Naissance:</p>
            <p>{bulletin.lieuNaissance}</p>
          </div>
          <div>
            <p className="font-semibold">Numéro Permanent:</p>
            <p>{bulletin.numeroPermanent}</p>
          </div>
          <div>
            <p className="font-semibold">Classe:</p>
            <p>{bulletin.classe}</p>
          </div>
          <div>
            <p className="font-semibold">Période:</p>
            <p>{bulletin.periode}</p>
          </div>
          <div>
            <p className="font-semibold">Place:</p>
            <p>{bulletin.place_nbreEleve}</p>
          </div>
        </div>

        {/* Tableau des notes */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold mb-4">Relevé des Notes</h2>
          <table className="w-full border-collapse border border-gray-300">
            <thead className="bg-blue-600 text-white">
              <tr>
                <th className="border border-gray-300 p-2">Cours</th>
                <th className="border border-gray-300 p-2">Pondération</th>
                <th className="border border-gray-300 p-2">Note Obtenue</th>
                <th className="border border-gray-300 p-2">Total Pondéré</th>
              </tr>
            </thead>
            <tbody>
              {bulletin.notes.map((note, index) => (
                <tr key={index} className={index % 2 === 0 ? 'bg-gray-50' : ''}>
                  <td className="border border-gray-300 p-2">{note.cours}</td>
                  <td className="border border-gray-300 p-2 text-center">{note.ponderation}</td>
                  <td className="border border-gray-300 p-2 text-center">{note.note}/20</td>
                  <td className="border border-gray-300 p-2 text-center font-semibold">
                    {(note.note * note.ponderation).toFixed(2)}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Résumé et appréciations */}
        <div className="grid grid-cols-2 gap-4 mb-8 p-4 bg-yellow-50 rounded">
          <div>
            <p className="font-semibold">Total Général:</p>
            <p className="text-xl text-blue-600">{bulletin.totalGeneral}</p>
          </div>
          <div>
            <p className="font-semibold">Maximum Général:</p>
            <p className="text-xl">{bulletin.maximumGeneral}</p>
          </div>
          <div>
            <p className="font-semibold">Pourcentage:</p>
            <p className="text-xl text-green-600">{bulletin.pourcentage}%</p>
          </div>
          <div>
            <p className="font-semibold">Mention:</p>
            <p className="text-xl font-bold text-purple-600">{bulletin.mention}</p>
          </div>
          <div>
            <p className="font-semibold">Conduite:</p>
            <p className="text-xl">{bulletin.conduite}</p>
          </div>
        </div>

        {/* Boutons d'action */}
        <div className="flex gap-4 justify-end">
          <button
            onClick={() => setShowPrint(true)}
            className="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700"
          >
            Imprimer
          </button>
          <button
            onClick={() => window.print()}
            className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700"
          >
            Télécharger PDF
          </button>
        </div>
      </div>

      {showPrint && <BulletinPrint bulletin={bulletin} onClose={() => setShowPrint(false)} />}
    </div>
  );
}
```

---

## 🔌 Configuration des Services API {#api}

### 1. **Service API Centralisé**

```jsx
// services/api.js
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Intercepteur pour les erreurs
api.interceptors.response.use(
  response => response,
  error => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export default api;
```

### 2. **Services Métier**

```jsx
// services/eleveService.js
import api from './api';

export const eleveService = {
  // Récupérer tous les élèves
  getAllEleves: () => api.get('/eleves'),

  // Récupérer un élève par ID
  getEleveById: (id) => api.get(`/eleves/${id}`),

  // Créer un élève
  createEleve: (eleveData) => api.post('/eleves', eleveData),

  // Mettre à jour un élève
  updateEleve: (id, eleveData) => api.put(`/eleves/${id}`, eleveData),

  // Supprimer un élève
  deleteEleve: (id) => api.delete(`/eleves/${id}`),
};
```

```jsx
// services/bulletinService.js
import api from './api';

export const bulletinService = {
  // Récupérer un bulletin
  getBulletin: (eleveId, periode) => 
    api.get(`/bulletins/${eleveId}/${periode}`),

  // Générer tous les bulletins pour une classe
  getBulletinsByClasse: (classe, periode) =>
    api.get(`/bulletins/classe/${classe}/${periode}`),
};
```

---

## 📡 Détails des Endpoints {#endpoints}

### **Élèves**
```
POST   /api/eleves                    Créer un élève
GET    /api/eleves                    Récupérer tous les élèves
GET    /api/eleves/{id}               Récupérer un élève
PUT    /api/eleves/{id}               Modifier un élève
DELETE /api/eleves/{id}               Supprimer un élève
```

### **Cours**
```
POST   /api/cours                     Créer un cours
GET    /api/cours                     Récupérer tous les cours
GET    /api/cours/{id}                Récupérer un cours
PUT    /api/cours/{id}                Modifier un cours
DELETE /api/cours/{id}                Supprimer un cours
```

### **Notes**
```
POST   /api/notes                     Créer une note
GET    /api/notes                     Récupérer toutes les notes
GET    /api/notes/{id}                Récupérer une note
PUT    /api/notes/{id}                Modifier une note
DELETE /api/notes/{id}                Supprimer une note
```

### **Bulletins (Principal)**
```
GET    /api/bulletins/{eleveId}/{periode}   Récupérer un bulletin
```

**Exemple de réponse:**
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

## 💻 Exemples de Code React {#exemples}

### **Custom Hook pour les Élèves**

```jsx
// hooks/useEleves.js
import { useState, useCallback } from 'react';
import { eleveService } from '../services/eleveService';

export function useEleves() {
  const [eleves, setEleves] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchEleves = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await eleveService.getAllEleves();
      setEleves(response.data);
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement des élèves');
    } finally {
      setLoading(false);
    }
  }, []);

  const createEleve = useCallback(async (eleveData) => {
    try {
      const response = await eleveService.createEleve(eleveData);
      setEleves([...eleves, response.data]);
      return response.data;
    } catch (err) {
      setError(err.message || 'Erreur lors de la création');
      throw err;
    }
  }, [eleves]);

  const updateEleve = useCallback(async (id, eleveData) => {
    try {
      const response = await eleveService.updateEleve(id, eleveData);
      setEleves(eleves.map(e => e.id === id ? response.data : e));
      return response.data;
    } catch (err) {
      setError(err.message || 'Erreur lors de la modification');
      throw err;
    }
  }, [eleves]);

  const deleteEleve = useCallback(async (id) => {
    try {
      await eleveService.deleteEleve(id);
      setEleves(eleves.filter(e => e.id !== id));
    } catch (err) {
      setError(err.message || 'Erreur lors de la suppression');
      throw err;
    }
  }, [eleves]);

  return {
    eleves,
    loading,
    error,
    fetchEleves,
    createEleve,
    updateEleve,
    deleteEleve,
  };
}
```

### **Custom Hook pour les Bulletins**

```jsx
// hooks/useBulletin.js
import { useState, useCallback } from 'react';
import { bulletinService } from '../services/bulletinService';

export function useBulletin() {
  const [bulletin, setBulletin] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchBulletin = useCallback(async (eleveId, periode) => {
    setLoading(true);
    setError(null);
    try {
      const response = await bulletinService.getBulletin(eleveId, periode);
      setBulletin(response.data);
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement du bulletin');
    } finally {
      setLoading(false);
    }
  }, []);

  return {
    bulletin,
    loading,
    error,
    fetchBulletin,
  };
}
```

### **Formulaire pour Créer/Modifier un Élève**

```jsx
// components/eleves/EleveForm.jsx
import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';

// Schéma de validation
const eleveSchema = z.object({
  nom: z.string().min(1, 'Le nom est requis'),
  postnom: z.string().min(1, 'Le postnom est requis'),
  prenom: z.string().min(1, 'Le prénom est requis'),
  sexe: z.enum(['M', 'F']),
  dateNaissance: z.string().refine(date => new Date(date) < new Date(), 'Date invalide'),
  lieuNaissance: z.string().min(1, 'Le lieu est requis'),
  numeroPermanent: z.string().min(1, 'Le numéro permanent est requis').unique('doit être unique'),
  classe: z.string().min(1, 'La classe est requise'),
  ecole: z.string().min(1, 'L\'école est requise'),
  code: z.string().min(1, 'Le code est requis'),
  ville: z.string().min(1, 'La ville est requise'),
  commune_territoire: z.string().min(1, 'La commune/territoire est requise'),
});

export default function EleveForm({ onSubmit, onClose, editingId }) {
  const { register, handleSubmit, formState: { errors }, reset } = useForm({
    resolver: zodResolver(eleveSchema),
  });

  const handleFormSubmit = async (data) => {
    try {
      await onSubmit(data);
      reset();
      onClose();
    } catch (err) {
      console.error('Erreur:', err);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-white rounded-lg p-8 max-w-2xl w-full">
        <h2 className="text-2xl font-bold mb-6">
          {editingId ? 'Modifier un élève' : 'Ajouter un élève'}
        </h2>

        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            {/* Champs du formulaire */}
            <input
              {...register('nom')}
              placeholder="Nom"
              className="border rounded px-3 py-2"
            />
            {errors.nom && <span className="text-red-600">{errors.nom.message}</span>}

            <input
              {...register('postnom')}
              placeholder="Postnom"
              className="border rounded px-3 py-2"
            />
            {errors.postnom && <span className="text-red-600">{errors.postnom.message}</span>}

            <input
              {...register('prenom')}
              placeholder="Prénom"
              className="border rounded px-3 py-2"
            />
            {errors.prenom && <span className="text-red-600">{errors.prenom.message}</span>}

            <select {...register('sexe')} className="border rounded px-3 py-2">
              <option value="">Sélectionner le sexe</option>
              <option value="M">Masculin</option>
              <option value="F">Féminin</option>
            </select>
            {errors.sexe && <span className="text-red-600">{errors.sexe.message}</span>}

            <input
              {...register('dateNaissance')}
              type="date"
              className="border rounded px-3 py-2"
            />
            {errors.dateNaissance && <span className="text-red-600">{errors.dateNaissance.message}</span>}

            <input
              {...register('lieuNaissance')}
              placeholder="Lieu de naissance"
              className="border rounded px-3 py-2"
            />
            {errors.lieuNaissance && <span className="text-red-600">{errors.lieuNaissance.message}</span>}

            <input
              {...register('numeroPermanent')}
              placeholder="Numéro permanent"
              className="border rounded px-3 py-2"
            />
            {errors.numeroPermanent && <span className="text-red-600">{errors.numeroPermanent.message}</span>}

            <input
              {...register('classe')}
              placeholder="Classe"
              className="border rounded px-3 py-2"
            />
            {errors.classe && <span className="text-red-600">{errors.classe.message}</span>}

            <input
              {...register('ecole')}
              placeholder="École"
              className="border rounded px-3 py-2"
            />
            {errors.ecole && <span className="text-red-600">{errors.ecole.message}</span>}

            <input
              {...register('code')}
              placeholder="Code"
              className="border rounded px-3 py-2"
            />
            {errors.code && <span className="text-red-600">{errors.code.message}</span>}

            <input
              {...register('ville')}
              placeholder="Ville"
              className="border rounded px-3 py-2"
            />
            {errors.ville && <span className="text-red-600">{errors.ville.message}</span>}

            <input
              {...register('commune_territoire')}
              placeholder="Commune/Territoire"
              className="border rounded px-3 py-2"
            />
            {errors.commune_territoire && <span className="text-red-600">{errors.commune_territoire.message}</span>}
          </div>

          <div className="flex gap-4 justify-end mt-6">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 border rounded hover:bg-gray-100"
            >
              Annuler
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
            >
              {editingId ? 'Modifier' : 'Créer'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
```

---

## 🗂️ Gestion d'État {#etat}

### **Avec Zustand (Recommandé)**

```jsx
// store/eleveStore.js
import { create } from 'zustand';
import { eleveService } from '../services/eleveService';

export const useEleveStore = create((set) => ({
  eleves: [],
  loading: false,
  error: null,

  fetchEleves: async () => {
    set({ loading: true });
    try {
      const response = await eleveService.getAllEleves();
      set({ eleves: response.data, error: null });
    } catch (error) {
      set({ error: error.message });
    } finally {
      set({ loading: false });
    }
  },

  addEleve: async (eleveData) => {
    try {
      const response = await eleveService.createEleve(eleveData);
      set(state => ({ eleves: [...state.eleves, response.data] }));
      return response.data;
    } catch (error) {
      set({ error: error.message });
      throw error;
    }
  },

  updateEleve: async (id, eleveData) => {
    try {
      const response = await eleveService.updateEleve(id, eleveData);
      set(state => ({
        eleves: state.eleves.map(e => e.id === id ? response.data : e)
      }));
      return response.data;
    } catch (error) {
      set({ error: error.message });
      throw error;
    }
  },

  deleteEleve: async (id) => {
    try {
      await eleveService.deleteEleve(id);
      set(state => ({ eleves: state.eleves.filter(e => e.id !== id) }));
    } catch (error) {
      set({ error: error.message });
      throw error;
    }
  },
}));
```

---

## ✅ Validations {#validations}

### **Schémas Zod Recommandés**

```jsx
// utils/validation.js
import { z } from 'zod';

export const eleveSchema = z.object({
  nom: z.string()
    .min(2, 'Le nom doit faire au moins 2 caractères')
    .max(100, 'Le nom ne peut pas dépasser 100 caractères'),
  
  postnom: z.string()
    .min(2, 'Le postnom doit faire au moins 2 caractères')
    .max(100, 'Le postnom ne peut pas dépasser 100 caractères'),
  
  prenom: z.string()
    .min(1, 'Le prénom est requis')
    .max(100, 'Le prénom ne peut pas dépasser 100 caractères'),
  
  sexe: z.enum(['M', 'F'], 'Le sexe doit être M ou F'),
  
  dateNaissance: z.string()
    .refine(date => !isNaN(Date.parse(date)), 'Date invalide')
    .refine(date => new Date(date) < new Date(), 'La date de naissance doit être dans le passé'),
  
  lieuNaissance: z.string()
    .min(2, 'Le lieu est requis')
    .max(100, 'Le lieu ne peut pas dépasser 100 caractères'),
  
  numeroPermanent: z.string()
    .min(1, 'Le numéro permanent est requis')
    .max(50, 'Le numéro permanent ne peut pas dépasser 50 caractères'),
  
  classe: z.string()
    .min(1, 'La classe est requise')
    .max(100, 'La classe ne peut pas dépasser 100 caractères'),
  
  ecole: z.string()
    .min(1, "L'école est requise")
    .max(100, "L'école ne peut pas dépasser 100 caractères"),
  
  code: z.string()
    .min(1, 'Le code est requis')
    .max(50, 'Le code ne peut pas dépasser 50 caractères'),
  
  ville: z.string()
    .min(1, 'La ville est requise')
    .max(100, 'La ville ne peut pas dépasser 100 caractères'),
  
  commune_territoire: z.string()
    .min(1, 'La commune/territoire est requise')
    .max(100, 'La commune/territoire ne peut pas dépasser 100 caractères'),
});

export const coursSchema = z.object({
  nomCours: z.string()
    .min(1, 'Le nom du cours est requis')
    .max(100, 'Le nom du cours ne peut pas dépasser 100 caractères'),
  
  ponderation: z.number()
    .int('La pondération doit être un nombre entier')
    .positive('La pondération doit être positive'),
});

export const noteSchema = z.object({
  eleveId: z.number().positive('ID élève invalide'),
  coursId: z.number().positive('ID cours invalide'),
  valeur: z.number()
    .min(0, 'La note minimum est 0')
    .max(20, 'La note maximum est 20'),
  periode: z.enum(['PREMIERE', 'DEUXIEME', 'TROISIEME']),
});
```

---

## 🚀 Installation et Configuration

### **1. Créer le projet React**
```bash
npx create-react-app ecole-bulletins
cd ecole-bulletins
```

### **2. Installer les dépendances**
```bash
npm install axios react-router-dom zustand react-hook-form @hookform/resolvers zod
npm install -D tailwindcss postcss autoprefixer
```

### **3. Créer le fichier .env**
```
REACT_APP_API_URL=http://localhost:8080/api
```

### **4. Structure finale de l'App.jsx**
```jsx
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ElevesPage from './pages/ElevesPage';
import CoursPage from './pages/CoursPage';
import NotesPage from './pages/NotesPage';
import BulletinsPage from './pages/BulletinsPage';
import Dashboard from './pages/Dashboard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/eleves" element={<ElevesPage />} />
        <Route path="/cours" element={<CoursPage />} />
        <Route path="/notes" element={<NotesPage />} />
        <Route path="/bulletins/:eleveId/:periode" element={<BulletinsPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

---

## 📊 Calculs du Bulletin

Les calculs sont effectués par le backend, mais voici comment les visualiser:

- **Total Général** = Σ(Note × Pondération)
- **Maximum Général** = Σ(20 × Pondération)
- **Pourcentage** = (Total Général / Maximum Général) × 100
- **Mention** = Basée sur le pourcentage:
  - < 40%: Faible
  - 40-50%: Passable
  - 50-60%: Assez Bien
  - 60-70%: Bien
  - 70-80%: Très Bien
  - ≥ 80%: Excellent

---

## 📱 Responsive Design

Tous les composants utilisent **Tailwind CSS** avec support mobile complet.

**Breakpoints:**
- `sm`: 640px
- `md`: 768px
- `lg`: 1024px
- `xl`: 1280px

---

Bonne chance avec votre projet ! 🎉
