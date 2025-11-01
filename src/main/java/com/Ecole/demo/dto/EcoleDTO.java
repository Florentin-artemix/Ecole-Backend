package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcoleDTO {
    private Long id;
    private String nomEcole;
    private String codeEcole;
    private String ville;
    private String commune_territoire;
    private String adresse;
    private String telephone;
    private String email;
    private String devise;
}
