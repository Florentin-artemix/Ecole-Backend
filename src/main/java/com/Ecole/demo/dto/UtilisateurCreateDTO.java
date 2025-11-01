package com.Ecole.demo.dto;

import com.Ecole.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurCreateDTO {
    private String nomComplet;
    private Role role;
    private String telephone;
    private String email;
    private String motDePasse;
}
