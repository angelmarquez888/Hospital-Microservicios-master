package com.example.TurnoHospital.repository;

import com.example.TurnoHospital.model.turnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface turnoRepository extends JpaRepository<turnoModel, Long> {

    List<turnoModel> findByRutEmpleado(String rutEmpleado);

    List<turnoModel> findByArea(String area);
}