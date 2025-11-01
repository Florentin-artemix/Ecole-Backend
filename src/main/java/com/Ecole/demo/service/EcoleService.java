package com.Ecole.demo.service;

import com.Ecole.demo.dto.EcoleCreateUpdateDTO;
import com.Ecole.demo.dto.EcoleDTO;
import com.Ecole.demo.entity.Ecole;
import com.Ecole.demo.repository.EcoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EcoleService {
    
    @Autowired
    private EcoleRepository ecoleRepository;
    
    public List<EcoleDTO> getAllEcoles() {
        return ecoleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EcoleDTO getEcoleById(Long id) {
        Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + id));
        return convertToDTO(ecole);
    }
    
    public EcoleDTO getEcoleInfo() {
        // Récupère la première école configurée
        List<Ecole> ecoles = ecoleRepository.findAll();
        if (ecoles.isEmpty()) {
            throw new RuntimeException("Aucune école configurée");
        }
        return convertToDTO(ecoles.get(0));
    }
    
    public EcoleDTO createEcole(EcoleCreateUpdateDTO dto) {
        // Vérifier si le code existe déjà
        if (ecoleRepository.existsByCodeEcole(dto.getCodeEcole())) {
            throw new RuntimeException("Une école avec ce code existe déjà");
        }
        
        Ecole ecole = new Ecole();
        ecole.setNomEcole(dto.getNomEcole());
        ecole.setCodeEcole(dto.getCodeEcole());
        ecole.setVille(dto.getVille());
        ecole.setCommune_territoire(dto.getCommune_territoire());
        ecole.setAdresse(dto.getAdresse());
        ecole.setTelephone(dto.getTelephone());
        ecole.setEmail(dto.getEmail());
        ecole.setDevise(dto.getDevise());
        
        Ecole savedEcole = ecoleRepository.save(ecole);
        return convertToDTO(savedEcole);
    }
    
    public EcoleDTO updateEcole(Long id, EcoleCreateUpdateDTO dto) {
        Ecole ecole = ecoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + id));
        
        // Vérifier si le nouveau code existe déjà (sauf si c'est le même)
        if (!ecole.getCodeEcole().equals(dto.getCodeEcole()) && 
            ecoleRepository.existsByCodeEcole(dto.getCodeEcole())) {
            throw new RuntimeException("Une école avec ce code existe déjà");
        }
        
        ecole.setNomEcole(dto.getNomEcole());
        ecole.setCodeEcole(dto.getCodeEcole());
        ecole.setVille(dto.getVille());
        ecole.setCommune_territoire(dto.getCommune_territoire());
        ecole.setAdresse(dto.getAdresse());
        ecole.setTelephone(dto.getTelephone());
        ecole.setEmail(dto.getEmail());
        ecole.setDevise(dto.getDevise());
        
        Ecole updatedEcole = ecoleRepository.save(ecole);
        return convertToDTO(updatedEcole);
    }
    
    public void deleteEcole(Long id) {
        if (!ecoleRepository.existsById(id)) {
            throw new RuntimeException("École non trouvée avec l'ID: " + id);
        }
        ecoleRepository.deleteById(id);
    }
    
    private EcoleDTO convertToDTO(Ecole ecole) {
        EcoleDTO dto = new EcoleDTO();
        dto.setId(ecole.getId());
        dto.setNomEcole(ecole.getNomEcole());
        dto.setCodeEcole(ecole.getCodeEcole());
        dto.setVille(ecole.getVille());
        dto.setCommune_territoire(ecole.getCommune_territoire());
        dto.setAdresse(ecole.getAdresse());
        dto.setTelephone(ecole.getTelephone());
        dto.setEmail(ecole.getEmail());
        dto.setDevise(ecole.getDevise());
        return dto;
    }
}
