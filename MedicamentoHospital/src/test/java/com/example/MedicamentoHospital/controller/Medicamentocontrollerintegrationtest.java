package com.example.MedicamentoHospital.controller;

import com.example.MedicamentoHospital.dto.medicamentoDTO;
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
class medicamentoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearMedicamento_deberiaRetornar201YElMedicamentoCreado() throws Exception {
        medicamentoDTO nuevo = medicamentoDTO.builder()
                .nombre("Amoxicilina")
                .principioActivo("Amoxicilina")
                .stockDisponible(60)
                .fechaVencimiento(LocalDate.of(2027, 3, 15))
                .build();

        mockMvc.perform(post("/api/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Amoxicilina")))
                .andExpect(jsonPath("$.idMedicamento").exists());
    }

    @Test
    void listarMedicamentos_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/medicamentos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerMedicamentoPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/medicamentos/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarMedicamento() throws Exception {
        medicamentoDTO nuevo = medicamentoDTO.builder()
                .nombre("Paracetamol")
                .principioActivo("Paracetamol")
                .stockDisponible(150)
                .fechaVencimiento(LocalDate.of(2027, 12, 31))
                .build();

        String response = mockMvc.perform(post("/api/medicamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        medicamentoDTO creado = objectMapper.readValue(response, medicamentoDTO.class);

        medicamentoDTO actualizado = medicamentoDTO.builder()
                .nombre("Paracetamol")
                .principioActivo("Paracetamol")
                .stockDisponible(100)
                .fechaVencimiento(LocalDate.of(2027, 12, 31))
                .build();

        mockMvc.perform(put("/api/medicamentos/" + creado.getIdMedicamento())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockDisponible", is(100)));

        mockMvc.perform(delete("/api/medicamentos/" + creado.getIdMedicamento()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/medicamentos/" + creado.getIdMedicamento()))
                .andExpect(status().isNotFound());
    }
}