package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.HygieneProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HygieneProductRepository extends JpaRepository<HygieneProductModel, Long> {
}
