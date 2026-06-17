package com.example.TurnoHospital.service;

import com.example.TurnoHospital.dto.turnoDTO;

import java.util.List;

public interface turnoService {

    List<turnoDTO> obtenerTodos();

    turnoDTO obtenerPorId(Long id);

    turnoDTO guardar(turnoDTO turnoDTO);

    turnoDTO actualizar(Long id, turnoDTO turnoDTO);

    void eliminar(Long id);

    List<turnoDTO> obtenerPorEmpleado(String rutEmpleado);

    List<turnoDTO> obtenerPorArea(String area);
}
