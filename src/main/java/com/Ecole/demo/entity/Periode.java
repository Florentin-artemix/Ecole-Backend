package com.Ecole.demo.entity;

public enum Periode {
    PREMIERE("1ère période"),
    DEUXIEME("2e période"),
    TROISIEME("3e période"),
    EXAMEN_PREMIER_SEMESTRE("Examen premier semestre"),
    EXAMEN_SECOND_SEMESTRE("Examen second semestre");

    private final String label;

    Periode(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
