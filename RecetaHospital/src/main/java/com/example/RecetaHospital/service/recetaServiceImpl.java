package com.example.RecetaHospital.service;

import com.example.RecetaHospital.exception.ResourceNotFoundException;
import com.example.RecetaHospital.model.recetaModel;
import com.example.RecetaHospital.repository.recetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class recetaServiceImpl implements recetaService {

    @Autowired
    private recetaRepository recetaRepository;

    @Override
    public List<recetaModel> obtenerTodas() {
        return recetaRepository.findAll();
    }

    @Override
    public recetaModel obtenerPorId(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));
    }

    @Override
    public recetaModel guardar(recetaModel receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public recetaModel actualizar(Long id, recetaModel receta) {
        recetaModel recetaExistente = obtenerPorId(id);

        recetaExistente.setPacienteNombre(receta.getPacienteNombre());
        recetaExistente.setMedicoNombre(receta.getMedicoNombre());
        recetaExistente.setMedicamento(receta.getMedicamento());
        recetaExistente.setDosis(receta.getDosis());
        recetaExistente.setFrecuencia(receta.getFrecuencia());
        recetaExistente.setDuracion(receta.getDuracion());
        recetaExistente.setInstrucciones(receta.getInstrucciones());
        recetaExistente.setFechaEmision(receta.getFechaEmision());
        recetaExistente.setCantidad(receta.getCantidad());

        return recetaRepository.save(recetaExistente);
    }

    @Override
    public void eliminar(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con id: " + id);
        }
        recetaRepository.deleteById(id);
    }
}