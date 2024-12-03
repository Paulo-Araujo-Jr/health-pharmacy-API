package com.healthPharmacy.demo.model;

import jakarta.persistence.Id;
import lombok.Data;

import jakarta.persistence.*;


@Entity
@Data
public class EmployeeModel extends PersonModel{

    @Id
    private Long id;
    @Column(length = 50)
    private String responsibility;

}