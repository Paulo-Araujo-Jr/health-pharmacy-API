package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.HygieneProductRequestDTO;
import com.healthPharmacy.demo.models.HygieneProductModel;
import com.healthPharmacy.demo.repository.HygieneProductRepository;
import org.springframework.stereotype.Service;

@Service
public class HygieneProductService {
    HygieneProductRepository hygieneProductRepository;
    public HygieneProductService(HygieneProductRepository hygieneProductRepository) {
        this.hygieneProductRepository = hygieneProductRepository;
    }
    public void registerHygieneProduct(HygieneProductRequestDTO hygieneProductRequestDTO) {
        HygieneProductModel hygieneProductModel = new HygieneProductModel();
        hygieneProductModel.setUsage(hygieneProductRequestDTO.usage());
        hygieneProductModel.setFragrance(hygieneProductRequestDTO.fragrance());
        hygieneProductModel.setName(hygieneProductRequestDTO.name());
        hygieneProductModel.setPrice(hygieneProductRequestDTO.price());
        hygieneProductModel.setStockQuantity(hygieneProductRequestDTO.stockQuantity());
        hygieneProductModel.setDescription(hygieneProductRequestDTO.description());
        hygieneProductModel.setCategory(hygieneProductRequestDTO.category());
        hygieneProductModel.setBrand(hygieneProductRequestDTO.brand());
        hygieneProductModel.setUncategorized(false);
        hygieneProductRepository.save(hygieneProductModel);
    }

    public void updateHygieneProductAttribute(HygieneProductModel product, String attributeName, String attributeValue) {
        switch (attributeName) {
            case "usage":
                product.setFragrance(attributeName);
                break;
            case  "fragrance":
                product.setFragrance(attributeValue);
                break;
            default:
                System.out.println("Invalid attribute name");
        }
    }
}
