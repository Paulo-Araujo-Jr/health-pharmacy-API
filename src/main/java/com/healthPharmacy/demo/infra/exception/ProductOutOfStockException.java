package com.healthPharmacy.demo.infra.exception;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String productOutOfStock) {
        super(productOutOfStock);
    }
}
