-- ============================================
-- MIGRATION : SYSTÈME DE GESTION DES PAIEMENTS
-- Description : Création des tables pour gérer les motifs de paiement,
--               les paiements, le suivi des paiements et les dérogations
-- Date : 2025-01-04
-- ============================================

-- Table : motif_paiement
-- Description : Stocke les différents motifs de paiement (frais scolaires, etc.)
CREATE TABLE IF NOT EXISTS motif_paiement (
    id BIGSERIAL PRIMARY KEY,
    libelle VARCHAR(200) NOT NULL,
    montant_total DECIMAL(10, 2) NOT NULL,
    description VARCHAR(500),
    periode VARCHAR(20),
    annee_scolaire VARCHAR(20),
    date_creation TIMESTAMP,
    date_echeance TIMESTAMP,
    actif BOOLEAN NOT NULL DEFAULT true
);

-- Table : paiement
-- Description : Enregistre tous les paiements effectués par les élèves
CREATE TABLE IF NOT EXISTS paiement (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    motif_paiement_id BIGINT NOT NULL,
    montant_paye DECIMAL(10, 2) NOT NULL,
    date_paiement TIMESTAMP NOT NULL,
    reference_paiement VARCHAR(100),
    mode_paiement VARCHAR(50),
    remarque VARCHAR(500),
    recu_par VARCHAR(100),
    date_creation TIMESTAMP,
    CONSTRAINT fk_paiement_eleve FOREIGN KEY (eleve_id) REFERENCES eleve(id) ON DELETE CASCADE,
    CONSTRAINT fk_paiement_motif FOREIGN KEY (motif_paiement_id) REFERENCES motif_paiement(id) ON DELETE CASCADE
);

-- Table : suivi_paiement
-- Description : Suit l'état des paiements de chaque élève pour chaque motif
CREATE TABLE IF NOT EXISTS suivi_paiement (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    motif_paiement_id BIGINT NOT NULL,
    montant_a_payer DECIMAL(10, 2) NOT NULL,
    montant_paye DECIMAL(10, 2) NOT NULL DEFAULT 0,
    montant_restant DECIMAL(10, 2) NOT NULL,
    statut_paiement VARCHAR(20) NOT NULL DEFAULT 'NON_PAYE',
    est_en_ordre BOOLEAN NOT NULL DEFAULT false,
    date_dernier_paiement TIMESTAMP,
    date_creation TIMESTAMP,
    date_maj TIMESTAMP,
    CONSTRAINT fk_suivi_eleve FOREIGN KEY (eleve_id) REFERENCES eleve(id) ON DELETE CASCADE,
    CONSTRAINT fk_suivi_motif FOREIGN KEY (motif_paiement_id) REFERENCES motif_paiement(id) ON DELETE CASCADE,
    CONSTRAINT uk_suivi_eleve_motif UNIQUE (eleve_id, motif_paiement_id)
);

-- Table : derogation
-- Description : Gère les dérogations accordées aux élèves pour accéder aux bulletins
CREATE TABLE IF NOT EXISTS derogation (
    id BIGSERIAL PRIMARY KEY,
    eleve_id BIGINT NOT NULL,
    motif_paiement_id BIGINT,
    motif VARCHAR(500) NOT NULL,
    statut VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    date_demande TIMESTAMP NOT NULL,
    date_acceptation TIMESTAMP,
    date_expiration TIMESTAMP,
    date_refus TIMESTAMP,
    raison_refus VARCHAR(500),
    accordee_par VARCHAR(100),
    active BOOLEAN NOT NULL DEFAULT false,
    est_expiree BOOLEAN NOT NULL DEFAULT false,
    date_creation TIMESTAMP,
    date_maj TIMESTAMP,
    CONSTRAINT fk_derogation_eleve FOREIGN KEY (eleve_id) REFERENCES eleve(id) ON DELETE CASCADE,
    CONSTRAINT fk_derogation_motif FOREIGN KEY (motif_paiement_id) REFERENCES motif_paiement(id) ON DELETE SET NULL
);

-- Ajout de la colonne statut_paiement_global à la table eleve
ALTER TABLE eleve 
ADD COLUMN IF NOT EXISTS statut_paiement_global VARCHAR(20) DEFAULT 'NON_EN_ORDRE';

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_paiement_eleve ON paiement(eleve_id);
CREATE INDEX IF NOT EXISTS idx_paiement_motif ON paiement(motif_paiement_id);
CREATE INDEX IF NOT EXISTS idx_paiement_date ON paiement(date_paiement);

CREATE INDEX IF NOT EXISTS idx_suivi_eleve ON suivi_paiement(eleve_id);
CREATE INDEX IF NOT EXISTS idx_suivi_motif ON suivi_paiement(motif_paiement_id);
CREATE INDEX IF NOT EXISTS idx_suivi_statut ON suivi_paiement(statut_paiement);
CREATE INDEX IF NOT EXISTS idx_suivi_en_ordre ON suivi_paiement(est_en_ordre);

CREATE INDEX IF NOT EXISTS idx_derogation_eleve ON derogation(eleve_id);
CREATE INDEX IF NOT EXISTS idx_derogation_statut ON derogation(statut);
CREATE INDEX IF NOT EXISTS idx_derogation_active ON derogation(active);
CREATE INDEX IF NOT EXISTS idx_derogation_expiree ON derogation(est_expiree);

CREATE INDEX IF NOT EXISTS idx_motif_periode ON motif_paiement(periode);
CREATE INDEX IF NOT EXISTS idx_motif_annee ON motif_paiement(annee_scolaire);
CREATE INDEX IF NOT EXISTS idx_motif_actif ON motif_paiement(actif);

-- Commentaires sur les tables
COMMENT ON TABLE motif_paiement IS 'Stocke les différents motifs de paiement (ex: Frais scolaire 1ère période)';
COMMENT ON TABLE paiement IS 'Enregistre tous les paiements effectués par les élèves';
COMMENT ON TABLE suivi_paiement IS 'Suit l''état des paiements de chaque élève pour chaque motif';
COMMENT ON TABLE derogation IS 'Gère les dérogations accordées aux élèves pour accéder aux bulletins malgré des impayés';

COMMENT ON COLUMN eleve.statut_paiement_global IS 'Statut global de paiement: EN_ORDRE, NON_EN_ORDRE, AVEC_DEROGATION';
