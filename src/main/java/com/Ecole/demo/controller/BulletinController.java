package com.Ecole.demo.controller;

import com.Ecole.demo.dto.BulletinDTO;
import com.Ecole.demo.entity.Periode;
import com.Ecole.demo.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bulletins")
public class BulletinController {
    
    @Autowired
    private BulletinService bulletinService;
    
    @GetMapping("/{eleveId}/{periode}")
    public ResponseEntity<BulletinDTO> getBulletin(
            @PathVariable Long eleveId,
            @PathVariable String periode) {
        
        Periode periodeEnum = Periode.valueOf(periode.toUpperCase());
        BulletinDTO bulletin = bulletinService.genererBulletin(eleveId, periodeEnum);
        return ResponseEntity.ok(bulletin);
    }
}
