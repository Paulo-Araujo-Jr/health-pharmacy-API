package com.healthPharmacy.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ProductOrderRequestDTO(
        @NotNull
        @JsonProperty("product_barcode")
        String productBarcode,
        Integer quantity
) {
        public int getSafeQuantity() {
                return (quantity == null || quantity <= 0) ? 1 : quantity;
        }
}
