package com.example.BoxHospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class boxDTO {

    private Long idBox;
    private String numeroSector;
    private String tipo;
    private String estado;
}