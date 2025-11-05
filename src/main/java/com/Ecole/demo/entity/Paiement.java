package com.Ecole.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;
    
    @ManyToOne
    @JoinColumn(name = "motif_paiement_id", nullable = false)
    private MotifPaiement motifPaiement;
    
    @Column(name = "montant_paye", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantPaye;
    
    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement;
    
    @Column(name = "reference_paiement", length = 100)
    private String referencePaiement;
    
    @Column(name = "mode_paiement", length = 50)
    private String modePaiement;  // Ex: "Espèces", "Virement", "Mobile Money"
    
    @Column(length = 500)
    private String remarque;
    
    @Column(name = "recu_par", length = 100)
    private String recuPar;  // Nom du percepteur
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        if (datePaiement == null) {
            datePaiement = LocalDateTime.now();
        }
    }
}
