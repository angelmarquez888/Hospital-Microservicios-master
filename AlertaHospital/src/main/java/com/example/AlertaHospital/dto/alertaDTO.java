package com.example.AlertaHospital.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class alertaDTO {
    private Long idAlerta;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaHora;
    private String estado;
}