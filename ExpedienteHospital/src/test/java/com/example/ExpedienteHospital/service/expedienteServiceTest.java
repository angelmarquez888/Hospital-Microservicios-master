package com.example.ExpedienteHospital.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ExpedienteHospital.dto.expedienteDTO;
import com.example.ExpedienteHospital.exception.ResourceNotFoundException;
import com.example.ExpedienteHospital.model.expedienteModel;
import com.example.ExpedienteHospital.repository.expedienteRepository;

@ExtendWith(MockitoExtension.class)
public class expedienteServiceTest {

    @Mock
    private expedienteRepository repository;

    @InjectMocks
    private expedienteService expedienteService;

    private expedienteModel model;
    private expedienteDTO dto;

    @BeforeEach
    void setUp() {
        model = expedienteModel.builder()
                .idExpediente(1L)
                .tipoSangre("O+")
                .alergias("Ninguna")
                .enfermedadesCronicas("Ninguna")
                .build();

        dto = expedienteDTO.builder()
                .idExpediente(1L)
                .tipoSangre("O+")
                .alergias("Ninguna")
                .enfermedadesCronicas("Ninguna")
                .build();
    }

    @Test
    void testListarTodos_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(model));

        List<expedienteDTO> resultado = expedienteService.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("O+", resultado.get(0).getTipoSangre());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        expedienteDTO resultado = expedienteService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("O+", resultado.getTipoSangre());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGuardar_Exito() {
        when(repository.save(any(expedienteModel.class))).thenReturn(model);

        expedienteDTO resultado = expedienteService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("O+", resultado.getTipoSangre());
        verify(repository, times(1)).save(any(expedienteModel.class));
    }

    @Test
    void testActualizar_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(repository.save(any(expedienteModel.class))).thenReturn(model);

        dto.setAlergias("Penicilina");
        expedienteDTO resultado = expedienteService.actualizar(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(expedienteModel.class));
    }

    @Test
    void testEliminar_Exito() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        expedienteService.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}