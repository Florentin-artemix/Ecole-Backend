package com.Ecole.demo.controller;

import com.Ecole.demo.dto.*;
import com.Ecole.demo.service.ParentEleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parent-eleve")
public class ParentEleveController {
    
    @Autowired
    private ParentEleveService parentEleveService;
    
    @GetMapping
    public ResponseEntity<List<ParentEleveDTO>> getAllRelations() {
        return ResponseEntity.ok(parentEleveService.getAllRelations());
    }
    
    @PostMapping
    public ResponseEntity<ParentEleveDTO> createRelation(@RequestBody ParentEleveCreateDTO dto) {
        return ResponseEntity.ok(parentEleveService.createParentEleveRelation(dto));
    }
    
    @PostMapping("/batch")
    public ResponseEntity<List<ParentEleveDTO>> createMultipleRelations(
    		@RequestBody List<ParentEleveCreateDTO> dtos) {
        return ResponseEntity.ok(parentEleveService.createMultipleRelations(dtos));
    }
    
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ParentAvecEnfantsDTO> getParentAvecEnfants(@PathVariable Long parentId) {
        return ResponseEntity.ok(parentEleveService.getParentAvecEnfants(parentId));
    }
    
    @GetMapping("/parent/{parentId}/enfants")
    public ResponseEntity<List<ParentEleveDTO>> getEnfantsByParent(@PathVariable Long parentId) {
        return ResponseEntity.ok(parentEleveService.getEnfantsByParent(parentId));
    }
    
    @GetMapping("/eleve/{eleveId}/parents")
    public ResponseEntity<List<ParentEleveDTO>> getParentsByEleve(@PathVariable Long eleveId) {
        return ResponseEntity.ok(parentEleveService.getParentsByEleve(eleveId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ParentEleveDTO> updateRelation(@PathVariable Long id, @RequestBody ParentEleveCreateDTO dto) {
        return ResponseEntity.ok(parentEleveService.updateRelation(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long id) {
        parentEleveService.deleteRelation(id);
        return ResponseEntity.noContent().build();
    }
}
