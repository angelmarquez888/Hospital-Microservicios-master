package com.example.BoxHospital.service;

import com.example.BoxHospital.dto.boxDTO;
import com.example.BoxHospital.exception.ResourceNotFoundException;
import com.example.BoxHospital.model.boxModel;
import com.example.BoxHospital.repository.boxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class boxServiceTest {

    @Mock
    private boxRepository repository;

    @InjectMocks
    private boxService service;

    private boxModel boxEntity;
    private boxDTO boxDto;

    @BeforeEach
    void setUp() {
        boxEntity = boxModel.builder()
                .idBox(1L)
                .numeroSector("B-12")
                .tipo("QUIRURGICO")
                .estado("DISPONIBLE")
                .build();

        boxDto = boxDTO.builder()
                .idBox(1L)
                .numeroSector("B-12")
                .tipo("QUIRURGICO")
                .estado("DISPONIBLE")
                .build();
    }

    @Test
    void getAllBoxes_deberiaRetornarListaDeDTOs() {
        when(repository.findAll()).thenReturn(List.of(boxEntity));

        List<boxDTO> resultado = service.getAllBoxes();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNumeroSector()).isEqualTo("B-12");
        verify(repository, times(1)).findAll();
    }

    @Test
    void getBoxById_cuandoExiste_deberiaRetornarDTO() {
        when(repository.findById(1L)).thenReturn(Optional.of(boxEntity));

        boxDTO resultado = service.getBoxById(1L);

        assertThat(resultado.getIdBox()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("DISPONIBLE");
    }

    @Test
    void getBoxById_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getBoxById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void createBox_deberiaGuardarYRetornarDTO() {
        when(repository.save(any(boxModel.class))).thenReturn(boxEntity);

        boxDTO resultado = service.createBox(boxDto);

        assertThat(resultado.getNumeroSector()).isEqualTo("B-12");
        verify(repository, times(1)).save(any(boxModel.class));
    }

    @Test
    void updateBox_cuandoExiste_deberiaActualizarYRetornarDTO() {
        boxDTO dtoActualizado = boxDTO.builder()
                .numeroSector("B-99")
                .tipo("AISLAMIENTO")
                .estado("OCUPADO")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(boxEntity));
        when(repository.save(any(boxModel.class))).thenReturn(boxEntity);

        boxDTO resultado = service.updateBox(1L, dtoActualizado);

        assertThat(resultado).isNotNull();
        verify(repository, times(1)).save(boxEntity);
    }

    @Test
    void updateBox_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateBox(99L, boxDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).save(any());
    }

    @Test
    void deleteBox_cuandoExiste_deberiaEliminar() {
        when(repository.existsById(1L)).thenReturn(true);

        service.deleteBox(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBox_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteBox(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(repository, never()).deleteById(any());
    }
}