package com.Ecole.demo.service;

import com.Ecole.demo.dto.EcoleDTO;
import com.Ecole.demo.dto.EleveDTO;
import com.Ecole.demo.entity.Ecole;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.repository.EcoleRepository;
import com.Ecole.demo.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EleveService {
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private EcoleRepository ecoleRepository;
    
    public EleveDTO createEleve(EleveDTO eleveDTO) {
        Eleve eleve = new Eleve();
        eleve.setNom(eleveDTO.getNom());
        eleve.setPostnom(eleveDTO.getPostnom());
        eleve.setPrenom(eleveDTO.getPrenom());
        eleve.setSexe(eleveDTO.getSexe());
        eleve.setDateNaissance(eleveDTO.getDateNaissance());
        eleve.setLieuNaissance(eleveDTO.getLieuNaissance());
        eleve.setNumeroPermanent(eleveDTO.getNumeroPermanent());
        eleve.setClasse(eleveDTO.getClasse());
        
        // Récupérer l'entité Ecole depuis la base de données
        if (eleveDTO.getEcole() != null && eleveDTO.getEcole().getId() != null) {
            Ecole ecole = ecoleRepository.findById(eleveDTO.getEcole().getId())
                    .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + eleveDTO.getEcole().getId()));
            eleve.setEcole(ecole);
        }
        
        eleve.setCode(eleveDTO.getCode());
        eleve.setVille(eleveDTO.getVille());
        eleve.setCommune_territoire(eleveDTO.getCommune_territoire());
        
        Eleve saved = eleveRepository.save(eleve);
        return convertToDTO(saved);
    }
    
    public EleveDTO getEleveById(Long id) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elève non trouvé avec l'ID: " + id));
        return convertToDTO(eleve);
    }
    
    public List<EleveDTO> getAllEleves() {
        return eleveRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EleveDTO updateEleve(Long id, EleveDTO eleveDTO) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elève non trouvé avec l'ID: " + id));
        
        eleve.setNom(eleveDTO.getNom());
        eleve.setPostnom(eleveDTO.getPostnom());
        eleve.setPrenom(eleveDTO.getPrenom());
        eleve.setSexe(eleveDTO.getSexe());
        eleve.setDateNaissance(eleveDTO.getDateNaissance());
        eleve.setLieuNaissance(eleveDTO.getLieuNaissance());
        eleve.setNumeroPermanent(eleveDTO.getNumeroPermanent());
        eleve.setClasse(eleveDTO.getClasse());
        
        // Récupérer l'entité Ecole depuis la base de données
        if (eleveDTO.getEcole() != null && eleveDTO.getEcole().getId() != null) {
            Ecole ecole = ecoleRepository.findById(eleveDTO.getEcole().getId())
                    .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + eleveDTO.getEcole().getId()));
            eleve.setEcole(ecole);
        }
        
        eleve.setCode(eleveDTO.getCode());
        eleve.setVille(eleveDTO.getVille());
        eleve.setCommune_territoire(eleveDTO.getCommune_territoire());
        
        Eleve updated = eleveRepository.save(eleve);
        return convertToDTO(updated);
    }
    
    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
    
    private EleveDTO convertToDTO(Eleve eleve) {
        EcoleDTO ecoleDTO = null;
        if (eleve.getEcole() != null) {
            ecoleDTO = new EcoleDTO();
            ecoleDTO.setId(eleve.getEcole().getId());
            ecoleDTO.setNomEcole(eleve.getEcole().getNomEcole());
            ecoleDTO.setCodeEcole(eleve.getEcole().getCodeEcole());
            ecoleDTO.setVille(eleve.getEcole().getVille());
            ecoleDTO.setCommune_territoire(eleve.getEcole().getCommune_territoire());
            ecoleDTO.setAdresse(eleve.getEcole().getAdresse());
            ecoleDTO.setTelephone(eleve.getEcole().getTelephone());
            ecoleDTO.setEmail(eleve.getEcole().getEmail());
            ecoleDTO.setDevise(eleve.getEcole().getDevise());
        }
        
        return new EleveDTO(
                eleve.getId(),
                eleve.getNom(),
                eleve.getPostnom(),
                eleve.getPrenom(),
                eleve.getSexe(),
                eleve.getDateNaissance(),
                eleve.getLieuNaissance(),
                eleve.getNumeroPermanent(),
                eleve.getClasse(),
                ecoleDTO,
                eleve.getCode(),
                eleve.getVille(),
                eleve.getCommune_territoire()
        );
    }
}
