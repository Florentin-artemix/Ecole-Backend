package com.Ecole.demo.repository;

import com.Ecole.demo.entity.MotifPaiement;
import com.Ecole.demo.entity.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotifPaiementRepository extends JpaRepository<MotifPaiement, Long> {
    List<MotifPaiement> findByActifTrue();
    List<MotifPaiement> findByPeriode(Periode periode);
    List<MotifPaiement> findByAnneeScolaire(String anneeScolaire);
    List<MotifPaiement> findByPeriodeAndAnneeScolaire(Periode periode, String anneeScolaire);
}
