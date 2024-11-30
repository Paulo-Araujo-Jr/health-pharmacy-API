package com.healthPharmacy.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class SupplementModel extends ProductModel {

    @Column(length = 50)
    private String nutritionalValue;

    @Column(length = 50)
    private String recommendedDosage;
}