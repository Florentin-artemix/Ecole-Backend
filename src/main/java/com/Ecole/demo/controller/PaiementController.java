package com.Ecole.demo.controller;

import com.Ecole.demo.dto.PaiementCreateDTO;
import com.Ecole.demo.dto.PaiementDTO;
import com.Ecole.demo.service.PaiementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/paiements", "/api/paiement"})
public class PaiementController {
    
    @Autowired
    private PaiementService paiementService;
    
    @PostMapping
    public ResponseEntity<PaiementDTO> enregistrerPaiement(@Valid @RequestBody PaiementCreateDTO dto) {
        PaiementDTO created = paiementService.enregistrerPaiement(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<PaiementDTO>> obtenirTousPaiements() {
        List<PaiementDTO> paiements = paiementService.obtenirTousPaiements();
        return ResponseEntity.ok(paiements);
    }
    
    @GetMapping("/eleve/{eleveId}")
    public ResponseEntity<List<PaiementDTO>> obtenirPaiementsParEleve(@PathVariable Long eleveId) {
        List<PaiementDTO> paiements = paiementService.obtenirPaiementsParEleve(eleveId);
        return ResponseEntity.ok(paiements);
    }
    
    @GetMapping("/motif/{motifId}")
    public ResponseEntity<List<PaiementDTO>> obtenirPaiementsParMotif(@PathVariable Long motifId) {
        List<PaiementDTO> paiements = paiementService.obtenirPaiementsParMotif(motifId);
        return ResponseEntity.ok(paiements);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> obtenirPaiementParId(@PathVariable Long id) {
        PaiementDTO paiement = paiementService.obtenirPaiementParId(id);
        return ResponseEntity.ok(paiement);
    }
}