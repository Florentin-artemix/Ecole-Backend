package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursDTO {
    private Long id;
    private String nomCours;
    private Integer ponderation;
    private String professeurNom;
    private Long professeurId;
}
