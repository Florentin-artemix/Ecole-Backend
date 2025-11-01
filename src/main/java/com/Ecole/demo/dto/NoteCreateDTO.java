package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Periode;
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
}
