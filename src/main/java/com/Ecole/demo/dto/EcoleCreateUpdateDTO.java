package com.Ecole.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcoleCreateUpdateDTO {
    @NotBlank(message = "Le nom de l'école est obligatoire")
    @Size(min = 1, max = 200)
    private String nomEcole;

    @NotBlank(message = "Le code de l'école est obligatoire")
    @Size(min = 1, max = 50)
    private String codeEcole;

    @NotBlank(message = "La ville est obligatoire")
    @Size(min = 1, max = 100)
    private String ville;

    @NotBlank(message = "La commune/territoire est obligatoire")
    @Size(min = 1, max = 100)
    private String commune_territoire;

    @Size(max = 255)
    private String adresse;

    @Size(max = 20)
    private String telephone;

    @Email(message = "Format email invalide")
    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String devise;
}
