package com.Ecole.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.text.Normalizer;

public enum TypeConduite {
    EXCELLENT("Excellent", 100.0),
    TRES_BON("Très Bon", 85.0),
    BON("Bon", 70.0),
    ASSEZ_BON("Assez Bon", 60.0),
    PASSABLE("Passable", 50.0),
    MEDIOCRE("Médiocre", 35.0),
    MAUVAIS("Mauvais", 20.0);

    private final String label;
    private final Double valeurNumerique;

    TypeConduite(String label, Double valeurNumerique) {
        this.label = label;
        this.valeurNumerique = valeurNumerique;
    }

    public String getLabel() {
        return label;
    }

    public Double getValeurNumerique() {
        return valeurNumerique;
    }

    /**
     * Détermine la mention finale selon le pourcentage
     * @param pourcentage Le pourcentage calculé (0-100)
     * @return La mention correspondante
     */
    public static TypeConduite fromPourcentage(Double pourcentage) {
        if (pourcentage >= 95) return EXCELLENT;
        if (pourcentage >= 80) return TRES_BON;
        if (pourcentage >= 65) return BON;
        if (pourcentage >= 55) return ASSEZ_BON;
        if (pourcentage >= 50) return PASSABLE;
        if (pourcentage >= 30) return MEDIOCRE;
        return MAUVAIS;
    }

    /**
     * Parse tolérant pour accepter des variantes utilisateur (espaces, tirets, accents, féminin)
     */
    public static TypeConduite parse(String value) {
        if (value == null) throw new IllegalArgumentException("TypeConduite is null");
        String v = normalize(value);
        switch (v) {
            case "EXCELLENT":
                return EXCELLENT;
            case "TRES_BON":
            case "TRESBON":
            case "TRES_BONNE":
            case "TRESBONNE":
                return TRES_BON;
            case "BON":
            case "BONNE":
                return BON;
            case "ASSEZ_BON":
            case "ASSEZBON":
            case "ASSEZ_BONNE":
            case "ASSEZBONNE":
                return ASSEZ_BON;
            case "PASSABLE":
                return PASSABLE;
            case "MEDIOCRE":
                return MEDIOCRE;
            case "MAUVAIS":
            case "MAUVAISE":
                return MAUVAIS;
            default:
                // fallback: essaye enum natif sur valeur normalisée
                return TypeConduite.valueOf(v);
        }
    }

    private static String normalize(String input) {
        String n = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // supprime diacritiques
        n = n.trim().toUpperCase()
                .replace('-', '_')
                .replace(' ', '_');
        // compact underscores multiples
        n = n.replaceAll("_+", "_");
        return n;
    }

    @JsonCreator
    public static TypeConduite fromJson(String value) {
        return parse(value);
    }
}