package com.example.ExamenHospital.service;

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

import com.example.ExamenHospital.dto.examenDTO;
import com.example.ExamenHospital.exception.ResourceNotFoundException;
import com.example.ExamenHospital.model.examenModel;
import com.example.ExamenHospital.repository.examenRepository;

@ExtendWith(MockitoExtension.class)
public class examenServiceTest {

    @Mock
    private examenRepository repository;

    @InjectMocks
    private examenService examenService;

    private examenModel model;
    private examenDTO dto;

    @BeforeEach
    void setUp() {
        model = examenModel.builder()
                .idExamen(1L)
                .tipoExamen("SANGRE")
                .fecha(LocalDate.now())
                .resultado("NORMAL")
                .estado("FINALIZADO")
                .build();

        dto = examenDTO.builder()
                .idExamen(1L)
                .tipoExamen("SANGRE")
                .fecha(LocalDate.now())
                .resultado("NORMAL")
                .estado("FINALIZADO")
                .build();
    }

    @Test
    void testGetAllExamenes_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(model));

        List<examenDTO> resultado = examenService.getAllExamenes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("SANGRE", resultado.get(0).getTipoExamen());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetExamenById_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        examenDTO resultado = examenService.getExamenById(1L);

        assertNotNull(resultado);
        assertEquals("SANGRE", resultado.getTipoExamen());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testCreateExamen_Exito() {
        when(repository.save(any(examenModel.class))).thenReturn(model);

        examenDTO resultado = examenService.createExamen(dto);

        assertNotNull(resultado);
        assertEquals("FINALIZADO", resultado.getEstado());
        verify(repository, times(1)).save(any(examenModel.class));
    }

    @Test
    void testUpdateExamen_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(repository.save(any(examenModel.class))).thenReturn(model);

        dto.setEstado("PENDIENTE");
        examenDTO resultado = examenService.updateExamen(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(examenModel.class));
    }

    @Test
    void testDeleteExamen_Exito() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        examenService.deleteExamen(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}