package com.example.HoraHospital.controller;

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

import com.example.HoraHospital.dto.horaDTO;
import com.example.HoraHospital.service.horaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/horas")
@RequiredArgsConstructor
public class horaController {

    private final horaService horaService;

  
    @GetMapping
    public ResponseEntity<List<horaDTO>> listarTodos() {
        return ResponseEntity.ok(horaService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<horaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horaService.buscarPorId(id));
    }


    @PostMapping
    public ResponseEntity<horaDTO> crear(@RequestBody horaDTO dto) {
        horaDTO creado = horaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<horaDTO> actualizar(@PathVariable Long id, @RequestBody horaDTO dto) {
        return ResponseEntity.ok(horaService.actualizar(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
