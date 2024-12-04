package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.HygieneProductDTO;
import com.healthPharmacy.demo.models.HygieneProductModel;
import com.healthPharmacy.demo.repository.HygieneProductRepository;
import org.springframework.stereotype.Service;

@Service
public class HygieneProductService {
    HygieneProductRepository hygieneProductRepository;
    public HygieneProductService(HygieneProductRepository hygieneProductRepository) {
        this.hygieneProductRepository = hygieneProductRepository;
    }
    public void registerHygieneProduct(HygieneProductDTO hygieneProductDTO) {
        HygieneProductModel hygieneProductModel = new HygieneProductModel();
        hygieneProductModel.setUsage(hygieneProductDTO.usage());
        hygieneProductModel.setFragrance(hygieneProductDTO.fragrance());
        hygieneProductModel.setName(hygieneProductDTO.name());
        hygieneProductModel.setPrice(hygieneProductDTO.price());
        hygieneProductModel.setStockQuantity(hygieneProductDTO.stockQuantity());
        hygieneProductModel.setDescription(hygieneProductDTO.description());
        hygieneProductModel.setCategory(hygieneProductDTO.category());
        hygieneProductModel.setBrand(hygieneProductDTO.brand());
        hygieneProductRepository.save(hygieneProductModel);
    }
}
