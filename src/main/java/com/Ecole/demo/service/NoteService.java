package com.Ecole.demo.service;

import com.Ecole.demo.entity.Note;
import com.Ecole.demo.repository.NoteRepository;
import com.Ecole.demo.entity.Periode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<Note> getNotesByEleveAndPeriode(Long eleveId, Periode periode) {
        return noteRepository.findByEleveIdAndPeriode(eleveId, periode);
    }
    
    public Double calculerTotalGeneral(List<Note> notes) {
        return notes.stream()
                .mapToDouble(note -> note.getValeur() * note.getCours().getPonderation())
                .sum();
    }
    
    public Double calculerMaximumGeneral(List<Note> notes) {
        return notes.stream()
                .mapToDouble(note -> 20.0 * note.getCours().getPonderation())
                .sum();
    }
    
    public Double calculerPourcentage(Double totalGeneral, Double maximumGeneral) {
        if (maximumGeneral == null || maximumGeneral == 0) {
            return 0.0;
        }
        return (totalGeneral / maximumGeneral) * 100;
    }
    
    public Long compterElevesClasse(String classe) {
        // Compte le nombre d'élèves ayant au moins une note dans cette classe
        return noteRepository.findAll().stream()
                .map(note -> note.getEleve())
                .filter(eleve -> eleve.getClasse().equals(classe))
                .map(eleve -> eleve.getId())
                .distinct()
                .count();
    }
    
    public Long determinerPlaceEleve(Long eleveId, String classe, Periode periode) {
        // Récupère tous les élèves de la classe avec leurs notes pour cette période
        List<Note> toutesLesNotes = noteRepository.findAll();
        
        // Grouper les notes par élève et calculer leur pourcentage
        var elevesAvecPourcentage = toutesLesNotes.stream()
                .filter(note -> note.getEleve().getClasse().equals(classe))
                .filter(note -> note.getPeriode().equals(periode))
                .collect(java.util.stream.Collectors.groupingBy(note -> note.getEleve().getId()))
                .entrySet().stream()
                .map(entry -> {
                    Long idEleve = entry.getKey();
                    List<Note> notesEleve = entry.getValue();
                    Double total = calculerTotalGeneral(notesEleve);
                    Double maximum = calculerMaximumGeneral(notesEleve);
                    Double pourcentage = calculerPourcentage(total, maximum);
                    return new java.util.AbstractMap.SimpleEntry<>(idEleve, pourcentage);
                })
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Tri décroissant
                .collect(java.util.stream.Collectors.toList());
        
        // Trouver la position de l'élève
        for (int i = 0; i < elevesAvecPourcentage.size(); i++) {
            if (elevesAvecPourcentage.get(i).getKey().equals(eleveId)) {
                return (long) (i + 1); // Position commence à 1
            }
        }
        
        return 1L; // Par défaut si l'élève n'est pas trouvé
    }
}
