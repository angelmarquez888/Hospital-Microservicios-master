package com.example.BoxHospital.model;

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
@Table(name = "boxes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class boxModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBox;
    private String numeroSector;
    private String tipo;
    private String estado;

}
