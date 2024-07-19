package com.example.comeonBusan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.comeonBusan.entity.DangerZone;
import com.example.comeonBusan.repository.DangerZoneRepository;
import java.util.List;

@RestController
@RequestMapping("/api/danger-zones")
public class DangerZoneController {

    @Autowired
    private DangerZoneRepository repository;

    @GetMapping
    public ResponseEntity<List<DangerZone>> getAllDangerZones() {
        List<DangerZone> zones = repository.findAll();
        return ResponseEntity.ok(zones);
    }

    @PostMapping
    public ResponseEntity<DangerZone> createDangerZone(@RequestBody DangerZone dangerZone) {
        DangerZone savedZone = repository.save(dangerZone);
        return ResponseEntity.ok(savedZone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DangerZone> updateDangerZone(@PathVariable("id") Long id, @RequestBody DangerZone dangerZone) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dangerZone.setId(id);
        DangerZone updatedZone = repository.save(dangerZone);
        return ResponseEntity.ok(updatedZone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDangerZone(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}