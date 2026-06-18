package com.example.HoraHospital.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class horaDTO {

    private Long idHora;
    private LocalDateTime fechaHora;
    private String medico;
    private String especialidad;
    private String estado;
}
