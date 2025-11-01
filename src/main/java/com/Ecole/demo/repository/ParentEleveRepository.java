package com.Ecole.demo.repository;

import com.Ecole.demo.entity.ParentEleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentEleveRepository extends JpaRepository<ParentEleve, Long> {
    
    List<ParentEleve> findByParentId(Long parentId);
    
    List<ParentEleve> findByEleveId(Long eleveId);
    
    boolean existsByParentIdAndEleveId(Long parentId, Long eleveId);
}
