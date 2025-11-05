package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {
    private Long id;
    private Long eleveId;
    private String nomEleve;
    private String prenomEleve;
    private Long motifPaiementId;
    private String libelleMotif;
    private BigDecimal montantPaye;
    private LocalDateTime datePaiement;
    private String referencePaiement;
    private String modePaiement;
    private String remarque;
    private String recuPar;
}
