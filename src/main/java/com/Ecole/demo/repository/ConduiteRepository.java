package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Conduite;
import com.Ecole.demo.entity.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConduiteRepository extends JpaRepository<Conduite, Long> {
    
    List<Conduite> findByEleveIdAndPeriode(Long eleveId, Periode periode);
    
    List<Conduite> findByProfesseurIdAndPeriode(Long professeurId, Periode periode);
    
    @Query("SELECT c.typeConduite, COUNT(c) as count FROM Conduite c " +
           "WHERE c.eleve.id = :eleveId AND c.periode = :periode " +
           "GROUP BY c.typeConduite " +
           "ORDER BY count DESC")
    List<Object[]> findMostFrequentConduiteByEleveAndPeriode(
            @Param("eleveId") Long eleveId, 
            @Param("periode") Periode periode);
}
