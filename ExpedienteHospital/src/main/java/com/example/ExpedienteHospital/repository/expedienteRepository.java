package com.example.ExpedienteHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExpedienteHospital.model.expedienteModel;

@Repository
public interface expedienteRepository extends JpaRepository<expedienteModel, Long> {
}