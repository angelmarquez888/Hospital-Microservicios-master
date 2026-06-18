package com.example.PacienteHospital.service;

import com.example.PacienteHospital.dto.pacienteDTO;
import com.example.PacienteHospital.model.pacienteModel;
import com.example.PacienteHospital.repository.pacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class pacienteServiceImpl implements pacienteService {

    private final pacienteRepository pacienteRepository;

    @Override
    public pacienteDTO crear(pacienteDTO pacienteDTO) {
        if (pacienteRepository.existsByRut(pacienteDTO.getRut())) {
            throw new RuntimeException("Ya existe un paciente registrado con el RUT: " + pacienteDTO.getRut());
        }
        pacienteModel entidad = mapearAEntidad(pacienteDTO);
        pacienteModel guardado = pacienteRepository.save(entidad);
        return mapearADto(guardado);
    }

    @Override
    public pacienteDTO obtenerPorId(Long id) {
        return mapearADto(buscarEntidadPorId(id));
    }

    @Override
    public List<pacienteDTO> obtenerTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    public pacienteDTO actualizar(Long id, pacienteDTO pacienteDTO) {
        pacienteModel existente = buscarEntidadPorId(id);

        existente.setRut(pacienteDTO.getRut());
        existente.setDv(pacienteDTO.getDv());
        existente.setNombres(pacienteDTO.getNombres());
        existente.setApellidos(pacienteDTO.getApellidos());
        existente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        existente.setGenero(pacienteDTO.getGenero());
        existente.setDireccion(pacienteDTO.getDireccion());
        existente.setTelefono(pacienteDTO.getTelefono());
        existente.setEmail(pacienteDTO.getEmail());

        return mapearADto(pacienteRepository.save(existente));
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.delete(buscarEntidadPorId(id));
    }

    private pacienteModel buscarEntidadPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    private pacienteDTO mapearADto(pacienteModel entidad) {
        return pacienteDTO.builder()
                .idPaciente(entidad.getIdPaciente())
                .rut(entidad.getRut())
                .dv(entidad.getDv())
                .nombres(entidad.getNombres())
                .apellidos(entidad.getApellidos())
                .fechaNacimiento(entidad.getFechaNacimiento())
                .genero(entidad.getGenero())
                .direccion(entidad.getDireccion())
                .telefono(entidad.getTelefono())
                .email(entidad.getEmail())
                .build();
    }

    private pacienteModel mapearAEntidad(pacienteDTO dto) {
        return pacienteModel.builder()
                .idPaciente(dto.getIdPaciente())
                .rut(dto.getRut())
                .dv(dto.getDv())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .fechaNacimiento(dto.getFechaNacimiento())
                .genero(dto.getGenero())
                .direccion(dto.getDireccion())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .build();
    }
}
