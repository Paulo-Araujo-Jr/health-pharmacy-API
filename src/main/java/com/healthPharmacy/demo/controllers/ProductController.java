package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.ProductDTO;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, ProductSort sort){
        Page<ProductDTO> products = productService.getAllProducts(page, size, sort);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
