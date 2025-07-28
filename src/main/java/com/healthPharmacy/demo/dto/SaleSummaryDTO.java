package com.healthPharmacy.demo.dto;

import com.healthPharmacy.demo.models.OrderModel;

import java.math.BigDecimal;
import java.util.List;

public record SaleSummaryDTO(
        String customerName,
        String cpf,
        List<SaleItemDTO> items,
        BigDecimal totalValue
) {
    public static SaleSummaryDTO fromEntity(OrderModel order) {
        List<SaleItemDTO> saleItems = order.getItems().stream()
                .map(item -> new SaleItemDTO(item.getProduct().getName(), item.getProduct().getBarcode()))
                .toList();

        return new SaleSummaryDTO(
                order.getCustomer().getPersonModel().getName(),
                order.getCustomer().getPersonModel().getCpf(),
                saleItems,
                order.getTotalValue()
        );
    }
}
