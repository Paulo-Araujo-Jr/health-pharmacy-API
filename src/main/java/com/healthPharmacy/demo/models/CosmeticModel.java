package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cosmetics")
public class CosmeticModel extends ProductModel {

    @Id
    private Long id;
    @Column(nullable = false)
    private String skinType;
    @Column(nullable = false)
    private String color;
    @Column (nullable = false)
    private String fragrance;
}
