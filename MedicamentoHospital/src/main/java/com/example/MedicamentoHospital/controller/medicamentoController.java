package com.example.MedicamentoHospital.controller;

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

import com.example.MedicamentoHospital.dto.medicamentoDTO;
import com.example.MedicamentoHospital.service.medicamentoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class medicamentoController {

    private final medicamentoService medicamentoService;

 
    @GetMapping
    public ResponseEntity<List<medicamentoDTO>> listarTodos() {
        return ResponseEntity.ok(medicamentoService.listarTodos());
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<medicamentoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentoService.buscarPorId(id));
    }

 
    @PostMapping
    public ResponseEntity<medicamentoDTO> crear(@RequestBody medicamentoDTO dto) {
        medicamentoDTO creado = medicamentoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<medicamentoDTO> actualizar(@PathVariable Long id, @RequestBody medicamentoDTO dto) {
        return ResponseEntity.ok(medicamentoService.actualizar(id, dto));
    }

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
