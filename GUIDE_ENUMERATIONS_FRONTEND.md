# 📘 GUIDE DES ÉNUMÉRATIONS - FRONTEND REACT

## 🎯 Vue d'ensemble

Le backend utilise des énumérations (ENUMS) pour les champs `role`, `periode` et `sexe`. Le frontend doit gérer ces énumérations correctement.

---

## 1️⃣ ÉNUMÉRATION ROLE (Utilisateur)

### Définition Backend
```java
public enum Role {
    ADMIN("Administrateur"),
    PROFESSEUR("Professeur"),
    PARENT("Parent"),
    PERCEPTEUR("Percepteur");
}
```

### Utilisation au Frontend

#### Constantes (utils/enums.js)
```javascript
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
```

#### Formulaire de Création Utilisateur
```jsx
import { ROLE_OPTIONS, ROLE_LABELS } from '../../utils/enums';

export default function UtilisateurForm() {
  return (
    <form>
      <div>
        <label>Rôle</label>
        <select {...register('role', { required: 'Requis' })}>
          <option value="">-- Sélectionner un rôle --</option>
          {ROLE_OPTIONS.map(option => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>
    </form>
  );
}
```

#### Affichage avec Badge
```jsx
import { ROLE_LABELS, ROLE_COLORS } from '../../utils/enums';

export default function UtilisateurCard({ utilisateur }) {
  return (
    <div>
      <h3>{utilisateur.nomComplet}</h3>
      <span className={`px-3 py-1 rounded-full text-xs font-semibold ${ROLE_COLORS[utilisateur.role]}`}>
        {ROLE_LABELS[utilisateur.role]}
      </span>
    </div>
  );
}
```

#### Service API avec Rôle
```javascript
export const utilisateurService = {
  // Créer un utilisateur avec un rôle spécifique
  createUtilisateur: (utilisateurData) => 
    api.post('/utilisateurs', {
      ...utilisateurData,
      role: utilisateurData.role, // Envoyer la valeur ENUM
    }),

  // Récupérer les utilisateurs par rôle
  getUtilisateursByRole: (role) => 
    api.get(`/utilisateurs/role/${role}`), // role est la valeur ENUM (ex: 'PROFESSEUR')
};
```

#### Utilisation du Hook
```jsx
import { useUtilisateurs } from '../../hooks/useUtilisateurs';
import { ROLE_ENUM } from '../../utils/enums';

export default function ListeProfesseurs() {
  const { utilisateurs, fetchUtilisateursByRole } = useUtilisateurs();

  useEffect(() => {
    // Récupérer uniquement les professeurs
    fetchUtilisateursByRole(ROLE_ENUM.PROFESSEUR);
  }, []);

  return (
    <div>
      {utilisateurs.map(prof => (
        <div key={prof.id}>{prof.nomComplet}</div>
      ))}
    </div>
  );
}
```

---

## 2️⃣ ÉNUMÉRATION PERIODE

### Définition Backend
```java
public enum Periode {
    PREMIERE("1ère période"),
    DEUXIEME("2e période"),
    TROISIEME("3e période"),
    EXAMEN_PREMIER_SEMESTRE("Examen premier semestre"),
    EXAMEN_SECOND_SEMESTRE("Examen second semestre");
}
```

### Utilisation au Frontend

#### Constantes (utils/enums.js)
```javascript
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

export const PERIODE_OPTIONS = [
  { value: PERIODE_ENUM.PREMIERE, label: PERIODE_LABELS.PREMIERE },
  { value: PERIODE_ENUM.DEUXIEME, label: PERIODE_LABELS.DEUXIEME },
  { value: PERIODE_ENUM.TROISIEME, label: PERIODE_LABELS.TROISIEME },
  { value: PERIODE_ENUM.EXAMEN_PREMIER_SEMESTRE, label: PERIODE_LABELS.EXAMEN_PREMIER_SEMESTRE },
  { value: PERIODE_ENUM.EXAMEN_SECOND_SEMESTRE, label: PERIODE_LABELS.EXAMEN_SECOND_SEMESTRE },
];
```

#### Formulaire de Sélection Période
```jsx
import { PERIODE_OPTIONS } from '../../utils/enums';

export default function BulletinSearch() {
  const [selectedPeriode, setSelectedPeriode] = useState('');

  return (
    <form>
      <div>
        <label>Période</label>
        <select value={selectedPeriode} onChange={(e) => setSelectedPeriode(e.target.value)}>
          <option value="">-- Sélectionner une période --</option>
          {PERIODE_OPTIONS.map(option => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>
    </form>
  );
}
```

#### Récupérer un Bulletin
```jsx
import { PERIODE_ENUM } from '../../utils/enums';

export default function BulletinDetail({ eleveId }) {
  const { bulletin, fetchBulletin } = useBulletin();

  const handleGetBulletin = async () => {
    // Utiliser la valeur ENUM
    await fetchBulletin(eleveId, PERIODE_ENUM.PREMIERE);
  };

  return (
    <div>
      {bulletin && (
        <div>
          <p>Période: {bulletin.periode}</p>
          <p>Numéro Période: {bulletin.numeroPeriode}</p>
        </div>
      )}
    </div>
  );
}
```

#### Service API
```javascript
export const bulletinService = {
  // Récupérer un bulletin
  // periodeEnum doit être une valeur ENUM (ex: 'PREMIERE', 'EXAMEN_PREMIER_SEMESTRE')
  getBulletin: (eleveId, periodeEnum) => 
    api.get(`/bulletins/${eleveId}/${periodeEnum}`),
};
```

---

## 3️⃣ ÉNUMÉRATION SEXE

### Définition Backend
```javascript
// Pas d'ENUM côté backend, mais utilisé comme String
// Valeurs acceptées: "M" ou "F"
```

### Utilisation au Frontend

#### Constantes (utils/enums.js)
```javascript
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

#### Formulaire
```jsx
import { SEXE_OPTIONS } from '../../utils/enums';

export default function EleveForm() {
  return (
    <form>
      <div>
        <label>Sexe</label>
        <select {...register('sexe', { required: 'Requis' })}>
          <option value="">-- Sélectionner --</option>
          {SEXE_OPTIONS.map(option => (
            <option key={option.value} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      </div>
    </form>
  );
}
```

---

## 4️⃣ FICHIER COMPLET utils/enums.js

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

export const ROLE_ICONS = {
  ADMIN: '👨‍💼',
  PROFESSEUR: '🎓',
  PARENT: '👨‍👩‍👧',
  PERCEPTEUR: '💰',
};

export const ROLE_OPTIONS = [
  { value: ROLE_ENUM.ADMIN, label: ROLE_LABELS.ADMIN, icon: ROLE_ICONS.ADMIN },
  { value: ROLE_ENUM.PROFESSEUR, label: ROLE_LABELS.PROFESSEUR, icon: ROLE_ICONS.PROFESSEUR },
  { value: ROLE_ENUM.PARENT, label: ROLE_LABELS.PARENT, icon: ROLE_ICONS.PARENT },
  { value: ROLE_ENUM.PERCEPTEUR, label: ROLE_LABELS.PERCEPTEUR, icon: ROLE_ICONS.PERCEPTEUR },
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

// ========================
// FONCTION UTILITAIRE
// ========================
export const getEnumLabel = (enumType, value) => {
  const labels = {
    role: ROLE_LABELS,
    periode: PERIODE_LABELS,
    sexe: SEXE_LABELS,
  };
  return labels[enumType]?.[value] || value;
};

export const getEnumColor = (enumType, value) => {
  const colors = {
    role: ROLE_COLORS,
    periode: PERIODE_COLORS,
  };
  return colors[enumType]?.[value] || 'bg-gray-100 text-gray-800';
};
```

---

## 5️⃣ EXEMPLES D'UTILISATION COMPLETS

### Exemple 1: Afficher un Rôle avec Badge
```jsx
import { ROLE_LABELS, ROLE_COLORS } from '../../utils/enums';

function RoleBadge({ role }) {
  return (
    <span className={`px-3 py-1 rounded-full text-xs font-semibold ${ROLE_COLORS[role]}`}>
      {ROLE_LABELS[role]}
    </span>
  );
}

// Utilisation
<RoleBadge role="PROFESSEUR" /> 
// Affiche: "Professeur" avec couleur bleue
```

### Exemple 2: Sélectionner une Période
```jsx
import { PERIODE_ENUM, PERIODE_OPTIONS, PERIODE_LABELS } from '../../utils/enums';

function PeriodeSelect({ value, onChange }) {
  return (
    <select value={value} onChange={(e) => onChange(e.target.value)}>
      <option value="">-- Choisir une période --</option>
      {PERIODE_OPTIONS.map(option => (
        <option key={option.value} value={option.value}>
          {option.label}
        </option>
      ))}
    </select>
  );
}

// Utilisation
const [periode, setPeriode] = useState(PERIODE_ENUM.PREMIERE);
<PeriodeSelect value={periode} onChange={setPeriode} />
```

### Exemple 3: Récupérer un Bulletin avec Période
```jsx
import { useBulletin } from '../../hooks/useBulletin';
import { PERIODE_ENUM } from '../../utils/enums';

export default function BulletinDetail() {
  const { bulletin, fetchBulletin } = useBulletin();

  const handleGetBulletin = async (eleveId, periodeValue) => {
    // periodeValue est une valeur ENUM (ex: 'PREMIERE')
    await fetchBulletin(eleveId, periodeValue);
  };

  return (
    <div>
      <button onClick={() => handleGetBulletin(1, PERIODE_ENUM.PREMIERE)}>
        Voir Bulletin 1ère Période
      </button>
      <button onClick={() => handleGetBulletin(1, PERIODE_ENUM.EXAMEN_PREMIER_SEMESTRE)}>
        Voir Examen 1er Semestre
      </button>
    </div>
  );
}
```

### Exemple 4: Filtrer les Utilisateurs par Rôle
```jsx
import { useUtilisateurs } from '../../hooks/useUtilisateurs';
import { ROLE_ENUM, ROLE_OPTIONS } from '../../utils/enums';

export default function FiltrerUtilisateurs() {
  const { utilisateurs, fetchUtilisateursByRole } = useUtilisateurs();
  const [roleFilter, setRoleFilter] = useState('');

  const handleFilterByRole = (role) => {
    if (role) {
      fetchUtilisateursByRole(role); // role est une valeur ENUM
    }
  };

  return (
    <div>
      <select value={roleFilter} onChange={(e) => setRoleFilter(e.target.value)}>
        <option value="">-- Tous les rôles --</option>
        {ROLE_OPTIONS.map(option => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
      <button onClick={() => handleFilterByRole(roleFilter)}>Filtrer</button>
      
      <div>
        {utilisateurs.map(user => (
          <div key={user.id}>{user.nomComplet} - {user.role}</div>
        ))}
      </div>
    </div>
  );
}
```

---

## 6️⃣ VALIDATION ZOD AVEC ÉNUMÉRATIONS

```javascript
// utils/validation.js
import { z } from 'zod';
import { ROLE_ENUM, PERIODE_ENUM, SEXE_ENUM } from './enums';

const roleValues = Object.values(ROLE_ENUM);
const periodeValues = Object.values(PERIODE_ENUM);
const sexeValues = Object.values(SEXE_ENUM);

export const utilisateurSchema = z.object({
  nomComplet: z.string().min(2, 'Minimum 2 caractères'),
  role: z.enum(roleValues, {
    errorMap: () => ({ message: 'Rôle invalide' })
  }),
  telephone: z.string().min(10, 'Téléphone invalide'),
  email: z.string().email('Email invalide'),
  motDePasse: z.string().min(6, 'Minimum 6 caractères'),
});

export const eleveSchema = z.object({
  nomComplet: z.string().min(2),
  sexe: z.enum(sexeValues, {
    errorMap: () => ({ message: 'Sexe invalide' })
  }),
  // ... autres champs
});

export const noteSchema = z.object({
  eleveId: z.number().positive(),
  coursId: z.number().positive(),
  valeur: z.number().min(0).max(20),
  periode: z.enum(periodeValues, {
    errorMap: () => ({ message: 'Période invalide' })
  }),
});
```

---

## 7️⃣ STRUCTURE DE RÉPONSE API

Quand l'API retourne un objet utilisateur:
```json
{
  "id": 1,
  "nomComplet": "Dr. Jean Mukendi",
  "role": "PROFESSEUR",
  "telephone": "+243123456789",
  "email": "jean.mukendi@umoja.edu",
  "actif": true
}
```

Le frontend doit traiter `role` comme une **valeur ENUM** :
```jsx
import { ROLE_LABELS, ROLE_COLORS } from '../../utils/enums';

// Afficher le rôle
<span className={ROLE_COLORS[user.role]}>
  {ROLE_LABELS[user.role]}
</span>
// Affiche: "Professeur" avec les bonnes couleurs
```

---

## 8️⃣ CHECKLIST FRONTEND

- ✅ Créer `utils/enums.js` avec toutes les énumérations
- ✅ Utiliser `ROLE_ENUM` pour envoyer au backend
- ✅ Utiliser `ROLE_LABELS` pour afficher à l'utilisateur
- ✅ Utiliser `PERIODE_ENUM` dans les appels API
- ✅ Utiliser `SEXE_ENUM` pour les formulaires
- ✅ Ajouter les validations Zod avec les énumérations
- ✅ Afficher les badges avec `ROLE_COLORS` et `PERIODE_COLORS`
- ✅ Sélectionner les options avec `ROLE_OPTIONS`, `PERIODE_OPTIONS`, etc.

---

## 📝 FORMAT DES DONNÉES REÇUES

### Utilisateur reçu
```javascript
{
  "id": 1,
  "nomComplet": "Dr. Jean Mukendi",
  "role": "PROFESSEUR",        // ⭐ Valeur ENUM
  "telephone": "+243123456789",
  "email": "jean.mukendi@umoja.edu",
  "actif": true
}

// Au frontend:
ROLE_LABELS[utilisateur.role]   // "Professeur"
ROLE_COLORS[utilisateur.role]   // "bg-blue-100 text-blue-800"
```

### Bulletin reçu
```javascript
{
  "nomComplet": "Kabongo Florent",
  "periode": "1ère période",           // Label
  "numeroPeriode": "PREMIERE",         // ⭐ Valeur ENUM
  "notes": [...],
  "mention": "Faible"
}

// Au frontend:
PERIODE_LABELS[bulletin.numeroPeriode]   // "1ère période"
PERIODE_COLORS[bulletin.numeroPeriode]   // "bg-blue-100 text-blue-800"
```

---

Bon développement frontend ! 🚀
