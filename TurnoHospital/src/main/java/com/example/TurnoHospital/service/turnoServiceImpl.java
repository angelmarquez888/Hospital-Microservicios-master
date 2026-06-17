package com.example.TurnoHospital.service;

import com.example.TurnoHospital.dto.turnoDTO;
import com.example.TurnoHospital.model.turnoModel;
import com.example.TurnoHospital.repository.turnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class turnoServiceImpl implements turnoService {

    @Autowired
    private turnoRepository turnoRepository;

    @Override
    public List<turnoDTO> obtenerTodos() {
        return turnoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public turnoDTO obtenerPorId(Long id) {
        turnoModel turno = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + id));
        return convertirADTO(turno);
    }

    @Override
    public turnoDTO guardar(turnoDTO turnoDTO) {
        validarHorario(turnoDTO);
        turnoModel turno = convertirAEntidad(turnoDTO);
        turnoModel turnoGuardado = turnoRepository.save(turno);
        return convertirADTO(turnoGuardado);
    }

    @Override
    public turnoDTO actualizar(Long id, turnoDTO turnoDTO) {
        validarHorario(turnoDTO);

        turnoModel turnoExistente = turnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turno no encontrado con id: " + id));

        turnoExistente.setRutEmpleado(turnoDTO.getRutEmpleado());
        turnoExistente.setArea(turnoDTO.getArea());
        turnoExistente.setInicioTurno(turnoDTO.getInicioTurno());
        turnoExistente.setFinTurno(turnoDTO.getFinTurno());

        return convertirADTO(turnoRepository.save(turnoExistente));
    }

    @Override
    public void eliminar(Long id) {
        turnoRepository.deleteById(id);
    }

    @Override
    public List<turnoDTO> obtenerPorEmpleado(String rutEmpleado) {
        return turnoRepository.findByRutEmpleado(rutEmpleado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<turnoDTO> obtenerPorArea(String area) {
        return turnoRepository.findByArea(area)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Regla de negocio: el fin del turno debe ser posterior al inicio
    private void validarHorario(turnoDTO turnoDTO) {
        if (turnoDTO.getInicioTurno() != null && turnoDTO.getFinTurno() != null
                && !turnoDTO.getFinTurno().isAfter(turnoDTO.getInicioTurno())) {
            throw new IllegalArgumentException("La hora de fin del turno debe ser posterior a la hora de inicio");
        }
    }

    // Métodos de conversión entre Entity y DTO

    private turnoDTO convertirADTO(turnoModel turno) {
        return turnoDTO.builder()
                .idTurno(turno.getIdTurno())
                .rutEmpleado(turno.getRutEmpleado())
                .area(turno.getArea())
                .inicioTurno(turno.getInicioTurno())
                .finTurno(turno.getFinTurno())
                .build();
    }

    private turnoModel convertirAEntidad(turnoDTO dto) {
        return turnoModel.builder()
                .idTurno(dto.getIdTurno())
                .rutEmpleado(dto.getRutEmpleado())
                .area(dto.getArea())
                .inicioTurno(dto.getInicioTurno())
                .finTurno(dto.getFinTurno())
                .build();
    }
}
