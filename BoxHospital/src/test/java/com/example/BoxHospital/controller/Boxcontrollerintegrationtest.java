package com.example.BoxHospital.controller;

import com.example.BoxHospital.dto.boxDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

/**
 * Prueba de INTEGRACIÓN: levanta el contexto completo de Spring (Controller real,
 * Service real, Repository real, conectado a la base de datos MySQL real).
 * No usa mocks. @Transactional hace que cada test revierta sus cambios al terminar,
 * para no dejar basura en la base de datos.
 *
 * Requisito: Docker + MySQL deben estar corriendo antes de ejecutar este test
 * (igual que cuando levantas el microservicio normalmente).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class boxControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearBox_deberiaRetornar201YElBoxCreado() throws Exception {
        boxDTO nuevoBox = boxDTO.builder()
                .numeroSector("B-99")
                .tipo("QUIRURGICO")
                .estado("DISPONIBLE")
                .build();

        mockMvc.perform(post("/api/boxes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoBox)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroSector", is("B-99")))
                .andExpect(jsonPath("$.idBox").exists());
    }

    @Test
    void listarBoxes_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/boxes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerBoxPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/boxes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarBox() throws Exception {
        // 1. Crear
        boxDTO nuevoBox = boxDTO.builder()
                .numeroSector("B-100")
                .tipo("TRIAGE")
                .estado("DISPONIBLE")
                .build();

        String response = mockMvc.perform(post("/api/boxes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevoBox)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        boxDTO creado = objectMapper.readValue(response, boxDTO.class);

        // 2. Actualizar
        boxDTO actualizado = boxDTO.builder()
                .numeroSector("B-100")
                .tipo("TRIAGE")
                .estado("OCUPADO")
                .build();

        mockMvc.perform(put("/api/boxes/" + creado.getIdBox())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("OCUPADO")));

        // 3. Eliminar
        mockMvc.perform(delete("/api/boxes/" + creado.getIdBox()))
                .andExpect(status().isNoContent());

        // 4. Confirmar que ya no existe
        mockMvc.perform(get("/api/boxes/" + creado.getIdBox()))
                .andExpect(status().isNotFound());
    }
}