package com.Ecole.demo.service;

import com.Ecole.demo.dto.CoursDTO;
import com.Ecole.demo.entity.Cours;
import com.Ecole.demo.entity.Utilisateur;
import com.Ecole.demo.entity.Role;
import com.Ecole.demo.repository.CoursRepository;
import com.Ecole.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursService {
    
    @Autowired
    private CoursRepository coursRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    public CoursDTO createCours(CoursDTO coursDTO) {
        Utilisateur professeur = utilisateurRepository.findById(coursDTO.getProfesseurId())
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
        
        if (professeur.getRole() != Role.PROFESSEUR) {
            throw new RuntimeException("L'utilisateur doit être un professeur");
        }
        
        Cours cours = new Cours();
        cours.setNomCours(coursDTO.getNomCours());
        cours.setPonderation(coursDTO.getPonderation());
        cours.setProfesseur(professeur);
        
        Cours saved = coursRepository.save(cours);
        return convertToDTO(saved);
    }
    
    public CoursDTO getCoursById(Long id) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec l'ID: " + id));
        return convertToDTO(cours);
    }
    
    public List<CoursDTO> getAllCours() {
        return coursRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public CoursDTO updateCours(Long id, CoursDTO coursDTO) {
        Cours cours = coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec l'ID: " + id));
        
        Utilisateur professeur = utilisateurRepository.findById(coursDTO.getProfesseurId())
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
        
        if (professeur.getRole() != Role.PROFESSEUR) {
            throw new RuntimeException("L'utilisateur doit être un professeur");
        }
        
        cours.setNomCours(coursDTO.getNomCours());
        cours.setPonderation(coursDTO.getPonderation());
        cours.setProfesseur(professeur);
        
        Cours updated = coursRepository.save(cours);
        return convertToDTO(updated);
    }
    
    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }
    
    private CoursDTO convertToDTO(Cours cours) {
        return new CoursDTO(
                cours.getId(),
                cours.getNomCours(),
                cours.getPonderation(),
                cours.getProfesseur().getNomComplet(),
                cours.getProfesseur().getId()
        );
    }
}
