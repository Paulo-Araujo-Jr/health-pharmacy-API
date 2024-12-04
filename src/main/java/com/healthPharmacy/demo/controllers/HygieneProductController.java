package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.HygieneProductDTO;
import com.healthPharmacy.demo.services.HygieneProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/hygieneProducts")
public class HygieneProductController {

    private final HygieneProductService hygieneProductService;

    public HygieneProductController(HygieneProductService hygieneProductService) {
        this.hygieneProductService = hygieneProductService;
    }

    @PostMapping
    public ResponseEntity<Void> registerHygieneProduct(HygieneProductDTO productDTO) {
        hygieneProductService.registerHygieneProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
