package com.Ecole.demo.dto;

import com.Ecole.demo.entity.TypeConduite;
import com.Ecole.demo.entity.Periode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConduiteCreateDTO {
    private Long eleveId;
    private Long professeurId;
    private TypeConduite typeConduite;
    private Periode periode;
    private String commentaire;
}
