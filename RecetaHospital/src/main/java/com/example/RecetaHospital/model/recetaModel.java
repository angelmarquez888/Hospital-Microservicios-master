package com.example.RecetaHospital.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "recetas")
public class recetaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pacienteNombre;

    @Column(nullable = false)
    private String medicoNombre;

    @Column(nullable = false)
    private String medicamento;

    @Column(nullable = false)
    private String dosis;

    private String frecuencia;

    private String duracion;

    @Column(length = 500)
    private String instrucciones;

    private LocalDate fechaEmision;

    private int cantidad;

    public recetaModel() {
    }

    public recetaModel(String pacienteNombre, String medicoNombre, String medicamento, String dosis,
                        String frecuencia, String duracion, String instrucciones, LocalDate fechaEmision,
                        int cantidad) {
        this.pacienteNombre = pacienteNombre;
        this.medicoNombre = medicoNombre;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracion = duracion;
        this.instrucciones = instrucciones;
        this.fechaEmision = fechaEmision;
        this.cantidad = cantidad;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(String medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
