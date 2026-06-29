package com.example.RecetaHospital.service;

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

import com.example.RecetaHospital.dto.recetaDTO;
import com.example.RecetaHospital.exception.ResourceNotFoundException;
import com.example.RecetaHospital.model.recetaModel;
import com.example.RecetaHospital.repository.recetaRepository;

@ExtendWith(MockitoExtension.class)
public class recetaServiceImplTest {

    @Mock
    private recetaRepository recetaRepository;

    @InjectMocks
    private recetaServiceImpl recetaServiceImpl;

    private recetaModel model;
    private recetaDTO dto;

    @BeforeEach
    void setUp() {
        model = new recetaModel();
        model.setId(1L);
        model.setPacienteNombre("Juan Perez");
        model.setMedicoNombre("Dr. House");
        model.setMedicamento("Paracetamol");
        model.setDosis("500mg");
        model.setFrecuencia("Cada 8 horas");
        model.setDuracion("5 días");
        model.setInstrucciones("Tomar con agua");
        model.setFechaEmision(LocalDate.now());
        model.setCantidad(15);

        dto = recetaDTO.builder()
                .id(1L)
                .pacienteNombre("Juan Perez")
                .medicoNombre("Dr. House")
                .medicamento("Paracetamol")
                .dosis("500mg")
                .frecuencia("Cada 8 horas")
                .duracion("5 días")
                .instrucciones("Tomar con agua")
                .fechaEmision(LocalDate.now())
                .cantidad(15)
                .build();
    }

    @Test
    void testObtenerTodas_Exito() {
        when(recetaRepository.findAll()).thenReturn(Arrays.asList(model));

        List<recetaDTO> resultado = recetaServiceImpl.obtenerTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Juan Perez", resultado.get(0).getPacienteNombre());
        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId_Exito() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(model));

        recetaDTO resultado = recetaServiceImpl.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan Perez", resultado.getPacienteNombre());
        verify(recetaRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar_Exito() {
        when(recetaRepository.save(any(recetaModel.class))).thenReturn(model);

        recetaDTO resultado = recetaServiceImpl.guardar(dto);

        assertNotNull(resultado);
        assertEquals("Paracetamol", resultado.getMedicamento());
        verify(recetaRepository, times(1)).save(any(recetaModel.class));
    }

    @Test
    void testActualizar_Exito() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(model));
        when(recetaRepository.save(any(recetaModel.class))).thenReturn(model);

        dto.setDosis("1000mg");
        recetaDTO resultado = recetaServiceImpl.actualizar(1L, dto);

        assertNotNull(resultado);
        verify(recetaRepository, times(1)).findById(1L);
        verify(recetaRepository, times(1)).save(any(recetaModel.class));
    }

    @Test
    void testEliminar_Exito() {
        when(recetaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(recetaRepository).deleteById(1L);

        recetaServiceImpl.eliminar(1L);

        verify(recetaRepository, times(1)).deleteById(1L);
    }
}