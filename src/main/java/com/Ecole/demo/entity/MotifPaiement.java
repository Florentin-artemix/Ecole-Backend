package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "motif_paiement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotifPaiement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false, length = 200)
    private String libelle;  // Ex: "Frais scolaire 1ère période"
    
    @NotNull
    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;
    
    @Column(length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Periode periode;
    
    @Column(name = "annee_scolaire", length = 20)
    private String anneeScolaire;  // Ex: "2024-2025"
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "date_echeance")
    private LocalDateTime dateEcheance;
    
    @Column(nullable = false)
    private Boolean actif = true;
    
    @OneToMany(mappedBy = "motifPaiement", cascade = CascadeType.ALL)
    private List<SuiviPaiement> suivisPaiement;
    
    @OneToMany(mappedBy = "motifPaiement", cascade = CascadeType.ALL)
    private List<Paiement> paiements;
    
    @OneToMany(mappedBy = "motifPaiement", cascade = CascadeType.ALL)
    private List<Derogation> derogations;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
