package com.healthPharmacy.demo.dto;

import com.healthPharmacy.demo.models.CartItemModel;

import java.math.BigDecimal;

public record CartItemDTO(
        String productName,
        String productBarcode,
        int quantity,
        BigDecimal price
) {
    public static CartItemDTO fromEntity(CartItemModel item) {
        return new CartItemDTO(
                item.getProduct().getName(),
                item.getProduct().getBarcode(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}

