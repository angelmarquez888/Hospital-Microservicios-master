package com.example.ExamenHospital.service;

import com.example.ExamenHospital.exception.ResourceNotFoundException;
import com.example.ExamenHospital.dto.examenDTO;
import com.example.ExamenHospital.model.examenModel;
import com.example.ExamenHospital.repository.examenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class examenService {

    private final examenRepository repository;

    public List<examenDTO> getAllExamenes() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public examenDTO getExamenById(Long id) {
        examenModel examen = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen no encontrado con id: " + id));
        return toDTO(examen);
    }

    public examenDTO createExamen(examenDTO dto) {
        examenModel examen = toEntity(dto);
        examenModel saved = repository.save(examen);
        return toDTO(saved);
    }

    public examenDTO updateExamen(Long id, examenDTO dto) {
        examenModel examen = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen no encontrado con id: " + id));

        examen.setTipoExamen(dto.getTipoExamen());
        examen.setFecha(dto.getFecha());
        examen.setResultado(dto.getResultado());
        examen.setEstado(dto.getEstado());

        examenModel updated = repository.save(examen);
        return toDTO(updated);
    }

    public void deleteExamen(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Examen no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private examenDTO toDTO(examenModel examen) {
        return examenDTO.builder()
                .idExamen(examen.getIdExamen())
                .tipoExamen(examen.getTipoExamen())
                .fecha(examen.getFecha())
                .resultado(examen.getResultado())
                .estado(examen.getEstado())
                .build();
    }

    private examenModel toEntity(examenDTO dto) {
        return examenModel.builder()
                .idExamen(dto.getIdExamen())
                .tipoExamen(dto.getTipoExamen())
                .fecha(dto.getFecha())
                .resultado(dto.getResultado())
                .estado(dto.getEstado())
                .build();
    }
}