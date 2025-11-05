package com.Ecole.demo.service;

import com.Ecole.demo.dto.DerogationAccepterDTO;
import com.Ecole.demo.dto.DerogationCreateDTO;
import com.Ecole.demo.dto.DerogationDTO;
import com.Ecole.demo.dto.DerogationRefuserDTO;
import com.Ecole.demo.entity.Derogation;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.MotifPaiement;
import com.Ecole.demo.repository.DerogationRepository;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.MotifPaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DerogationService {
    
    @Autowired
    private DerogationRepository derogationRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private MotifPaiementRepository motifPaiementRepository;
    
    @Autowired
    private EleveService eleveService;
    
    @Transactional
    public DerogationDTO creerDerogation(DerogationCreateDTO dto) {
        Eleve eleve = eleveRepository.findById(dto.getEleveId())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID: " + dto.getEleveId()));
        
        Derogation derogation = new Derogation();
        derogation.setEleve(eleve);
        derogation.setMotif(dto.getMotif());
        
        if (dto.getMotifPaiementId() != null) {
            MotifPaiement motif = motifPaiementRepository.findById(dto.getMotifPaiementId())
                    .orElseThrow(() -> new RuntimeException("Motif de paiement non trouvé avec l'ID: " + dto.getMotifPaiementId()));
            derogation.setMotifPaiement(motif);
        }
        
        Derogation saved = derogationRepository.save(derogation);
        return convertirEnDTO(saved);
    }
    
    @Transactional
    public DerogationDTO accepterDerogation(Long derogationId, DerogationAccepterDTO dto) {
        Derogation derogation = derogationRepository.findById(derogationId)
                .orElseThrow(() -> new RuntimeException("Dérogation non trouvée avec l'ID: " + derogationId));
        
        if (derogation.getStatut() != Derogation.StatutDerogation.EN_ATTENTE) {
            throw new RuntimeException("Seules les dérogations en attente peuvent être acceptées");
        }
        
        derogation.accepter(dto.getAccordeePar(), dto.getDateExpiration());
        Derogation saved = derogationRepository.save(derogation);
        
        // Mettre à jour le statut global de l'élève
        eleveService.mettreAJourStatutPaiementGlobal(derogation.getEleve().getId());
        
        return convertirEnDTO(saved);
    }
    
    @Transactional
    public DerogationDTO refuserDerogation(Long derogationId, DerogationRefuserDTO dto) {
        Derogation derogation = derogationRepository.findById(derogationId)
                .orElseThrow(() -> new RuntimeException("Dérogation non trouvée avec l'ID: " + derogationId));
        
        if (derogation.getStatut() != Derogation.StatutDerogation.EN_ATTENTE) {
            throw new RuntimeException("Seules les dérogations en attente peuvent être refusées");
        }
        
        derogation.refuser(dto.getRaisonRefus());
        Derogation saved = derogationRepository.save(derogation);
        
        return convertirEnDTO(saved);
    }
    
    public List<DerogationDTO> obtenirDerogationsParEleve(Long eleveId) {
        return derogationRepository.findByEleveId(eleveId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<DerogationDTO> obtenirDerogationsEnAttente() {
        return derogationRepository.findDerogationsEnAttente().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public List<DerogationDTO> obtenirDerogationsActives(Long eleveId) {
        return derogationRepository.findDerogationsActivesParEleve(eleveId).stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public boolean eleveADerogationValide(Long eleveId) {
        Optional<Derogation> derogation = derogationRepository.findDerogationValideParEleve(eleveId);
        return derogation.isPresent() && derogation.get().estValide();
    }
    
    public DerogationDTO obtenirDerogationValide(Long eleveId) {
        Derogation derogation = derogationRepository.findDerogationValideParEleve(eleveId)
                .orElseThrow(() -> new RuntimeException("Aucune dérogation valide pour cet élève"));
        return convertirEnDTO(derogation);
    }
    
    public List<DerogationDTO> obtenirToutesDerogations() {
        return derogationRepository.findAll().stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }
    
    public DerogationDTO obtenirDerogationParId(Long id) {
        Derogation derogation = derogationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dérogation non trouvée avec l'ID: " + id));
        return convertirEnDTO(derogation);
    }
    
    @Transactional
    public void verifierEtMettreAJourExpirations() {
        List<Derogation> derogationsActives = derogationRepository.findAll().stream()
                .filter(d -> d.getActive() && !d.getEstExpiree())
                .collect(Collectors.toList());
        
        for (Derogation derogation : derogationsActives) {
            derogation.verifierExpiration();
            if (derogation.getEstExpiree()) {
                derogationRepository.save(derogation);
                // Mettre à jour le statut de l'élève
                eleveService.mettreAJourStatutPaiementGlobal(derogation.getEleve().getId());
            }
        }
    }
    
    private DerogationDTO convertirEnDTO(Derogation derogation) {
        DerogationDTO dto = new DerogationDTO();
        dto.setId(derogation.getId());
        dto.setEleveId(derogation.getEleve().getId());
        dto.setNomEleve(derogation.getEleve().getNom());
        dto.setPrenomEleve(derogation.getEleve().getPrenom());
        
        if (derogation.getMotifPaiement() != null) {
            dto.setMotifPaiementId(derogation.getMotifPaiement().getId());
            dto.setLibelleMotif(derogation.getMotifPaiement().getLibelle());
        }
        
        dto.setMotif(derogation.getMotif());
        dto.setStatut(derogation.getStatut());
        dto.setDateDemande(derogation.getDateDemande());
        dto.setDateAcceptation(derogation.getDateAcceptation());
        dto.setDateExpiration(derogation.getDateExpiration());
        dto.setDateRefus(derogation.getDateRefus());
        dto.setRaisonRefus(derogation.getRaisonRefus());
        dto.setAccordeePar(derogation.getAccordeePar());
        dto.setActive(derogation.getActive());
        dto.setEstExpiree(derogation.getEstExpiree());
        dto.setEstValide(derogation.estValide());
        
        return dto;
    }
}
