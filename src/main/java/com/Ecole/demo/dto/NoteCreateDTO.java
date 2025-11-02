package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.entity.TypeConduite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteCreateDTO {
    private Long eleveId;
    private Long coursId;
    private Double valeur;
    private Periode periode;
    
    // Optionnel: Pour permettre au professeur de noter la conduite en même temps
    private TypeConduite typeConduite;
    private String commentaireConduite;
}
