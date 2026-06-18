package com.example.ExamenHospital.controller;

import com.example.ExamenHospital.dto.examenDTO;
import com.example.ExamenHospital.service.examenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
public class examenController {

    private final examenService service;

    @GetMapping
    public ResponseEntity<List<examenDTO>> getAllExamenes() {
        return ResponseEntity.ok(service.getAllExamenes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<examenDTO> getExamenById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getExamenById(id));
    }

    @PostMapping
    public ResponseEntity<examenDTO> createExamen(@RequestBody examenDTO dto) {
        examenDTO created = service.createExamen(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<examenDTO> updateExamen(@PathVariable Long id, @RequestBody examenDTO dto) {
        return ResponseEntity.ok(service.updateExamen(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        service.deleteExamen(id);
        return ResponseEntity.noContent().build();
    }
}
