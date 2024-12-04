package com.healthPharmacy.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CosmeticDTO(
        String name,
        BigDecimal price,
        Integer stockQuantity,
        String description,
        String category,
        String brand,
        String skinType,
        String color,
        String fragrance) {
}
