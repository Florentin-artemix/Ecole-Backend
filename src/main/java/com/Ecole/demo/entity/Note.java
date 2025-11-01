package com.Ecole.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eleve_id", nullable = false)
    private Eleve eleve;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cours_id", nullable = false)
    private Cours cours;

    @NotNull
    @DecimalMin("0")
    @DecimalMax("20")
    @Column(name = "valeur", columnDefinition = "DECIMAL(5,2)", nullable = false)
    private Double valeur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periode periode;
}
