package com.example.PacienteHospital.controller;

import com.example.PacienteHospital.dto.pacienteDTO;
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
class pacienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearPaciente_deberiaRetornar201YElPacienteCreado() throws Exception {
        pacienteDTO nuevo = pacienteDTO.builder()
                .rut(99887766)
                .dv("5")
                .nombres("María")
                .apellidos("López")
                .fechaNacimiento(LocalDate.of(1995, 4, 10))
                .genero("FEMENINO")
                .direccion("Calle Falsa 456")
                .telefono("+56911112222")
                .email("maria.lopez@example.com")
                .build();

        mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombres", is("María")))
                .andExpect(jsonPath("$.idPaciente").exists());
    }

    @Test
    void listarPacientes_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerPacientePorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/pacientes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarPaciente() throws Exception {
        pacienteDTO nuevo = pacienteDTO.builder()
                .rut(11223344)
                .dv("K")
                .nombres("Carlos")
                .apellidos("Soto")
                .fechaNacimiento(LocalDate.of(1988, 1, 20))
                .genero("MASCULINO")
                .direccion("Av. Test 123")
                .telefono("+56933334444")
                .email("carlos.soto@example.com")
                .build();

        String response = mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        pacienteDTO creado = objectMapper.readValue(response, pacienteDTO.class);

        pacienteDTO actualizado = pacienteDTO.builder()
                .rut(11223344)
                .dv("K")
                .nombres("Carlos")
                .apellidos("Soto")
                .fechaNacimiento(LocalDate.of(1988, 1, 20))
                .genero("MASCULINO")
                .direccion("Av. Nueva 999")
                .telefono("+56933334444")
                .email("carlos.soto@example.com")
                .build();

        mockMvc.perform(put("/api/pacientes/" + creado.getIdPaciente())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion", is("Av. Nueva 999")));

        mockMvc.perform(delete("/api/pacientes/" + creado.getIdPaciente()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/pacientes/" + creado.getIdPaciente()))
                .andExpect(status().isNotFound());
    }
}