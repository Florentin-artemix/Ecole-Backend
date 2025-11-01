package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String nomComplet;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotNull
    @Size(min = 10, max = 20)
    @Column(nullable = false)
    private String telephone;

    @NotNull
    @Email
    @Size(min = 5, max = 100)
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 6, max = 255)
    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = true)
    private Boolean actif = true;
}
