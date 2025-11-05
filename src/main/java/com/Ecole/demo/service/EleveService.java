package com.Ecole.demo.service;

import com.Ecole.demo.dto.EcoleDTO;
import com.Ecole.demo.dto.EleveDTO;
import com.Ecole.demo.entity.Classe;
import com.Ecole.demo.entity.Ecole;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.repository.ClasseRepository;
import com.Ecole.demo.repository.EcoleRepository;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.SuiviPaiementRepository;
import com.Ecole.demo.repository.DerogationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EleveService {
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private EcoleRepository ecoleRepository;
    
    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private SuiviPaiementRepository suiviPaiementRepository;
    
    @Autowired
    private DerogationRepository derogationRepository;
    
    public EleveDTO createEleve(EleveDTO eleveDTO) {
        Eleve eleve = new Eleve();
        eleve.setNom(eleveDTO.getNom());
        eleve.setPostnom(eleveDTO.getPostnom());
        eleve.setPrenom(eleveDTO.getPrenom());
        eleve.setSexe(eleveDTO.getSexe());
        eleve.setDateNaissance(eleveDTO.getDateNaissance());
        eleve.setLieuNaissance(eleveDTO.getLieuNaissance());
        eleve.setNumeroPermanent(eleveDTO.getNumeroPermanent());
        
        // Récupérer l'entité Classe depuis la base de données
        if (eleveDTO.getClasseId() != null) {
            Classe classe = classeRepository.findById(eleveDTO.getClasseId())
                    .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID: " + eleveDTO.getClasseId()));
            eleve.setClasse(classe);
        } else {
            throw new RuntimeException("L'ID de la classe est obligatoire");
        }
        
        // Récupérer l'entité Ecole depuis la base de données
        if (eleveDTO.getEcole() != null && eleveDTO.getEcole().getId() != null) {
            Ecole ecole = ecoleRepository.findById(eleveDTO.getEcole().getId())
                    .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + eleveDTO.getEcole().getId()));
            eleve.setEcole(ecole);
        }
        
        // Ces champs sont optionnels car les informations sont dans l'entité Ecole
        if (eleveDTO.getCode() != null) {
            eleve.setCode(eleveDTO.getCode());
        }
        if (eleveDTO.getVille() != null) {
            eleve.setVille(eleveDTO.getVille());
        }
        if (eleveDTO.getCommune_territoire() != null) {
            eleve.setCommune_territoire(eleveDTO.getCommune_territoire());
        }
        
        Eleve saved = eleveRepository.save(eleve);
        return convertToDTO(saved);
    }
    
    public EleveDTO getEleveById(Long id) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elève non trouvé avec l'ID: " + id));
        return convertToDTO(eleve);
    }
    
    public List<EleveDTO> getAllEleves() {
        return eleveRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EleveDTO updateEleve(Long id, EleveDTO eleveDTO) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Elève non trouvé avec l'ID: " + id));
        
        eleve.setNom(eleveDTO.getNom());
        eleve.setPostnom(eleveDTO.getPostnom());
        eleve.setPrenom(eleveDTO.getPrenom());
        eleve.setSexe(eleveDTO.getSexe());
        eleve.setDateNaissance(eleveDTO.getDateNaissance());
        eleve.setLieuNaissance(eleveDTO.getLieuNaissance());
        eleve.setNumeroPermanent(eleveDTO.getNumeroPermanent());
        
        // Récupérer l'entité Classe depuis la base de données
        if (eleveDTO.getClasseId() != null) {
            Classe classe = classeRepository.findById(eleveDTO.getClasseId())
                    .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID: " + eleveDTO.getClasseId()));
            eleve.setClasse(classe);
        }
        
        // Récupérer l'entité Ecole depuis la base de données
        if (eleveDTO.getEcole() != null && eleveDTO.getEcole().getId() != null) {
            Ecole ecole = ecoleRepository.findById(eleveDTO.getEcole().getId())
                    .orElseThrow(() -> new RuntimeException("École non trouvée avec l'ID: " + eleveDTO.getEcole().getId()));
            eleve.setEcole(ecole);
        }
        
        // Ces champs sont optionnels car les informations sont dans l'entité Ecole
        if (eleveDTO.getCode() != null) {
            eleve.setCode(eleveDTO.getCode());
        }
        if (eleveDTO.getVille() != null) {
            eleve.setVille(eleveDTO.getVille());
        }
        if (eleveDTO.getCommune_territoire() != null) {
            eleve.setCommune_territoire(eleveDTO.getCommune_territoire());
        }
        
        Eleve updated = eleveRepository.save(eleve);
        return convertToDTO(updated);
    }
    
    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
    
    private EleveDTO convertToDTO(Eleve eleve) {
        EcoleDTO ecoleDTO = null;
        if (eleve.getEcole() != null) {
            ecoleDTO = new EcoleDTO();
            ecoleDTO.setId(eleve.getEcole().getId());
            ecoleDTO.setNomEcole(eleve.getEcole().getNomEcole());
            ecoleDTO.setCodeEcole(eleve.getEcole().getCodeEcole());
            ecoleDTO.setVille(eleve.getEcole().getVille());
            ecoleDTO.setCommune_territoire(eleve.getEcole().getCommune_territoire());
            ecoleDTO.setAdresse(eleve.getEcole().getAdresse());
            ecoleDTO.setTelephone(eleve.getEcole().getTelephone());
            ecoleDTO.setEmail(eleve.getEcole().getEmail());
            ecoleDTO.setDevise(eleve.getEcole().getDevise());
        }
        
        return new EleveDTO(
                eleve.getId(),
                eleve.getNom(),
                eleve.getPostnom(),
                eleve.getPrenom(),
                eleve.getSexe(),
                eleve.getDateNaissance(),
                eleve.getLieuNaissance(),
                eleve.getNumeroPermanent(),
                eleve.getClasse() != null ? eleve.getClasse().getId() : null,
                eleve.getClasse() != null ? eleve.getClasse().getNom() : null,
                ecoleDTO,
                eleve.getCode(),
                eleve.getVille(),
                eleve.getCommune_territoire()
        );
    }
    
    /**
     * Met à jour le statut de paiement global d'un élève
     * EN_ORDRE : tous les paiements sont à jour
     * AVEC_DEROGATION : a une dérogation valide
     * NON_EN_ORDRE : a des paiements en retard et pas de dérogation
     */
    @Transactional
    public void mettreAJourStatutPaiementGlobal(Long eleveId) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID: " + eleveId));
        
        // Vérifier s'il y a une dérogation valide
        boolean aDerogationValide = derogationRepository.findDerogationValideParEleve(eleveId)
                .map(d -> d.estValide())
                .orElse(false);
        
        if (aDerogationValide) {
            eleve.setStatutPaiementGlobal(Eleve.StatutPaiementGlobal.AVEC_DEROGATION);
        } else {
            // Vérifier si tous les paiements sont en ordre
            boolean toutEnOrdre = suiviPaiementRepository.estToutEnOrdre(eleveId);
            
            if (toutEnOrdre) {
                eleve.setStatutPaiementGlobal(Eleve.StatutPaiementGlobal.EN_ORDRE);
            } else {
                eleve.setStatutPaiementGlobal(Eleve.StatutPaiementGlobal.NON_EN_ORDRE);
            }
        }
        
        eleveRepository.save(eleve);
    }
    
    /**
     * Vérifie si un élève peut consulter son bulletin
     * Un élève peut consulter son bulletin s'il est EN_ORDRE ou AVEC_DEROGATION valide
     */
    public boolean peutConsulterBulletin(Long eleveId) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'ID: " + eleveId));
        
        // Mettre à jour le statut avant de vérifier
        mettreAJourStatutPaiementGlobal(eleveId);
        
        // Recharger l'élève après mise à jour
        eleve = eleveRepository.findById(eleveId).get();
        
        return eleve.getStatutPaiementGlobal() == Eleve.StatutPaiementGlobal.EN_ORDRE ||
               eleve.getStatutPaiementGlobal() == Eleve.StatutPaiementGlobal.AVEC_DEROGATION;
    }
}
