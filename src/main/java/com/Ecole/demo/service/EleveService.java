package com.Ecole.demo.service;

import com.Ecole.demo.dto.EleveDTO;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.repository.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EleveService {
    
    @Autowired
    private EleveRepository eleveRepository;
    
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
        eleve.setEcole(eleveDTO.getEcole());
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
        eleve.setEcole(eleveDTO.getEcole());
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
                eleve.getEcole(),
                eleve.getCode(),
                eleve.getVille(),
                eleve.getCommune_territoire()
        );
    }
}
