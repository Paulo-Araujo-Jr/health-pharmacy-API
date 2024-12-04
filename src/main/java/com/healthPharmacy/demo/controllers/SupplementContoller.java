package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.SupplementDTO;
import com.healthPharmacy.demo.services.SupplementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products/supplements")
public class SupplementContoller {
    SupplementService supplementService;
    public SupplementContoller(SupplementService supplementService) {
        this.supplementService = supplementService;
    }

    @PostMapping
    public ResponseEntity<Void> registerSupplement(SupplementDTO supplementDTO) {
        supplementService.registerSupplement(supplementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
