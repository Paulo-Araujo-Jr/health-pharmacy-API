package com.healthPharmacy.demo.models;


import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.*;


@Entity
@Table(name = "employees")
@Data
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String responsibility;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_model_id", referencedColumnName = "id", nullable = false)
    private PersonModel personModel;

}
