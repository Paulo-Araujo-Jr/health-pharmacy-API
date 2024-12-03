package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "customers")
@Data
public class CustomerModel extends PersonModel implements Serializable{
    @Id
    private Long id;
    @Column(length = 3)
    private Integer age;
    @Column(length = 100)
    private String address;

}