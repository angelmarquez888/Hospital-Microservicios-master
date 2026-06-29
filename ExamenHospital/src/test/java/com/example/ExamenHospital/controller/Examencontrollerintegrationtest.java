package com.example.ExamenHospital.controller;

import com.example.ExamenHospital.dto.examenDTO;
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
class examenControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearExamen_deberiaRetornar201YElExamenCreado() throws Exception {
        examenDTO nuevo = examenDTO.builder()
                .tipoExamen("HEMOGRAMA")
                .fecha(LocalDate.of(2026, 6, 20))
                .resultado("Pendiente")
                .estado("PENDIENTE")
                .build();

        mockMvc.perform(post("/api/examenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoExamen", is("HEMOGRAMA")))
                .andExpect(jsonPath("$.idExamen").exists());
    }

    @Test
    void listarExamenes_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/examenes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerExamenPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/examenes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarExamen() throws Exception {
        examenDTO nuevo = examenDTO.builder()
                .tipoExamen("RADIOGRAFIA")
                .fecha(LocalDate.of(2026, 6, 20))
                .resultado("Pendiente")
                .estado("PENDIENTE")
                .build();

        String response = mockMvc.perform(post("/api/examenes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        examenDTO creado = objectMapper.readValue(response, examenDTO.class);

        examenDTO actualizado = examenDTO.builder()
                .tipoExamen("RADIOGRAFIA")
                .fecha(LocalDate.of(2026, 6, 20))
                .resultado("Sin hallazgos patológicos")
                .estado("COMPLETADO")
                .build();

        mockMvc.perform(put("/api/examenes/" + creado.getIdExamen())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("COMPLETADO")));

        mockMvc.perform(delete("/api/examenes/" + creado.getIdExamen()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/examenes/" + creado.getIdExamen()))
                .andExpect(status().isNotFound());
    }
}