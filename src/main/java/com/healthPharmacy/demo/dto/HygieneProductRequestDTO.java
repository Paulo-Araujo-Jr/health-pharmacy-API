package com.healthPharmacy.demo.dto;

import java.math.BigDecimal;

public record HygieneProductRequestDTO(
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        String category,
        String brand,
        String usage,
        String fragrance,
        String barcode
){
}
