package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.MedicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<MedicationModel, Long> {
}
