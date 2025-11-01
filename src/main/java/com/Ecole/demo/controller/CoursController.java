package com.Ecole.demo.controller;

import com.Ecole.demo.dto.CoursDTO;
import com.Ecole.demo.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursController {
    
    @Autowired
    private CoursService coursService;
    
    @PostMapping
    public ResponseEntity<CoursDTO> createCours(@RequestBody CoursDTO coursDTO) {
        return ResponseEntity.ok(coursService.createCours(coursDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CoursDTO> getCours(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.getCoursById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<CoursDTO>> getAllCours() {
        return ResponseEntity.ok(coursService.getAllCours());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CoursDTO> updateCours(@PathVariable Long id, @RequestBody CoursDTO coursDTO) {
        return ResponseEntity.ok(coursService.updateCours(id, coursDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }
}
