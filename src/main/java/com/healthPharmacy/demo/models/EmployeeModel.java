package com.healthPharmacy.demo.models;

import com.healthPharmacy.demo.enums.UserRole;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class EmployeeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private String responsibility;

    @OneToOne
    @JoinColumn(name = "person_model_id", referencedColumnName = "id", nullable = false)
    private PersonModel personModel;

}
