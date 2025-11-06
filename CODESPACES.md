# 🚀 GitHub Codespaces - Guide de démarrage rapide

Ce projet est maintenant configuré avec GitHub Codespaces pour un développement facile avec Docker.

## 📦 Que contient cette configuration ?

Cette configuration crée automatiquement **2 containers Docker** :

### 1. 🐘 PostgreSQL
- **Image** : postgres:16-alpine
- **Port** : 5432
- **Credentials** :
  - Username : `postgres`
  - Password : `2025`
  - Database : `Ecole`

### 2. ☕ Spring Boot (Java 21)
- **Port** : 8080
- **Outils inclus** : Maven, PostgreSQL Client
- **Extensions VS Code** : Java Pack, Spring Boot, Docker

## 🎯 Démarrage rapide

### 1. Créer un Codespace

1. Sur GitHub, cliquez sur **Code** > **Codespaces**
2. Cliquez sur **Create codespace on main** (ou sur votre branche)
3. Attendez quelques minutes pendant la construction des containers

### 2. Lancer l'application

Une fois le Codespace ouvert, dans le terminal :

```bash
# Méthode 1 : Avec Maven Wrapper
./mvnw spring-boot:run

# Méthode 2 : Build puis exécution
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

L'application sera accessible sur **http://localhost:8080**

### 3. Vérifier PostgreSQL

```bash
# Se connecter à la base de données
psql -h postgres -U postgres -d Ecole
# Entrer le mot de passe : 2025

# Lister les tables
\dt

# Quitter
\q
```

## 🔧 Configuration technique

### Communication entre containers

Les containers communiquent via un réseau Docker nommé `ecole-network`. L'application Spring Boot se connecte à PostgreSQL en utilisant le hostname `postgres` au lieu de `localhost`.

### Variables d'environnement

Dans le container Spring Boot :
- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/Ecole`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=2025`

### Développement local vs Docker

Le fichier `application.properties` est maintenant compatible avec les deux environnements :
- **Dans Codespaces** : Utilise les variables d'environnement Docker
- **En local** : Utilise `localhost:5432` par défaut

## 📚 Documentation complète

Pour plus de détails, consultez [.devcontainer/README.md](.devcontainer/README.md)

## ⚡ Tips

- Les données PostgreSQL sont **persistées** entre les sessions
- Le cache Maven est **partagé** pour des builds plus rapides
- VS Code détecte automatiquement les ports et propose de les ouvrir
- Utilisez le **Spring Boot Dashboard** (extension) pour gérer l'application

## 🆘 Problèmes courants

### L'application ne se connecte pas à PostgreSQL
```bash
# Vérifier que PostgreSQL est en cours d'exécution
docker ps

# Vérifier les logs
docker logs devcontainer-postgres-1
```

### Rebuild du container
Si vous modifiez le Dockerfile ou docker-compose.yml :
1. Ouvrez la palette de commandes (Ctrl+Shift+P)
2. Tapez "Rebuild Container"
3. Sélectionnez "Codespaces: Rebuild Container"

## 🎉 C'est tout !

Vous êtes prêt à développer ! 🚀
