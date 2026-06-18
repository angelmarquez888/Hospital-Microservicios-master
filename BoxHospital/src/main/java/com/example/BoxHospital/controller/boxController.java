package com.example.BoxHospital.controller;

import com.example.BoxHospital.dto.boxDTO;
import com.example.BoxHospital.service.boxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
public class boxController {

    private final boxService service;

    @GetMapping
    public ResponseEntity<List<boxDTO>> getAllBoxes() {
        return ResponseEntity.ok(service.getAllBoxes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<boxDTO> getBoxById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBoxById(id));
    }

    @PostMapping
    public ResponseEntity<boxDTO> createBox(@RequestBody boxDTO dto) {
        boxDTO created = service.createBox(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<boxDTO> updateBox(@PathVariable Long id, @RequestBody boxDTO dto) {
        return ResponseEntity.ok(service.updateBox(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        service.deleteBox(id);
        return ResponseEntity.noContent().build();
    }
}
