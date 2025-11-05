package com.Ecole.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogationCreateDTO {
    @NotNull(message = "L'ID de l'élève est obligatoire")
    private Long eleveId;
    
    private Long motifPaiementId;
    
    @NotBlank(message = "Le motif de la dérogation est obligatoire")
    private String motif;
}
