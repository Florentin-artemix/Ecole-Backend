# Contrat API Frontend — Projet demo

Ce document est la source unique de vérité pour le frontend. Il décrit:
- Tous les endpoints REST disponibles (URL, méthodes, statuts).
- Les schémas JSON attendus par le backend (requêtes) et renvoyés au frontend (réponses).
- Les énumérations (valeurs acceptées) et le format d’erreur standard.

Base URL: http://localhost:8080/api
Headers:
- Content-Type: application/json
- Accept: application/json

Erreurs (format standard):
{
  "timestamp": "2025-11-05T12:34:56",
  "status": 404,
  "error": "Not Found",
  "message": "Détail de l'erreur",
  "path": "/api/..."
}

Enumérations (valeurs autorisées):
- Periode: PREMIERE, DEUXIEME, TROISIEME, EXAMEN_PREMIER_SEMESTRE, EXAMEN_SECOND_SEMESTRE
  Synonymes acceptés côté backend (tolérants): "1ere", "1ere_periode", "premiere", "examen_1er_semestre", etc. (les tirets/espaces sont normalisés).
- TypeConduite: EXCELLENT, TRES_BON, BON, ASSEZ_BON, PASSABLE, MEDIOCRE, MAUVAIS
- Role: ADMIN, PROFESSEUR, PARENT, PERCEPTEUR

Schémas JSON (DTOs)
Note: champs non listés comme @NotNull peuvent être omis; types de date ISO 8601.

- EcoleDTO (réponse)
  {
    "id": number,
    "nomEcole": string,
    "codeEcole": string,
    "ville": string,
    "commune_territoire": string,
    "adresse": string|null,
    "telephone": string|null,
    "email": string|null,
    "devise": string|null
  }
- EcoleCreateUpdateDTO (requête)
  {
    "nomEcole": string,
    "codeEcole": string,
    "ville": string,
    "commune_territoire": string,
    "adresse": string?,
    "telephone": string?,
    "email": string?,
    "devise": string?
  }

- ClasseDTO (requête/réponse)
  { "id": number?, "nom": string, "description": string? }

- CoursDTO (requête/réponse)
  {
    "id": number?,
    "nomCours": string,
    "ponderation": number,
    "classeId": number?,
    "classeNom": string?,
    "professeurNom": string?,
    "professeurId": number?
  }

- UtilisateurDTO (réponse)
  { "id": number, "nomComplet": string, "role": "ADMIN"|"PROFESSEUR"|"PARENT"|"PERCEPTEUR", "telephone": string?, "email": string?, "actif": boolean }
- UtilisateurCreateDTO (requête)
  { "nomComplet": string, "role": "ADMIN"|"PROFESSEUR"|"PARENT"|"PERCEPTEUR", "telephone": string?, "email": string?, "motDePasse": string? }

- EleveDTO (requête/réponse)
  {
    "id": number?,
    "nom": string, "postnom": string, "prenom": string,
    "sexe": string, // "M" ou "F" selon votre convention
    "dateNaissance": "YYYY-MM-DD",
    "lieuNaissance": string,
    "numeroPermanent": string,
    "classeId": number?,
    "classeNom": string?,
    "ecole": EcoleDTO?,
    "code": string?,
    "ville": string?,
    "commune_territoire": string?
  }

- ParentEleveDTO (réponse)
  { "id": number, "parentId": number, "parentNom": string?, "parentEmail": string?, "parentTelephone": string?, "eleveId": number, "eleveNom": string?, "eleveClasse": string?, "eleveEcole": EcoleDTO?, "lienParente": string }
- ParentEleveCreateDTO (requête)
  { "parentId": number, "eleveId": number, "lienParente": string }
- ParentAvecEnfantsDTO (réponse)
  { "parentId": number, "nomComplet": string?, "email": string?, "telephone": string?, "enfants": EnfantDTO[] }
- EnfantDTO (réponse)
  { "eleveId": number, "nomComplet": string, "sexe": string, "dateNaissance": string, "classe": string, "ecole": EcoleDTO?, "lienParente": string }

- NoteDTO (réponse)
  { "id": number, "eleveId": number, "eleveNom": string?, "coursId": number, "coursNom": string?, "ponderation": number, "valeur": number, "periode": Periode }
- NoteCreateDTO (requête)
  { "eleveId": number, "coursId": number, "valeur": number, "periode": Periode, "typeConduite": TypeConduite?, "commentaireConduite": string? }

- ConduiteDTO (réponse)
  { "id": number, "eleveId": number, "eleveNom": string?, "professeurId": number, "professeurNom": string?, "typeConduite": TypeConduite, "periode": Periode, "commentaire": string? }
- ConduiteCreateDTO (requête)
  { "eleveId": number, "professeurId": number, "typeConduite": TypeConduite, "periode": Periode, "commentaire": string? }
- ConduiteCalculDTO (réponse)
  { "eleveId": number, "eleveNom": string?, "periode": string, "nombreEvaluations": number, "pourcentageMoyen": number, "mentionFinale": TypeConduite, "appreciation": string }

- BulletinDTO (réponse)
  {
    "nomComplet": string,
    "sexe": string,
    "dateNaissance": string,
    "lieuNaissance": string,
    "numeroPermanent": string,
    "classe": string,
    "ecole": EcoleDTO,
    "periode": string,
    "numeroPeriode": string,
    "Code": string,
    "ville": string,
    "commune_territoire": string,
    "notes": NoteDTO[],
    "totalGeneral": number,
    "maximumGeneral": number,
    "pourcentage": number,
    "mention": string,
    "conduite": string,
    "place_nbreEleve": string
  }

- MotifPaiementDTO (réponse)
  { "id": number, "libelle": string, "montantTotal": number, "description": string?, "periode": Periode?, "anneeScolaire": string?, "dateCreation": string?, "dateEcheance": string?, "actif": boolean }
- MotifPaiementCreateDTO (requête)
  { "libelle": string, "montantTotal": number, "description": string?, "periode": Periode?, "anneeScolaire": string?, "dateEcheance": string?, "actif": boolean? }

- PaiementDTO (réponse)
  { "id": number, "eleveId": number, "nomEleve": string?, "prenomEleve": string?, "motifPaiementId": number, "libelleMotif": string?, "montantPaye": number, "datePaiement": string?, "referencePaiement": string?, "modePaiement": string?, "remarque": string?, "recuPar": string? }
- PaiementCreateDTO (requête)
  { "eleveId": number, "motifPaiementId": number, "montantPaye": number, "datePaiement": string?, "referencePaiement": string?, "modePaiement": string?, "remarque": string?, "recuPar": string? }

- SuiviPaiementDTO (réponse)
  { "id": number, "eleveId": number, "nomEleve": string?, "prenomEleve": string?, "motifPaiementId": number, "libelleMotif": string?, "montantAPayer": number, "montantTotal": number (alias de montantAPayer), "montantPaye": number, "montantRestant": number, "statutPaiement": "NON_PAYE"|"PAIEMENT_PARTIEL"|"PAYE_COMPLET", "estEnOrdre": boolean, "dateDernierPaiement": string?, "dateCreation": string?, "dateMaj": string? }

- DerogationDTO (réponse)
  { "id": number, "eleveId": number, "nomEleve": string?, "prenomEleve": string?, "motifPaiementId": number?, "libelleMotif": string?, "motif": string, "statut": "EN_ATTENTE"|"ACCEPTEE"|"REFUSEE"|"EXPIREE", "dateDemande": string, "dateAcceptation": string?, "dateExpiration": string?, "dateRefus": string?, "raisonRefus": string?, "accordeePar": string?, "active": boolean, "estExpiree": boolean, "estValide": boolean? }
- DerogationCreateDTO (requête)
  { "eleveId": number, "motifPaiementId": number?, "motif": string }
- DerogationAccepterDTO (requête)
  { "dateExpiration": string(ISO datetime), "accordeePar": string }
- DerogationRefuserDTO (requête)
  { "raisonRefus": string }


Endpoints par domaine

1) Ecole — /api/ecole
- GET /api/ecole → 200 EcoleDTO | 404
- GET /api/ecole/all → 200 EcoleDTO[]
- GET /api/ecole/{id} → 200 EcoleDTO | 404
- POST /api/ecole → 201 EcoleDTO (body: EcoleCreateUpdateDTO)
- PUT /api/ecole/{id} → 200 EcoleDTO (body: EcoleCreateUpdateDTO)
- DELETE /api/ecole/{id} → 204 | 404

2) Classes — /api/classes
- POST /api/classes → 200 ClasseDTO (body: ClasseDTO)
- GET /api/classes → 200 ClasseDTO[]
- GET /api/classes/{id} → 200 ClasseDTO
- PUT /api/classes/{id} → 200 ClasseDTO (body: ClasseDTO)
- DELETE /api/classes/{id} → 204

3) Cours — /api/cours
- POST /api/cours → 200 CoursDTO (body: CoursDTO)
- GET /api/cours → 200 CoursDTO[]
- GET /api/cours/{id} → 200 CoursDTO
- PUT /api/cours/{id} → 200 CoursDTO (body: CoursDTO)
- DELETE /api/cours/{id} → 204

4) Utilisateurs — /api/utilisateurs
- POST /api/utilisateurs → 200 UtilisateurDTO (body: UtilisateurCreateDTO)
- GET /api/utilisateurs → 200 UtilisateurDTO[]
- GET /api/utilisateurs/{id} → 200 UtilisateurDTO
- GET /api/utilisateurs/role/{role} → 200 UtilisateurDTO[] (role = ADMIN|PROFESSEUR|PARENT|PERCEPTEUR)
- PUT /api/utilisateurs/{id} → 200 UtilisateurDTO (body: UtilisateurCreateDTO)
- DELETE /api/utilisateurs/{id} → 204

5) Eleves — /api/eleves
- POST /api/eleves → 200 EleveDTO (body: EleveDTO)
- GET /api/eleves → 200 EleveDTO[]
- GET /api/eleves/{id} → 200 EleveDTO
- PUT /api/eleves/{id} → 200 EleveDTO (body: EleveDTO)
- DELETE /api/eleves/{id} → 204

6) Parent-Élève — /api/parent-eleve
- GET /api/parent-eleve → 200 ParentEleveDTO[]
- POST /api/parent-eleve → 200 ParentEleveDTO (body: ParentEleveCreateDTO)
- POST /api/parent-eleve/batch → 200 ParentEleveDTO[] (body: ParentEleveCreateDTO[])
- GET /api/parent-eleve/parent/{parentId} → 200 ParentAvecEnfantsDTO
- GET /api/parent-eleve/parent/{parentId}/enfants → 200 ParentEleveDTO[]
- GET /api/parent-eleve/eleve/{eleveId}/parents → 200 ParentEleveDTO[]
- PUT /api/parent-eleve/{id} → 200 ParentEleveDTO (body: ParentEleveCreateDTO)
- DELETE /api/parent-eleve/{id} → 204

7) Notes — /api/notes
- POST /api/notes → 200 NoteDTO (body: NoteCreateDTO)
- POST /api/notes/batch → 200 NoteDTO[] (body: NoteCreateDTO[])
- GET /api/notes → 200 NoteDTO[]
- GET /api/notes/{id} → 200 NoteDTO
- PUT /api/notes/{id} → 200 NoteDTO (body: NoteCreateDTO)
- DELETE /api/notes/{id} → 204

8) Conduites — /api/conduites
- POST /api/conduites → 200 ConduiteDTO (body: ConduiteCreateDTO)
- POST /api/conduites/batch → 200 ConduiteDTO[] (body: ConduiteCreateDTO[])
- GET /api/conduites → 200 ConduiteDTO[]
- GET /api/conduites/{id} → 200 ConduiteDTO
- GET /api/conduites/eleve/{eleveId}/periode/{periode} → 200 ConduiteDTO[]
- GET /api/conduites/professeur/{professeurId}/periode/{periode} → 200 ConduiteDTO[]
- GET /api/conduites/eleve/{eleveId}/periode/{periode}/most-frequent → 200 string (mention dominante)
- GET /api/conduites/eleve/{eleveId}/periode/{periode}/calcul → 200 ConduiteCalculDTO
- GET /api/conduites/eleve/{eleveId}/periode/{periode}/pourcentage → 200 number
- PUT /api/conduites/{id} → 200 ConduiteDTO (body: ConduiteCreateDTO)
- DELETE /api/conduites/{id} → 204

9) Bulletins — /api/bulletins
- GET /api/bulletins/eleve/{eleveId}/periode/{periode} → 200 BulletinDTO
- GET /api/bulletins/{eleveId}/{periode} → 200 BulletinDTO (legacy compatible)

10) Motifs de Paiement — /api/motifs-paiement
- POST /api/motifs-paiement → 201 MotifPaiementDTO (body: MotifPaiementCreateDTO)
- GET /api/motifs-paiement → 200 MotifPaiementDTO[]
- GET /api/motifs-paiement/actifs → 200 MotifPaiementDTO[]
- GET /api/motifs-paiement/periode/{periode} → 200 MotifPaiementDTO[]
- GET /api/motifs-paiement/annee/{anneeScolaire} → 200 MotifPaiementDTO[]
- GET /api/motifs-paiement/{id} → 200 MotifPaiementDTO
- PUT /api/motifs-paiement/{id} → 200 MotifPaiementDTO (body: MotifPaiementCreateDTO)
- PATCH /api/motifs-paiement/{id}/desactiver → 204
- DELETE /api/motifs-paiement/{id} → 204

11) Paiements — /api/paiements (alias /api/paiement)
- POST /api/paiements → 201 PaiementDTO (body: PaiementCreateDTO)
- GET /api/paiements → 200 PaiementDTO[]
- GET /api/paiements/eleve/{eleveId} → 200 PaiementDTO[]
- GET /api/paiements/motif/{motifId} → 200 PaiementDTO[]
- GET /api/paiements/{id} → 200 PaiementDTO

12) Suivi Paiement — /api/suivis-paiement
- GET /api/suivis-paiement → 200 SuiviPaiementDTO[]
- GET /api/suivis-paiement/eleve/{eleveId} → 200 SuiviPaiementDTO[]
- GET /api/suivis-paiement/eleve/{eleveId}/non-en-ordre → 200 SuiviPaiementDTO[]
- GET /api/suivis-paiement/motif/{motifId} → 200 SuiviPaiementDTO[]
- GET /api/suivis-paiement/eleve/{eleveId}/motif/{motifId} → 200 SuiviPaiementDTO
- GET /api/suivis-paiement/eleve/{eleveId}/en-ordre → 200 boolean
- POST /api/suivis-paiement/eleve/{eleveId}/motif/{motifId} → 201 SuiviPaiementDTO (body: none)
- POST /api/suivis-paiement/motif/{motifId}/tous-eleves → 201 (body: none)

13) Dérogations — /api/derogations
- POST /api/derogations → 201 DerogationDTO (body: DerogationCreateDTO)
- POST /api/derogations/demander → 201 DerogationDTO (body: DerogationCreateDTO)
- GET /api/derogations → 200 DerogationDTO[]
- GET /api/derogations/en-attente → 200 DerogationDTO[]
- GET /api/derogations/eleve/{eleveId} → 200 DerogationDTO[]
- GET /api/derogations/eleve/{eleveId}/actives → 200 DerogationDTO[]
- GET /api/derogations/eleve/{eleveId}/valide → 200 DerogationDTO | 404
- GET /api/derogations/eleve/{eleveId}/a-derogation-valide → 200 boolean
- GET /api/derogations/{id} → 200 DerogationDTO
- PATCH /api/derogations/{id}/accepter → 200 DerogationDTO (body: DerogationAccepterDTO)
- PATCH /api/derogations/{id}/refuser → 200 DerogationDTO (body: DerogationRefuserDTO)
- POST /api/derogations/verifier-expirations → 200

Exemples de payloads (création)
- Eleve (POST /api/eleves)
  {
    "nom": "Kabongo", "postnom": "Florent", "prenom": "",
    "sexe": "M", "dateNaissance": "2008-04-12", "lieuNaissance": "Bukavu",
    "numeroPermanent": "12345", "classeId": 1
  }
- Cours (POST /api/cours)
  { "nomCours": "Algèbre", "ponderation": 20, "classeId": 1 }
- Note (POST /api/notes)
  { "eleveId": 1, "coursId": 2, "valeur": 14, "periode": "PREMIERE" }
- Conduite (POST /api/conduites)
  { "eleveId": 1, "professeurId": 10, "typeConduite": "BON", "periode": "DEUXIEME", "commentaire": "Respectueux" }
- Utilisateur (POST /api/utilisateurs)
  { "nomComplet": "Jane Doe", "role": "PROFESSEUR", "email": "jane@ecole.cd" }
- Parent-Élève (POST /api/parent-eleve)
  { "parentId": 5, "eleveId": 1, "lienParente": "Père" }
- Motif Paiement (POST /api/motifs-paiement)
  { "libelle": "Frais scolaire 1ère période", "montantTotal": 100.00, "periode": "PREMIERE", "anneeScolaire": "2024-2025" }
- Paiement (POST /api/paiements)
  { "eleveId": 1, "motifPaiementId": 3, "montantPaye": 50.00, "modePaiement": "CASH", "recuPar": "Percepteur" }
- Dérogation Demande (POST /api/derogations)
  { "eleveId": 1, "motifPaiementId": 3, "motif": "Difficultés temporaires" }
- Dérogation Accepter (PATCH /api/derogations/{id}/accepter)
  { "dateExpiration": "2025-12-31T23:59:59", "accordeePar": "Admin" }
- Dérogation Refuser (PATCH /api/derogations/{id}/refuser)
  { "raisonRefus": "Pièces manquantes" }

Statuts HTTP usuels
- 200 OK: lecture/réussite
- 201 Created: création réalisée
- 204 No Content: suppression/désactivation
- 400 Bad Request: validation invalide
- 404 Not Found: ressource manquante (ou RuntimeException mappée en 404)
- 500 Internal Server Error: erreur serveur

Notes d’intégration Frontend
- Les énumérations ne sont pas sensibles à la casse côté backend (Periode parse tolérant) mais recommandez les valeurs officielles listées.
- Les dates sont au format ISO (LocalDate: YYYY-MM-DD; LocalDateTime: YYYY-MM-DDThh:mm:ss).
- Toujours envoyer/attendre JSON; éviter les formulaires.