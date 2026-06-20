package com.example.RecetaHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class recetaDTO {

    private Long id;
    private String pacienteNombre;
    private String medicoNombre;
    private String medicamento;
    private String dosis;
    private String frecuencia;
    private String duracion;
    private String instrucciones;
    private LocalDate fechaEmision;
    private int cantidad;
}