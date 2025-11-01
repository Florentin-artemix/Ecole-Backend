package com.Ecole.demo.controller;

import com.Ecole.demo.dto.EleveDTO;
import com.Ecole.demo.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {
    
    @Autowired
    private EleveService eleveService;
    
    @PostMapping
    public ResponseEntity<EleveDTO> createEleve(@RequestBody EleveDTO eleveDTO) {
        return ResponseEntity.ok(eleveService.createEleve(eleveDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id) {
        return ResponseEntity.ok(eleveService.getEleveById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<EleveDTO>> getAllEleves() {
        return ResponseEntity.ok(eleveService.getAllEleves());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EleveDTO> updateEleve(@PathVariable Long id, @RequestBody EleveDTO eleveDTO) {
        return ResponseEntity.ok(eleveService.updateEleve(id, eleveDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }
}
