package com.Ecole.demo.service;

import com.Ecole.demo.dto.NoteCreateDTO;
import com.Ecole.demo.dto.NoteDTO;
import com.Ecole.demo.entity.Cours;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.Note;
import com.Ecole.demo.repository.CoursRepository;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteGestionService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private CoursRepository coursRepository;
    
    public NoteDTO createNote(NoteCreateDTO noteDTO) {
        Eleve eleve = eleveRepository.findById(noteDTO.getEleveId())
                .orElseThrow(() -> new RuntimeException("Elève non trouvé"));
        
        Cours cours = coursRepository.findById(noteDTO.getCoursId())
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        
        Note note = new Note();
        note.setEleve(eleve);
        note.setCours(cours);
        note.setValeur(noteDTO.getValeur());
        note.setPeriode(noteDTO.getPeriode());
        
        Note savedNote = noteRepository.save(note);
        return convertToDTO(savedNote);
    }
    
    public NoteDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + id));
        return convertToDTO(note);
    }
    
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public NoteDTO updateNote(Long id, NoteCreateDTO noteDTO) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée avec l'ID: " + id));
        
        Eleve eleve = eleveRepository.findById(noteDTO.getEleveId())
                .orElseThrow(() -> new RuntimeException("Elève non trouvé"));
        
        Cours cours = coursRepository.findById(noteDTO.getCoursId())
                .orElseThrow(() -> new RuntimeException("Cours non trouvé"));
        
        note.setEleve(eleve);
        note.setCours(cours);
        note.setValeur(noteDTO.getValeur());
        note.setPeriode(noteDTO.getPeriode());
        
        Note updatedNote = noteRepository.save(note);
        return convertToDTO(updatedNote);
    }
    
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
    
    public List<NoteDTO> createMultipleNotes(List<NoteCreateDTO> notesDTO) {
        return notesDTO.stream()
                .map(this::createNote)
                .collect(Collectors.toList());
    }
    
    private NoteDTO convertToDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setEleveId(note.getEleve().getId());
        dto.setEleveNom(note.getEleve().getNom() + " " + note.getEleve().getPostnom() + " " + note.getEleve().getPrenom());
        dto.setCoursId(note.getCours().getId());
        dto.setCoursNom(note.getCours().getNomCours());
        dto.setPonderation(note.getCours().getPonderation());
        dto.setValeur(note.getValeur());
        dto.setPeriode(note.getPeriode());
        return dto;
    }
}
