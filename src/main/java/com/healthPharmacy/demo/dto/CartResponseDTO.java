package com.healthPharmacy.demo.dto;

import com.healthPharmacy.demo.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        List<CartItemDTO> items,
        BigDecimal totalValue,
        OrderStatus status
) {}

