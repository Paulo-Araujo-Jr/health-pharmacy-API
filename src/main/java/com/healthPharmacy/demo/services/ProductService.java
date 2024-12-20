package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.ProductDTO;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.models.*;
import com.healthPharmacy.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final SupplementRepository supplementRepository;
    private final MedicationRepository medicationRepository;
    private final HygieneProductRepository hygieneProductRepository;
    private final CosmeticRepository cosmeticRepository;

    @Autowired
    public ProductService(SupplementRepository supplementRepository,
                          MedicationRepository medicationRepository,
                          HygieneProductRepository hygieneProductRepository,
                          CosmeticRepository cosmeticRepository) {
        this.supplementRepository = supplementRepository;
        this.medicationRepository = medicationRepository;
        this.hygieneProductRepository = hygieneProductRepository;
        this.cosmeticRepository = cosmeticRepository;
    }

    public Page<ProductDTO> getAllProducts(int page, int size, ProductSort sort) {
        if (sort == null ) sort = ProductSort.NAME_ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(getSortDirection(sort), "name"));

        Page<SupplementModel> supplements = supplementRepository.findAll(pageable);
        Page<MedicationModel> medications = medicationRepository.findAll(pageable);
        Page<HygieneProductModel> hygieneProducts = hygieneProductRepository.findAll(pageable);
        Page<CosmeticModel> cosmetics = cosmeticRepository.findAll(pageable);

        List<ProductDTO> productDTOs = new ArrayList<>();
        productDTOs.addAll(supplements.getContent().stream()
                .map(supplement -> new ProductDTO(supplement.getName(), supplement.getPrice(), supplement.getStockQuantity(),
                        supplement.getDescription(), supplement.getCategory(), supplement.getBrand()))
                .collect(Collectors.toList()));

        productDTOs.addAll(medications.getContent().stream()
                .map(medication -> new ProductDTO(medication.getName(), medication.getPrice(), medication.getStockQuantity(),
                        medication.getDescription(), medication.getCategory(), medication.getBrand()))
                .collect(Collectors.toList()));

        productDTOs.addAll(hygieneProducts.getContent().stream()
                .map(hygiene -> new ProductDTO(hygiene.getName(), hygiene.getPrice(), hygiene.getStockQuantity(),
                        hygiene.getDescription(), hygiene.getCategory(), hygiene.getBrand()))
                .collect(Collectors.toList()));

        productDTOs.addAll(cosmetics.getContent().stream()
                .map(cosmetic -> new ProductDTO(cosmetic.getName(), cosmetic.getPrice(), cosmetic.getStockQuantity(),
                        cosmetic.getDescription(), cosmetic.getCategory(), cosmetic.getBrand()))
                .collect(Collectors.toList()));

        int start = Math.min((int) pageable.getOffset(), productDTOs.size());
        int end = Math.min((start + size), productDTOs.size());
        List<ProductDTO> paginatedProductDTOs = productDTOs.subList(start, end);

        return new PageImpl<>(paginatedProductDTOs, pageable, productDTOs.size());

    }

    private Sort.Direction getSortDirection(ProductSort sort) {
        switch (sort) {
            case NAME_ASC:
                return Sort.Direction.ASC;
            case NAME_DESC:
                return Sort.Direction.DESC;
            case PRICE_ASC:
                return Sort.Direction.ASC;
            case PRICE_DESC:
                return Sort.Direction.DESC;
            default:
                return Sort.Direction.ASC;
        }
    }

}
