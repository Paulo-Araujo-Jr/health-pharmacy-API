package com.healthPharmacy.demo.dto;

import java.math.BigDecimal;

public record MedicationRequestDTO(
    String name,
    BigDecimal price,
    Integer stockQuantity,
    String description,
    String category,
    String brand,
    Boolean prescriptionRequired,
    String dosage,
    String barcode
){}
