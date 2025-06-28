package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.MedicationRequestDTO;
import com.healthPharmacy.demo.infra.exception.NoExistentAttributeException;
import com.healthPharmacy.demo.models.MedicationModel;
import com.healthPharmacy.demo.repository.MedicationRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicationService {
    MedicationRepository medicationRepository;
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public void registerMedication(MedicationRequestDTO medicationRequestDTO) {
        MedicationModel medicationModel = new MedicationModel();
        medicationModel.setName(medicationRequestDTO.name());
        medicationModel.setPrice(medicationRequestDTO.price());
        medicationModel.setStockQuantity(medicationRequestDTO.stockQuantity());
        medicationModel.setDescription(medicationRequestDTO.description());
        medicationModel.setCategory(medicationRequestDTO.category());
        medicationModel.setBrand(medicationRequestDTO.brand());
        medicationModel.setPrescriptionRequired(medicationRequestDTO.prescriptionRequired());
        medicationModel.setDosage(medicationRequestDTO.dosage());
        medicationModel.setUncategorized(false);
        medicationRepository.save(medicationModel);
    }

    public void updateMedicationAttribute(MedicationModel product, String attributeName, String attributeValue) {
        switch (attributeName) {
            case "prescriptionRequired":
                product.setPrescriptionRequired(Boolean.parseBoolean(attributeValue));
                break;
            case "dosage":
                product.setDosage(attributeValue);
                break;
            default:
                try {
                    throw new NoExistentAttributeException("Unknown generic attribute '" + attributeName + "'");
                }catch (NoExistentAttributeException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
