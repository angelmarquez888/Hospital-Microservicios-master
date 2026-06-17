package com.example.TurnoHospital.controller;

import com.example.TurnoHospital.dto.turnoDTO;
import com.example.TurnoHospital.service.turnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class turnoController {

    @Autowired
    private turnoService turnoService;

    @GetMapping
    public ResponseEntity<List<turnoDTO>> obtenerTodos() {
        return ResponseEntity.ok(turnoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<turnoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @GetMapping("/empleado/{rutEmpleado}")
    public ResponseEntity<List<turnoDTO>> obtenerPorEmpleado(@PathVariable String rutEmpleado) {
        return ResponseEntity.ok(turnoService.obtenerPorEmpleado(rutEmpleado));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<List<turnoDTO>> obtenerPorArea(@PathVariable String area) {
        return ResponseEntity.ok(turnoService.obtenerPorArea(area));
    }

    @PostMapping
    public ResponseEntity<turnoDTO> crear(@Valid @RequestBody turnoDTO turnoDTO) {
        turnoDTO nuevoTurno = turnoService.guardar(turnoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTurno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<turnoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody turnoDTO turnoDTO) {
        return ResponseEntity.ok(turnoService.actualizar(id, turnoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        turnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
