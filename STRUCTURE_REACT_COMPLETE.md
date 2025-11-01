# 📁 STRUCTURE REACT - FICHIERS À CRÉER

## 🎯 Structure Complète du Dossier Frontend

```
ecole-bulletins/
├── public/
│   ├── index.html
│   └── favicon.ico
├── src/
│   ├── assets/
│   │   ├── images/
│   │   └── styles/
│   │       └── tailwind.css
│   ├── components/
│   │   ├── common/
│   │   │   ├── Header.jsx
│   │   │   ├── Sidebar.jsx
│   │   │   ├── Loading.jsx
│   │   │   ├── ErrorAlert.jsx
│   │   │   └── SuccessAlert.jsx
│   │   ├── eleves/
│   │   │   ├── EleveList.jsx
│   │   │   ├── EleveForm.jsx
│   │   │   ├── EleveDetail.jsx
│   │   │   ├── EleveCard.jsx
│   │   │   └── EleveDelete.jsx
│   │   ├── cours/
│   │   │   ├── CoursList.jsx
│   │   │   ├── CoursForm.jsx
│   │   │   ├── CoursCard.jsx
│   │   │   └── CoursDelete.jsx
│   │   ├── notes/
│   │   │   ├── NoteList.jsx
│   │   │   ├── NoteForm.jsx
│   │   │   ├── NoteByEleve.jsx
│   │   │   ├── NoteTable.jsx
│   │   │   └── NoteDelete.jsx
│   │   └── bulletins/
│   │       ├── BulletinDetail.jsx
│   │       ├── BulletinSearch.jsx
│   │       ├── BulletinCard.jsx
│   │       ├── BulletinPrint.jsx
│   │       └── BulletinDownloadPDF.jsx
│   ├── pages/
│   │   ├── ElevesPage.jsx
│   │   ├── CoursPage.jsx
│   │   ├── NotesPage.jsx
│   │   ├── BulletinsPage.jsx
│   │   ├── Dashboard.jsx
│   │   └── NotFound.jsx
│   ├── services/
│   │   ├── api.js
│   │   ├── eleveService.js
│   │   ├── coursService.js
│   │   ├── noteService.js
│   │   └── bulletinService.js
│   ├── hooks/
│   │   ├── useEleves.js
│   │   ├── useCours.js
│   │   ├── useNotes.js
│   │   ├── useBulletin.js
│   │   └── useFetch.js
│   ├── store/
│   │   ├── eleveStore.js
│   │   ├── coursStore.js
│   │   ├── noteStore.js
│   │   └── bulletinStore.js
│   ├── utils/
│   │   ├── validation.js
│   │   ├── formatters.js
│   │   ├── constants.js
│   │   └── helpers.js
│   ├── context/
│   │   ├── AuthContext.jsx
│   │   └── NotificationContext.jsx
│   ├── App.jsx
│   ├── App.css
│   └── index.js
├── .env
├── .env.local
├── .gitignore
├── package.json
├── package-lock.json
├── tailwind.config.js
├── postcss.config.js
└── README.md
```

---

## 📦 Package.json Recommandé

```json
{
  "name": "ecole-bulletins",
  "version": "1.0.0",
  "description": "Application de gestion de bulletins scolaires",
  "private": true,
  "dependencies": {
    "react": "^18.3.1",
    "react-dom": "^18.3.1",
    "react-router-dom": "^6.20.1",
    "axios": "^1.6.2",
    "zustand": "^4.4.7",
    "react-hook-form": "^7.50.0",
    "@hookform/resolvers": "^3.3.4",
    "zod": "^3.22.4",
    "@heroicons/react": "^2.0.18",
    "date-fns": "^2.30.0",
    "html2pdf.js": "^0.10.1",
    "jspdf": "^2.5.1",
    "html2canvas": "^1.4.1",
    "clsx": "^2.0.0",
    "react-scripts": "5.0.1"
  },
  "devDependencies": {
    "tailwindcss": "^3.4.1",
    "postcss": "^8.4.32",
    "autoprefixer": "^10.4.17"
  },
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },
  "eslintConfig": {
    "extends": [
      "react-app"
    ]
  },
  "browserslist": {
    "production": [
      ">0.2%",
      "not dead",
      "not op_mini all"
    ],
    "development": [
      "last 1 chrome version",
      "last 1 firefox version",
      "last 1 safari version"
    ]
  }
}
```

---

## 🎨 Exemple: services/eleveService.js

```javascript
import api from './api';

export const eleveService = {
  // ✅ GET all
  getAllEleves: () => 
    api.get('/eleves')
      .then(res => res.data)
      .catch(err => {
        console.error('Erreur getAllEleves:', err);
        throw new Error(err.response?.data?.message || 'Erreur de chargement');
      }),

  // ✅ GET by ID
  getEleveById: (id) => 
    api.get(`/eleves/${id}`)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Élève non trouvé');
      }),

  // ✅ POST create
  createEleve: (eleveData) => 
    api.post('/eleves', eleveData)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de création');
      }),

  // ✅ PUT update
  updateEleve: (id, eleveData) => 
    api.put(`/eleves/${id}`, eleveData)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de modification');
      }),

  // ✅ DELETE
  deleteEleve: (id) => 
    api.delete(`/eleves/${id}`)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de suppression');
      }),
};
```

---

## 🎨 Exemple: services/bulletinService.js

```javascript
import api from './api';

export const bulletinService = {
  // ✅ GET bulletin pour un élève et une période
  getBulletin: (eleveId, periode) => 
    api.get(`/bulletins/${eleveId}/${periode}`)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Bulletin non trouvé');
      }),

  // ✅ GET bulletins par classe
  getBulletinsByClasse: (classe, periode) => 
    api.get(`/bulletins/classe/${classe}/${periode}`)
      .then(res => res.data)
      .catch(err => {
        throw new Error(err.response?.data?.message || 'Erreur de chargement');
      }),

  // ✅ Télécharger en PDF
  downloadBulletinPDF: (eleveId, periode) => 
    api.get(`/bulletins/${eleveId}/${periode}/pdf`, { responseType: 'blob' })
      .then(res => res.data)
      .catch(err => {
        throw new Error('Erreur du téléchargement PDF');
      }),
};
```

---

## 🎨 Exemple: hooks/useBulletin.js

```javascript
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
      const data = await bulletinService.getBulletin(eleveId, periode);
      setBulletin(data);
      return data;
    } catch (err) {
      const message = err.message || 'Erreur lors du chargement du bulletin';
      setError(message);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const clearBulletin = useCallback(() => {
    setBulletin(null);
    setError(null);
  }, []);

  return {
    bulletin,
    loading,
    error,
    fetchBulletin,
    clearBulletin,
  };
}
```

---

## 🎨 Exemple: utils/formatters.js

```javascript
// Formatage des dates
export const formatDate = (date) => {
  if (!date) return '';
  return new Date(date).toLocaleDateString('fr-FR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  });
};

// Formatage des nombres
export const formatNumber = (number, decimals = 2) => {
  return Number(number).toFixed(decimals);
};

// Formatage du pourcentage
export const formatPercentage = (percentage) => {
  return `${formatNumber(percentage, 2)}%`;
};

// Nom complet
export const getFullName = (nom, postnom, prenom) => {
  return `${nom} ${postnom} ${prenom}`.trim();
};

// Sexe lisible
export const formatSexe = (sexe) => {
  return sexe === 'M' ? 'Masculin' : sexe === 'F' ? 'Féminin' : sexe;
};

// Mention avec couleur
export const getMentionColor = (mention) => {
  const colors = {
    'Faible': 'text-red-600 bg-red-100',
    'Passable': 'text-orange-600 bg-orange-100',
    'Assez Bien': 'text-yellow-600 bg-yellow-100',
    'Bien': 'text-blue-600 bg-blue-100',
    'Très Bien': 'text-green-600 bg-green-100',
    'Excellent': 'text-purple-600 bg-purple-100',
  };
  return colors[mention] || 'text-gray-600 bg-gray-100';
};
```

---

## 🎨 Exemple: utils/constants.js

```javascript
// Périodes disponibles
export const PERIODES = [
  { value: 'PREMIERE', label: '1ère période' },
  { value: 'DEUXIEME', label: '2e période' },
  { value: 'TROISIEME', label: '3e période' },
];

// Sexes disponibles
export const SEXES = [
  { value: 'M', label: 'Masculin' },
  { value: 'F', label: 'Féminin' },
];

// Mentions
export const MENTIONS = {
  'Faible': { min: 0, max: 40, color: 'red' },
  'Passable': { min: 40, max: 50, color: 'orange' },
  'Assez Bien': { min: 50, max: 60, color: 'yellow' },
  'Bien': { min: 60, max: 70, color: 'blue' },
  'Très Bien': { min: 70, max: 80, color: 'green' },
  'Excellent': { min: 80, max: 100, color: 'purple' },
};

// Messages
export const MESSAGES = {
  SUCCESS_CREATE: 'Création réussie',
  SUCCESS_UPDATE: 'Modification réussie',
  SUCCESS_DELETE: 'Suppression réussie',
  ERROR_CREATE: 'Erreur lors de la création',
  ERROR_UPDATE: 'Erreur lors de la modification',
  ERROR_DELETE: 'Erreur lors de la suppression',
  ERROR_LOAD: 'Erreur lors du chargement',
};

// API Base URL
export const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
```

---

## 🎨 Exemple: App.jsx

```jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/common/Header';
import Sidebar from './components/common/Sidebar';
import ElevesPage from './pages/ElevesPage';
import CoursPage from './pages/CoursPage';
import NotesPage from './pages/NotesPage';
import BulletinsPage from './pages/BulletinsPage';
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

## 🎨 Exemple: .env

```bash
# API Configuration
REACT_APP_API_URL=http://localhost:8080/api

# Application Configuration
REACT_APP_NAME=Gestion de Bulletins
REACT_APP_VERSION=1.0.0

# Feature Flags
REACT_APP_ENABLE_PDF_EXPORT=true
REACT_APP_ENABLE_PRINT=true
```

---

## 🎨 Exemple: tailwind.config.js

```javascript
/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#3b82f6',
        secondary: '#8b5cf6',
        danger: '#ef4444',
        success: '#10b981',
        warning: '#f59e0b',
        info: '#06b6d4',
      },
    },
  },
  plugins: [],
}
```

---

## 🎨 Exemple: pages/Dashboard.jsx

```jsx
import React, { useEffect, useState } from 'react';
import { useEleveStore } from '../store/eleveStore';
import { useCoursStore } from '../store/coursStore';
import { useNoteStore } from '../store/noteStore';

export default function Dashboard() {
  const { eleves, fetchEleves } = useEleveStore();
  const { cours, fetchCours } = useCoursStore();
  const { notes, fetchNotes } = useNoteStore();

  useEffect(() => {
    fetchEleves();
    fetchCours();
    fetchNotes();
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-4xl font-bold mb-8">Tableau de Bord</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Carte Élèves */}
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Élèves</h3>
          <p className="text-4xl font-bold text-blue-600 mt-2">{eleves.length}</p>
          <p className="text-gray-500 text-sm mt-2">Total inscrits</p>
        </div>

        {/* Carte Cours */}
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Cours</h3>
          <p className="text-4xl font-bold text-green-600 mt-2">{cours.length}</p>
          <p className="text-gray-500 text-sm mt-2">Total cours</p>
        </div>

        {/* Carte Notes */}
        <div className="bg-white rounded-lg shadow p-6">
          <h3 className="text-gray-500 text-sm font-semibold uppercase">Notes</h3>
          <p className="text-4xl font-bold text-purple-600 mt-2">{notes.length}</p>
          <p className="text-gray-500 text-sm mt-2">Total notes</p>
        </div>
      </div>
    </div>
  );
}
```

---

## ✅ Checklist Installation React

- [ ] `npx create-react-app ecole-bulletins`
- [ ] `npm install axios react-router-dom zustand react-hook-form @hookform/resolvers zod`
- [ ] `npm install -D tailwindcss postcss autoprefixer`
- [ ] `npx tailwindcss init -p`
- [ ] Créer la structure de dossiers
- [ ] Créer les fichiers services
- [ ] Créer les fichiers hooks
- [ ] Créer les fichiers components
- [ ] Créer le fichier App.jsx
- [ ] Créer les pages
- [ ] Tester avec `npm start`

---

Bon développement ! 🚀
