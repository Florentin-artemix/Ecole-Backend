package com.Ecole.demo.controller;

import com.Ecole.demo.dto.ConduiteCreateDTO;
import com.Ecole.demo.dto.ConduiteDTO;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.service.ConduiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conduites")
public class ConduiteController {
    
    @Autowired
    private ConduiteService conduiteService;
    
    @PostMapping
    public ResponseEntity<ConduiteDTO> createConduite(@RequestBody ConduiteCreateDTO conduiteDTO) {
        return ResponseEntity.ok(conduiteService.createConduite(conduiteDTO));
    }
    
    @PostMapping("/batch")
    public ResponseEntity<List<ConduiteDTO>> createMultipleConduites(@RequestBody List<ConduiteCreateDTO> conduitesDTO) {
        return ResponseEntity.ok(conduiteService.createMultipleConduites(conduitesDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConduiteDTO> getConduite(@PathVariable Long id) {
        return ResponseEntity.ok(conduiteService.getConduiteById(id));
    }
    
    @GetMapping("/eleve/{eleveId}/periode/{periode}")
    public ResponseEntity<List<ConduiteDTO>> getConduitesByEleveAndPeriode(
            @PathVariable Long eleveId, 
            @PathVariable Periode periode) {
        return ResponseEntity.ok(conduiteService.getConduitesByEleveAndPeriode(eleveId, periode));
    }
    
    @GetMapping("/professeur/{professeurId}/periode/{periode}")
    public ResponseEntity<List<ConduiteDTO>> getConduitesByProfesseurAndPeriode(
            @PathVariable Long professeurId, 
            @PathVariable Periode periode) {
        return ResponseEntity.ok(conduiteService.getConduitesByProfesseurAndPeriode(professeurId, periode));
    }
    
    @GetMapping("/eleve/{eleveId}/periode/{periode}/most-frequent")
    public ResponseEntity<String> getMostFrequentConduite(
            @PathVariable Long eleveId, 
            @PathVariable Periode periode) {
        return ResponseEntity.ok(conduiteService.getMostFrequentConduite(eleveId, periode));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConduiteDTO> updateConduite(@PathVariable Long id, @RequestBody ConduiteCreateDTO conduiteDTO) {
        return ResponseEntity.ok(conduiteService.updateConduite(id, conduiteDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConduite(@PathVariable Long id) {
        conduiteService.deleteConduite(id);
        return ResponseEntity.noContent().build();
    }
}
