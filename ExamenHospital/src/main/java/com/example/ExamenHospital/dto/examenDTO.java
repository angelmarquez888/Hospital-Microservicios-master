package com.example.ExamenHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class examenDTO {

    private Long idExamen;
    private String tipoExamen;
    private LocalDate fecha;
    private String resultado;
    private String estado;
}
