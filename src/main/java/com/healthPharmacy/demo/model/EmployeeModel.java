package com.healthPharmacy.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity
public class EmployeeModel extends PersonModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column(length = 50)
    private String position;
    @Column(length = 11)
    private String cpf;
}