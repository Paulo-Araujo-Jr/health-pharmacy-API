package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.SupplementRequestDTO;
import com.healthPharmacy.demo.models.SupplementModel;
import com.healthPharmacy.demo.repository.SupplementRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplementService {
    SupplementRepository supplementRepository;
    public SupplementService(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }
    public void registerSupplement(SupplementRequestDTO supplementRequestDTO) {
        SupplementModel supplementModel = new SupplementModel();
        supplementModel.setName(supplementRequestDTO.name());
        supplementModel.setPrice(supplementRequestDTO.price());
        supplementModel.setStockQuantity(supplementRequestDTO.stockQuantity());
        supplementModel.setDescription(supplementRequestDTO.description());
        supplementModel.setCategory(supplementRequestDTO.category());
        supplementModel.setBrand(supplementRequestDTO.brand());
        supplementModel.setNutritionalValue(supplementRequestDTO.nutritionalValue());
        supplementModel.setRecommendedDosage(supplementRequestDTO.recommendedDosage());
        supplementModel.setUncategorized(false);
        supplementModel.setBarcode(supplementRequestDTO.barcode());
        supplementRepository.save(supplementModel);
    }

    public void updateSupplementAttribute(SupplementModel product, String attributeName, String attributeValue) {
        switch (attributeName) {
            case "nutritionalValue":
                product.setNutritionalValue(attributeValue);
                break;
            case "recommendedDosage":
                product.setRecommendedDosage(attributeValue);
                break;
            default:
                System.out.println("Invalid attribute name");
                break;
        }
    }
}
