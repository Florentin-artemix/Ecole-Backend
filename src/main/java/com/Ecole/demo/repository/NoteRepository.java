package com.Ecole.demo.repository;

import com.Ecole.demo.entity.Note;
import com.Ecole.demo.entity.Periode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEleveIdAndPeriode(Long eleveId, Periode periode);
    List<Note> findByEleveId(Long eleveId);
}
