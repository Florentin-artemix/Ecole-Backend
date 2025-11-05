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

    // Added: tolerant parser to accept legacy values
    public static Periode parse(String value) {
        if (value == null) throw new IllegalArgumentException("Periode is null");
        String v = value.trim().toUpperCase()
                .replace('-', '_')
                .replace(' ', '_');
        switch (v) {
            case "PREMIERE":
            case "PREMIERE_PERIODE":
            case "1ERE":
            case "1ERE_PERIODE":
                return PREMIERE;
            case "DEUXIEME":
            case "DEUXIEME_PERIODE":
            case "2EME":
            case "2EME_PERIODE":
                return DEUXIEME;
            case "TROISIEME":
            case "TROISIEME_PERIODE":
            case "3EME":
            case "3EME_PERIODE":
                return TROISIEME;
            case "EXAMEN_PREMIER_SEMESTRE":
            case "EXAMEN_1ER_SEMESTRE":
                return EXAMEN_PREMIER_SEMESTRE;
            case "EXAMEN_SECOND_SEMESTRE":
            case "EXAMEN_2EME_SEMESTRE":
                return EXAMEN_SECOND_SEMESTRE;
            default:
                // Try native valueOf as last resort
                return Periode.valueOf(v);
        }
    }
}