package com.example.MedicamentoHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MedicamentoHospital.model.medicamentoModel;

@Repository
public interface medicamentoRepository extends JpaRepository<medicamentoModel, Long> {
}
