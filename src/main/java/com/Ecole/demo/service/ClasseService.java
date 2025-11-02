package com.Ecole.demo.service;

import com.Ecole.demo.dto.ClasseDTO;
import com.Ecole.demo.entity.Classe;
import com.Ecole.demo.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseService {
    
    @Autowired
    private ClasseRepository classeRepository;
    
    public ClasseDTO createClasse(ClasseDTO classeDTO) {
        if (classeRepository.existsByNom(classeDTO.getNom())) {
            throw new RuntimeException("Une classe avec ce nom existe déjà");
        }
        
        Classe classe = new Classe();
        classe.setNom(classeDTO.getNom());
        classe.setDescription(classeDTO.getDescription());
        
        Classe saved = classeRepository.save(classe);
        return convertToDTO(saved);
    }
    
    public ClasseDTO getClasseById(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID: " + id));
        return convertToDTO(classe);
    }
    
    public List<ClasseDTO> getAllClasses() {
        return classeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ClasseDTO updateClasse(Long id, ClasseDTO classeDTO) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID: " + id));
        
        classe.setNom(classeDTO.getNom());
        classe.setDescription(classeDTO.getDescription());
        
        Classe updated = classeRepository.save(classe);
        return convertToDTO(updated);
    }
    
    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }
    
    private ClasseDTO convertToDTO(Classe classe) {
        return new ClasseDTO(
                classe.getId(),
                classe.getNom(),
                classe.getDescription()
        );
    }
}
