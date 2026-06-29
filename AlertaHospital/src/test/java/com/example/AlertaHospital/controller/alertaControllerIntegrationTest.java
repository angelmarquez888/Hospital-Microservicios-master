package com.example.AlertaHospital.controller;

import com.example.AlertaHospital.dto.alertaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class alertaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearAlerta_deberiaRetornar201YLaAlertaCreada() throws Exception {
        alertaDTO nueva = alertaDTO.builder()
                .tipo("CÓDIGO AZUL")
                .mensaje("Paro cardiorrespiratorio en sala 4")
                .fechaHora(LocalDateTime.of(2026, 6, 28, 15, 30))
                .estado("ACTIVA")
                .build();

        mockMvc.perform(post("/api/alertas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo", is("CÓDIGO AZUL")))
                .andExpect(jsonPath("$.idAlerta").exists());
    }

    @Test
    void listarAlertas_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/alertas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerAlertaPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/alertas/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarAlerta() throws Exception {
        // 1. Crear
        alertaDTO nueva = alertaDTO.builder()
                .tipo("URGENCIA MÉDICA")
                .mensaje("Se requiere cirujano en box 2")
                .fechaHora(LocalDateTime.of(2026, 6, 28, 16, 0))
                .estado("ACTIVA")
                .build();

        String response = mockMvc.perform(post("/api/alertas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        alertaDTO creada = objectMapper.readValue(response, alertaDTO.class);

        // 2. Actualizar (Cambiar estado a RESUELTA)
        alertaDTO actualizada = alertaDTO.builder()
                .idAlerta(creada.getIdAlerta())
                .tipo("URGENCIA MÉDICA")
                .mensaje("Se requiere cirujano en box 2")
                .fechaHora(LocalDateTime.of(2026, 6, 28, 16, 0))
                .estado("RESUELTA")
                .build();

        mockMvc.perform(put("/api/alertas/" + creada.getIdAlerta())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("RESUELTA")));

        // 3. Eliminar
        mockMvc.perform(delete("/api/alertas/" + creada.getIdAlerta()))
                .andExpect(status().isNoContent());

        // 4. Verificar que ya no existe
        mockMvc.perform(get("/api/alertas/" + creada.getIdAlerta()))
                .andExpect(status().isNotFound());
    }
}