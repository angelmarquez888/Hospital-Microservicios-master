package com.example.RecetaHospital.controller;

import com.example.RecetaHospital.model.recetaModel;
import com.example.RecetaHospital.service.recetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class recetaController {

    @Autowired
    private recetaService recetaService;

    @GetMapping
    public ResponseEntity<List<recetaModel>> obtenerTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<recetaModel> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recetaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<recetaModel> crear(@RequestBody recetaModel receta) {
        recetaModel nuevaReceta = recetaService.guardar(receta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<recetaModel> actualizar(@PathVariable Long id, @RequestBody recetaModel receta) {
        return ResponseEntity.ok(recetaService.actualizar(id, receta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        recetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
