package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ecole")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ecole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(nullable = false)
    private String nomEcole;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, unique = true)
    private String codeEcole;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String ville;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String commune_territoire;

    @Size(max = 255)
    private String adresse;

    @Size(max = 20)
    private String telephone;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 255)
    private String devise;
}
