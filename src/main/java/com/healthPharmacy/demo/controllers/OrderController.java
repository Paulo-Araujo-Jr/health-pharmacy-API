package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.ProductOrderRequestDTO;
import com.healthPharmacy.demo.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
