package com.example.HoraHospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.HoraHospital.exception.ResourceNotFoundException;
import com.example.HoraHospital.dto.horaDTO;
import com.example.HoraHospital.model.horaModel;
import com.example.HoraHospital.repository.horaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class horaService {

    private final horaRepository horaRepository;

    public List<horaDTO> listarTodos() {
        return horaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public horaDTO buscarPorId(Long id) {
        horaModel hora = horaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hora no encontrada con id: " + id));
        return convertirADTO(hora);
    }

    public horaDTO guardar(horaDTO dto) {
        horaModel hora = convertirAModel(dto);
        horaModel guardado = horaRepository.save(hora);
        return convertirADTO(guardado);
    }

    public horaDTO actualizar(Long id, horaDTO dto) {
        horaModel hora = horaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hora no encontrada con id: " + id));

        hora.setFechaHora(dto.getFechaHora());
        hora.setMedico(dto.getMedico());
        hora.setEspecialidad(dto.getEspecialidad());
        hora.setEstado(dto.getEstado());

        horaModel actualizado = horaRepository.save(hora);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!horaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hora no encontrada con id: " + id);
        }
        horaRepository.deleteById(id);
    }

    private horaDTO convertirADTO(horaModel hora) {
        return horaDTO.builder()
                .idHora(hora.getIdHora())
                .fechaHora(hora.getFechaHora())
                .medico(hora.getMedico())
                .especialidad(hora.getEspecialidad())
                .estado(hora.getEstado())
                .build();
    }

    private horaModel convertirAModel(horaDTO dto) {
        return horaModel.builder()
                .idHora(dto.getIdHora())
                .fechaHora(dto.getFechaHora())
                .medico(dto.getMedico())
                .especialidad(dto.getEspecialidad())
                .estado(dto.getEstado())
                .build();
    }
}