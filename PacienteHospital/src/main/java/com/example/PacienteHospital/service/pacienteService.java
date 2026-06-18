package com.example.PacienteHospital.service;

import com.example.PacienteHospital.dto.pacienteDTO;

import java.util.List;

public interface pacienteService {

    pacienteDTO crear(pacienteDTO pacienteDTO);

    pacienteDTO obtenerPorId(Long id);

    List<pacienteDTO> obtenerTodos();

    pacienteDTO actualizar(Long id, pacienteDTO pacienteDTO);

    void eliminar(Long id);
}