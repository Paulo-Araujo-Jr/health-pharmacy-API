package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MedicationModel extends ProductModel {

    // Specific attributes for medications
    @Column(length = 50)
    private String prescriptionRequired;

    @Column(length = 50)
    private String dosage;
}