package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
