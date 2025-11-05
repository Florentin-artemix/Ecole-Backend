package com.Ecole.demo.controller;

import com.Ecole.demo.dto.DerogationAccepterDTO;
import com.Ecole.demo.dto.DerogationCreateDTO;
import com.Ecole.demo.dto.DerogationDTO;
import com.Ecole.demo.dto.DerogationRefuserDTO;
import com.Ecole.demo.service.DerogationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/derogations")
public class DerogationController {
    
    @Autowired
    private DerogationService derogationService;
    
    @PostMapping
    public ResponseEntity<DerogationDTO> creerDerogation(@Valid @RequestBody DerogationCreateDTO dto) {
        DerogationDTO created = derogationService.creerDerogation(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PostMapping("/demander")
    public ResponseEntity<DerogationDTO> demanderDerogation(@Valid @RequestBody DerogationCreateDTO dto) {
        DerogationDTO created = derogationService.creerDerogation(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<DerogationDTO>> obtenirToutesDerogations() {
        List<DerogationDTO> derogations = derogationService.obtenirToutesDerogations();
        return ResponseEntity.ok(derogations);
    }
    
    @GetMapping("/en-attente")
    public ResponseEntity<List<DerogationDTO>> obtenirDerogationsEnAttente() {
        List<DerogationDTO> derogations = derogationService.obtenirDerogationsEnAttente();
        return ResponseEntity.ok(derogations);
    }
    
    @GetMapping("/eleve/{eleveId}")
    public ResponseEntity<List<DerogationDTO>> obtenirDerogationsParEleve(@PathVariable Long eleveId) {
        List<DerogationDTO> derogations = derogationService.obtenirDerogationsParEleve(eleveId);
        return ResponseEntity.ok(derogations);
    }
    
    @GetMapping("/eleve/{eleveId}/actives")
    public ResponseEntity<List<DerogationDTO>> obtenirDerogationsActives(@PathVariable Long eleveId) {
        List<DerogationDTO> derogations = derogationService.obtenirDerogationsActives(eleveId);
        return ResponseEntity.ok(derogations);
    }
    
    @GetMapping("/eleve/{eleveId}/valide")
    public ResponseEntity<DerogationDTO> obtenirDerogationValide(@PathVariable Long eleveId) {
        try {
            DerogationDTO derogation = derogationService.obtenirDerogationValide(eleveId);
            return ResponseEntity.ok(derogation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/eleve/{eleveId}/a-derogation-valide")
    public ResponseEntity<Boolean> eleveADerogationValide(@PathVariable Long eleveId) {
        boolean aDerogation = derogationService.eleveADerogationValide(eleveId);
        return ResponseEntity.ok(aDerogation);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DerogationDTO> obtenirDerogationParId(@PathVariable Long id) {
        DerogationDTO derogation = derogationService.obtenirDerogationParId(id);
        return ResponseEntity.ok(derogation);
    }
    
    @PatchMapping("/{id}/accepter")
    public ResponseEntity<DerogationDTO> accepterDerogation(
            @PathVariable Long id,
            @Valid @RequestBody DerogationAccepterDTO dto) {
        DerogationDTO accepted = derogationService.accepterDerogation(id, dto);
        return ResponseEntity.ok(accepted);
    }
    
    @PatchMapping("/{id}/refuser")
    public ResponseEntity<DerogationDTO> refuserDerogation(
            @PathVariable Long id,
            @Valid @RequestBody DerogationRefuserDTO dto) {
        DerogationDTO refused = derogationService.refuserDerogation(id, dto);
        return ResponseEntity.ok(refused);
    }
    
    @PostMapping("/verifier-expirations")
    public ResponseEntity<Void> verifierEtMettreAJourExpirations() {
        derogationService.verifierEtMettreAJourExpirations();
        return ResponseEntity.ok().build();
    }
}
