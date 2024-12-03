package com.healthPharmacy.demo.models;

import jakarta.persistence.Id;
import lombok.Data;

import jakarta.persistence.*;


@Entity
@Data
@Table(name = "employees")
public class EmployeeModel extends PersonModel{

    @Id
    private Long id;
    @Column(length = 50)
    private String responsibility;
}