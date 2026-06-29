package com.example.BoxHospital.service;

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

import com.example.BoxHospital.dto.boxDTO;
import com.example.BoxHospital.exception.ResourceNotFoundException;
import com.example.BoxHospital.model.boxModel;
import com.example.BoxHospital.repository.boxRepository;

@ExtendWith(MockitoExtension.class)
public class boxServiceTest {

    @Mock
    private boxRepository repository;

    @InjectMocks
    private boxService boxService; // Como usas @RequiredArgsConstructor, @InjectMocks inyecta el mock por constructor automáticamente

    private boxModel model;
    private boxDTO dto;

    @BeforeEach
    void setUp() {
        model = boxModel.builder()
                .idBox(1L)
                .numeroSector("SECTOR-A")
                .tipo("BOX-CONSULTA")
                .estado("DISPONIBLE")
                .build();

        dto = boxDTO.builder()
                .idBox(1L)
                .numeroSector("SECTOR-A")
                .tipo("BOX-CONSULTA")
                .estado("DISPONIBLE")
                .build();
    }

    @Test
    void testGetAllBoxes_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(model));

        List<boxDTO> resultado = boxService.getAllBoxes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("SECTOR-A", resultado.get(0).getNumeroSector());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetBoxById_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        boxDTO resultado = boxService.getBoxById(1L);

        assertNotNull(resultado);
        assertEquals("SECTOR-A", resultado.getNumeroSector());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGetBoxById_NoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            boxService.getBoxById(99L);
        });
    }

    @Test
    void testCreateBox_Exito() {
        when(repository.save(any(boxModel.class))).thenReturn(model);

        boxDTO resultado = boxService.createBox(dto);

        assertNotNull(resultado);
        assertEquals("DISPONIBLE", resultado.getEstado());
        verify(repository, times(1)).save(any(boxModel.class));
    }

    @Test
    void testUpdateBox_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(repository.save(any(boxModel.class))).thenReturn(model);

        dto.setEstado("OCUPADO");
        boxDTO resultado = boxService.updateBox(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(boxModel.class));
    }

    @Test
    void testDeleteBox_Exito() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        boxService.deleteBox(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}