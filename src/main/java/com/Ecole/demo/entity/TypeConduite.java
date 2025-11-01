package com.Ecole.demo.entity;

public enum TypeConduite {
    EXCELLENT("Excellent"),
    TRES_BON("Très Bon"),
    BON("Bon"),
    ASSEZ_BON("Assez Bon"),
    PASSABLE("Passable"),
    MEDIOCRE("Médiocre"),
    MAUVAIS("Mauvais");

    private final String label;

    TypeConduite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
