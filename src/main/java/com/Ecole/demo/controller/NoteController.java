package com.Ecole.demo.controller;

import com.Ecole.demo.dto.NoteCreateDTO;
import com.Ecole.demo.dto.NoteDTO;
import com.Ecole.demo.service.NoteGestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    
    @Autowired
    private NoteGestionService noteGestionService;
    
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteCreateDTO noteDTO) {
        return ResponseEntity.ok(noteGestionService.createNote(noteDTO));
    }
    
    @PostMapping("/batch")
    public ResponseEntity<List<NoteDTO>> createMultipleNotes(@RequestBody List<NoteCreateDTO> notesDTO) {
        return ResponseEntity.ok(noteGestionService.createMultipleNotes(notesDTO));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteGestionService.getNoteById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(noteGestionService.getAllNotes());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody NoteCreateDTO noteDTO) {
        return ResponseEntity.ok(noteGestionService.updateNote(id, noteDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteGestionService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
