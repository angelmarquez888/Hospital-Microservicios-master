package com.example.RecetaHospital.repository;

import com.example.RecetaHospital.model.recetaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface recetaRepository extends JpaRepository<recetaModel, Long> {

    List<recetaModel> findByPacienteNombre(String pacienteNombre);

    List<recetaModel> findByMedicoNombre(String medicoNombre);
}
