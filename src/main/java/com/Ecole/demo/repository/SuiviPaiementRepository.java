package com.Ecole.demo.repository;

import com.Ecole.demo.entity.SuiviPaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuiviPaiementRepository extends JpaRepository<SuiviPaiement, Long> {
    List<SuiviPaiement> findByEleveId(Long eleveId);
    List<SuiviPaiement> findByMotifPaiementId(Long motifPaiementId);
    Optional<SuiviPaiement> findByEleveIdAndMotifPaiementId(Long eleveId, Long motifPaiementId);
    
    @Query("SELECT sp FROM SuiviPaiement sp WHERE sp.eleve.id = :eleveId AND sp.estEnOrdre = false")
    List<SuiviPaiement> findPaiementsNonEnOrdreByEleveId(Long eleveId);
    
    @Query("SELECT sp FROM SuiviPaiement sp WHERE sp.eleve.id = :eleveId AND sp.estEnOrdre = true")
    List<SuiviPaiement> findPaiementsEnOrdreByEleveId(Long eleveId);
    
    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN false ELSE true END FROM SuiviPaiement sp WHERE sp.eleve.id = :eleveId AND sp.estEnOrdre = false")
    boolean estToutEnOrdre(Long eleveId);
}
