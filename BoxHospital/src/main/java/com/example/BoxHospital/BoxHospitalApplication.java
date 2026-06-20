package com.example.BoxHospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; 

@SpringBootApplication
@EnableDiscoveryClient 
public class BoxHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoxHospitalApplication.class, args);
    }

}