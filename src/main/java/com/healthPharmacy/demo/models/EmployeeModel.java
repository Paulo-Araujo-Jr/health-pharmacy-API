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
public class EmployeeModel extends PersonModel {

    @Column(length = 50)
    private String responsibility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PersonModel person;

}
