package com.Ecole.demo.service;

import com.Ecole.demo.dto.*;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.ParentEleve;
import com.Ecole.demo.entity.Role;
import com.Ecole.demo.entity.Utilisateur;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.ParentEleveRepository;
import com.Ecole.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentEleveService {
    
    @Autowired
    private ParentEleveRepository parentEleveRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    public List<ParentEleveDTO> getAllRelations() {
        return parentEleveRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ParentEleveDTO createParentEleveRelation(ParentEleveCreateDTO dto) {
        Utilisateur parent = utilisateurRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        
        if (!parent.getRole().equals(Role.PARENT)) {
            throw new RuntimeException("L'utilisateur doit avoir le rôle PARENT");
        }
        
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));
        
        if (parentEleveRepository.existsByParentIdAndEleveId(dto.getParentId(), dto.getEleveId())) {
            throw new RuntimeException("Cette relation parent-élève existe déjà");
        }
        
        ParentEleve parentEleve = new ParentEleve();
        parentEleve.setParent(parent);
        parentEleve.setEleve(eleve);
        parentEleve.setLienParente(dto.getLienParente());
        
        ParentEleve saved = parentEleveRepository.save(parentEleve);
        return convertToDTO(saved);
    }
    
    public List<ParentEleveDTO> createMultipleRelations(List<ParentEleveCreateDTO> dtos) {
        return dtos.stream()
                .map(this::createParentEleveRelation)
                .collect(Collectors.toList());
    }
    
    public ParentAvecEnfantsDTO getParentAvecEnfants(Long parentId) {
        Utilisateur parent = utilisateurRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        
        if (!parent.getRole().equals(Role.PARENT)) {
            throw new RuntimeException("L'utilisateur doit avoir le rôle PARENT");
        }
        
        List<ParentEleve> relations = parentEleveRepository.findByParentId(parentId);
        
        List<EnfantDTO> enfants = relations.stream()
                .map(relation -> {
                    Eleve eleve = relation.getEleve();
                    EnfantDTO enfantDTO = new EnfantDTO();
                    enfantDTO.setEleveId(eleve.getId());
                    enfantDTO.setNomComplet(eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom());
                    enfantDTO.setSexe(eleve.getSexe());
                    enfantDTO.setDateNaissance(eleve.getDateNaissance().format(DateTimeFormatter.ISO_DATE));
                    enfantDTO.setClasse(eleve.getClasse());
                    
                    // Convertir l'entité Ecole en EcoleDTO
                    if (eleve.getEcole() != null) {
                        EcoleDTO ecoleDTO = new EcoleDTO();
                        ecoleDTO.setId(eleve.getEcole().getId());
                        ecoleDTO.setNomEcole(eleve.getEcole().getNomEcole());
                        ecoleDTO.setCodeEcole(eleve.getEcole().getCodeEcole());
                        ecoleDTO.setVille(eleve.getEcole().getVille());
                        ecoleDTO.setCommune_territoire(eleve.getEcole().getCommune_territoire());
                        ecoleDTO.setAdresse(eleve.getEcole().getAdresse());
                        ecoleDTO.setTelephone(eleve.getEcole().getTelephone());
                        ecoleDTO.setEmail(eleve.getEcole().getEmail());
                        ecoleDTO.setDevise(eleve.getEcole().getDevise());
                        enfantDTO.setEcole(ecoleDTO);
                    }
                    
                    enfantDTO.setLienParente(relation.getLienParente());
                    return enfantDTO;
                })
                .collect(Collectors.toList());
        
        ParentAvecEnfantsDTO result = new ParentAvecEnfantsDTO();
        result.setParentId(parent.getId());
        result.setNomComplet(parent.getNomComplet());
        result.setEmail(parent.getEmail());
        result.setTelephone(parent.getTelephone());
        result.setEnfants(enfants);
        
        return result;
    }
    
    public List<ParentEleveDTO> getEnfantsByParent(Long parentId) {
        return parentEleveRepository.findByParentId(parentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ParentEleveDTO> getParentsByEleve(Long eleveId) {
        return parentEleveRepository.findByEleveId(eleveId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ParentEleveDTO updateRelation(Long id, ParentEleveCreateDTO dto) {
        ParentEleve relation = parentEleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relation non trouvée"));
        
        Utilisateur parent = utilisateurRepository.findById(dto.getParentId())
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        
        if (!parent.getRole().equals(Role.PARENT)) {
            throw new RuntimeException("L'utilisateur doit avoir le rôle PARENT");
        }
        
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé"));
        
        relation.setParent(parent);
        relation.setEleve(eleve);
        relation.setLienParente(dto.getLienParente());
        
        ParentEleve updated = parentEleveRepository.save(relation);
        return convertToDTO(updated);
    }
    
    public void deleteRelation(Long id) {
        parentEleveRepository.deleteById(id);
    }
    
    private ParentEleveDTO convertToDTO(ParentEleve relation) {
        ParentEleveDTO dto = new ParentEleveDTO();
        dto.setId(relation.getId());
        dto.setParentId(relation.getParent().getId());
        dto.setParentNom(relation.getParent().getNomComplet());
        dto.setParentEmail(relation.getParent().getEmail());
        dto.setParentTelephone(relation.getParent().getTelephone());
        dto.setEleveId(relation.getEleve().getId());
        dto.setEleveNom(relation.getEleve().getNom() + " " + 
                       relation.getEleve().getPostnom() + " " + 
                       relation.getEleve().getPrenom());
        dto.setEleveClasse(relation.getEleve().getClasse());
        
        // Convertir l'entité Ecole en EcoleDTO
        if (relation.getEleve().getEcole() != null) {
            EcoleDTO ecoleDTO = new EcoleDTO();
            ecoleDTO.setId(relation.getEleve().getEcole().getId());
            ecoleDTO.setNomEcole(relation.getEleve().getEcole().getNomEcole());
            ecoleDTO.setCodeEcole(relation.getEleve().getEcole().getCodeEcole());
            ecoleDTO.setVille(relation.getEleve().getEcole().getVille());
            ecoleDTO.setCommune_territoire(relation.getEleve().getEcole().getCommune_territoire());
            ecoleDTO.setAdresse(relation.getEleve().getEcole().getAdresse());
            ecoleDTO.setTelephone(relation.getEleve().getEcole().getTelephone());
            ecoleDTO.setEmail(relation.getEleve().getEcole().getEmail());
            ecoleDTO.setDevise(relation.getEleve().getEcole().getDevise());
            dto.setEleveEcole(ecoleDTO);
        }
        
        dto.setLienParente(relation.getLienParente());
        return dto;
    }
}
