package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hygienes")
public class HygieneProductModel extends ProductModel {

    @Id
    private Long id;
    @Column(nullable = false)
    private String usage;
    @Column(nullable = false)
    private String fragrance;
}