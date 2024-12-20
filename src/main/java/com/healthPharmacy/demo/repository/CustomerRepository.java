package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    CustomerModel findByEmail(String email);
    CustomerModel findByCpf(String cpf);
}
