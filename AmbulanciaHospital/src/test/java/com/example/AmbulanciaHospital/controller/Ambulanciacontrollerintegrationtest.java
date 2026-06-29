package com.example.AmbulanciaHospital.controller;

import com.example.AmbulanciaHospital.dto.ambulanciaDTO;
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
class ambulanciaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearAmbulancia_deberiaRetornar201YLaAmbulanciaCreada() throws Exception {
        ambulanciaDTO nueva = ambulanciaDTO.builder()
                .patente("ZZ-0001")
                .tipo("AVANZADA")
                .estado("DISPONIBLE")
                .ubicacionActual("Hospital Central")
                .build();

        mockMvc.perform(post("/api/ambulancias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patente", is("ZZ-0001")))
                .andExpect(jsonPath("$.idAmbulancia").exists());
    }

    @Test
    void listarAmbulancias_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/ambulancias"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerAmbulanciaPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/ambulancias/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarAmbulancia() throws Exception {
        ambulanciaDTO nueva = ambulanciaDTO.builder()
                .patente("ZZ-0002")
                .tipo("BASICA")
                .estado("DISPONIBLE")
                .ubicacionActual("Hospital Central")
                .build();

        String response = mockMvc.perform(post("/api/ambulancias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ambulanciaDTO creada = objectMapper.readValue(response, ambulanciaDTO.class);

        ambulanciaDTO actualizada = ambulanciaDTO.builder()
                .patente("ZZ-0002")
                .tipo("BASICA")
                .estado("EN_RUTA")
                .ubicacionActual("Av. Principal")
                .build();

        mockMvc.perform(put("/api/ambulancias/" + creada.getIdAmbulancia())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("EN_RUTA")));

        mockMvc.perform(delete("/api/ambulancias/" + creada.getIdAmbulancia()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/ambulancias/" + creada.getIdAmbulancia()))
                .andExpect(status().isNotFound());
    }
}