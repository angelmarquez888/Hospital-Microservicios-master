package com.example.AmbulanciaHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ambulanciaDTO {

    private Long idAmbulancia;
    private String patente;
    private String tipo;
    private String estado;
    private String ubicacionActual;
}
