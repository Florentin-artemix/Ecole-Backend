package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByEleveId(Long eleveId);
    List<Paiement> findByMotifPaiementId(Long motifPaiementId);
    List<Paiement> findByEleveIdAndMotifPaiementId(Long eleveId, Long motifPaiementId);
    
    @Query("SELECT p FROM Paiement p WHERE p.eleve.id = :eleveId ORDER BY p.datePaiement DESC")
    List<Paiement> findByEleveIdOrderByDatePaiementDesc(Long eleveId);
}
