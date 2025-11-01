package com.Ecole.demo.entity;

public enum Role {
    ADMIN("Administrateur"),
    PROFESSEUR("Professeur"),
    PARENT("Parent"),
    PERCEPTEUR("Percepteur");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
