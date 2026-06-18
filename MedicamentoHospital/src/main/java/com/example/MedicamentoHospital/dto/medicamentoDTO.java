package com.example.MedicamentoHospital.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class medicamentoDTO {

    private Long idMedicamento;
    private String nombre;
    private String principioActivo;
    private Integer stockDisponible;
    private LocalDate fechaVencimiento;
}
