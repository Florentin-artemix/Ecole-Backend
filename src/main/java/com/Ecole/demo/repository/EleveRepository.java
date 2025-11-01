package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, Long> {
    Optional<Eleve> findByNumeroPermanent(String numeroPermanent);
}
