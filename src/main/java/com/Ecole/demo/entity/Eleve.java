package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "eleve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String nom;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String postnom;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String prenom;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(nullable = false)
    private String sexe;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String lieuNaissance;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, unique = true)
    private String numeroPermanent;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String classe;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String ecole;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false)
    private String code;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String ville;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false)
    private String commune_territoire;

    @JsonIgnore
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;
}
