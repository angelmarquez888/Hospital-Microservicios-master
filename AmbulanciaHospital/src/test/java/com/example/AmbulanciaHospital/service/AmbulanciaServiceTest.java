package com.example.AmbulanciaHospital.service;

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

import com.example.AmbulanciaHospital.dto.ambulanciaDTO;
import com.example.AmbulanciaHospital.exception.ResourceNotFoundException;
import com.example.AmbulanciaHospital.model.ambulanciaModel;
import com.example.AmbulanciaHospital.repository.ambulanciaRepository;

@ExtendWith(MockitoExtension.class)
public class AmbulanciaServiceTest {

    @Mock
    private ambulanciaRepository ambulanciaRepository;

    @InjectMocks
    private ambulanciaService ambulanciaService;

    private ambulanciaModel model;
    private ambulanciaDTO dto;

    @BeforeEach
    void setUp() {
        model = ambulanciaModel.builder()
                .idAmbulancia(1L)
                .patente("XYZ-123")
                .tipo("BÁSICA")
                .estado("DISPONIBLE")
                .ubicacionActual("Hospital Central")
                .build();

        dto = ambulanciaDTO.builder()
                .idAmbulancia(1L)
                .patente("XYZ-123")
                .tipo("BÁSICA")
                .estado("DISPONIBLE")
                .ubicacionActual("Hospital Central")
                .build();
    }

    @Test
    void testGetAllAmbulancias_Exito() {
        when(ambulanciaRepository.findAll()).thenReturn(Arrays.asList(model));

        List<ambulanciaDTO> resultado = ambulanciaService.getAllAmbulancias();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("XYZ-123", resultado.get(0).getPatente());
        verify(ambulanciaRepository, times(1)).findAll();
    }

    @Test
    void testGetAmbulanciaById_Exito() {
        when(ambulanciaRepository.findById(1L)).thenReturn(Optional.of(model));

        ambulanciaDTO resultado = ambulanciaService.getAmbulanciaById(1L);

        assertNotNull(resultado);
        assertEquals("XYZ-123", resultado.getPatente());
        verify(ambulanciaRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAmbulanciaById_NoEncontrado() {
        when(ambulanciaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ambulanciaService.getAmbulanciaById(99L);
        });
    }

    @Test
    void testCreateAmbulancia_Exito() {
        when(ambulanciaRepository.save(any(ambulanciaModel.class))).thenReturn(model);

        ambulanciaDTO resultado = ambulanciaService.createAmbulancia(dto);

        assertNotNull(resultado);
        assertEquals("DISPONIBLE", resultado.getEstado());
        verify(ambulanciaRepository, times(1)).save(any(ambulanciaModel.class));
    }

    @Test
    void testUpdateAmbulancia_Exito() {
        when(ambulanciaRepository.findById(1L)).thenReturn(Optional.of(model));
        when(ambulanciaRepository.save(any(ambulanciaModel.class))).thenReturn(model);

        dto.setEstado("EN_SERVICIO");
        ambulanciaDTO resultado = ambulanciaService.updateAmbulancia(1L, dto);

        assertNotNull(resultado);
        verify(ambulanciaRepository, times(1)).findById(1L);
        verify(ambulanciaRepository, times(1)).save(any(ambulanciaModel.class));
    }

    @Test
    void testDeleteAmbulancia_Exito() {
        when(ambulanciaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(ambulanciaRepository).deleteById(1L);

        ambulanciaService.deleteAmbulancia(1L);

        verify(ambulanciaRepository, times(1)).deleteById(1L);
    }
}