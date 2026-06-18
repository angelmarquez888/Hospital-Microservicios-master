package com.example.ExamenHospital.repository;

import com.example.ExamenHospital.model.examenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface examenRepository extends JpaRepository<examenModel, Long> {
}
