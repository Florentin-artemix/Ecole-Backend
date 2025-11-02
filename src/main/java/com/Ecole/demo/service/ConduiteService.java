package com.Ecole.demo.service;

import com.Ecole.demo.dto.ConduiteCalculDTO;
import com.Ecole.demo.dto.ConduiteCreateDTO;
import com.Ecole.demo.dto.ConduiteDTO;
import com.Ecole.demo.entity.Conduite;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.entity.TypeConduite;
import com.Ecole.demo.entity.Utilisateur;
import com.Ecole.demo.repository.ConduiteRepository;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConduiteService {
    
    @Autowired
    private ConduiteRepository conduiteRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    public ConduiteDTO createConduite(ConduiteCreateDTO dto) {
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Elève non trouvé"));
        
        Utilisateur professeur = utilisateurRepository.findById(dto.getProfesseurId())
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
        
        Conduite conduite = new Conduite();
        conduite.setEleve(eleve);
        conduite.setProfesseur(professeur);
        conduite.setTypeConduite(dto.getTypeConduite());
        conduite.setPeriode(dto.getPeriode());
        conduite.setCommentaire(dto.getCommentaire());
        
        Conduite saved = conduiteRepository.save(conduite);
        return convertToDTO(saved);
    }
    
    public List<ConduiteDTO> createMultipleConduites(List<ConduiteCreateDTO> dtos) {
        return dtos.stream()
                .map(this::createConduite)
                .collect(Collectors.toList());
    }
    
    public ConduiteDTO getConduiteById(Long id) {
        Conduite conduite = conduiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conduite non trouvée"));
        return convertToDTO(conduite);
    }
    
    /**
     * Récupère toutes les conduites
     * @return Liste de toutes les conduites
     */
    public List<ConduiteDTO> getAllConduites() {
        return conduiteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ConduiteDTO> getConduitesByEleveAndPeriode(Long eleveId, Periode periode) {
        return conduiteRepository.findByEleveIdAndPeriode(eleveId, periode)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ConduiteDTO> getConduitesByProfesseurAndPeriode(Long professeurId, Periode periode) {
        return conduiteRepository.findByProfesseurIdAndPeriode(professeurId, periode)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Calcule la conduite finale d'un élève pour une période donnée
     * Basé sur le pourcentage moyen des notes de conduite
     * @param eleveId L'ID de l'élève
     * @param periode La période concernée
     * @return Un objet ConduiteCalculDTO avec tous les détails du calcul
     */
    public ConduiteCalculDTO calculerConduiteFinale(Long eleveId, Periode periode) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));
        
        // Récupérer toutes les conduites de l'élève pour la période
        List<Conduite> conduites = conduiteRepository.findByEleveIdAndPeriode(eleveId, periode);
        
        ConduiteCalculDTO resultat = new ConduiteCalculDTO();
        resultat.setEleveId(eleveId);
        resultat.setEleveNom(eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom());
        resultat.setPeriode(periode.name());
        resultat.setNombreEvaluations(conduites.size());
        
        if (conduites.isEmpty()) {
            resultat.setPourcentageMoyen(0.0);
            resultat.setMentionFinale(null);
            resultat.setAppreciation("Aucune évaluation de conduite disponible");
            return resultat;
        }
        
        // Calculer le pourcentage moyen
        double sommePourcentages = conduites.stream()
                .mapToDouble(c -> c.getTypeConduite().getValeurNumerique())
                .sum();
        
        double pourcentageMoyen = sommePourcentages / conduites.size();
        resultat.setPourcentageMoyen(Math.round(pourcentageMoyen * 100.0) / 100.0); // Arrondir à 2 décimales
        
        // Déterminer la mention finale selon le pourcentage
        TypeConduite mentionFinale = TypeConduite.fromPourcentage(pourcentageMoyen);
        resultat.setMentionFinale(mentionFinale);
        
        // Générer l'appréciation automatique
        resultat.genererAppreciation();
        
        return resultat;
    }
    
    /**
     * Retourne uniquement la mention finale sous forme de String (pour compatibilité)
     * @param eleveId L'ID de l'élève
     * @param periode La période concernée
     * @return La mention finale ou "Non évalué"
     */
    public String getMostFrequentConduite(Long eleveId, Periode periode) {
        ConduiteCalculDTO calcul = calculerConduiteFinale(eleveId, periode);
        
        if (calcul.getMentionFinale() == null) {
            return "Non évalué";
        }
        
        return calcul.getMentionFinale().getLabel();
    }
    
    /**
     * Retourne le pourcentage de conduite pour un élève
     * @param eleveId L'ID de l'élève
     * @param periode La période concernée
     * @return Le pourcentage moyen (0-100)
     */
    public Double getPourcentageConduite(Long eleveId, Periode periode) {
        ConduiteCalculDTO calcul = calculerConduiteFinale(eleveId, periode);
        return calcul.getPourcentageMoyen();
    }
    
    public ConduiteDTO updateConduite(Long id, ConduiteCreateDTO dto) {
        Conduite conduite = conduiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conduite non trouvée"));
        
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Elève non trouvé"));
        
        Utilisateur professeur = utilisateurRepository.findById(dto.getProfesseurId())
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
        
        conduite.setEleve(eleve);
        conduite.setProfesseur(professeur);
        conduite.setTypeConduite(dto.getTypeConduite());
        conduite.setPeriode(dto.getPeriode());
        conduite.setCommentaire(dto.getCommentaire());
        
        Conduite updated = conduiteRepository.save(conduite);
        return convertToDTO(updated);
    }
    
    public void deleteConduite(Long id) {
        conduiteRepository.deleteById(id);
    }
    
    private ConduiteDTO convertToDTO(Conduite conduite) {
        ConduiteDTO dto = new ConduiteDTO();
        dto.setId(conduite.getId());
        dto.setEleveId(conduite.getEleve().getId());
        dto.setEleveNom(conduite.getEleve().getNom() + " " + 
                       conduite.getEleve().getPostnom() + " " + 
                       conduite.getEleve().getPrenom());
        dto.setProfesseurId(conduite.getProfesseur().getId());
        dto.setProfesseurNom(conduite.getProfesseur().getNomComplet());
        dto.setTypeConduite(conduite.getTypeConduite());
        dto.setPeriode(conduite.getPeriode());
        dto.setCommentaire(conduite.getCommentaire());
        return dto;
    }
}