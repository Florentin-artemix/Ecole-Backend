package com.Ecole.demo.controller;

import com.Ecole.demo.dto.ClasseDTO;
import com.Ecole.demo.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    
    @Autowired
    private ClasseService classeService;
    
    @PostMapping
    public ResponseEntity<ClasseDTO> createClasse(@RequestBody ClasseDTO classe) {
        return ResponseEntity.ok(classeService.createClasse(classe));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable Long id) {
        return ResponseEntity.ok(classeService.getClasseById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ClasseDTO>> getAllClasses() {
        return ResponseEntity.ok(classeService.getAllClasses());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClasseDTO> updateClasse(@PathVariable Long id, @RequestBody ClasseDTO classe) {
        return ResponseEntity.ok(classeService.updateClasse(id, classe));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}

