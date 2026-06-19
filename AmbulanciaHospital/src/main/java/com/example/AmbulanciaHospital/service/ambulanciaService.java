package com.example.AmbulanciaHospital.service;

import com.example.AmbulanciaHospital.exception.ResourceNotFoundException;
import com.example.AmbulanciaHospital.dto.ambulanciaDTO;
import com.example.AmbulanciaHospital.model.ambulanciaModel;
import com.example.AmbulanciaHospital.repository.ambulanciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ambulanciaService {

    @Autowired
    private ambulanciaRepository ambulanciaRepository;

    private ambulanciaDTO mapToDTO(ambulanciaModel model) {
        return ambulanciaDTO.builder()
                .idAmbulancia(model.getIdAmbulancia())
                .patente(model.getPatente())
                .tipo(model.getTipo())
                .estado(model.getEstado())
                .ubicacionActual(model.getUbicacionActual())
                .build();
    }

    private ambulanciaModel mapToEntity(ambulanciaDTO dto) {
        return ambulanciaModel.builder()
                .idAmbulancia(dto.getIdAmbulancia())
                .patente(dto.getPatente())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .ubicacionActual(dto.getUbicacionActual())
                .build();
    }

    public List<ambulanciaDTO> getAllAmbulancias() {
        return ambulanciaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ambulanciaDTO getAmbulanciaById(Long id) {
        ambulanciaModel ambulancia = ambulanciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulancia no encontrada con ID: " + id));
        return mapToDTO(ambulancia);
    }

    public ambulanciaDTO createAmbulancia(ambulanciaDTO ambulanciaDTO) {
        ambulanciaModel ambulancia = mapToEntity(ambulanciaDTO);
        ambulanciaModel nuevaAmbulancia = ambulanciaRepository.save(ambulancia);
        return mapToDTO(nuevaAmbulancia);
    }

    public ambulanciaDTO updateAmbulancia(Long id, ambulanciaDTO ambulanciaDTO) {
        ambulanciaModel ambulancia = ambulanciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulancia no encontrada con ID: " + id));

        ambulancia.setPatente(ambulanciaDTO.getPatente());
        ambulancia.setTipo(ambulanciaDTO.getTipo());
        ambulancia.setEstado(ambulanciaDTO.getEstado());
        ambulancia.setUbicacionActual(ambulanciaDTO.getUbicacionActual());

        ambulanciaModel ambulanciaActualizada = ambulanciaRepository.save(ambulancia);
        return mapToDTO(ambulanciaActualizada);
    }

    public void deleteAmbulancia(Long id) {
        if (!ambulanciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ambulancia no encontrada con ID: " + id);
        }
        ambulanciaRepository.deleteById(id);
    }
}