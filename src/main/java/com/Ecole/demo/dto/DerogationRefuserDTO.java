package com.Ecole.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogationRefuserDTO {
    @NotBlank(message = "La raison du refus est obligatoire")
    private String raisonRefus;
}
