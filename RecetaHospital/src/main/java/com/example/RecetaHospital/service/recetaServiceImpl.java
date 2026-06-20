package com.example.RecetaHospital.service;

import com.example.RecetaHospital.dto.recetaDTO;
import com.example.RecetaHospital.exception.ResourceNotFoundException;
import com.example.RecetaHospital.model.recetaModel;
import com.example.RecetaHospital.repository.recetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class recetaServiceImpl implements recetaService {

    @Autowired
    private recetaRepository recetaRepository;

    @Override
    public List<recetaDTO> obtenerTodas() {
        return recetaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public recetaDTO obtenerPorId(Long id) {
        recetaModel receta = recetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));
        return toDTO(receta);
    }

    @Override
    public recetaDTO guardar(recetaDTO dto) {
        recetaModel receta = toEntity(dto);
        recetaModel guardada = recetaRepository.save(receta);
        return toDTO(guardada);
    }

    @Override
    public recetaDTO actualizar(Long id, recetaDTO dto) {
        recetaModel recetaExistente = recetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con id: " + id));

        recetaExistente.setPacienteNombre(dto.getPacienteNombre());
        recetaExistente.setMedicoNombre(dto.getMedicoNombre());
        recetaExistente.setMedicamento(dto.getMedicamento());
        recetaExistente.setDosis(dto.getDosis());
        recetaExistente.setFrecuencia(dto.getFrecuencia());
        recetaExistente.setDuracion(dto.getDuracion());
        recetaExistente.setInstrucciones(dto.getInstrucciones());
        recetaExistente.setFechaEmision(dto.getFechaEmision());
        recetaExistente.setCantidad(dto.getCantidad());

        recetaModel actualizada = recetaRepository.save(recetaExistente);
        return toDTO(actualizada);
    }

    @Override
    public void eliminar(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con id: " + id);
        }
        recetaRepository.deleteById(id);
    }

    // ----- Métodos de conversión -----

    private recetaDTO toDTO(recetaModel receta) {
        return recetaDTO.builder()
                .id(receta.getId())
                .pacienteNombre(receta.getPacienteNombre())
                .medicoNombre(receta.getMedicoNombre())
                .medicamento(receta.getMedicamento())
                .dosis(receta.getDosis())
                .frecuencia(receta.getFrecuencia())
                .duracion(receta.getDuracion())
                .instrucciones(receta.getInstrucciones())
                .fechaEmision(receta.getFechaEmision())
                .cantidad(receta.getCantidad())
                .build();
    }

    private recetaModel toEntity(recetaDTO dto) {
        recetaModel receta = new recetaModel();
        receta.setPacienteNombre(dto.getPacienteNombre());
        receta.setMedicoNombre(dto.getMedicoNombre());
        receta.setMedicamento(dto.getMedicamento());
        receta.setDosis(dto.getDosis());
        receta.setFrecuencia(dto.getFrecuencia());
        receta.setDuracion(dto.getDuracion());
        receta.setInstrucciones(dto.getInstrucciones());
        receta.setFechaEmision(dto.getFechaEmision());
        receta.setCantidad(dto.getCantidad());
        return receta;
    }
}