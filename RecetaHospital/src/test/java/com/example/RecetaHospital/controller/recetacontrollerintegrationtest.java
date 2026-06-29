package com.example.RecetaHospital.controller;

import com.example.RecetaHospital.dto.recetaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class recetaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearReceta_deberiaRetornar201YLaRecetaCreada() throws Exception {
        recetaDTO nueva = recetaDTO.builder()
                .pacienteNombre("Javier Fernández")
                .medicoNombre("Dr. Pérez")
                .medicamento("Ibuprofeno")
                .dosis("400mg")
                .frecuencia("Cada 12 horas")
                .duracion("5 días")
                .instrucciones("Tomar después de las comidas")
                .fechaEmision(LocalDate.of(2026, 6, 20))
                .cantidad(10)
                .build();

        mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medicamento", is("Ibuprofeno")))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void listarRecetas_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/recetas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerRecetaPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/recetas/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarReceta() throws Exception {
        recetaDTO nueva = recetaDTO.builder()
                .pacienteNombre("Ana Torres")
                .medicoNombre("Dra. Vargas")
                .medicamento("Paracetamol")
                .dosis("500mg")
                .frecuencia("Cada 8 horas")
                .duracion("7 días")
                .instrucciones("Tomar con alimentos")
                .fechaEmision(LocalDate.of(2026, 6, 20))
                .cantidad(21)
                .build();

        String response = mockMvc.perform(post("/api/recetas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        recetaDTO creada = objectMapper.readValue(response, recetaDTO.class);

        recetaDTO actualizada = recetaDTO.builder()
                .pacienteNombre("Ana Torres")
                .medicoNombre("Dra. Vargas")
                .medicamento("Paracetamol")
                .dosis("500mg")
                .frecuencia("Cada 6 horas")
                .duracion("10 días")
                .instrucciones("Tomar con alimentos")
                .fechaEmision(LocalDate.of(2026, 6, 20))
                .cantidad(30)
                .build();

        mockMvc.perform(put("/api/recetas/" + creada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad", is(30)));

        mockMvc.perform(delete("/api/recetas/" + creada.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/recetas/" + creada.getId()))
                .andExpect(status().isNotFound());
    }
}