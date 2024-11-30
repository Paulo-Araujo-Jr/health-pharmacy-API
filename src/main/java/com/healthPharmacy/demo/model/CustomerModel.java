package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CustomerModel extends PersonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3)
    private int age;
    @Column(length = 100)
    private String address;
}