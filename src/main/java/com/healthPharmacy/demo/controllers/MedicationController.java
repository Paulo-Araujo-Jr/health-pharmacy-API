package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.MedicationDTO;
import com.healthPharmacy.demo.services.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products/medications")
public class MedicationController {
    MedicationService medicationService;
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }
    @PostMapping
    public ResponseEntity<Void> registerMedication(MedicationDTO medicationDTO) {
        medicationService.registerMedication(medicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
