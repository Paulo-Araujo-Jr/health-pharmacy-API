package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    Optional<CustomerModel> findByPersonModelId(Long id);

    Optional<CustomerModel> findByPersonModelEmail(String email);
}
