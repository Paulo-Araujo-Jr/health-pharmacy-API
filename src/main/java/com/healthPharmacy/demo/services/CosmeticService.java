package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CosmeticRequestDTO;
import com.healthPharmacy.demo.infra.exception.NoExistentAttributeException;
import com.healthPharmacy.demo.models.CosmeticModel;
import com.healthPharmacy.demo.repository.CosmeticRepository;
import org.springframework.stereotype.Service;

@Service
public class CosmeticService {

    CosmeticRepository cosmeticRepository;
    public CosmeticService(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }
    public void registerCosmetic(CosmeticRequestDTO cosmeticRequestDTO) {
        CosmeticModel cosmeticModel = new CosmeticModel();
        cosmeticModel.setSkinType(cosmeticRequestDTO.skinType());
        cosmeticModel.setColor(cosmeticRequestDTO.color());
        cosmeticModel.setFragrance(cosmeticRequestDTO.fragrance());
        cosmeticModel.setName(cosmeticRequestDTO.name());
        cosmeticModel.setPrice(cosmeticRequestDTO.price());
        cosmeticModel.setStockQuantity(cosmeticRequestDTO.stockQuantity());
        cosmeticModel.setDescription(cosmeticRequestDTO.description());
        cosmeticModel.setCategory(cosmeticRequestDTO.category());
        cosmeticModel.setBrand(cosmeticRequestDTO.brand());
        cosmeticModel.setUncategorized(false);
        cosmeticRepository.save(cosmeticModel);
    }

    public void updateCosmeticAttribute(CosmeticModel product, String attributeName, String attributeValue) {
        switch (attributeName) {
            case "skinType":
                product.setSkinType(attributeValue);
                break;
            case "color":
                product.setColor(attributeValue);
                break;
            case "fragrance":
                product.setFragrance(attributeValue);
                break;
            default:
                try {
                    throw new NoExistentAttributeException("Unknown generic attribute '" + attributeName + "'");
                } catch (NoExistentAttributeException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
