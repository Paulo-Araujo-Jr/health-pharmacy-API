package com.healthPharmacy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.healthPharmacy.demo")
public class HealthPharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthPharmacyApplication.class, args);
	}

}
