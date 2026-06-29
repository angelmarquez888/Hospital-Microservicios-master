package com.example.TurnoHospital.controller;

import com.example.TurnoHospital.dto.turnoDTO;
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
public class turnoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearTurno_deberiaRetornar201YElTurnoCreado() throws Exception {
        turnoDTO nuevo = turnoDTO.builder()
                .rutEmpleado("12345678-9")
                .area("URGENCIAS")
                .inicioTurno(LocalDateTime.of(2026, 6, 28, 8, 0, 0))
                .finTurno(LocalDateTime.of(2026, 6, 28, 20, 0, 0))
                .build();

        mockMvc.perform(post("/api/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rutEmpleado", is("12345678-9")))
                .andExpect(jsonPath("$.idTurno").exists());
    }

    @Test
    void listarTurnos_deberiaRetornar200YUnaListaJSON() throws Exception {
        mockMvc.perform(get("/api/turnos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void obtenerTurnoPorId_cuandoNoExiste_deberiaRetornar404() throws Exception {
        mockMvc.perform(get("/api/turnos/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void flujoCompleto_crearActualizarYEliminarTurno() throws Exception {
        // 1. Crear
        turnoDTO nuevo = turnoDTO.builder()
                .rutEmpleado("98765432-1")
                .area("PEDIATRÍA")
                .inicioTurno(LocalDateTime.of(2026, 6, 29, 8, 0, 0))
                .finTurno(LocalDateTime.of(2026, 6, 29, 20, 0, 0))
                .build();

        String response = mockMvc.perform(post("/api/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        turnoDTO creado = objectMapper.readValue(response, turnoDTO.class);

        // 2. Actualizar (Cambiar área a UCI)
        turnoDTO actualizado = turnoDTO.builder()
                .idTurno(creado.getIdTurno())
                .rutEmpleado("98765432-1")
                .area("UCI") // Cambio clave
                .inicioTurno(LocalDateTime.of(2026, 6, 29, 8, 0, 0))
                .finTurno(LocalDateTime.of(2026, 6, 29, 20, 0, 0))
                .build();

        mockMvc.perform(put("/api/turnos/" + creado.getIdTurno())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.area", is("UCI")));

        // 3. Probar endpoint de búsqueda por empleado
        mockMvc.perform(get("/api/turnos/empleado/98765432-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].area", is("UCI")));

        // 4. Eliminar
        mockMvc.perform(delete("/api/turnos/" + creado.getIdTurno()))
                .andExpect(status().isNoContent());

        // 5. Verificar que ya no existe
        mockMvc.perform(get("/api/turnos/" + creado.getIdTurno()))
                .andExpect(status().isNotFound());
    }
}