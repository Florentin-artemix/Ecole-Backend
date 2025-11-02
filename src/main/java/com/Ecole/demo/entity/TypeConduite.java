package com.Ecole.demo.entity;

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
}
