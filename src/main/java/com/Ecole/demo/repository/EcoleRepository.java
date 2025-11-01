package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Ecole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcoleRepository extends JpaRepository<Ecole, Long> {
    Optional<Ecole> findByCodeEcole(String codeEcole);
    boolean existsByCodeEcole(String codeEcole);
}
