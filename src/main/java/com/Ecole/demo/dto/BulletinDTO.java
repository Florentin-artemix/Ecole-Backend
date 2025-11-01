package com.Ecole.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinDTO {
    @JsonProperty("nomComplet")
    private String nomComplet;
    
    private String sexe;
    
    @JsonProperty("dateNaissance")
    private String dateNaissance;
    
    @JsonProperty("lieuNaissance")
    private String lieuNaissance;
    
    @JsonProperty("numeroPermanent")
    private String numeroPermanent;
    
    private String classe;
    
    private EcoleDTO ecole;
    
    private String periode;
    
    @JsonProperty("numeroPeriode")
    private String numeroPeriode;
    
    @JsonProperty("Code")
    private String code;
    
    private String ville;
    
    @JsonProperty("commune_territoire")
    private String commune_territoire;
    
    private List<NoteDTO> notes;
    
    @JsonProperty("totalGeneral")
    private Double totalGeneral;
    
    @JsonProperty("maximumGeneral")
    private Double maximumGeneral;
    
    private Double pourcentage;
    
    private String mention;
    
    private String conduite;
    
    @JsonProperty("place_nbreEleve")
    private String place_nbreEleve;
}
