package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Derogation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogationDTO {
    private Long id;
    private Long eleveId;
    private String nomEleve;
    private String prenomEleve;
    private Long motifPaiementId;
    private String libelleMotif;
    private String motif;
    private Derogation.StatutDerogation statut;
    private LocalDateTime dateDemande;
    private LocalDateTime dateAcceptation;
    private LocalDateTime dateExpiration;
    private LocalDateTime dateRefus;
    private String raisonRefus;
    private String accordeePar;
    private Boolean active;
    private Boolean estExpiree;
    private Boolean estValide;
}
