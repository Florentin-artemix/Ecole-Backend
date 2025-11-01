package com.Ecole.demo.service;

import com.Ecole.demo.dto.UtilisateurDTO;
import com.Ecole.demo.dto.UtilisateurCreateDTO;
import com.Ecole.demo.entity.Utilisateur;
import com.Ecole.demo.entity.Role;
import com.Ecole.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    public UtilisateurDTO createUtilisateur(UtilisateurCreateDTO utilisateurDTO) {
        // Vérifier que l'email est unique
        if (utilisateurRepository.findByEmail(utilisateurDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomComplet(utilisateurDTO.getNomComplet());
        utilisateur.setRole(utilisateurDTO.getRole());
        utilisateur.setTelephone(utilisateurDTO.getTelephone());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        // En production, utiliser BCryptPasswordEncoder
        utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        utilisateur.setActif(true);
        
        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return convertToDTO(saved);
    }
    
    public UtilisateurDTO getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        return convertToDTO(utilisateur);
    }
    
    public List<UtilisateurDTO> getAllUtilisateurs() {
        return utilisateurRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<UtilisateurDTO> getUtilisateursByRole(Role role) {
        return utilisateurRepository.findByRole(role).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public UtilisateurDTO updateUtilisateur(Long id, UtilisateurCreateDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
        
        utilisateur.setNomComplet(utilisateurDTO.getNomComplet());
        utilisateur.setRole(utilisateurDTO.getRole());
        utilisateur.setTelephone(utilisateurDTO.getTelephone());
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setMotDePasse(utilisateurDTO.getMotDePasse());
        
        Utilisateur updated = utilisateurRepository.save(utilisateur);
        return convertToDTO(updated);
    }
    
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }
    
    private UtilisateurDTO convertToDTO(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getId(),
                utilisateur.getNomComplet(),
                utilisateur.getRole(),
                utilisateur.getTelephone(),
                utilisateur.getEmail(),
                utilisateur.getActif()
        );
    }
}
