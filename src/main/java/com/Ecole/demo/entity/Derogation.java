package com.Ecole.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "derogation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Derogation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;
    
    @ManyToOne
    @JoinColumn(name = "motif_paiement_id")
    private MotifPaiement motifPaiement;  // Dérogation peut être liée à un motif spécifique
    
    @Column(nullable = false, length = 500)
    private String motif;  // Raison de la dérogation
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDerogation statut = StatutDerogation.EN_ATTENTE;
    
    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;
    
    @Column(name = "date_acceptation")
    private LocalDateTime dateAcceptation;
    
    @Column(name = "date_expiration")
    private LocalDateTime dateExpiration;
    
    @Column(name = "date_refus")
    private LocalDateTime dateRefus;
    
    @Column(name = "raison_refus", length = 500)
    private String raisonRefus;
    
    @Column(name = "accordee_par", length = 100)
    private String accordeePar;  // Nom de l'administrateur qui a accordé
    
    @Column(nullable = false)
    private Boolean active = false;
    
    @Column(name = "est_expiree", nullable = false)
    private Boolean estExpiree = false;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_maj")
    private LocalDateTime dateMaj;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateMaj = LocalDateTime.now();
        if (dateDemande == null) {
            dateDemande = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateMaj = LocalDateTime.now();
        verifierExpiration();
    }
    
    /**
     * Vérifie si la dérogation est expirée et met à jour son statut
     */
    public void verifierExpiration() {
        if (dateExpiration != null && LocalDateTime.now().isAfter(dateExpiration)) {
            this.estExpiree = true;
            this.active = false;
            if (this.statut == StatutDerogation.ACCEPTEE) {
                this.statut = StatutDerogation.EXPIREE;
            }
        }
    }
    
    /**
     * Vérifie si la dérogation est valide (acceptée, active et non expirée)
     */
    public boolean estValide() {
        verifierExpiration();
        return statut == StatutDerogation.ACCEPTEE && 
               active && 
               !estExpiree &&
               (dateExpiration == null || LocalDateTime.now().isBefore(dateExpiration));
    }
    
    /**
     * Accepte la dérogation
     */
    public void accepter(String accordeePar, LocalDateTime dateExpiration) {
        this.statut = StatutDerogation.ACCEPTEE;
        this.dateAcceptation = LocalDateTime.now();
        this.dateExpiration = dateExpiration;
        this.accordeePar = accordeePar;
        this.active = true;
        this.estExpiree = false;
    }
    
    /**
     * Refuse la dérogation
     */
    public void refuser(String raisonRefus) {
        this.statut = StatutDerogation.REFUSEE;
        this.dateRefus = LocalDateTime.now();
        this.raisonRefus = raisonRefus;
        this.active = false;
    }
    
    public enum StatutDerogation {
        EN_ATTENTE,
        ACCEPTEE,
        REFUSEE,
        EXPIREE
    }
}
