package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conduite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conduite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professeur_id", nullable = false)
    private Utilisateur professeur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeConduite typeConduite;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periode periode;

    @Column(length = 500)
    private String commentaire;
}
