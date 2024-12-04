package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.MedicationDTO;
import com.healthPharmacy.demo.models.MedicationModel;
import com.healthPharmacy.demo.repository.MedicationRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {
    MedicationRepository medicationRepository;
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public void registerMedication(MedicationDTO medicationDTO) {
        MedicationModel medicationModel = new MedicationModel();
        medicationModel.setName(medicationDTO.name());
        medicationModel.setPrice(medicationDTO.price());
        medicationModel.setStockQuantity(medicationDTO.stockQuantity());
        medicationModel.setDescription(medicationDTO.description());
        medicationModel.setCategory(medicationDTO.category());
        medicationModel.setBrand(medicationDTO.brand());
        medicationModel.setPrescriptionRequired(medicationDTO.prescriptionRequired());
        medicationModel.setDosage(medicationDTO.dosage());
        medicationRepository.save(medicationModel);
    }
}
