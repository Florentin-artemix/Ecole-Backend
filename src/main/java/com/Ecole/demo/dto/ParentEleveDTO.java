package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentEleveDTO {
    private Long id;
    private Long parentId;
    private String parentNom;
    private String parentEmail;
    private String parentTelephone;
    private Long eleveId;
    private String eleveNom;
    private String eleveClasse;
    private String eleveEcole;
    private String lienParente;
}
