package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.CosmeticDTO;
import com.healthPharmacy.demo.services.CosmeticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products/cosmetics")
public class CosmeticController {
    CosmeticService cosmeticService;
    public CosmeticController(CosmeticService cosmeticService) {
        this.cosmeticService = cosmeticService;
    }
    @PostMapping
    public ResponseEntity<Void> registerCosmetic(CosmeticDTO cosmeticDTO) {
        cosmeticService.registerCosmetic(cosmeticDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
