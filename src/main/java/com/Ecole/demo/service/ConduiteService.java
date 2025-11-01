package com.Ecole.demo.service;

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
    
    public String getMostFrequentConduite(Long eleveId, Periode periode) {
        List<Object[]> results = conduiteRepository.findMostFrequentConduiteByEleveAndPeriode(eleveId, periode);
        
        if (results.isEmpty()) {
            return "Non évalué"; // Par défaut si aucune conduite n'a été attribuée
        }
        
        // La première ligne contient la conduite la plus fréquente
        TypeConduite mostFrequent = (TypeConduite) results.get(0)[0];
        return mostFrequent.getLabel();
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
