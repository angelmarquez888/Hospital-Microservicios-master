package com.example.AmbulanciaHospital.controller;

import com.example.AmbulanciaHospital.dto.ambulanciaDTO;
import com.example.AmbulanciaHospital.service.ambulanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulancias")
public class ambulanciaController {

    @Autowired
    private ambulanciaService ambulanciaService;


    @GetMapping
    public ResponseEntity<List<ambulanciaDTO>> getAllAmbulancias() {
        return new ResponseEntity<>(ambulanciaService.getAllAmbulancias(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ambulanciaDTO> getAmbulanciaById(@PathVariable Long id) {
        return new ResponseEntity<>(ambulanciaService.getAmbulanciaById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ambulanciaDTO> createAmbulancia(@RequestBody ambulanciaDTO ambulanciaDTO) {
        return new ResponseEntity<>(ambulanciaService.createAmbulancia(ambulanciaDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ambulanciaDTO> updateAmbulancia(@PathVariable Long id, @RequestBody ambulanciaDTO ambulanciaDTO) {
        return new ResponseEntity<>(ambulanciaService.updateAmbulancia(id, ambulanciaDTO), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmbulancia(@PathVariable Long id) {
        ambulanciaService.deleteAmbulancia(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}