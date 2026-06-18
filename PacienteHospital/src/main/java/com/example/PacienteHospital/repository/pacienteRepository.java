package com.example.PacienteHospital.repository;

import com.example.PacienteHospital.model.pacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface pacienteRepository extends JpaRepository<pacienteModel, Long> {

    Optional<pacienteModel> findByRutAndDv(Integer rut, String dv);

    boolean existsByRut(Integer rut);
}
