# 📱 EXEMPLE COMPLET: COMPOSANT UTILISATEUR AVEC ÉNUMÉRATIONS

## 1️⃣ Fichier utils/enums.js

```javascript
// ========================
// ÉNUMÉRATION: ROLE
// ========================
export const ROLE_ENUM = {
  ADMIN: 'ADMIN',
  PROFESSEUR: 'PROFESSEUR',
  PARENT: 'PARENT',
  PERCEPTEUR: 'PERCEPTEUR',
};

export const ROLE_LABELS = {
  ADMIN: 'Administrateur',
  PROFESSEUR: 'Professeur',
  PARENT: 'Parent',
  PERCEPTEUR: 'Percepteur',
};

export const ROLE_COLORS = {
  ADMIN: 'bg-red-100 text-red-800',
  PROFESSEUR: 'bg-blue-100 text-blue-800',
  PARENT: 'bg-green-100 text-green-800',
  PERCEPTEUR: 'bg-yellow-100 text-yellow-800',
};

export const ROLE_OPTIONS = [
  { value: ROLE_ENUM.ADMIN, label: ROLE_LABELS.ADMIN },
  { value: ROLE_ENUM.PROFESSEUR, label: ROLE_LABELS.PROFESSEUR },
  { value: ROLE_ENUM.PARENT, label: ROLE_LABELS.PARENT },
  { value: ROLE_ENUM.PERCEPTEUR, label: ROLE_LABELS.PERCEPTEUR },
];

// ========================
// ÉNUMÉRATION: PERIODE
// ========================
export const PERIODE_ENUM = {
  PREMIERE: 'PREMIERE',
  DEUXIEME: 'DEUXIEME',
  TROISIEME: 'TROISIEME',
  EXAMEN_PREMIER_SEMESTRE: 'EXAMEN_PREMIER_SEMESTRE',
  EXAMEN_SECOND_SEMESTRE: 'EXAMEN_SECOND_SEMESTRE',
};

export const PERIODE_LABELS = {
  PREMIERE: '1ère période',
  DEUXIEME: '2e période',
  TROISIEME: '3e période',
  EXAMEN_PREMIER_SEMESTRE: 'Examen premier semestre',
  EXAMEN_SECOND_SEMESTRE: 'Examen second semestre',
};

export const PERIODE_COLORS = {
  PREMIERE: 'bg-blue-100 text-blue-800',
  DEUXIEME: 'bg-green-100 text-green-800',
  TROISIEME: 'bg-purple-100 text-purple-800',
  EXAMEN_PREMIER_SEMESTRE: 'bg-orange-100 text-orange-800',
  EXAMEN_SECOND_SEMESTRE: 'bg-red-100 text-red-800',
};

export const PERIODE_OPTIONS = [
  { value: PERIODE_ENUM.PREMIERE, label: PERIODE_LABELS.PREMIERE },
  { value: PERIODE_ENUM.DEUXIEME, label: PERIODE_LABELS.DEUXIEME },
  { value: PERIODE_ENUM.TROISIEME, label: PERIODE_LABELS.TROISIEME },
  { value: PERIODE_ENUM.EXAMEN_PREMIER_SEMESTRE, label: PERIODE_LABELS.EXAMEN_PREMIER_SEMESTRE },
  { value: PERIODE_ENUM.EXAMEN_SECOND_SEMESTRE, label: PERIODE_LABELS.EXAMEN_SECOND_SEMESTRE },
];

// ========================
// ÉNUMÉRATION: SEXE
// ========================
export const SEXE_ENUM = {
  MASCULIN: 'M',
  FEMININ: 'F',
};

export const SEXE_LABELS = {
  M: 'Masculin',
  F: 'Féminin',
};

export const SEXE_OPTIONS = [
  { value: SEXE_ENUM.MASCULIN, label: SEXE_LABELS.M },
  { value: SEXE_ENUM.FEMININ, label: SEXE_LABELS.F },
];
```

---

## 2️⃣ Fichier services/utilisateurService.js

```javascript
import api from './api';

export const utilisateurService = {
  // Récupérer tous les utilisateurs
  getAllUtilisateurs: () => 
    api.get('/utilisateurs')
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de chargement');
      }),

  // Récupérer un utilisateur par ID
  getUtilisateurById: (id) => 
    api.get(`/utilisateurs/${id}`)
      .then(res => res.data)
      .catch(err => {
        throw new Error('Utilisateur non trouvé');
      }),

  // Récupérer les utilisateurs par rôle (ENUM)
  getUtilisateursByRole: (role) => // role est une valeur ENUM (ex: 'PROFESSEUR')
    api.get(`/utilisateurs/role/${role}`)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de chargement');
      }),

  // Créer un utilisateur
  createUtilisateur: (utilisateurData) => 
    api.post('/utilisateurs', {
      nomComplet: utilisateurData.nomComplet,
      role: utilisateurData.role, // Envoyer la valeur ENUM
      telephone: utilisateurData.telephone,
      email: utilisateurData.email,
      motDePasse: utilisateurData.motDePasse,
    })
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de création');
      }),

  // Modifier un utilisateur
  updateUtilisateur: (id, utilisateurData) => 
    api.put(`/utilisateurs/${id}`, {
      nomComplet: utilisateurData.nomComplet,
      role: utilisateurData.role, // Envoyer la valeur ENUM
      telephone: utilisateurData.telephone,
      email: utilisateurData.email,
      motDePasse: utilisateurData.motDePasse,
    })
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de modification');
      }),

  // Supprimer un utilisateur
  deleteUtilisateur: (id) => 
    api.delete(`/utilisateurs/${id}`)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de suppression');
      }),
};
```

---

## 3️⃣ Fichier hooks/useUtilisateurs.js

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
      const data = await utilisateurService.getAllUtilisateurs();
      setUtilisateurs(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  // ⭐ IMPORTANT: role doit être une valeur ENUM
  const fetchUtilisateursByRole = useCallback(async (role) => {
    setLoading(true);
    setError(null);
    try {
      const data = await utilisateurService.getUtilisateursByRole(role);
      setUtilisateurs(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  const createUtilisateur = useCallback(async (utilisateurData) => {
    try {
      const data = await utilisateurService.createUtilisateur(utilisateurData);
      setUtilisateurs([...utilisateurs, data]);
      return data;
    } catch (err) {
      setError(err.message);
      throw err;
    }
  }, [utilisateurs]);

  const updateUtilisateur = useCallback(async (id, utilisateurData) => {
    try {
      const data = await utilisateurService.updateUtilisateur(id, utilisateurData);
      setUtilisateurs(utilisateurs.map(u => u.id === id ? data : u));
      return data;
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

---

## 4️⃣ Fichier components/utilisateurs/UtilisateurForm.jsx

```jsx
import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { ROLE_OPTIONS, ROLE_ENUM } from '../../utils/enums';

// ⭐ Validation Zod avec énumération
const utilisateurSchema = z.object({
  nomComplet: z.string().min(2, 'Minimum 2 caractères'),
  role: z.enum(Object.values(ROLE_ENUM), {
    errorMap: () => ({ message: 'Rôle invalide' })
  }),
  telephone: z.string().min(10, 'Téléphone invalide'),
  email: z.string().email('Email invalide'),
  motDePasse: z.string().min(6, 'Minimum 6 caractères'),
});

export default function UtilisateurForm({ onSubmit, onClose, editingId }) {
  const { register, handleSubmit, formState: { errors }, reset } = useForm({
    resolver: zodResolver(utilisateurSchema),
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
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-8 max-w-2xl w-full">
        <h2 className="text-2xl font-bold mb-6">
          {editingId ? 'Modifier un utilisateur' : 'Ajouter un utilisateur'}
        </h2>

        <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            {/* Nom Complet */}
            <div>
              <label className="block text-sm font-medium mb-1">Nom Complet</label>
              <input
                {...register('nomComplet')}
                placeholder="Ex: Dr. Jean Mukendi"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.nomComplet && (
                <span className="text-red-600 text-sm">{errors.nomComplet.message}</span>
              )}
            </div>

            {/* Rôle - ⭐ IMPORTANT: Utiliser ROLE_OPTIONS et ROLE_ENUM */}
            <div>
              <label className="block text-sm font-medium mb-1">Rôle</label>
              <select
                {...register('role')}
                className="border rounded px-3 py-2 w-full"
              >
                <option value="">-- Sélectionner un rôle --</option>
                {ROLE_OPTIONS.map(option => (
                  <option key={option.value} value={option.value}>
                    {option.label}
                  </option>
                ))}
              </select>
              {errors.role && (
                <span className="text-red-600 text-sm">{errors.role.message}</span>
              )}
            </div>

            {/* Téléphone */}
            <div>
              <label className="block text-sm font-medium mb-1">Téléphone</label>
              <input
                {...register('telephone')}
                placeholder="+243..."
                className="border rounded px-3 py-2 w-full"
              />
              {errors.telephone && (
                <span className="text-red-600 text-sm">{errors.telephone.message}</span>
              )}
            </div>

            {/* Email */}
            <div>
              <label className="block text-sm font-medium mb-1">Email</label>
              <input
                {...register('email')}
                type="email"
                placeholder="email@example.com"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.email && (
                <span className="text-red-600 text-sm">{errors.email.message}</span>
              )}
            </div>

            {/* Mot de Passe */}
            <div className="col-span-2">
              <label className="block text-sm font-medium mb-1">Mot de Passe</label>
              <input
                {...register('motDePasse')}
                type="password"
                placeholder="••••••"
                className="border rounded px-3 py-2 w-full"
              />
              {errors.motDePasse && (
                <span className="text-red-600 text-sm">{errors.motDePasse.message}</span>
              )}
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

---

## 5️⃣ Fichier components/utilisateurs/UtilisateurCard.jsx

```jsx
import React from 'react';
import { PencilIcon, TrashIcon } from '@heroicons/react/24/outline';
import { ROLE_LABELS, ROLE_COLORS } from '../../utils/enums';

export default function UtilisateurCard({ utilisateur, onEdit, onDelete }) {
  return (
    <div className="bg-white rounded-lg shadow p-6 hover:shadow-lg transition">
      <div className="flex justify-between items-start mb-4">
        <h3 className="text-lg font-semibold">{utilisateur.nomComplet}</h3>
        
        {/* ⭐ Badge avec couleur basée sur l'ENUM role */}
        <span className={`px-3 py-1 rounded-full text-xs font-semibold ${ROLE_COLORS[utilisateur.role]}`}>
          {ROLE_LABELS[utilisateur.role]}
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

---

## 6️⃣ Fichier components/utilisateurs/UtilisateurList.jsx

```jsx
import React, { useState, useEffect } from 'react';
import { useUtilisateurs } from '../../hooks/useUtilisateurs';
import { ROLE_ENUM, ROLE_OPTIONS } from '../../utils/enums';
import UtilisateurForm from './UtilisateurForm';
import UtilisateurCard from './UtilisateurCard';
import { PlusIcon } from '@heroicons/react/24/outline';

export default function UtilisateurList() {
  const { utilisateurs, loading, error, createUtilisateur, updateUtilisateur, deleteUtilisateur, fetchUtilisateurs, fetchUtilisateursByRole } = useUtilisateurs();
  const [showForm, setShowForm] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [selectedRole, setSelectedRole] = useState('');

  useEffect(() => {
    fetchUtilisateurs();
  }, []);

  const handleFilterByRole = (roleValue) => {
    setSelectedRole(roleValue);
    if (roleValue) {
      // ⭐ Passer la valeur ENUM (ex: 'PROFESSEUR')
      fetchUtilisateursByRole(roleValue);
    } else {
      fetchUtilisateurs();
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      await deleteUtilisateur(id);
    }
  };

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

      {/* ⭐ Filtre par rôle avec ENUM */}
      <div className="mb-6">
        <label className="block text-sm font-medium mb-2">Filtrer par rôle:</label>
        <select
          value={selectedRole}
          onChange={(e) => handleFilterByRole(e.target.value)}
          className="border rounded px-4 py-2 bg-white"
        >
          <option value="">Tous les rôles</option>
          {ROLE_OPTIONS.map(option => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {utilisateurs.map(utilisateur => (
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

---

## 7️⃣ Fichier components/bulletins/BulletinDetail.jsx

```jsx
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useBulletin } from '../../hooks/useBulletin';
import { PERIODE_ENUM, PERIODE_LABELS, PERIODE_COLORS } from '../../utils/enums';

export default function BulletinDetail() {
  const { eleveId, periodo } = useParams(); // periodo vient de l'URL (valeur ENUM)
  const { bulletin, loading, error, fetchBulletin } = useBulletin();

  useEffect(() => {
    if (eleveId && periodo) {
      // ⭐ Passer directement la valeur ENUM
      fetchBulletin(eleveId, periodo);
    }
  }, [eleveId, periodo]);

  if (loading) return <div className="text-center py-8">Chargement du bulletin...</div>;
  if (error) return <div className="text-red-600 text-center py-8">{error}</div>;
  if (!bulletin) return <div className="text-center py-8">Aucun bulletin trouvé</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="bg-white rounded-lg shadow-lg p-8">
        {/* En-tête */}
        <div className="text-center mb-8 border-b-2 pb-6">
          <h1 className="text-3xl font-bold">{bulletin.ecole}</h1>
          <p className="text-gray-600">Code: {bulletin.Code}</p>
          <p className="text-gray-600">{bulletin.ville}, {bulletin.commune_territoire}</p>
        </div>

        {/* Période - ⭐ Afficher avec couleur et label */}
        <div className="mb-4">
          <span className={`px-4 py-2 rounded-full text-sm font-semibold ${PERIODE_COLORS[bulletin.numeroPeriode]}`}>
            {bulletin.periode}
          </span>
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
          {/* ... autres champs ... */}
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

        {/* Résumé */}
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
        </div>
      </div>
    </div>
  );
}
```

---

## 8️⃣ Exemple d'utilisation: Sélectionner une Période

```jsx
import { PERIODE_ENUM, PERIODE_OPTIONS } from '../../utils/enums';
import { useBulletin } from '../../hooks/useBulletin';

export default function BulletinSearch() {
  const [eleveId, setEleveId] = useState('');
  const [selectedPeriode, setSelectedPeriode] = useState(PERIODE_ENUM.PREMIERE);
  const { fetchBulletin } = useBulletin();

  const handleSearch = () => {
    if (eleveId) {
      // ⭐ Passer selectedPeriode qui est une valeur ENUM
      fetchBulletin(eleveId, selectedPeriode);
    }
  };

  return (
    <div className="p-4 bg-white rounded-lg shadow">
      <h2 className="text-xl font-bold mb-4">Rechercher un Bulletin</h2>
      
      <div className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">ID Élève</label>
          <input
            type="number"
            value={eleveId}
            onChange={(e) => setEleveId(e.target.value)}
            placeholder="1"
            className="border rounded px-3 py-2 w-full"
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Période</label>
          <select
            value={selectedPeriode}
            onChange={(e) => setSelectedPeriode(e.target.value)}
            className="border rounded px-3 py-2 w-full"
          >
            {PERIODE_OPTIONS.map(option => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </div>

        <button
          onClick={handleSearch}
          className="w-full bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Chercher le Bulletin
        </button>
      </div>
    </div>
  );
}
```

---

## 🎯 POINTS CLÉS À RETENIR

### ✅ Utiliser les ÉNUMS correctement:

1. **Pour envoyer au backend:**
   ```javascript
   role: 'PROFESSEUR'        // ⭐ Utiliser ROLE_ENUM.PROFESSEUR
   periode: 'PREMIERE'        // ⭐ Utiliser PERIODE_ENUM.PREMIERE
   ```

2. **Pour afficher à l'utilisateur:**
   ```javascript
   {ROLE_LABELS[user.role]}   // Affiche "Professeur"
   {PERIODE_LABELS[bulletin.numeroPeriode]}  // Affiche "1ère période"
   ```

3. **Pour les couleurs:**
   ```javascript
   className={ROLE_COLORS[user.role]}
   className={PERIODE_COLORS[bulletin.numeroPeriode]}
   ```

4. **Pour les listes:**
   ```javascript
   {ROLE_OPTIONS.map(option => (...))}
   {PERIODE_OPTIONS.map(option => (...))}
   ```

5. **Pour la validation:**
   ```javascript
   z.enum(Object.values(ROLE_ENUM))
   z.enum(Object.values(PERIODE_ENUM))
   ```

---

Voilà ! Le projet est maintenant complet avec les énumérations correctement gérées ! 🚀
