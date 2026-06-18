package com.example.PacienteHospital.controller;

import com.example.PacienteHospital.dto.pacienteDTO;
import com.example.PacienteHospital.service.pacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class pacienteController {

    private final pacienteService pacienteService;

    @PostMapping
    public ResponseEntity<pacienteDTO> crear(@RequestBody pacienteDTO pacienteDTO) {
        pacienteDTO creado = pacienteService.crear(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<pacienteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<pacienteDTO>> obtenerTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<pacienteDTO> actualizar(@PathVariable Long id, @RequestBody pacienteDTO pacienteDTO) {
        return ResponseEntity.ok(pacienteService.actualizar(id, pacienteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
