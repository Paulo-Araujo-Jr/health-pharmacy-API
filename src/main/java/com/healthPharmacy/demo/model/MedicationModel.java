package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "medications")
public class MedicationModel extends ProductModel {

    @Id
    private Long id;
    @Column(nullable = false)
    private String prescriptionRequired;
    @Column(nullable = false)
    private String dosage;
}