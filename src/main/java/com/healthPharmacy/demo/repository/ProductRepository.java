package com.healthPharmacy.demo.repository;

import com.healthPharmacy.demo.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByBarcode(String barcode);
}
