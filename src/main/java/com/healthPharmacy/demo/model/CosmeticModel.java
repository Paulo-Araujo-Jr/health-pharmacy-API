package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CosmeticModel extends ProductModel {

    @Column(length = 50)
    private String skinType;

    @Column(length = 50)
    private String color;

    @Column(length = 100)
    private String fragrance;
}
