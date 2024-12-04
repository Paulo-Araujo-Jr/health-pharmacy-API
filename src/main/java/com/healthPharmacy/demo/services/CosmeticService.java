package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.CosmeticDTO;
import com.healthPharmacy.demo.models.CosmeticModel;
import com.healthPharmacy.demo.repository.CosmeticRepository;
import org.springframework.stereotype.Service;

@Service
public class CosmeticService {

    CosmeticRepository cosmeticRepository;
    public CosmeticService(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }
    public void registerCosmetic(CosmeticDTO cosmeticDTO) {
        CosmeticModel cosmeticModel = new CosmeticModel();
        cosmeticModel.setSkinType(cosmeticDTO.skinType());
        cosmeticModel.setColor(cosmeticDTO.color());
        cosmeticModel.setFragrance(cosmeticDTO.fragrance());
        cosmeticModel.setName(cosmeticDTO.name());
        cosmeticModel.setPrice(cosmeticDTO.price());
        cosmeticModel.setStockQuantity(cosmeticDTO.stockQuantity());
        cosmeticModel.setDescription(cosmeticDTO.description());
        cosmeticModel.setCategory(cosmeticDTO.category());
        cosmeticModel.setBrand(cosmeticDTO.brand());
        cosmeticRepository.save(cosmeticModel);
    }
}
