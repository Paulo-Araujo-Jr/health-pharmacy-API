package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.SupplementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplementRepository extends JpaRepository<SupplementModel, Long> {
}
