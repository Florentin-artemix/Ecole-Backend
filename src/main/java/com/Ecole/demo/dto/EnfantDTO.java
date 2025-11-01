package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnfantDTO {
    private Long eleveId;
    private String nomComplet;
    private String sexe;
    private String dateNaissance;
    private String classe;
    private String ecole;
    private String lienParente;
}
