package com.example.RecetaHospital.service;

import com.example.RecetaHospital.dto.recetaDTO;

import java.util.List;

public interface recetaService {

    List<recetaDTO> obtenerTodas();

    recetaDTO obtenerPorId(Long id);

    recetaDTO guardar(recetaDTO receta);

    recetaDTO actualizar(Long id, recetaDTO receta);

    void eliminar(Long id);
}