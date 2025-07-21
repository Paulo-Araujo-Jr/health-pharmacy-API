package com.healthPharmacy.demo.dto;

import com.healthPharmacy.demo.models.OrderModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderSummaryDTO(
        Long orderId,
        LocalDateTime orderDate,
        BigDecimal totalValue,
        List<CartItemDTO> items
) {
    public static OrderSummaryDTO fromEntity(OrderModel order) {
        List<CartItemDTO> itemDTOs = order.getItems().stream()
                .map(CartItemDTO::fromEntity)
                .toList();
        return new OrderSummaryDTO(order.getId(), order.getOrderDate(), order.getTotalValue(), itemDTOs);
    }
}
