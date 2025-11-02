package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByNom(String nom);
    boolean existsByNom(String nom);
}
