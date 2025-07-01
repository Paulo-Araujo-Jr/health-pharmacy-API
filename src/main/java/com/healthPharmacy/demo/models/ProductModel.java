package com.healthPharmacy.demo.models;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(length = 255)
    private String description;

    @Column(length = 50)
    private String category;

    @Column(length = 50)
    private String brand;

    @Column(length = 50, unique = true)
    private String barcode;

    public ProductModel(String name, BigDecimal price, Integer stockQuantity, String description, String category, String brand, String barcode) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.barcode = barcode;
    }

    public ProductModel() {

    }
}