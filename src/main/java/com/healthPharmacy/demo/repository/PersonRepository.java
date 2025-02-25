package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<PersonModel, Long> {
    UserDetails findByEmail(String email);
}
