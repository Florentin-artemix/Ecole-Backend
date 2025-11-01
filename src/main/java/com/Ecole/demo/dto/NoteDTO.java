package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Periode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private Long eleveId;
    private String eleveNom;
    private Long coursId;
    private String coursNom;
    private Integer ponderation;
    private Double valeur;
    private Periode periode;
}
