package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentAvecEnfantsDTO {
    private Long parentId;
    private String nomComplet;
    private String email;
    private String telephone;
    private List<EnfantDTO> enfants;
}
