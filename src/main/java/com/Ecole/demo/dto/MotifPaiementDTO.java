package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Periode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotifPaiementDTO {
    private Long id;
    private String libelle;
    private BigDecimal montantTotal;
    private String description;
    private Periode periode;
    private String anneeScolaire;
    private LocalDateTime dateCreation;
    private LocalDateTime dateEcheance;
    private Boolean actif;
}
