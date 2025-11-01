package com.Ecole.demo.controller;

import com.Ecole.demo.dto.EcoleCreateUpdateDTO;
import com.Ecole.demo.dto.EcoleDTO;
import com.Ecole.demo.service.EcoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ecole")
public class EcoleController {
    
    @Autowired
    private EcoleService ecoleService;
    
    @GetMapping
    public ResponseEntity<EcoleDTO> getEcoleInfo() {
        try {
            return ResponseEntity.ok(ecoleService.getEcoleInfo());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<EcoleDTO>> getAllEcoles() {
        return ResponseEntity.ok(ecoleService.getAllEcoles());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EcoleDTO> getEcoleById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ecoleService.getEcoleById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<EcoleDTO> createEcole(@Valid @RequestBody EcoleCreateUpdateDTO dto) {
        try {
            EcoleDTO created = ecoleService.createEcole(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EcoleDTO> updateEcole(
            @PathVariable Long id, 
            @Valid @RequestBody EcoleCreateUpdateDTO dto) {
        try {
            EcoleDTO updated = ecoleService.updateEcole(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcole(@PathVariable Long id) {
        try {
            ecoleService.deleteEcole(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
