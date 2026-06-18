package com.example.AmbulanciaHospital.repository;

import com.example.AmbulanciaHospital.model.ambulanciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ambulanciaRepository extends JpaRepository<ambulanciaModel, Long> {

}
    
