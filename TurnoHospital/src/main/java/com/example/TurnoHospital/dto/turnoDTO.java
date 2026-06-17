package com.example.TurnoHospital.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class turnoDTO {

    private Long idTurno;

    @NotBlank(message = "El RUT del empleado es obligatorio")
    private String rutEmpleado;

    @NotBlank(message = "El área es obligatoria")
    private String area;

    @NotNull(message = "La fecha/hora de inicio del turno es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime inicioTurno;

    @NotNull(message = "La fecha/hora de fin del turno es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime finTurno;
}
