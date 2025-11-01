package com.Ecole.demo.controller;

import com.Ecole.demo.dto.UtilisateurDTO;
import com.Ecole.demo.dto.UtilisateurCreateDTO;
import com.Ecole.demo.entity.Role;
import com.Ecole.demo.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {
    
    @Autowired
    private UtilisateurService utilisateurService;
    
    @PostMapping
    public ResponseEntity<UtilisateurDTO> createUtilisateur(@RequestBody UtilisateurCreateDTO utilisateurDTO) {
        return ResponseEntity.ok(utilisateurService.createUtilisateur(utilisateurDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUtilisateur(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<UtilisateurDTO>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UtilisateurDTO>> getUtilisateursByRole(@PathVariable String role) {
        Role roleEnum = Role.valueOf(role.toUpperCase());
        return ResponseEntity.ok(utilisateurService.getUtilisateursByRole(roleEnum));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @RequestBody UtilisateurCreateDTO utilisateurDTO) {
        return ResponseEntity.ok(utilisateurService.updateUtilisateur(id, utilisateurDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
