package com.healthPharmacy.demo.models;

import com.healthPharmacy.demo.enums.UserRole;
import lombok.Data;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persons")
@Data
public class PersonModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(length = 100)
    private String name;

    @Column(length = 11)
    private String phoneNumber;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public PersonModel(String cpf, String name, String phoneNumber, String email, String password, UserRole userRole) {
        this.cpf = cpf;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.role = userRole;
    }

    public PersonModel() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.getRole() == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_EMPLOYEE")
            );
        } else if (this.getRole() == UserRole.CUSTOMER) {
            return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}