package com.Ecole.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "classe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, unique = true)
    private String nom; // Ex: "1ère", "2ème", "3ème", "4ème", "5ème", "6ème"

    @Size(max = 200)
    @Column
    private String description; // Ex: "Première année du secondaire"

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cours> cours;

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Eleve> eleves;
}
