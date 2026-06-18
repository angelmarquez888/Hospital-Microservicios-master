package com.example.ExpedienteHospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ExpedienteHospital.dto.expedienteDTO;
import com.example.ExpedienteHospital.model.expedienteModel;
import com.example.ExpedienteHospital.repository.expedienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class expedienteService {

    private final expedienteRepository expedienteRepository;


    public List<expedienteDTO> listarTodos() {
        return expedienteRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }


    public expedienteDTO buscarPorId(Long id) {
        expedienteModel expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con id: " + id));
        return convertirADTO(expediente);
    }


    public expedienteDTO guardar(expedienteDTO dto) {
        expedienteModel expediente = convertirAModel(dto);
        expedienteModel guardado = expedienteRepository.save(expediente);
        return convertirADTO(guardado);
    }


    public expedienteDTO actualizar(Long id, expedienteDTO dto) {
        expedienteModel expediente = expedienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con id: " + id));

        expediente.setTipoSangre(dto.getTipoSangre());
        expediente.setAlergias(dto.getAlergias());
        expediente.setEnfermedadesCronicas(dto.getEnfermedadesCronicas());

        expedienteModel actualizado = expedienteRepository.save(expediente);
        return convertirADTO(actualizado);
    }


    public void eliminar(Long id) {
        if (!expedienteRepository.existsById(id)) {
            throw new RuntimeException("Expediente no encontrado con id: " + id);
        }
        expedienteRepository.deleteById(id);
    }


    private expedienteDTO convertirADTO(expedienteModel expediente) {
        return expedienteDTO.builder()
                .idExpediente(expediente.getIdExpediente())
                .tipoSangre(expediente.getTipoSangre())
                .alergias(expediente.getAlergias())
                .enfermedadesCronicas(expediente.getEnfermedadesCronicas())
                .build();
    }


    private expedienteModel convertirAModel(expedienteDTO dto) {
        return expedienteModel.builder()
                .idExpediente(dto.getIdExpediente())
                .tipoSangre(dto.getTipoSangre())
                .alergias(dto.getAlergias())
                .enfermedadesCronicas(dto.getEnfermedadesCronicas())
                .build();
    }
}
