package com.example.MedicamentoHospital.service;

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

import com.example.MedicamentoHospital.dto.medicamentoDTO;
import com.example.MedicamentoHospital.exception.ResourceNotFoundException;
import com.example.MedicamentoHospital.model.medicamentoModel;
import com.example.MedicamentoHospital.repository.medicamentoRepository;

@ExtendWith(MockitoExtension.class)
public class medicamentoServiceTest {

    @Mock
    private medicamentoRepository medicamentoRepository;

    @InjectMocks
    private medicamentoService medicamentoService;

    private medicamentoModel model;
    private medicamentoDTO dto;

    @BeforeEach
    void setUp() {
        model = medicamentoModel.builder()
                .idMedicamento(1L)
                .nombre("Paracetamol")
                .principioActivo("Paracetamol 500mg")
                .stockDisponible(100)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .build();

        dto = medicamentoDTO.builder()
                .idMedicamento(1L)
                .nombre("Paracetamol")
                .principioActivo("Paracetamol 500mg")
                .stockDisponible(100)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .build();
    }

    @Test
    void testListarTodos_Exito() {
        when(medicamentoRepository.findAll()).thenReturn(Arrays.asList(model));

        List<medicamentoDTO> resultado = medicamentoService.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Paracetamol", resultado.get(0).getNombre());
        verify(medicamentoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Exito() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(model));

        medicamentoDTO resultado = medicamentoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Paracetamol", resultado.getNombre());
        verify(medicamentoRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar_Exito() {
        when(medicamentoRepository.save(any(medicamentoModel.class))).thenReturn(model);

        medicamentoDTO resultado = medicamentoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(100, resultado.getStockDisponible());
        verify(medicamentoRepository, times(1)).save(any(medicamentoModel.class));
    }

    @Test
    void testActualizar_Exito() {
        when(medicamentoRepository.findById(1L)).thenReturn(Optional.of(model));
        when(medicamentoRepository.save(any(medicamentoModel.class))).thenReturn(model);

        dto.setStockDisponible(90);
        medicamentoDTO resultado = medicamentoService.actualizar(1L, dto);

        assertNotNull(resultado);
        verify(medicamentoRepository, times(1)).findById(1L);
        verify(medicamentoRepository, times(1)).save(any(medicamentoModel.class));
    }

    @Test
    void testEliminar_Exito() {
        when(medicamentoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(medicamentoRepository).deleteById(1L);

        medicamentoService.eliminar(1L);

        verify(medicamentoRepository, times(1)).deleteById(1L);
    }
}