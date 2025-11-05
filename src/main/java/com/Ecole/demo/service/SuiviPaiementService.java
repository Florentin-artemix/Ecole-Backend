package com.Ecole.demo.service;

import com.Ecole.demo.dto.SuiviPaiementDTO;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.MotifPaiement;
import com.Ecole.demo.entity.SuiviPaiement;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.MotifPaiementRepository;
import com.Ecole.demo.repository.SuiviPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuiviPaiementService {
    
    @Autowired
    private SuiviPaiementRepository suiviPaiementRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private MotifPaiementRepository motifPaiementRepository;
    
    public List<SuiviPaiementDTO> obtenirSuivisParEleve(Long eleveId) {
        return suiviPaiementRepository.findByEleveId(eleveId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<SuiviPaiementDTO> obtenirSuivisNonEnOrdreParEleve(Long eleveId) {
        return suiviPaiementRepository.findPaiementsNonEnOrdreByEleveId(eleveId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<SuiviPaiementDTO> obtenirSuivisParMotif(Long motifId) {
        return suiviPaiementRepository.findByMotifPaiementId(motifId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public SuiviPaiementDTO obtenirSuiviParEleveEtMotif(Long eleveId, Long motifId) {
        SuiviPaiement suivi = suiviPaiementRepository.findByEleveIdAndMotifPaiementId(eleveId, motifId)
                .orElseThrow(() -> new RuntimeException("Suivi de paiement non trouvé"));
        return convertirEnDTO(suivi);
    }
    
    public boolean eleveEstEnOrdre(Long eleveId) {
        return suiviPaiementRepository.estToutEnOrdre(eleveId);
    }
    
    @Transactional
    public SuiviPaiementDTO creerSuiviPourEleve(Long eleveId, Long motifPaiementId) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID: " + eleveId));
        
        MotifPaiement motif = motifPaiementRepository.findById(motifPaiementId)
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + motifPaiementId));
        
        // Vérifier si le suivi existe déjà
        if (suiviPaiementRepository.findByEleveIdAndMotifPaiementId(eleveId, motifPaiementId).isPresent()) {
            throw new RuntimeException("Un suivi existe déjà pour cet élève et ce motif");
        }
        
        SuiviPaiement suivi = new SuiviPaiement();
        suivi.setEleve(eleve);
        suivi.setMotifPaiement(motif);
        suivi.setMontantAPayer(motif.getMontantTotal());
        suivi.setMontantPaye(BigDecimal.ZERO);
        
        SuiviPaiement saved = suiviPaiementRepository.save(suivi);
        return convertirEnDTO(saved);
    }
    
    @Transactional
    public void creerSuivisPourTousEleves(Long motifPaiementId) {
        MotifPaiement motif = motifPaiementRepository.findById(motifPaiementId)
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + motifPaiementId));
        
        List<Eleve> eleves = eleveRepository.findAll();
        
        for (Eleve eleve : eleves) {
            // Vérifier si le suivi n'existe pas déjà
            if (suiviPaiementRepository.findByEleveIdAndMotifPaiementId(eleve.getId(), motifPaiementId).isEmpty()) {
                SuiviPaiement suivi = new SuiviPaiement();
                suivi.setEleve(eleve);
                suivi.setMotifPaiement(motif);
                suivi.setMontantAPayer(motif.getMontantTotal());
                suivi.setMontantPaye(BigDecimal.ZERO);
                suiviPaiementRepository.save(suivi);
            }
        }
    }
    
    public List<SuiviPaiementDTO> obtenirTousSuivis() {
        return suiviPaiementRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    private SuiviPaiementDTO convertirEnDTO(SuiviPaiement suivi) {
        SuiviPaiementDTO dto = new SuiviPaiementDTO();
        dto.setId(suivi.getId());
        dto.setEleveId(suivi.getEleve().getId());
        dto.setNomEleve(suivi.getEleve().getNom());
        dto.setPrenomEleve(suivi.getEleve().getPrenom());
        dto.setMotifPaiementId(suivi.getMotifPaiement().getId());
        dto.setLibelleMotif(suivi.getMotifPaiement().getLibelle());
        dto.setMontantAPayer(suivi.getMontantAPayer());
        dto.setMontantPaye(suivi.getMontantPaye());
        dto.setMontantRestant(suivi.getMontantRestant());
        dto.setStatutPaiement(suivi.getStatutPaiement());
        dto.setEstEnOrdre(suivi.getEstEnOrdre());
        dto.setDateDernierPaiement(suivi.getDateDernierPaiement());
        dto.setDateCreation(suivi.getDateCreation());
        dto.setDateMaj(suivi.getDateMaj());
        return dto;
    }
}
