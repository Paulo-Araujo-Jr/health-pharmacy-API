package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.CosmeticModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosmeticRepository extends JpaRepository<CosmeticModel, Long> {
}
