package com.example.BoxHospital.service;

import com.example.BoxHospital.dto.boxDTO;
import com.example.BoxHospital.model.boxModel;
import com.example.BoxHospital.repository.boxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class boxService {

    private final boxRepository repository;

    public List<boxDTO> getAllBoxes() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public boxDTO getBoxById(Long id) {
        boxModel box = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box no encontrado con id: " + id));
        return toDTO(box);
    }

    public boxDTO createBox(boxDTO dto) {
        boxModel box = toEntity(dto);
        boxModel saved = repository.save(box);
        return toDTO(saved);
    }

    public boxDTO updateBox(Long id, boxDTO dto) {
        boxModel box = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box no encontrado con id: " + id));

        box.setNumeroSector(dto.getNumeroSector());
        box.setTipo(dto.getTipo());
        box.setEstado(dto.getEstado());

        boxModel updated = repository.save(box);
        return toDTO(updated);
    }

    public void deleteBox(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Box no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private boxDTO toDTO(boxModel box) {
        return boxDTO.builder()
                .idBox(box.getIdBox())
                .numeroSector(box.getNumeroSector())
                .tipo(box.getTipo())
                .estado(box.getEstado())
                .build();
    }

    private boxModel toEntity(boxDTO dto) {
        return boxModel.builder()
                .idBox(dto.getIdBox())
                .numeroSector(dto.getNumeroSector())
                .tipo(dto.getTipo())
                .estado(dto.getEstado())
                .build();
    }
}