package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Periode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotifPaiementCreateDTO {
    @NotBlank(message = "Le libellé est obligatoire")
    private String libelle;
    
    @NotNull(message = "Le montant total est obligatoire")
    private BigDecimal montantTotal;
    
    private String description;
    private Periode periode;
    private String anneeScolaire;
    private LocalDateTime dateEcheance;
    private Boolean actif = true;
}
