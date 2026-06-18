package com.example.AlertaHospital.repository;

import com.example.AlertaHospital.model.alertaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface alertaRepository extends JpaRepository<alertaModel, Long> {
}
