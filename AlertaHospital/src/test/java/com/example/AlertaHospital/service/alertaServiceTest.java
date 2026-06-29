package com.example.AlertaHospital.service;

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

import com.example.AlertaHospital.dto.alertaDTO;
import com.example.AlertaHospital.exception.ResourceNotFoundException;
import com.example.AlertaHospital.model.alertaModel;
import com.example.AlertaHospital.repository.alertaRepository;

@ExtendWith(MockitoExtension.class)
public class alertaServiceTest {

    @Mock
    private alertaRepository repository;

    @InjectMocks
    private alertaService alertaService;

    private alertaModel model;
    private alertaDTO dto;

    @BeforeEach
    void setUp() {
        model = alertaModel.builder()
                .idAlerta(1L)
                .tipo("CRÍTICA")
                .mensaje("Falla en el sistema")
                .fechaHora(LocalDateTime.now())
                .estado("ACTIVA")
                .build();

        dto = alertaDTO.builder()
                .idAlerta(1L)
                .tipo("CRÍTICA")
                .mensaje("Falla en el sistema")
                .fechaHora(LocalDateTime.now())
                .estado("ACTIVA")
                .build();
    }

    @Test
    void testGetAllAlertas_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(model));

        List<alertaDTO> resultado = alertaService.getAllAlertas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("CRÍTICA", resultado.get(0).getTipo());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAlertaById_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        alertaDTO resultado = alertaService.getAlertaById(1L);

        assertNotNull(resultado);
        assertEquals("Falla en el sistema", resultado.getMensaje());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetAlertaById_NoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            alertaService.getAlertaById(99L);
        });
    }

    @Test
    void testCreateAlerta_Exito() {
        when(repository.save(any(alertaModel.class))).thenReturn(model);

        alertaDTO resultado = alertaService.createAlerta(dto);

        assertNotNull(resultado);
        assertEquals("ACTIVA", resultado.getEstado());
        verify(repository, times(1)).save(any(alertaModel.class));
    }

    @Test
    void testUpdateAlerta_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(repository.save(any(alertaModel.class))).thenReturn(model);

        dto.setEstado("RESUELTA");
        alertaDTO resultado = alertaService.updateAlerta(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(alertaModel.class));
    }

    @Test
    void testDeleteAlerta_Exito() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        alertaService.deleteAlerta(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}