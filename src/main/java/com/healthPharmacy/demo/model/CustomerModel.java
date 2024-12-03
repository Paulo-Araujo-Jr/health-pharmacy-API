package com.healthPharmacy.demo.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class CustomerModel extends PersonModel implements Serializable{
    @Id
    private Long id;
    @Column(length = 3)
    private int age;
    @Column(length = 100)
    private String address;

}