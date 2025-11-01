package com.Ecole.demo.dto;

import com.Ecole.demo.entity.TypeConduite;
import com.Ecole.demo.entity.Periode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConduiteDTO {
    private Long id;
    private Long eleveId;
    private String eleveNom;
    private Long professeurId;
    private String professeurNom;
    private TypeConduite typeConduite;
    private Periode periode;
    private String commentaire;
}
