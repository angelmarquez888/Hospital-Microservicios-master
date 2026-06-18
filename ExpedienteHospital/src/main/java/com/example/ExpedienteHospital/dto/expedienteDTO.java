package com.example.ExpedienteHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class expedienteDTO {

    private Long idExpediente;
    private String tipoSangre;
    private String alergias;
    private String enfermedadesCronicas;
}
