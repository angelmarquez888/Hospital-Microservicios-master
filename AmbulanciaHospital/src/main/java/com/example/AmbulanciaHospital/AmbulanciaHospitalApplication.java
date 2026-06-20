package com.example.AmbulanciaHospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class AmbulanciaHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmbulanciaHospitalApplication.class, args);
	}

}
