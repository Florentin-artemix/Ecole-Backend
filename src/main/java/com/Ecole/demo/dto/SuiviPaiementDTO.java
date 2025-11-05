package com.Ecole.demo.dto;

import com.Ecole.demo.entity.SuiviPaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuiviPaiementDTO {
    private Long id;
    private Long eleveId;
    private String nomEleve;
    private String prenomEleve;
    private Long motifPaiementId;
    private String libelleMotif;
    private BigDecimal montantAPayer;
    private BigDecimal montantPaye;
    private BigDecimal montantRestant;
    private SuiviPaiement.StatutPaiement statutPaiement;
    private Boolean estEnOrdre;
    private LocalDateTime dateDernierPaiement;
    private LocalDateTime dateCreation;
    private LocalDateTime dateMaj;

    @JsonProperty("montantTotal")
    public BigDecimal getMontantTotal() {
        return montantAPayer;
    }
}