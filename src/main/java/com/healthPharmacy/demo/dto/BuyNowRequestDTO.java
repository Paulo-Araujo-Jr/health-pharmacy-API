package com.healthPharmacy.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record BuyNowRequestDTO(
        @NotNull
        @JsonProperty("product_barcode")
        String productBarcode,
        Integer quantity
) {}
