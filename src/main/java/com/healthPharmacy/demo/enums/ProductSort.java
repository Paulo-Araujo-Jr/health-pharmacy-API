package com.healthPharmacy.demo.enums;

import com.healthPharmacy.demo.dto.CartItemDTO;

import java.util.Comparator;

public enum ProductSort {
    NAME_ASC,
    NAME_DESC,
    PRICE_ASC,
    PRICE_DESC;

    public Comparator<CartItemDTO> getComparator() {
        return switch (this) {
            case NAME_ASC -> Comparator.comparing(CartItemDTO::productName);
            case PRICE_ASC -> Comparator.comparing(CartItemDTO::price);
            case PRICE_DESC -> Comparator.comparing(CartItemDTO::price).reversed();
            case NAME_DESC ->  Comparator.comparing(CartItemDTO::productName).reversed();
        };
    }
}
