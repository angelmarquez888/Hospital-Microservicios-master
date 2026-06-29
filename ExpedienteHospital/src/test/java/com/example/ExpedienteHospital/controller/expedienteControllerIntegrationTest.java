package com.example.ExpedienteHospital.controller;

import com.example.ExpedienteHospital.dto.expedienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class expedienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearExpediente_deberiaRetornar201() throws Exception {
        expedienteDTO nuevo = expedienteDTO.builder()
                .tipoSangre("O+")
                .alergias("Ninguna")
                .enfermedadesCronicas("Ninguna")
                .build();

        mockMvc.perform(post("/api/expedientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoSangre", is("O+")));
    }

    @Test
    void flujoCompleto_crudExpediente() throws Exception {
        // 1. Crear
        expedienteDTO nuevo = expedienteDTO.builder()
                .tipoSangre("A-")
                .alergias("Penicilina")
                .enfermedadesCronicas("Diabetes")
                .build();

        String jsonResponse = mockMvc.perform(post("/api/expedientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        expedienteDTO creado = objectMapper.readValue(jsonResponse, expedienteDTO.class);

        // 2. Actualizar
        expedienteDTO modificado = expedienteDTO.builder()
                .idExpediente(creado.getIdExpediente())
                .tipoSangre("A-")
                .alergias("Ninguna") // Cambiamos alergias
                .enfermedadesCronicas("Diabetes")
                .build();

        mockMvc.perform(put("/api/expedientes/" + creado.getIdExpediente())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alergias", is("Ninguna")));

        // 3. Eliminar
        mockMvc.perform(delete("/api/expedientes/" + creado.getIdExpediente()))
                .andExpect(status().isNoContent());
    }
}