package com.Ecole.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementCreateDTO {
    @NotNull(message = "L'ID de l'élève est obligatoire")
    private Long eleveId;
    
    @NotNull(message = "L'ID du motif de paiement est obligatoire")
    private Long motifPaiementId;
    
    @NotNull(message = "Le montant payé est obligatoire")
    private BigDecimal montantPaye;
    
    private LocalDateTime datePaiement;
    private String referencePaiement;
    private String modePaiement;
    private String remarque;
    private String recuPar;
}
