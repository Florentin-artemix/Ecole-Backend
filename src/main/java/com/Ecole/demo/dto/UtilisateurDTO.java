package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    private Long id;
    private String nomComplet;
    private Role role;
    private String telephone;
    private String email;
    private Boolean actif;
}
