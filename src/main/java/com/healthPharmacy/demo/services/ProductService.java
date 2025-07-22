package com.healthPharmacy.demo.services;

import com.healthPharmacy.demo.dto.ProductDTO;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.infra.exception.NoExistentAttributeException;
import com.healthPharmacy.demo.infra.exception.ProductNotFoundException;
import com.healthPharmacy.demo.infra.exception.ProductOutOfStockException;
import com.healthPharmacy.demo.models.*;
import com.healthPharmacy.demo.repository.*;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final SupplementRepository supplementRepository;
    private final MedicationRepository medicationRepository;
    private final HygieneProductRepository hygieneProductRepository;
    private final CosmeticRepository cosmeticRepository;
    private final ProductRepository productRepository;

    private final CosmeticService cosmeticService;
    private final HygieneProductService hygieneProductService;
    private final MedicationService medicationService;
    private final SupplementService supplementService;

    public ProductService(SupplementRepository supplementRepository,
                          MedicationRepository medicationRepository,
                          HygieneProductRepository hygieneProductRepository,
                          CosmeticRepository cosmeticRepository,
                          ProductRepository productRepository,
                          CosmeticService cosmeticService,
                          HygieneProductService hygieneProductService,
                          MedicationService medicationService,
                          SupplementService supplementService) {
        this.supplementRepository = supplementRepository;
        this.medicationRepository = medicationRepository;
        this.hygieneProductRepository = hygieneProductRepository;
        this.cosmeticRepository = cosmeticRepository;
        this.productRepository = productRepository;
        this.cosmeticService = cosmeticService;
        this.hygieneProductService = hygieneProductService;
        this.medicationService = medicationService;
        this.supplementService = supplementService;
    }

    public Page<ProductDTO> getAllProducts(int page, int size, ProductSort sort) {
        if (sort == null ) sort = ProductSort.NAME_ASC;

        List<ProductDTO> allProductDTOs = new ArrayList<>();

        cosmeticRepository.findAll().forEach(cosmetic -> allProductDTOs.add(mapToProductDTO(cosmetic)));
        hygieneProductRepository.findAll().forEach(hygiene -> allProductDTOs.add(mapToProductDTO(hygiene)));
        medicationRepository.findAll().forEach(medication -> allProductDTOs.add(mapToProductDTO(medication)));
        supplementRepository.findAll().forEach(supplement -> allProductDTOs.add(mapToProductDTO(supplement)));

        Comparator<ProductDTO> comparator;
        switch (sort) {
            case NAME_DESC:
                comparator = Comparator.comparing(ProductDTO::name).reversed();
                break;
            case PRICE_ASC:
                comparator = Comparator.comparing(ProductDTO::price);
                break;
            case PRICE_DESC:
                comparator = Comparator.comparing(ProductDTO::price).reversed();
                break;
            default:
                comparator = Comparator.comparing(ProductDTO::name);
                break;
        }
        allProductDTOs.sort(comparator);

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allProductDTOs.size());

        if (start > allProductDTOs.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, allProductDTOs.size());
        }

        List<ProductDTO> paginatedProductDTOs = allProductDTOs.subList(start, end);

        return new PageImpl<>(paginatedProductDTOs, pageable, allProductDTOs.size());
    }

    public ProductDTO getProductByBarcode(String barcode) {
        ProductModel product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with barcode: " + barcode));
        return mapToProductDTO(product);

    }
    public ProductModel findProductModelByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with barcode: " + barcode));
    }


    public void updateProductAttribute(String barcode, String attributeName, String attributeValue) {
        ProductModel product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with barcode: " + barcode));

        if (product instanceof CosmeticModel) {
            cosmeticService.updateCosmeticAttribute((CosmeticModel) product, attributeName, attributeValue);
        } else if (product instanceof HygieneProductModel) {
            hygieneProductService.updateHygieneProductAttribute((HygieneProductModel) product, attributeName, attributeValue);
        } else if (product instanceof MedicationModel) {
            medicationService.updateMedicationAttribute((MedicationModel) product, attributeName, attributeValue);
        } else if (product instanceof SupplementModel) {
            supplementService.updateSupplementAttribute((SupplementModel) product, attributeName, attributeValue);
        }
        switch (attributeName) {
            case "name":
                product.setName(attributeValue);
                break;
            case "price":
                product.setPrice(new BigDecimal(attributeValue));
                break;
            case "stockQuantity":
                product.setStockQuantity(Integer.parseInt(attributeValue));
                break;
            case "description":
                product.setDescription(attributeValue);
                break;
            case "category":
                product.setCategory(attributeValue);
                break;
            case "brand":
                product.setBrand(attributeValue);
                break;
            case "barcode":
                product.setBarcode(attributeValue);
                break;
            default:
                try {
                    throw new NoExistentAttributeException("Unknown generic attribute '" + attributeName + "'");
                } catch (NoExistentAttributeException e) {
                    throw new RuntimeException(e);
                }
        }
        productRepository.save(product);
    }

    public void haveInStock(String barcode, int quantity) {
        Optional<ProductModel> productModel = productRepository.findByBarcode(barcode);
        if (productModel.get().getStockQuantity() <= 0)
            throw new ProductOutOfStockException("Product out of stock");
        else if (quantity > productModel.get().getStockQuantity())
            throw new ProductOutOfStockException("Quantity in stock exceeded. Quantity ordered: " + quantity + "Quantity in stock: " +  productModel.get().getStockQuantity());
    }

    public void productPurchased(String barcode, int quantity) {
        Optional<ProductModel> productModel = productRepository.findByBarcode(barcode);
        productModel.get().setStockQuantity(productModel.get().getStockQuantity() - quantity);
        productRepository.save(productModel.get());
    }

    private ProductDTO mapToProductDTO(ProductModel productModel) {
        ProductDTO dto = new ProductDTO(
                productModel.getName(),
                productModel.getPrice(),
                productModel.getStockQuantity(),
                productModel.getDescription(),
                productModel.getCategory(),
                productModel.getBrand(),
                productModel.getBarcode()
        );
        return dto;
    }

    @Transactional
    public void deleteProductByBarcode(String barcode) {
        ProductModel product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with barcode: " + barcode));

        if (product instanceof CosmeticModel cosmetic) {
            cosmeticRepository.delete(cosmetic);
        } else if (product instanceof HygieneProductModel hygieneProduct) {
            hygieneProductRepository.delete(hygieneProduct);
        } else if (product instanceof MedicationModel medication) {
            medicationRepository.delete(medication);
        } else if (product instanceof SupplementModel supplement) {
            supplementRepository.delete(supplement);
        } else {
            productRepository.delete(product);
        }
    }

}