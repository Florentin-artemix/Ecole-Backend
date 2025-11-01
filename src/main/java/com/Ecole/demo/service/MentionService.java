package com.Ecole.demo.service;

import org.springframework.stereotype.Service;

@Service
public class MentionService {
    
    public String determinerMention(Double pourcentage) {
        if (pourcentage == null) {
            return "Non évalué";
        }
        
        if (pourcentage < 40) {
            return "Faible";
        } else if (pourcentage < 50) {
            return "Passable";
        } else if (pourcentage < 60) {
            return "Assez Bien";
        } else if (pourcentage < 70) {
            return "Bien";
        } else if (pourcentage < 80) {
            return "Très Bien";
        } else {
            return "Excellent";
        }
    }
}
