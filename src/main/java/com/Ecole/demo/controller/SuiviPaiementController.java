package com.Ecole.demo.controller;

import com.Ecole.demo.dto.SuiviPaiementDTO;
import com.Ecole.demo.service.SuiviPaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suivis-paiement")
public class SuiviPaiementController {
    
    @Autowired
    private SuiviPaiementService suiviPaiementService;
    
    @GetMapping
    public ResponseEntity<List<SuiviPaiementDTO>> obtenirTousSuivis() {
        List<SuiviPaiementDTO> suivis = suiviPaiementService.obtenirTousSuivis();
        return ResponseEntity.ok(suivis);
    }
    
    @GetMapping("/eleve/{eleveId}")
    public ResponseEntity<List<SuiviPaiementDTO>> obtenirSuivisParEleve(@PathVariable Long eleveId) {
        List<SuiviPaiementDTO> suivis = suiviPaiementService.obtenirSuivisParEleve(eleveId);
        return ResponseEntity.ok(suivis);
    }
    
    @GetMapping("/eleve/{eleveId}/non-en-ordre")
    public ResponseEntity<List<SuiviPaiementDTO>> obtenirSuivisNonEnOrdreParEleve(@PathVariable Long eleveId) {
        List<SuiviPaiementDTO> suivis = suiviPaiementService.obtenirSuivisNonEnOrdreParEleve(eleveId);
        return ResponseEntity.ok(suivis);
    }
    
    @GetMapping("/motif/{motifId}")
    public ResponseEntity<List<SuiviPaiementDTO>> obtenirSuivisParMotif(@PathVariable Long motifId) {
        List<SuiviPaiementDTO> suivis = suiviPaiementService.obtenirSuivisParMotif(motifId);
        return ResponseEntity.ok(suivis);
    }
    
    @GetMapping("/eleve/{eleveId}/motif/{motifId}")
    public ResponseEntity<SuiviPaiementDTO> obtenirSuiviParEleveEtMotif(
            @PathVariable Long eleveId,
            @PathVariable Long motifId) {
        SuiviPaiementDTO suivi = suiviPaiementService.obtenirSuiviParEleveEtMotif(eleveId, motifId);
        return ResponseEntity.ok(suivi);
    }
    
    @GetMapping("/eleve/{eleveId}/en-ordre")
    public ResponseEntity<Boolean> eleveEstEnOrdre(@PathVariable Long eleveId) {
        boolean enOrdre = suiviPaiementService.eleveEstEnOrdre(eleveId);
        return ResponseEntity.ok(enOrdre);
    }
    
    @PostMapping("/eleve/{eleveId}/motif/{motifId}")
    public ResponseEntity<SuiviPaiementDTO> creerSuiviPourEleve(
            @PathVariable Long eleveId,
            @PathVariable Long motifId) {
        SuiviPaiementDTO created = suiviPaiementService.creerSuiviPourEleve(eleveId, motifId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PostMapping("/motif/{motifId}/tous-eleves")
    public ResponseEntity<Void> creerSuivisPourTousEleves(@PathVariable Long motifId) {
        suiviPaiementService.creerSuivisPourTousEleves(motifId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
