package com.Ecole.demo.dto;

import com.Ecole.demo.entity.TypeConduite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConduiteCalculDTO {
    private Long eleveId;
    private String eleveNom;
    private String periode;
    private Integer nombreEvaluations;
    private Double pourcentageMoyen;
    private TypeConduite mentionFinale;
    private String appreciation;
    
    /**
     * Génère une appréciation automatique selon le pourcentage
     */
    public void genererAppreciation() {
        if (nombreEvaluations == 0) {
            this.appreciation = "Aucune évaluation de conduite disponible";
            return;
        }
        
        if (pourcentageMoyen >= 95) {
            this.appreciation = "Conduite exemplaire. Continue ainsi !";
        } else if (pourcentageMoyen >= 80) {
            this.appreciation = "Très bonne conduite. Élève discipliné.";
        } else if (pourcentageMoyen >= 65) {
            this.appreciation = "Bonne conduite générale.";
        } else if (pourcentageMoyen >= 55) {
            this.appreciation = "Conduite acceptable. Peut mieux faire.";
        } else if (pourcentageMoyen >= 50) {
            this.appreciation = "Conduite passable. Des efforts sont nécessaires.";
        } else if (pourcentageMoyen >= 30) {
            this.appreciation = "Conduite médiocre. Doit améliorer son comportement.";
        } else {
            this.appreciation = "Conduite inacceptable. Amélioration urgente nécessaire.";
        }
    }
}
