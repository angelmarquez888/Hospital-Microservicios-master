package com.example.TurnoHospital.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "turnos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class turnoModel {
    @Id
    @GeneratedValue
    private Long idTurno;
    private String rutEmpleado;
    private String area;
    private LocalDateTime inicioTurno;
    private LocalDateTime finTurno;


}
