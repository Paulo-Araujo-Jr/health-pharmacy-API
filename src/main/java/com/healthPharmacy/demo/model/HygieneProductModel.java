package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HygieneProductModel extends ProductModel {

    @Column(length = 50)
    private String usage;

    @Column(length = 50)
    private String fragrance;
}