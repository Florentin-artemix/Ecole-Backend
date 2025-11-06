# GitHub Codespaces - Configuration

Cette configuration crée un environnement de développement complet pour le projet Ecole Backend avec deux containers Docker :

## Containers

### 1. PostgreSQL (Service: `postgres`)
- **Image**: postgres:16-alpine
- **Port**: 5432
- **Configuration**:
  - Username: `postgres`
  - Password: `2025`
  - Database: `Ecole`
- **Volume**: Les données sont persistées dans un volume Docker nommé `postgres-data`

### 2. Spring Boot Application (Service: `app`)
- **Image**: Basée sur mcr.microsoft.com/devcontainers/java:21
- **Port**: 8080
- **Outils inclus**:
  - Java 21 (OpenJDK)
  - Maven
  - PostgreSQL Client
- **Volume**: Le code source est monté dans `/workspace`

## Communication entre les containers

Les deux containers communiquent via le réseau Docker `ecole-network`. L'application Spring Boot accède à PostgreSQL via le hostname `postgres` (nom du service Docker) au lieu de `localhost`.

Les variables d'environnement suivantes sont configurées dans le container Spring Boot :
- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/Ecole`
- `SPRING_DATASOURCE_USERNAME=postgres`
- `SPRING_DATASOURCE_PASSWORD=2025`

## Utilisation

### Démarrer dans GitHub Codespaces

1. Allez sur le repository GitHub
2. Cliquez sur "Code" > "Codespaces" > "Create codespace on main"
3. Attendez que l'environnement soit prêt (les containers seront construits automatiquement)

### Démarrer l'application

Une fois dans le Codespace :

```bash
# Option 1: Utiliser Maven directement
./mvnw spring-boot:run

# Option 2: Compiler et exécuter
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Vérifier la connexion à PostgreSQL

```bash
# Se connecter à PostgreSQL depuis le terminal
psql -h postgres -U postgres -d Ecole
# Mot de passe: 2025
```

### Accéder à l'application

L'application sera accessible sur le port 8080. VS Code détectera automatiquement le port et proposera de l'ouvrir dans votre navigateur.

## Extensions VS Code installées

Les extensions suivantes sont automatiquement installées :
- Java Extension Pack
- Spring Boot Extension Pack
- Docker Extension
- Spring Boot Dashboard

## Volumes persistants

- `postgres-data`: Stocke les données PostgreSQL
- `maven-cache`: Cache les dépendances Maven pour accélérer les builds

## Notes

- Les containers restent actifs même après fermeture de VS Code (jusqu'à arrêt manuel)
- Les données PostgreSQL sont persistées entre les sessions
- Le cache Maven est partagé pour accélérer les builds successifs
