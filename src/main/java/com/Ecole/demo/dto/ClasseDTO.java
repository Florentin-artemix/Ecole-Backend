package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasseDTO {
    private Long id;
    private String nom;
    private String description;
}
