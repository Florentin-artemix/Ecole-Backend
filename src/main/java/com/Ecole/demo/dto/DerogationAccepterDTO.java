package com.Ecole.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DerogationAccepterDTO {
    @NotNull(message = "La date d'expiration est obligatoire")
    private LocalDateTime dateExpiration;
    
    @NotNull(message = "Le nom de la personne accordant la dérogation est obligatoire")
    private String accordeePar;
}
