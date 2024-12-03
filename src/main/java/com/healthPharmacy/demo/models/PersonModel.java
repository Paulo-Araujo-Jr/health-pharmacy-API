package com.healthPharmacy.demo.models;

import com.healthPharmacy.demo.models.enums.Role;
import lombok.Data;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class PersonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 11)
    private String cpf;
    @Column(length = 100)
    private String name;
    @Column(length = 11)
    private String phoneNumber;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}