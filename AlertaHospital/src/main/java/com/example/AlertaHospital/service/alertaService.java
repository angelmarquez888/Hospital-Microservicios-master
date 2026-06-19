package com.example.AlertaHospital.service;

import com.example.AlertaHospital.exception.ResourceNotFoundException;
import com.example.AlertaHospital.dto.alertaDTO;
import com.example.AlertaHospital.model.alertaModel;
import com.example.AlertaHospital.repository.alertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class alertaService {

    @Autowired
    private alertaRepository repository;

    private alertaDTO mapToDTO(alertaModel model) {
        return alertaDTO.builder()
                .idAlerta(model.getIdAlerta())
                .tipo(model.getTipo())
                .mensaje(model.getMensaje())
                .fechaHora(model.getFechaHora())
                .estado(model.getEstado())
                .build();
    }

    private alertaModel mapToEntity(alertaDTO dto) {
        return alertaModel.builder()
                .idAlerta(dto.getIdAlerta())
                .tipo(dto.getTipo())
                .mensaje(dto.getMensaje())
                .fechaHora(dto.getFechaHora())
                .estado(dto.getEstado())
                .build();
    }

    public List<alertaDTO> getAllAlertas() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public alertaDTO getAlertaById(Long id) {
        alertaModel alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con ID: " + id));
        return mapToDTO(alerta);
    }

    public alertaDTO createAlerta(alertaDTO dto) {
        alertaModel alerta = mapToEntity(dto);
        alertaModel nuevaAlerta = repository.save(alerta);
        return mapToDTO(nuevaAlerta);
    }

    public alertaDTO updateAlerta(Long id, alertaDTO dto) {
        alertaModel alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con ID: " + id));

        alerta.setTipo(dto.getTipo());
        alerta.setMensaje(dto.getMensaje());
        alerta.setFechaHora(dto.getFechaHora());
        alerta.setEstado(dto.getEstado());

        alertaModel alertaActualizada = repository.save(alerta);
        return mapToDTO(alertaActualizada);
    }

    public void deleteAlerta(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }
}