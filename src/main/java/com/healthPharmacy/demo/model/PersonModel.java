package com.healthPharmacy.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class PersonModel {
    @Id
    @Column(length = 11)
    private String cpf;
    @Column(length = 100)
    private String name;
    @Column(length = 11)
    private String phone;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String password;
}