package com.healthPharmacy.demo.model;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class SupplementModel extends ProductModel {

    @Id
    private Long id;

    @Column(length = 50)
    private String nutritionalValue;

    @Column(length = 50)
    private String recommendedDosage;

}