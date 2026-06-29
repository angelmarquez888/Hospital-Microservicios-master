package com.example.HoraHospital.service;

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

import com.example.HoraHospital.dto.horaDTO;
import com.example.HoraHospital.exception.ResourceNotFoundException;
import com.example.HoraHospital.model.horaModel;
import com.example.HoraHospital.repository.horaRepository;

@ExtendWith(MockitoExtension.class)
public class horaServiceTest {

    @Mock
    private horaRepository repository;

    @InjectMocks
    private horaService horaService;

    private horaModel model;
    private horaDTO dto;

    @BeforeEach
    void setUp() {
        model = horaModel.builder()
                .idHora(1L)
                .fechaHora(LocalDateTime.now())
                .medico("Dr. House")
                .especialidad("Diagnóstico")
                .estado("PROGRAMADA")
                .build();

        dto = horaDTO.builder()
                .idHora(1L)
                .fechaHora(LocalDateTime.now())
                .medico("Dr. House")
                .especialidad("Diagnóstico")
                .estado("PROGRAMADA")
                .build();
    }

    @Test
    void testListarTodos_Exito() {
        when(repository.findAll()).thenReturn(Arrays.asList(model));

        List<horaDTO> resultado = horaService.listarTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Dr. House", resultado.get(0).getMedico());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));

        horaDTO resultado = horaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Dr. House", resultado.getMedico());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testGuardar_Exito() {
        when(repository.save(any(horaModel.class))).thenReturn(model);

        horaDTO resultado = horaService.guardar(dto);

        assertNotNull(resultado);
        assertEquals("PROGRAMADA", resultado.getEstado());
        verify(repository, times(1)).save(any(horaModel.class));
    }

    @Test
    void testActualizar_Exito() {
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(repository.save(any(horaModel.class))).thenReturn(model);

        dto.setEstado("ATENDIDA");
        horaDTO resultado = horaService.actualizar(1L, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(horaModel.class));
    }

    @Test
    void testEliminar_Exito() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        horaService.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}