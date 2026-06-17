package com.example.RecetaHospital.service;

import com.example.RecetaHospital.model.recetaModel;

import java.util.List;

public interface recetaService {

    List<recetaModel> obtenerTodas();

    recetaModel obtenerPorId(Long id);

    recetaModel guardar(recetaModel receta);

    recetaModel actualizar(Long id, recetaModel receta);

    void eliminar(Long id);
}