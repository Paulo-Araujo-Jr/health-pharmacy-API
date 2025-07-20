package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.CartResponseDTO;
import com.healthPharmacy.demo.dto.ProductOrderRequestDTO;
import com.healthPharmacy.demo.enums.ProductSort;
import com.healthPharmacy.demo.services.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/buy-now")
    public ResponseEntity<Void> buyNow(@RequestBody ProductOrderRequestDTO request) {
        orderService.buyNow(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/cart/add")
    public ResponseEntity<Void> addToCart(@RequestBody ProductOrderRequestDTO request) {
        orderService.addToCart(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponseDTO> viewCart(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    ProductSort sort) {
        CartResponseDTO response = orderService.viewCart(page, size, sort);
        return ResponseEntity.ok(response);
    }

}
