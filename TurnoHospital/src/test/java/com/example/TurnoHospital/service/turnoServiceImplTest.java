package com.example.TurnoHospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.TurnoHospital.dto.turnoDTO;
import com.example.TurnoHospital.exception.ResourceNotFoundException;
import com.example.TurnoHospital.model.turnoModel;
import com.example.TurnoHospital.repository.turnoRepository;

@ExtendWith(MockitoExtension.class)
public class turnoServiceImplTest {

    @Mock
    private turnoRepository turnoRepository;

    @InjectMocks
    private turnoServiceImpl turnoServiceImpl;

    private turnoModel model;
    private turnoDTO dto;

    @BeforeEach
    void setUp() {
        model = turnoModel.builder()
                .idTurno(1L)
                .rutEmpleado("12345678-9")
                .area("URGENCIA")
                .inicioTurno(LocalDateTime.now())
                .finTurno(LocalDateTime.now().plusHours(8))
                .build();

        dto = turnoDTO.builder()
                .idTurno(1L)
                .rutEmpleado("12345678-9")
                .area("URGENCIA")
                .inicioTurno(LocalDateTime.now())
                .finTurno(LocalDateTime.now().plusHours(8))
                .build();
    }

    @Test
    void testObtenerTodos_Exito() {
        when(turnoRepository.findAll()).thenReturn(Arrays.asList(model));

        List<turnoDTO> resultado = turnoServiceImpl.obtenerTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(turnoRepository, times(1)).findAll();
    }

    @Test
    void testGuardar_Exito() {
        when(turnoRepository.save(any(turnoModel.class))).thenReturn(model);

        turnoDTO resultado = turnoServiceImpl.guardar(dto);

        assertNotNull(resultado);
        assertEquals("URGENCIA", resultado.getArea());
        verify(turnoRepository, times(1)).save(any(turnoModel.class));
    }

    @Test
    void testGuardar_ErrorHorarioInvalido() {
        dto.setFinTurno(dto.getInicioTurno().minusHours(1)); // Fin antes que inicio

        assertThrows(IllegalArgumentException.class, () -> {
            turnoServiceImpl.guardar(dto);
        });
    }

    @Test
    void testObtenerPorEmpleado_Exito() {
        when(turnoRepository.findByRutEmpleado("12345678-9")).thenReturn(Arrays.asList(model));

        List<turnoDTO> resultado = turnoServiceImpl.obtenerPorEmpleado("12345678-9");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testEliminar_Exito() {
        when(turnoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(turnoRepository).deleteById(1L);

        turnoServiceImpl.eliminar(1L);

        verify(turnoRepository, times(1)).deleteById(1L);
    }
}