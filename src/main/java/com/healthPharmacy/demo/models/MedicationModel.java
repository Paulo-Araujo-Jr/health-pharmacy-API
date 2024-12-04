package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medications")
public class MedicationModel extends ProductModel {

    @Id
    private Long id;
    @Column(nullable = false)
    private Boolean prescriptionRequired;
    @Column(nullable = false)
    private String dosage;
}