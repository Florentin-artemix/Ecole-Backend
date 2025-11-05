package com.Ecole.demo.service;

import com.Ecole.demo.dto.MotifPaiementCreateDTO;
import com.Ecole.demo.dto.MotifPaiementDTO;
import com.Ecole.demo.entity.MotifPaiement;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.repository.MotifPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotifPaiementService {
    
    @Autowired
    private MotifPaiementRepository motifPaiementRepository;
    
    @Transactional
    public MotifPaiementDTO creerMotifPaiement(MotifPaiementCreateDTO dto) {
        MotifPaiement motif = new MotifPaiement();
        motif.setLibelle(dto.getLibelle());
        motif.setMontantTotal(dto.getMontantTotal());
        motif.setDescription(dto.getDescription());
        motif.setPeriode(dto.getPeriode());
        motif.setAnneeScolaire(dto.getAnneeScolaire());
        motif.setDateEcheance(dto.getDateEcheance());
        motif.setActif(dto.getActif() != null ? dto.getActif() : true);
        
        MotifPaiement saved = motifPaiementRepository.save(motif);
        return convertirEnDTO(saved);
    }
    
    public List<MotifPaiementDTO> obtenirTousMotifs() {
        return motifPaiementRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<MotifPaiementDTO> obtenirMotifsActifs() {
        return motifPaiementRepository.findByActifTrue().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<MotifPaiementDTO> obtenirMotifsParPeriode(Periode periode) {
        return motifPaiementRepository.findByPeriode(periode).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<MotifPaiementDTO> obtenirMotifsParAnneeScolaire(String anneeScolaire) {
        return motifPaiementRepository.findByAnneeScolaire(anneeScolaire).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public MotifPaiementDTO obtenirMotifParId(Long id) {
        MotifPaiement motif = motifPaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + id));
        return convertirEnDTO(motif);
    }
    
    @Transactional
    public MotifPaiementDTO mettreAJourMotif(Long id, MotifPaiementCreateDTO dto) {
        MotifPaiement motif = motifPaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + id));
        
        motif.setLibelle(dto.getLibelle());
        motif.setMontantTotal(dto.getMontantTotal());
        motif.setDescription(dto.getDescription());
        motif.setPeriode(dto.getPeriode());
        motif.setAnneeScolaire(dto.getAnneeScolaire());
        motif.setDateEcheance(dto.getDateEcheance());
        if (dto.getActif() != null) {
            motif.setActif(dto.getActif());
        }
        
        MotifPaiement updated = motifPaiementRepository.save(motif);
        return convertirEnDTO(updated);
    }
    
    @Transactional
    public void desactiverMotif(Long id) {
        MotifPaiement motif = motifPaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + id));
        motif.setActif(false);
        motifPaiementRepository.save(motif);
    }
    
    @Transactional
    public void supprimerMotif(Long id) {
        motifPaiementRepository.deleteById(id);
    }
    
    private MotifPaiementDTO convertirEnDTO(MotifPaiement motif) {
        MotifPaiementDTO dto = new MotifPaiementDTO();
        dto.setId(motif.getId());
        dto.setLibelle(motif.getLibelle());
        dto.setMontantTotal(motif.getMontantTotal());
        dto.setDescription(motif.getDescription());
        dto.setPeriode(motif.getPeriode());
        dto.setAnneeScolaire(motif.getAnneeScolaire());
        dto.setDateCreation(motif.getDateCreation());
        dto.setDateEcheance(motif.getDateEcheance());
        dto.setActif(motif.getActif());
        return dto;
    }
}
