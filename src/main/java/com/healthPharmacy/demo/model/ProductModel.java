package com.healthPharmacy.demo.model;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class ProductModel {
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

    @Column(nullable = false)
    private LocalDate expirationDate;

}