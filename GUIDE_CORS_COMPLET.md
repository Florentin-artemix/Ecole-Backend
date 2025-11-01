# 🔒 CONFIGURATION CORS - GUIDE COMPLET

## 📋 Vue d'ensemble

La configuration CORS (Cross-Origin Resource Sharing) permet à votre frontend React de communiquer avec votre backend Spring Boot sans restrictions de sécurité.

---

## 1️⃣ FICHIER: CorsConfig.java

### Localisation
```
src/main/java/com/Ecole/demo/config/CorsConfig.java
```

### Description
Cette classe configure les paramètres CORS au niveau de l'application Spring Boot.

### Fonctionnalités
✅ **Origines autorisées** - Configurable via `application.properties`
✅ **Méthodes HTTP** - GET, POST, PUT, DELETE, OPTIONS, PATCH
✅ **Headers autorisés** - Tous les headers (*) 
✅ **Credentials** - Support des cookies/authentification
✅ **Cache** - 3600 secondes (1 heure) pour /api/**
✅ **Fichiers statiques** - Configuration séparée pour /static/**

### Code
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins:http://localhost:3000,http://localhost:8080}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuration pour /api/**
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        // Configuration pour /static/**
        registry.addMapping("/static/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .maxAge(86400);
    }
}
```

---

## 2️⃣ FICHIER: application.properties

### Configuration CORS
```properties
# CORS Configuration
cors.allowed.origins=http://localhost:3000,http://localhost:8080,http://localhost:5173
```

### Paramètres

| Paramètre | Valeur | Description |
|-----------|--------|-------------|
| `cors.allowed.origins` | `http://localhost:3000,http://localhost:8080,http://localhost:5173` | Origines autorisées (séparées par virgule) |

### Différents Environnements

#### Développement (Development)
```properties
cors.allowed.origins=http://localhost:3000,http://localhost:8080,http://localhost:5173
```

#### Production
```properties
cors.allowed.origins=https://monsite.com,https://www.monsite.com
```

#### Test
```properties
cors.allowed.origins=http://localhost:3000
```

---

## 3️⃣ MÉTHODES HTTP AUTORISÉES

| Méthode | Utilisation | Autorisée |
|---------|-------------|-----------|
| `GET` | Récupérer des données | ✅ Oui |
| `POST` | Créer des données | ✅ Oui |
| `PUT` | Modifier complètement | ✅ Oui |
| `DELETE` | Supprimer | ✅ Oui |
| `PATCH` | Modification partielle | ✅ Oui |
| `OPTIONS` | Requête preflight | ✅ Oui |
| `HEAD` | Comme GET sans body | ✅ Oui |

---

## 4️⃣ HEADERS AUTORISÉS

### Actuellement
```
"*"  → Tous les headers sont autorisés
```

### Spécifiques (Optionnel)
Si vous voulez restreindre à certains headers :
```java
.allowedHeaders("Content-Type", "Authorization", "X-Requested-With")
```

### Headers Courants
- `Content-Type` - Type de contenu (application/json)
- `Authorization` - Token JWT
- `X-Requested-With` - Identifiant requête
- `Accept` - Format attendu
- `Origin` - Origine de la requête

---

## 5️⃣ FLUX CORS - REQUÊTE PREFLIGHT

### Requête Preflight (OPTIONS)
```
1. Le navigateur envoie une requête OPTIONS
2. Il demande les autorisations CORS
3. Le serveur répond avec les headers CORS
4. Si OK, la requête réelle est envoyée
```

### Exemple cURL
```bash
curl -X OPTIONS http://localhost:8080/api/utilisateurs \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -v
```

### Réponse du Serveur
```
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
Access-Control-Allow-Headers: *
Access-Control-Max-Age: 3600
```

---

## 6️⃣ PROBLÈMES CORS COURANTS

### ❌ Erreur: "No 'Access-Control-Allow-Origin' header"
**Cause:** L'origine n'est pas autorisée
**Solution:** Ajouter l'origine à `cors.allowed.origins`

### ❌ Erreur: "Method not allowed"
**Cause:** La méthode HTTP n'est pas autorisée
**Solution:** Vérifier `allowedMethods` dans CorsConfig

### ❌ Erreur: "Credentials not included"
**Cause:** Les credentials (cookies) ne sont pas envoyés
**Solution:** Ajouter `allowCredentials(true)` et configurer le frontend

### ❌ Erreur: "CORS policy: Credentials mode is 'include'"
**Cause:** Incohérence entre frontend et backend
**Solution:** Utiliser `allowCredentials(true)` ET `withCredentials: true` au frontend

---

## 7️⃣ CONFIGURATION FRONTEND - AXIOS

### Avec Support des Credentials

```javascript
// services/api.js
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,  // ⭐ Envoyer les cookies
});

export default api;
```

### Sans Credentials (Mode par défaut)

```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
  // withCredentials: false  (par défaut)
});
```

---

## 8️⃣ CONFIGURATION FRONTEND - FETCH API

### Avec Support des Credentials

```javascript
fetch('http://localhost:8080/api/utilisateurs', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'include',  // ⭐ Envoyer les cookies
})
.then(response => response.json())
.catch(error => console.error('Erreur:', error));
```

### Sans Credentials

```javascript
fetch('http://localhost:8080/api/utilisateurs', {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  },
  credentials: 'omit',  // Ne pas envoyer les cookies
})
.then(response => response.json());
```

---

## 9️⃣ CONFIGURATION SELON L'ENVIRONNEMENT

### application-dev.properties
```properties
# Développement - Frontend local
cors.allowed.origins=http://localhost:3000,http://localhost:5173,http://localhost:8080
```

### application-prod.properties
```properties
# Production - Domaine sécurisé
cors.allowed.origins=https://www.monecole.com
```

### Utilisation
```bash
# Démarrer en développement
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Démarrer en production
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

---

## 🔟 SÉCURITÉ - BONNES PRATIQUES

### ✅ À FAIRE

1. **En développement:** Autoriser `localhost:3000`
   ```properties
   cors.allowed.origins=http://localhost:3000
   ```

2. **En production:** Utiliser des HTTPS
   ```properties
   cors.allowed.origins=https://www.monecole.com
   ```

3. **Limiter les origines:** Ne pas utiliser `*` en production
   ```java
   .allowedOrigins("https://www.monecole.com")  // ✅ Bien
   .allowedOrigins("*")                         // ❌ Éviter en prod
   ```

4. **Sécuriser les credentials:** Toujours utiliser HTTPS
   ```java
   .allowCredentials(true)  // Seulement avec HTTPS
   ```

### ❌ À ÉVITER

1. Autoriser tous les domaines en production
   ```properties
   cors.allowed.origins=*  // ⚠️ Sécurité réduite
   ```

2. Utiliser les credentials sans HTTPS
   ```java
   .allowCredentials(true)  // Dangereux sans HTTPS
   ```

3. Expouser toutes les origines au frontend
   ```javascript
   baseURL: 'http://localhost:8080'  // OK dev, pas en prod
   ```

---

## 1️⃣1️⃣ CONFIGURATION COMPLÈTE - EXEMPLE

### CorsConfig.java - Version Avancée

```java
package com.Ecole.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins:http://localhost:3000}")
    private String allowedOrigins;

    @Value("${cors.allow.credentials:true}")
    private boolean allowCredentials;

    @Value("${cors.max.age:3600}")
    private long maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuration pour les endpoints API
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(allowCredentials)
                .maxAge(maxAge);

        // Configuration pour les fichiers statiques
        registry.addMapping("/static/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .maxAge(86400);  // 24 heures

        // Configuration pour les ressources publiques
        registry.addMapping("/public/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "OPTIONS")
                .maxAge(86400);
    }
}
```

### application.properties - Configuration Complète

```properties
# =====================
# CORS Configuration
# =====================
cors.allowed.origins=http://localhost:3000,http://localhost:5173,http://localhost:8080
cors.allow.credentials=true
cors.max.age=3600
```

---

## 1️⃣2️⃣ VÉRIFICATION - TEST CORS

### Test avec cURL

```bash
# Test preflight (OPTIONS)
curl -X OPTIONS http://localhost:8080/api/utilisateurs \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type" \
  -v

# Résultat attendu: 200 OK avec headers CORS
```

### Test POST

```bash
# Test POST depuis le frontend
curl -X POST http://localhost:8080/api/utilisateurs \
  -H "Origin: http://localhost:3000" \
  -H "Content-Type: application/json" \
  -d '{
    "nomComplet": "Test",
    "role": "ADMIN",
    "telephone": "+243123456789",
    "email": "test@example.com",
    "motDePasse": "test123"
  }' \
  -v
```

---

## 1️⃣3️⃣ CHECKLIST - MISE EN PLACE

- ✅ Fichier `CorsConfig.java` créé dans `config/`
- ✅ Configuration CORS dans `application.properties`
- ✅ Origines autorisées configurées (localhost:3000)
- ✅ Méthodes HTTP configurées
- ✅ Headers autorisés
- ✅ Credentials activés
- ✅ Frontend utilise `withCredentials: true` (si nécessaire)
- ✅ Test des requêtes CORS

---

## 🎯 RÉSUMÉ

**CORS Config** = Permet au frontend de communiquer avec le backend
**Origines autorisées** = Liste des domaines acceptés
**Méthodes** = HTTP verbes autorisés
**Headers** = Données d'en-tête acceptées
**Credentials** = Support des cookies/authentification
**MaxAge** = Durée du cache des preflight

---

**Le CORS est maintenant complètement configuré ! 🚀**
