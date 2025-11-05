package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Derogation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DerogationRepository extends JpaRepository<Derogation, Long> {
    List<Derogation> findByEleveId(Long eleveId);
    List<Derogation> findByStatut(Derogation.StatutDerogation statut);
    
    @Query("SELECT d FROM Derogation d WHERE d.eleve.id = :eleveId AND d.active = true AND d.estExpiree = false")
    List<Derogation> findDerogationsActivesParEleve(Long eleveId);
    
    @Query("SELECT d FROM Derogation d WHERE d.eleve.id = :eleveId AND d.statut = 'ACCEPTEE' AND d.active = true AND d.estExpiree = false AND (d.dateExpiration IS NULL OR d.dateExpiration > CURRENT_TIMESTAMP)")
    Optional<Derogation> findDerogationValideParEleve(Long eleveId);
    
    @Query("SELECT d FROM Derogation d WHERE d.statut = 'EN_ATTENTE' ORDER BY d.dateDemande ASC")
    List<Derogation> findDerogationsEnAttente();
}
