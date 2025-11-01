# 📚 GUIDE FRONTEND REACT - MISE À JOUR AVEC GESTION DES UTILISATEURS

## 🆕 NOUVEAUTÉS AJOUTÉES

### 1. Gestion des Utilisateurs
- Création, modification, suppression d'utilisateurs
- Rôles: ADMIN, PROFESSEUR, PARENT, PERCEPTEUR
- Authentification et autorisation

### 2. Professeurs dans les Cours
- Chaque cours est assigné à un professeur
- Les professeurs peuvent gérer leurs cours

### 3. Périodes Étendue
- PREMIERE (1ère période)
- DEUXIEME (2e période)
- TROISIEME (3e période)
- EXAMEN_PREMIER_SEMESTRE (Examen premier semestre)
- EXAMEN_SECOND_SEMESTRE (Examen second semestre)

---

## 📁 STRUCTURE REACT MISE À JOUR

```
src/
├── components/
│   ├── utilisateurs/
│   │   ├── UtilisateurList.jsx        ⭐ NEW
│   │   ├── UtilisateurForm.jsx        ⭐ NEW
│   │   ├── UtilisateurCard.jsx        ⭐ NEW
│   │   └── UtilisateurDelete.jsx      ⭐ NEW
│   ├── cours/
│   │   ├── CoursList.jsx              ✏️ UPDATED
│   │   ├── CoursForm.jsx              ✏️ UPDATED
│   │   └── CoursCard.jsx
│   └── ... (autres composants)
├── services/
│   ├── utilisateurService.js          ⭐ NEW
│   ├── coursService.js                ✏️ UPDATED
│   └── ... (autres services)
├── hooks/
│   ├── useUtilisateurs.js             ⭐ NEW
│   ├── useCours.js                    ✏️ UPDATED
│   └── ... (autres hooks)
└── ... (autres dossiers)
```

---

## 🔧 SERVICES API - UTILISATEURS

### services/api.js

```javascript
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

### services/utilisateurService.js

```javascript
import api from './api';

export const utilisateurService = {
  // Récupérer tous les utilisateurs
  getAllUtilisateurs: () => api.get('/utilisateurs'),

  // Récupérer un utilisateur par ID
  getUtilisateurById: (id) => api.get(`/utilisateurs/${id}`),

  // Récupérer les utilisateurs par rôle
  getUtilisateursByRole: (role) => api.get(`/utilisateurs/role/${role}`),

  // Créer un utilisateur
  createUtilisateur: (utilisateurData) => api.post('/utilisateurs', utilisateurData),

  // Modifier un utilisateur
  updateUtilisateur: (id, utilisateurData) => api.put(`/utilisateurs/${id}`, utilisateurData),

  // Supprimer un utilisateur
  deleteUtilisateur: (id) => api.delete(`/utilisateurs/${id}`),
};
```

### services/coursService.js (MISE À JOUR)

```javascript
import api from './api';

export const coursService = {
  getAllCours: () => 
    api.get('/cours')
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de chargement');
      }),

  getCoursById: (id) => 
    api.get(`/cours/${id}`)
      .then(res => res.data),

  // ✏️ MISE À JOUR: Inclure l'ID du professeur
  createCours: (coursData) => 
    api.post('/cours', {
      nomCours: coursData.nomCours,
      ponderation: coursData.ponderation,
      professeurId: coursData.professeurId,  // ⭐ NOUVEAU
    })
      .then(res => res.data),

  updateCours: (id, coursData) => 
    api.put(`/cours/${id}`, {
      nomCours: coursData.nomCours,
      ponderation: coursData.ponderation,
      professeurId: coursData.professeurId,  // ⭐ NOUVEAU
    })
      .then(res => res.data),

  deleteCours: (id) => 
    api.delete(`/cours/${id}`),
};
```

---

## 🎣 CUSTOM HOOKS

### hooks/useUtilisateurs.js (NOUVEAU)

```javascript
import { useState, useCallback } from 'react';
import { utilisateurService } from '../services/utilisateurService';

export function useUtilisateurs() {
  const [utilisateurs, setUtilisateurs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchUtilisateurs = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await utilisateurService.getAllUtilisateurs();
      setUtilisateurs(response.data);
    } catch (err) {
      setError(err.message || 'Erreur lors du chargement');
    } finally {
      setLoading(false);
    }
  }, []);

  const fetchUtilisateursByRole = useCallback(async (role) => {
    setLoading(true);
    setError(null);
    try {
      const response = await utilisateurService.getUtilisateursByRole(role);
      setUtilisateurs(response.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  const createUtilisateur = useCallback(async (utilisateurData) => {
    try {
      const response = await utilisateurService.createUtilisateur(utilisateurData);
      setUtilisateurs([...utilisateurs, response.data]);
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [utilisateurs]);

  const updateUtilisateur = useCallback(async (id, utilisateurData) => {
    try {
      const response = await utilisateurService.updateUtilisateur(id, utilisateurData);
      setUtilisateurs(utilisateurs.map(u => u.id === id ? response.data : u));
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [utilisateurs]);

  const deleteUtilisateur = useCallback(async (id) => {
    try {
      await utilisateurService.deleteUtilisateur(id);
      setUtilisateurs(utilisateurs.filter(u => u.id !== id));
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [utilisateurs]);

  return {
    utilisateurs,
    loading,
    error,
    fetchUtilisateurs,
    fetchUtilisateursByRole,
    createUtilisateur,
    updateUtilisateur,
    deleteUtilisateur,
  };
}
```

### hooks/useCours.js (MISE À JOUR)

```javascript
import { useState, useCallback } from 'react';
import { coursService } from '../services/coursService';

export function useCours() {
  const [cours, setCours] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchCours = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await coursService.getAllCours();
      setCours(response.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  const createCours = useCallback(async (coursData) => {
    try {
      // ✏️ MISE À JOUR: Inclure le professeur
      const response = await coursService.createCours(coursData);
      setCours([...cours, response.data]);
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [cours]);

  const updateCours = useCallback(async (id, coursData) => {
    try {
      // ✏️ MISE À JOUR: Inclure le professeur
      const response = await coursService.updateCours(id, coursData);
      setCours(cours.map(c => c.id === id ? response.data : c));
      return response.data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [cours]);

  const deleteCours = useCallback(async (id) => {
    try {
      await coursService.deleteCours(id);
      setCours(cours.filter(c => c.id !== id));
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [cours]);

  return {
    cours,
    loading,
    error,
    fetchCours,
    createCours,
    updateCours,
    deleteCours,
  };
}
```

---

## 🎨 COMPOSANTS REACT

### components/utilisateurs/UtilisateurList.jsx (NOUVEAU)

```jsx
import React, { useState, useEffect } from 'react';
import { useUtilisateurs } from '../../hooks/useUtilisateurs';
import UtilisateurForm from './UtilisateurForm';
import UtilisateurCard from './UtilisateurCard';
import { PencilIcon, TrashIcon, PlusIcon } from '@heroicons/react/24/outline';

const ROLES = [
  { value: 'ADMIN', label: 'Administrateur' },
  { value: 'PROFESSEUR', label: 'Professeur' },
  { value: 'PARENT', label: 'Parent' },
  { value: 'PERCEPTEUR', label: 'Percepteur' },
];

export default function UtilisateurList() {
  const { utilisateurs, loading, error, createUtilisateur, updateUtilisateur, deleteUtilisateur, fetchUtilisateurs } = useUtilisateurs();
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [selectedRole, setSelectedRole] = useState('');

  useEffect(() => {
    fetchUtilisateurs();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      await deleteUtilisateur(id);
    }
  };

  const filteredUtilisateurs = selectedRole 
    ? utilisateurs.filter(u => u.role === selectedRole)
    : utilisateurs;

  if (loading) return <div className="text-center py-8">Chargement...</div>;
  if (error) return <div className="text-red-600 text-center py-8">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Gestion des Utilisateurs</h1>
        <button
          onClick={() => {
            setEditingId(null);
            setShowForm(!showForm);
          }}
          className="bg-blue-600 text-white px-4 py-2 rounded flex items-center gap-2 hover:bg-blue-700"
        >
          <PlusIcon className="w-5 h-5" />
          Ajouter un utilisateur
        </button>
      </div>

      {showForm && (
        <UtilisateurForm
          onSubmit={editingId ? updateUtilisateur : createUtilisateur}
          onClose={() => setShowForm(false)}
          editingId={editingId}
        />
      )}

      <div className="mb-4">
        <select
          value={selectedRole}
          onChange={(e) => setSelectedRole(e.target.value)}
          className="border rounded px-4 py-2 bg-white"
        >
          <option value="">Tous les rôles</option>
          {ROLES.map(role => (
            <option key={role.value} value={role.value}>{role.label}</option>
          ))}
        </select>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {filteredUtilisateurs.map(utilisateur => (
          <UtilisateurCard
            key={utilisateur.id}
            utilisateur={utilisateur}
            onEdit={() => {
              setEditingId(utilisateur.id);
              setShowForm(true);
            }}
            onDelete={() => handleDelete(utilisateur.id)}
          />
        ))}
      </div>
    </div>
  );
}
```

### components/utilisateurs/UtilisateurForm.jsx (NOUVEAU)

```jsx
import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';

const ROLES = [
  { value: 'ADMIN', label: 'Administrateur' },
  { value: 'PROFESSEUR', label: 'Professeur' },
  { value: 'PARENT', label: 'Parent' },
  { value: 'PERCEPTEUR', label: 'Percepteur' },
];

export default function UtilisateurForm({ onSubmit, onClose, editingId }) {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();

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
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-2xl w-full">
        <h2 className="text-2xl font-bold mb-6">
          {editingId ? 'Modifier un utilisateur' : 'Ajouter un utilisateur'}
        </h2>

        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1">Nom Complet</label>
              <input
                {...register('nomComplet', { required: 'Requis' })}
                placeholder="Nom complet"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.nomComplet && <span className="text-red-600 text-sm">{errors.nomComplet.message}</span>}
            </div>

            <div>
              <label className="block text-sm font-medium mb-1">Rôle</label>
              <select {...register('role', { required: 'Requis' })} className="border rounded px-3 py-2 w-full">
                <option value="">Sélectionner le rôle</option>
                {ROLES.map(role => (
                  <option key={role.value} value={role.value}>{role.label}</option>
                ))}
              </select>
              {errors.role && <span className="text-red-600 text-sm">{errors.role.message}</span>}
            </div>

            <div>
              <label className="block text-sm font-medium mb-1">Téléphone</label>
              <input
                {...register('telephone', { required: 'Requis' })}
                placeholder="+243..."
                className="border rounded px-3 py-2 w-full"
              />
              {errors.telephone && <span className="text-red-600 text-sm">{errors.telephone.message}</span>}
            </div>

            <div>
              <label className="block text-sm font-medium mb-1">Email</label>
              <input
                {...register('email', { required: 'Requis', pattern: { value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i } })}
                type="email"
                placeholder="email@example.com"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.email && <span className="text-red-600 text-sm">{errors.email.message}</span>}
            </div>

            <div className="col-span-2">
              <label className="block text-sm font-medium mb-1">Mot de Passe</label>
              <input
                {...register('motDePasse', { required: 'Requis', minLength: { value: 6, message: 'Min 6 caractères' } })}
                type="password"
                placeholder="••••••"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.motDePasse && <span className="text-red-600 text-sm">{errors.motDePasse.message}</span>}
            </div>
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

### components/utilisateurs/UtilisateurCard.jsx (NOUVEAU)

```jsx
import React from 'react';
import { PencilIcon, TrashIcon } from '@heroicons/react/24/outline';

const roleColors = {
  'ADMIN': 'bg-red-100 text-red-800',
  'PROFESSEUR': 'bg-blue-100 text-blue-800',
  'PARENT': 'bg-green-100 text-green-800',
  'PERCEPTEUR': 'bg-yellow-100 text-yellow-800',
};

export default function UtilisateurCard({ utilisateur, onEdit, onDelete }) {
  return (
    <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-lg font-semibold">{utilisateur.nomComplet}</h3>
        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${roleColors[utilisateur.role]}`}>
          {utilisateur.role}
        </span>
      </div>
      
      <div className="space-y-2 text-sm text-gray-600 mb-4">
        <p>📧 {utilisateur.email}</p>
        <p>📱 {utilisateur.telephone}</p>
        <p>✅ {utilisateur.actif ? 'Actif' : 'Inactif'}</p>
      </div>

      <div className="flex gap-2">
        <button
          onClick={onEdit}
          className="flex items-center gap-1 bg-yellow-500 text-white px-3 py-1 rounded text-sm hover:bg-yellow-600"
        >
          <PencilIcon className="w-4 h-4" />
        </button>
        <button
          onClick={onDelete}
          className="flex items-center gap-1 bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700"
        >
          <TrashIcon className="w-4 h-4" />
        </button>
      </div>
    </div>
  );
}
```

### components/cours/CoursForm.jsx (MISE À JOUR)

```jsx
import React, { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useUtilisateurs } from '../../hooks/useUtilisateurs';

export default function CoursForm({ onSubmit, onClose, editingId }) {
  const { register, handleSubmit, formState: { errors }, reset } = useForm();
  const { utilisateurs, fetchUtilisateursByRole } = useUtilisateurs();

  useEffect(() => {
    // Charger uniquement les professeurs
    fetchUtilisateursByRole('PROFESSEUR');
  }, []);

  const handleFormSubmit = async (data) => {
    try {
      await onSubmit({
        nomCours: data.nomCours,
        ponderation: parseInt(data.ponderation),
        professeurId: parseInt(data.professeurId),  // ⭐ NOUVEAU
      });
      reset();
      onClose();
    } catch (err) {
      console.error('Erreur:', err);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-lg w-full">
        <h2 className="text-2xl font-bold mb-6">
          {editingId ? 'Modifier un cours' : 'Ajouter un cours'}
        </h2>

        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
          <div>
            <label className="block text-sm font-medium mb-1">Nom du Cours</label>
            <input
              {...register('nomCours', { required: 'Requis' })}
              placeholder="Ex: Algèbre"
              className="border rounded px-3 py-2 w-full"
            />
            {errors.nomCours && <span className="text-red-600 text-sm">{errors.nomCours.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Pondération</label>
            <input
              {...register('ponderation', { required: 'Requis', min: 1 })}
              type="number"
              placeholder="20"
              className="border rounded px-3 py-2 w-full"
            />
            {errors.ponderation && <span className="text-red-600 text-sm">{errors.ponderation.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Professeur</label>
            <select 
              {...register('professeurId', { required: 'Requis' })}
              className="border rounded px-3 py-2 w-full"
            >
              <option value="">Sélectionner un professeur</option>
              {utilisateurs.map(user => (
                <option key={user.id} value={user.id}>{user.nomComplet}</option>
              ))}
            </select>
            {errors.professeurId && <span className="text-red-600 text-sm">{errors.professeurId.message}</span>}
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

## 📱 CONSTANTES MISES À JOUR

### utils/constants.js (MISE À JOUR)

```javascript
// ✏️ MISE À JOUR: Ajouter les nouvelles périodes
export const PERIODES = [
  { value: 'PREMIERE', label: '1ère période' },
  { value: 'DEUXIEME', label: '2e période' },
  { value: 'TROISIEME', label: '3e période' },
  { value: 'EXAMEN_PREMIER_SEMESTRE', label: 'Examen premier semestre' },
  { value: 'EXAMEN_SECOND_SEMESTRE', label: 'Examen second semestre' },
];

export const SEXES = [
  { value: 'M', label: 'Masculin' },
  { value: 'F', label: 'Féminin' },
];

// ⭐ NOUVEAU: Rôles
export const ROLES = [
  { value: 'ADMIN', label: 'Administrateur' },
  { value: 'PROFESSEUR', label: 'Professeur' },
  { value: 'PARENT', label: 'Parent' },
  { value: 'PERCEPTEUR', label: 'Percepteur' },
];

export const MENTIONS = {
  'Faible': { min: 0, max: 40, color: 'red' },
  'Passable': { min: 40, max: 50, color: 'orange' },
  'Assez Bien': { min: 50, max: 60, color: 'yellow' },
  'Bien': { min: 60, max: 70, color: 'blue' },
  'Très Bien': { min: 70, max: 80, color: 'green' },
  'Excellent': { min: 80, max: 100, color: 'purple' },
};

export const MESSAGES = {
  SUCCESS_CREATE: 'Création réussie',
  SUCCESS_UPDATE: 'Modification réussie',
  SUCCESS_DELETE: 'Suppression réussie',
  ERROR_CREATE: 'Erreur lors de la création',
  ERROR_UPDATE: 'Erreur lors de la modification',
  ERROR_DELETE: 'Erreur lors de la suppression',
  ERROR_LOAD: 'Erreur lors du chargement',
};

export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
```

---

## 🗂️ APP.JSX - ROUTES MISES À JOUR

```jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/common/Header';
import Sidebar from './components/common/Sidebar';
import ElevesPage from './pages/ElevesPage';
import CoursPage from './pages/CoursPage';
import NotesPage from './pages/NotesPage';
import BulletinsPage from './pages/BulletinsPage';
import UtilisateursPage from './pages/UtilisateursPage';  // ⭐ NOUVEAU
import Dashboard from './pages/Dashboard';
import NotFound from './pages/NotFound';
import './App.css';

function App() {
  return (
    <Router>
      <div className="flex h-screen bg-gray-100">
        <Sidebar />
        <div className="flex-1 flex flex-col overflow-hidden">
          <Header />
          <main className="flex-1 overflow-auto">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/eleves" element={<ElevesPage />} />
              <Route path="/cours" element={<CoursPage />} />
              <Route path="/notes" element={<NotesPage />} />
              <Route path="/bulletins/:eleveId/:periode" element={<BulletinsPage />} />
              <Route path="/utilisateurs" element={<UtilisateursPage />} />  {/* ⭐ NOUVEAU */}
              <Route path="/404" element={<NotFound />} />
              <Route path="*" element={<Navigate to="/404" />} />
            </Routes>
          </main>
        </div>
      </div>
    </Router>
  );
}

export default App;
```

---

## 📊 MISE À JOUR DU DASHBOARD

```jsx
import React, { useEffect } from 'react';
import { useEleveStore } from '../store/eleveStore';
import { useCoursStore } from '../store/coursStore';
import { useNoteStore } from '../store/noteStore';
import { useUtilisateurs } from '../hooks/useUtilisateurs';  // ⭐ NOUVEAU

export default function Dashboard() {
  const { eleves, fetchEleves } = useEleveStore();
  const { cours, fetchCours } = useCoursStore();
  const { notes, fetchNotes } = useNoteStore();
  const { utilisateurs, fetchUtilisateurs } = useUtilisateurs();  // ⭐ NOUVEAU

  useEffect(() => {
    fetchEleves();
    fetchCours();
    fetchNotes();
    fetchUtilisateurs();  // ⭐ NOUVEAU
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-8">Tableau de Bord</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">  {/* ✏️ 4 colonnes au lieu de 3 */}
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Élèves</h3>
          <p className="text-4xl font-bold text-blue-600 mt-2">{eleves.length}</p>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Cours</h3>
          <p className="text-4xl font-bold text-green-600 mt-2">{cours.length}</p>
        </div>

        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Notes</h3>
          <p className="text-4xl font-bold text-purple-600 mt-2">{notes.length}</p>
        </div>

        {/* ⭐ NOUVEAU */}
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Utilisateurs</h3>
          <p className="text-4xl font-bold text-orange-600 mt-2">{utilisateurs.length}</p>
        </div>
      </div>
    </div>
  );
}
```

---

## 📝 ENDPOINTS API MISES À JOUR

### Utilisateurs
```
POST   /api/utilisateurs                    Créer un utilisateur
GET    /api/utilisateurs                    Récupérer tous les utilisateurs
GET    /api/utilisateurs/{id}               Récupérer un utilisateur
GET    /api/utilisateurs/role/{role}        Récupérer les utilisateurs par rôle
PUT    /api/utilisateurs/{id}               Modifier un utilisateur
DELETE /api/utilisateurs/{id}               Supprimer un utilisateur
```

### Cours (MISE À JOUR)
```
POST   /api/cours                           Créer un cours (avec professeurId)
GET    /api/cours                           Récupérer tous les cours
GET    /api/cours/{id}                      Récupérer un cours
PUT    /api/cours/{id}                      Modifier un cours (avec professeurId)
DELETE /api/cours/{id}                      Supprimer un cours
```

---

## 🧪 EXEMPLE: TESTER AVEC CURL

### 1. Créer un utilisateur (Professeur)
```bash
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Dr. Jean Mukendi",
    "role": "PROFESSEUR",
    "telephone": "+243123456789",
    "email": "jean.mukendi@umoja.edu",
    "motDePasse": "professeur123"
  }'
```

### 2. Créer un cours avec professeur
```bash
curl -X POST http://localhost:8080/api/cours \
  -H "Content-Type: application/json" \
  -d '{
    "nomCours": "Algèbre",
    "ponderation": 20,
    "professeurId": 1
  }'
```

### 3. Récupérer les professeurs
```bash
curl http://localhost:8080/api/utilisateurs/role/PROFESSEUR
```

---

Bon développement ! 🚀
