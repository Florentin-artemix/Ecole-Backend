package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentEleveCreateDTO {
    private Long parentId;
    private Long eleveId;
    private String lienParente; // Père, Mère, Tuteur, Oncle, Tante, etc.
}
