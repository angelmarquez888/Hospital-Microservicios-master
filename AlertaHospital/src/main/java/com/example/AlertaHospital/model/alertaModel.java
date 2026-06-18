package com.example.AlertaHospital.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alertas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class alertaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlerta;
    private String tipo;
    private String mensaje;
    private LocalDateTime fechaHora;
    private String estado;

}
