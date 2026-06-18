package com.example.HoraHospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HoraHospital.model.horaModel;

@Repository
public interface horaRepository extends JpaRepository<horaModel, Long> {
}
