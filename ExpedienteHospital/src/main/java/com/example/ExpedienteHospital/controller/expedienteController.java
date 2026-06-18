package com.example.ExpedienteHospital.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpedienteHospital.dto.expedienteDTO;
import com.example.ExpedienteHospital.service.expedienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expedientes")
@RequiredArgsConstructor
public class expedienteController {

    private final expedienteService expedienteService;


    @GetMapping
    public ResponseEntity<List<expedienteDTO>> listarTodos() {
        return ResponseEntity.ok(expedienteService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<expedienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(expedienteService.buscarPorId(id));
    }


    @PostMapping
    public ResponseEntity<expedienteDTO> crear(@RequestBody expedienteDTO dto) {
        expedienteDTO creado = expedienteService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<expedienteDTO> actualizar(@PathVariable Long id, @RequestBody expedienteDTO dto) {
        return ResponseEntity.ok(expedienteService.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        expedienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
