package com.healthPharmacy.demo.dto;

import java.math.BigDecimal;

public record SupplementRequestDTO(
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        String category,
        String brand,
        String nutritionalValue,
        String recommendedDosage,
        String barcode
) {
}
