package com.Ecole.demo.service;

import com.Ecole.demo.dto.BulletinDTO;
import com.Ecole.demo.dto.NoteDTO;
import com.Ecole.demo.entity.Eleve;
import com.Ecole.demo.entity.Note;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.repository.EleveRepository;
import com.Ecole.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BulletinService {
    
    @Autowired
    private EleveRepository eleveRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private NoteService noteService;
    
    @Autowired
    private MentionService mentionService;
    
    @Autowired
    private ConduiteService conduiteService;
    
    public BulletinDTO genererBulletin(Long eleveId, Periode periode) {
        Eleve eleve = eleveRepository.findById(eleveId)
                .orElseThrow(() -> new RuntimeException("Elève non trouvé avec l'ID: " + eleveId));
        
        List<Note> notes = noteRepository.findByEleveIdAndPeriode(eleveId, periode);
        
        if (notes.isEmpty()) {
            throw new RuntimeException("Aucune note trouvée pour cet élève et cette période");
        }
        
        // Calculs
        Double totalGeneral = noteService.calculerTotalGeneral(notes);
        Double maximumGeneral = noteService.calculerMaximumGeneral(notes);
        Double pourcentage = noteService.calculerPourcentage(totalGeneral, maximumGeneral);
        String mention = mentionService.determinerMention(pourcentage);
        
        // Conversion des notes en DTOs
        List<NoteDTO> noteDTOs = notes.stream()
                .map(note -> {
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
                })
                .collect(Collectors.toList());
        
        // Construction du bulletin
        BulletinDTO bulletin = new BulletinDTO();
        bulletin.setNomComplet(eleve.getNom() + " " + eleve.getPostnom() + " " + eleve.getPrenom());
        bulletin.setSexe(eleve.getSexe());
        bulletin.setDateNaissance(eleve.getDateNaissance().format(DateTimeFormatter.ISO_DATE));
        bulletin.setLieuNaissance(eleve.getLieuNaissance());
        bulletin.setNumeroPermanent(eleve.getNumeroPermanent());
        bulletin.setClasse(eleve.getClasse());
        bulletin.setEcole(eleve.getEcole());
        bulletin.setPeriode(periode.getLabel());
        bulletin.setNumeroPeriode(periode.name());
        bulletin.setCode(eleve.getCode());
        bulletin.setVille(eleve.getVille());
        bulletin.setCommune_territoire(eleve.getCommune_territoire());
        bulletin.setNotes(noteDTOs);
        bulletin.setTotalGeneral(totalGeneral);
        bulletin.setMaximumGeneral(maximumGeneral);
        bulletin.setPourcentage(Math.round(pourcentage * 100.0) / 100.0);
        bulletin.setMention(mention);
        
        // Calcul de la conduite la plus fréquente
        String conduite = conduiteService.getMostFrequentConduite(eleveId, periode);
        bulletin.setConduite(conduite);
        
        // Calcul dynamique de la place et du nombre d'élèves
        Long place = noteService.determinerPlaceEleve(eleveId, eleve.getClasse(), periode);
        Long nombreEleves = noteService.compterElevesClasse(eleve.getClasse());
        bulletin.setPlace_nbreEleve(place + "/" + nombreEleves);
        
        return bulletin;
    }
}
