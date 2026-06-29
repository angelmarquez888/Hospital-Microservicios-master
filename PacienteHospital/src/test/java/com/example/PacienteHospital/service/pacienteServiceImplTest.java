package com.example.PacienteHospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.PacienteHospital.dto.pacienteDTO;
import com.example.PacienteHospital.exception.ResourceNotFoundException;
import com.example.PacienteHospital.model.pacienteModel;
import com.example.PacienteHospital.repository.pacienteRepository;

@ExtendWith(MockitoExtension.class)
public class pacienteServiceImplTest {

    @Mock
    private pacienteRepository pacienteRepository;

    @InjectMocks
    private pacienteServiceImpl pacienteServiceImpl;

    private pacienteModel model;
    private pacienteDTO dto;

    @BeforeEach
    void setUp() {
        model = pacienteModel.builder()
                .idPaciente(1L)
                .rut(12345678)
                .dv("9")
                .nombres("Juan")
                .apellidos("Perez")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .genero("M")
                .direccion("Calle Falsa 123")
                .telefono("123456789")
                .email("juan@test.com")
                .build();

        dto = pacienteDTO.builder()
                .idPaciente(1L)
                .rut(12345678)
                .dv("9")
                .nombres("Juan")
                .apellidos("Perez")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .genero("M")
                .direccion("Calle Falsa 123")
                .telefono("123456789")
                .email("juan@test.com")
                .build();
    }

    @Test
    void testCrear_Exito() {
        when(pacienteRepository.existsByRut(anyInt())).thenReturn(false);
        when(pacienteRepository.save(any(pacienteModel.class))).thenReturn(model);

        pacienteDTO resultado = pacienteServiceImpl.crear(dto);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombres());
        verify(pacienteRepository, times(1)).save(any(pacienteModel.class));
    }

    @Test
    void testCrear_ErrorYaExisteRut() {
        when(pacienteRepository.existsByRut(12345678)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            pacienteServiceImpl.crear(dto);
        });
    }

    @Test
    void testObtenerTodos_Exito() {
        when(pacienteRepository.findAll()).thenReturn(Arrays.asList(model));

        List<pacienteDTO> resultado = pacienteServiceImpl.obtenerTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_Exito() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(model));

        pacienteDTO resultado = pacienteServiceImpl.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombres());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    void testActualizar_Exito() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(model));
        when(pacienteRepository.save(any(pacienteModel.class))).thenReturn(model);

        dto.setNombres("Juan Carlos");
        pacienteDTO resultado = pacienteServiceImpl.actualizar(1L, dto);

        assertNotNull(resultado);
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).save(any(pacienteModel.class));
    }

    @Test
    void testEliminar_Exito() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(model));
        doNothing().when(pacienteRepository).delete(any(pacienteModel.class));

        pacienteServiceImpl.eliminar(1L);

        verify(pacienteRepository, times(1)).delete(any(pacienteModel.class));
    }
}