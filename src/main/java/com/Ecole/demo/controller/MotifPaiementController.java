package com.Ecole.demo.controller;

import com.Ecole.demo.dto.MotifPaiementCreateDTO;
import com.Ecole.demo.dto.MotifPaiementDTO;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.service.MotifPaiementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motifs-paiement")
public class MotifPaiementController {
    
    @Autowired
    private MotifPaiementService motifPaiementService;
    
    @PostMapping
    public ResponseEntity<MotifPaiementDTO> creerMotifPaiement(@Valid @RequestBody MotifPaiementCreateDTO dto) {
        MotifPaiementDTO created = motifPaiementService.creerMotifPaiement(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<MotifPaiementDTO>> obtenirTousMotifs() {
        List<MotifPaiementDTO> motifs = motifPaiementService.obtenirTousMotifs();
        return ResponseEntity.ok(motifs);
    }
    
    @GetMapping("/actifs")
    public ResponseEntity<List<MotifPaiementDTO>> obtenirMotifsActifs() {
        List<MotifPaiementDTO> motifs = motifPaiementService.obtenirMotifsActifs();
        return ResponseEntity.ok(motifs);
    }
    
    @GetMapping("/periode/{periode}")
    public ResponseEntity<List<MotifPaiementDTO>> obtenirMotifsParPeriode(@PathVariable Periode periode) {
        List<MotifPaiementDTO> motifs = motifPaiementService.obtenirMotifsParPeriode(periode);
        return ResponseEntity.ok(motifs);
    }
    
    @GetMapping("/annee/{anneeScolaire}")
    public ResponseEntity<List<MotifPaiementDTO>> obtenirMotifsParAnneeScolaire(@PathVariable String anneeScolaire) {
        List<MotifPaiementDTO> motifs = motifPaiementService.obtenirMotifsParAnneeScolaire(anneeScolaire);
        return ResponseEntity.ok(motifs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MotifPaiementDTO> obtenirMotifParId(@PathVariable Long id) {
        MotifPaiementDTO motif = motifPaiementService.obtenirMotifParId(id);
        return ResponseEntity.ok(motif);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MotifPaiementDTO> mettreAJourMotif(
            @PathVariable Long id,
            @Valid @RequestBody MotifPaiementCreateDTO dto) {
        MotifPaiementDTO updated = motifPaiementService.mettreAJourMotif(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @PatchMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverMotif(@PathVariable Long id) {
        motifPaiementService.desactiverMotif(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerMotif(@PathVariable Long id) {
        motifPaiementService.supprimerMotif(id);
        return ResponseEntity.noContent().build();
    }
}
