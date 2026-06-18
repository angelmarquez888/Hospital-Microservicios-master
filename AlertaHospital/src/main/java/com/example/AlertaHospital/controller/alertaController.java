package com.example.AlertaHospital.controller;

import com.example.AlertaHospital.dto.alertaDTO;
import com.example.AlertaHospital.service.alertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class alertaController {

    @Autowired
    private alertaService service;


    @GetMapping
    public ResponseEntity<List<alertaDTO>> getAllAlertas() {
        return new ResponseEntity<>(service.getAllAlertas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<alertaDTO> getAlertaById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getAlertaById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<alertaDTO> createAlerta(@RequestBody alertaDTO dto) {
        return new ResponseEntity<>(service.createAlerta(dto), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<alertaDTO> updateAlerta(@PathVariable Long id, @RequestBody alertaDTO dto) {
        return new ResponseEntity<>(service.updateAlerta(id, dto), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long id) {
        service.deleteAlerta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}