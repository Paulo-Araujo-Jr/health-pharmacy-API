package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.*;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.services.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final CosmeticService cosmeticService;
    private final HygieneProductService hygieneProductService;
    private final MedicationService medicationService;
    private final SupplementService supplementService;

    public ProductController(ProductService productService,
                             CosmeticService cosmeticService,
                             HygieneProductService hygieneProductService,
                             MedicationService medicationService,
                             SupplementService supplementService) {
        this.productService = productService;
        this.cosmeticService = cosmeticService;
        this.hygieneProductService = hygieneProductService;
        this.medicationService = medicationService;
        this.supplementService = supplementService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            ProductSort sort) {
        Page<ProductDTO> products = productService.getAllProducts(page, size, sort);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/cosmetics")
    public ResponseEntity<Void> registerCosmetic(@RequestBody CosmeticRequestDTO cosmeticRequestDTO) {
        cosmeticService.registerCosmetic(cosmeticRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductDTO> getProductByBarcode(@PathVariable String barcode) {
        ProductDTO product = productService.getProductByBarcode(barcode);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/hygieneProducts")
    public ResponseEntity<Void> registerHygieneProduct(@RequestBody HygieneProductRequestDTO productDTO) {
        hygieneProductService.registerHygieneProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/medications")
    public ResponseEntity<Void> registerMedication(@RequestBody MedicationRequestDTO medicationRequestDTO) {
        medicationService.registerMedication(medicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/supplements")
    public ResponseEntity<Void> registerSupplement(@RequestBody SupplementRequestDTO supplementRequestDTO) {
        supplementService.registerSupplement(supplementRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{barcode}")
    public ResponseEntity<Void> updateAtribute(@PathVariable String barcode, @RequestParam String attributeName, @RequestParam String attributeValue) {
        productService.updateProductAttribute(barcode, attributeName, attributeValue);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{barcode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String barcode) {
        productService.deleteProductByBarcode(barcode);
        return ResponseEntity.noContent().build();
    }

}