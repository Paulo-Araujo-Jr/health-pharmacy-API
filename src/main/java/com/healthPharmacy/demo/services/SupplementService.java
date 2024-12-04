package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.SupplementDTO;
import com.healthPharmacy.demo.models.SupplementModel;
import com.healthPharmacy.demo.repository.SupplementRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplementService {
    SupplementRepository supplementRepository;
    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }
    public void registerSupplement(SupplementDTO supplementDTO) {
        SupplementModel supplementModel = new SupplementModel();
        supplementModel.setName(supplementDTO.name());
        supplementModel.setPrice(supplementDTO.price());
        supplementModel.setStockQuantity(supplementDTO.stockQuantity());
        supplementModel.setDescription(supplementDTO.description());
        supplementModel.setCategory(supplementDTO.category());
        supplementModel.setBrand(supplementDTO.brand());
        supplementModel.setNutritionalValue(supplementDTO.nutritionalValue());
        supplementModel.setRecommendedDosage(supplementDTO.recommendedDosage());
        supplementRepository.save(supplementModel);
    }
}
