package com.Ecole.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "suivi_paiement", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"eleve_id", "motif_paiement_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuiviPaiement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;
    
    @ManyToOne
    @JoinColumn(name = "motif_paiement_id", nullable = false)
    private MotifPaiement motifPaiement;
    
    @Column(name = "montant_a_payer", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantAPayer;  // Montant total à payer pour ce motif
    
    @Column(name = "montant_paye", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantPaye = BigDecimal.ZERO;  // Montant déjà payé
    
    @Column(name = "montant_restant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantRestant;  // Montant restant à payer
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement", nullable = false)
    private StatutPaiement statutPaiement = StatutPaiement.NON_PAYE;
    
    @Column(name = "est_en_ordre", nullable = false)
    private Boolean estEnOrdre = false;
    
    @Column(name = "date_dernier_paiement")
    private LocalDateTime dateDernierPaiement;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_maj")
    private LocalDateTime dateMaj;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateMaj = LocalDateTime.now();
        if (montantRestant == null) {
            calculerMontantRestant();
        }
        mettreAJourStatut();
    }
    
    @PreUpdate
    protected void onUpdate() {
        dateMaj = LocalDateTime.now();
        calculerMontantRestant();
        mettreAJourStatut();
    }
    
    public void calculerMontantRestant() {
        if (montantAPayer != null && montantPaye != null) {
            this.montantRestant = montantAPayer.subtract(montantPaye);
            if (this.montantRestant.compareTo(BigDecimal.ZERO) < 0) {
                this.montantRestant = BigDecimal.ZERO;
            }
        }
    }
    
    public void mettreAJourStatut() {
        if (montantRestant != null) {
            if (montantRestant.compareTo(BigDecimal.ZERO) == 0) {
                this.statutPaiement = StatutPaiement.PAYE_COMPLET;
                this.estEnOrdre = true;
            } else if (montantPaye.compareTo(BigDecimal.ZERO) > 0) {
                this.statutPaiement = StatutPaiement.PAIEMENT_PARTIEL;
                this.estEnOrdre = false;
            } else {
                this.statutPaiement = StatutPaiement.NON_PAYE;
                this.estEnOrdre = false;
            }
        }
    }
    
    public enum StatutPaiement {
        NON_PAYE,
        PAIEMENT_PARTIEL,
        PAYE_COMPLET
    }
}
