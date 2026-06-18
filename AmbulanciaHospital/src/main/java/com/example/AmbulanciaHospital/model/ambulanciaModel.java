package com.example.AmbulanciaHospital.model;

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
@Table(name = "ambulancias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ambulanciaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAmbulancia;
    private String patente;
    private String tipo;
    private String estado;
    private String ubicacionActual;

}
