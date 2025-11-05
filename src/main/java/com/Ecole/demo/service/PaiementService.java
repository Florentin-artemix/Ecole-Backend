package com.Ecole.demo.service;

import com.Ecole.demo.dto.PaiementCreateDTO;
import com.Ecole.demo.dto.PaiementDTO;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.MotifPaiement;
import com.Ecole.demo.entity.Paiement;
import com.Ecole.demo.entity.SuiviPaiement;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.MotifPaiementRepository;
import com.Ecole.demo.repository.PaiementRepository;
import com.Ecole.demo.repository.SuiviPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaiementService {
    
    @Autowired
    private PaiementRepository paiementRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private MotifPaiementRepository motifPaiementRepository;
    
    @Autowired
    private SuiviPaiementRepository suiviPaiementRepository;
    
    @Autowired
    private EleveService eleveService;
    
    @Transactional
    public PaiementDTO enregistrerPaiement(PaiementCreateDTO dto) {
        // Vérifier que l'élève existe
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID: " + dto.getEleveId()));
        
        // Vérifier que le motif existe
        MotifPaiement motif = motifPaiementRepository.findById(dto.getMotifPaiementId())
                .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + dto.getMotifPaiementId()));
        
        // Créer le paiement
        Paiement paiement = new Paiement();
        paiement.setEleve(eleve);
        paiement.setMotifPaiement(motif);
        paiement.setMontantPaye(dto.getMontantPaye());
        paiement.setDatePaiement(dto.getDatePaiement() != null ? dto.getDatePaiement() : LocalDateTime.now());
        paiement.setReferencePaiement(dto.getReferencePaiement());
        paiement.setModePaiement(dto.getModePaiement());
        paiement.setRemarque(dto.getRemarque());
        paiement.setRecuPar(dto.getRecuPar());
        
        Paiement saved = paiementRepository.save(paiement);
        
        // Mettre à jour le suivi de paiement
        mettreAJourSuiviPaiement(eleve.getId(), motif.getId(), dto.getMontantPaye());
        
        // Mettre à jour le statut global de l'élève
        eleveService.mettreAJourStatutPaiementGlobal(eleve.getId());
        
        return convertirEnDTO(saved);
    }
    
    @Transactional
    public void mettreAJourSuiviPaiement(Long eleveId, Long motifPaiementId, BigDecimal montantPaye) {
        Optional<SuiviPaiement> suiviOpt = suiviPaiementRepository.findByEleveIdAndMotifPaiementId(eleveId, motifPaiementId);
        
        SuiviPaiement suivi;
        if (suiviOpt.isPresent()) {
            suivi = suiviOpt.get();
            // Initialiser montantAPayer si manquant avec le montant du motif
            if (suivi.getMontantAPayer() == null || suivi.getMontantAPayer().compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal montantMotif = suivi.getMotifPaiement().getMontantTotal();
                suivi.setMontantAPayer(montantMotif);
            }
            // Initialiser montantPaye si null
            if (suivi.getMontantPaye() == null) {
                suivi.setMontantPaye(BigDecimal.ZERO);
            }
            // Ajouter le montant payé
            suivi.setMontantPaye(suivi.getMontantPaye().add(montantPaye));
        } else {
            // Créer un nouveau suivi si n'existe pas
            Eleve eleve = eleveRepository.findById(eleveId)
                    .orElseThrow(() -> new RuntimeException("Élève non trouvé"));
            MotifPaiement motif = motifPaiementRepository.findById(motifPaiementId)
                    .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé"));
            
            suivi = new SuiviPaiement();
            suivi.setEleve(eleve);
            suivi.setMotifPaiement(motif);
            suivi.setMontantAPayer(motif.getMontantTotal());
            suivi.setMontantPaye(montantPaye != null ? montantPaye : BigDecimal.ZERO);
        }
        
        suivi.setDateDernierPaiement(LocalDateTime.now());
        suiviPaiementRepository.save(suivi);
    }
    
    public List<PaiementDTO> obtenirPaiementsParEleve(Long eleveId) {
        return paiementRepository.findByEleveIdOrderByDatePaiementDesc(eleveId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<PaiementDTO> obtenirPaiementsParMotif(Long motifId) {
        return paiementRepository.findByMotifPaiementId(motifId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<PaiementDTO> obtenirTousPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public PaiementDTO obtenirPaiementParId(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé avec l'ID: " + id));
        return convertirEnDTO(paiement);
    }
    
    private PaiementDTO convertirEnDTO(Paiement paiement) {
        PaiementDTO dto = new PaiementDTO();
        dto.setId(paiement.getId());
        dto.setEleveId(paiement.getEleve().getId());
        dto.setNomEleve(paiement.getEleve().getNom());
        dto.setPrenomEleve(paiement.getEleve().getPrenom());
        dto.setMotifPaiementId(paiement.getMotifPaiement().getId());
        dto.setLibelleMotif(paiement.getMotifPaiement().getLibelle());
        dto.setMontantPaye(paiement.getMontantPaye());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setReferencePaiement(paiement.getReferencePaiement());
        dto.setModePaiement(paiement.getModePaiement());
        dto.setRemarque(paiement.getRemarque());
        dto.setRecuPar(paiement.getRecuPar());
        return dto;
    }
}