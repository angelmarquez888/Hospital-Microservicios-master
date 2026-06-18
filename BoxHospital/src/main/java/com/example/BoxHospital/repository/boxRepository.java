package com.example.BoxHospital.repository;

import com.example.BoxHospital.model.boxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface boxRepository extends JpaRepository<boxModel, Long> {
}