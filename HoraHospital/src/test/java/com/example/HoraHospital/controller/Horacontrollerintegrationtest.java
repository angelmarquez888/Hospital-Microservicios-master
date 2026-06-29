package com.example.HoraHospital.controller;

import com.example.HoraHospital.dto.horaDTO;
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
class horaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearHora_deberiaRetornar201YLaHoraCreada() throws Exception {
        horaDTO nueva = horaDTO.builder()
                .fechaHora(LocalDateTime.of(2026, 7, 1, 9, 0))
                .medico("Dr. Soto")
                .especialidad("Dermatología")
                .estado("AGENDADA")
                .build();

        mockMvc.perform(post("/api/horas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medico", is("Dr. Soto")))
                .andExpect(jsonPath("$.idHora").exists());
    }

    @Test
    void listarHoras_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/horas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerHoraPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/horas/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarHora() throws Exception {
        horaDTO nueva = horaDTO.builder()
                .fechaHora(LocalDateTime.of(2026, 7, 2, 11, 0))
                .medico("Dra. Rojas")
                .especialidad("Traumatología")
                .estado("AGENDADA")
                .build();

        String response = mockMvc.perform(post("/api/horas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        horaDTO creada = objectMapper.readValue(response, horaDTO.class);

        horaDTO actualizada = horaDTO.builder()
                .fechaHora(LocalDateTime.of(2026, 7, 2, 11, 0))
                .medico("Dra. Rojas")
                .especialidad("Traumatología")
                .estado("CANCELADA")
                .build();

        mockMvc.perform(put("/api/horas/" + creada.getIdHora())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("CANCELADA")));

        mockMvc.perform(delete("/api/horas/" + creada.getIdHora()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/horas/" + creada.getIdHora()))
                .andExpect(status().isNotFound());
    }
}