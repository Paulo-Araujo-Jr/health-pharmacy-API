package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "supplements")
public class SupplementModel extends ProductModel {

    @Id
    private Long id;

    @Column(length = 50)
    private String nutritionalValue;

    @Column(length = 50)
    private String recommendedDosage;

}