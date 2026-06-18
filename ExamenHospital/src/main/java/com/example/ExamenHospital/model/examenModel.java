package com.example.ExamenHospital.model;

import java.time.LocalDate;
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
@Table(name = "examenes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class examenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExamen;
    private String tipoExamen;
    private LocalDate fecha;
    private String resultado;
    private String estado;

}
