package com.healthPharmacy.demo.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "customers")
@Data
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 3)
    private Integer age;

    @Column(length = 100)
    private String address;

    @OneToOne
    @JoinColumn(name = "person_model_id", referencedColumnName = "id", nullable = false)
    private PersonModel personModel;

}