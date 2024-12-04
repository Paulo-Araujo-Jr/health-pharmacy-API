package com.healthPharmacy.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SupplementDTO(
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        String category,
        String brand,
        String nutritionalValue,
        String recommendedDosage) {
}
