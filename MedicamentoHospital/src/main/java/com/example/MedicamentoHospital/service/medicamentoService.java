package com.example.MedicamentoHospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.MedicamentoHospital.exception.ResourceNotFoundException;
import com.example.MedicamentoHospital.dto.medicamentoDTO;
import com.example.MedicamentoHospital.model.medicamentoModel;
import com.example.MedicamentoHospital.repository.medicamentoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class medicamentoService {

    private final medicamentoRepository medicamentoRepository;

    public List<medicamentoDTO> listarTodos() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public medicamentoDTO buscarPorId(Long id) {
        medicamentoModel medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));
        return convertirADTO(medicamento);
    }

    public medicamentoDTO guardar(medicamentoDTO dto) {
        medicamentoModel medicamento = convertirAModel(dto);
        medicamentoModel guardado = medicamentoRepository.save(medicamento);
        return convertirADTO(guardado);
    }

    public medicamentoDTO actualizar(Long id, medicamentoDTO dto) {
        medicamentoModel medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));

        medicamento.setNombre(dto.getNombre());
        medicamento.setPrincipioActivo(dto.getPrincipioActivo());
        medicamento.setStockDisponible(dto.getStockDisponible());
        medicamento.setFechaVencimiento(dto.getFechaVencimiento());

        medicamentoModel actualizado = medicamentoRepository.save(medicamento);
        return convertirADTO(actualizado);
    }

    public void eliminar(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento no encontrado con id: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

    private medicamentoDTO convertirADTO(medicamentoModel medicamento) {
        return medicamentoDTO.builder()
                .idMedicamento(medicamento.getIdMedicamento())
                .nombre(medicamento.getNombre())
                .principioActivo(medicamento.getPrincipioActivo())
                .stockDisponible(medicamento.getStockDisponible())
                .fechaVencimiento(medicamento.getFechaVencimiento())
                .build();
    }

    private medicamentoModel convertirAModel(medicamentoDTO dto) {
        return medicamentoModel.builder()
                .idMedicamento(dto.getIdMedicamento())
                .nombre(dto.getNombre())
                .principioActivo(dto.getPrincipioActivo())
                .stockDisponible(dto.getStockDisponible())
                .fechaVencimiento(dto.getFechaVencimiento())
                .build();
    }
}