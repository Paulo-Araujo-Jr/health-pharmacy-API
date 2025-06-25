package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.customer.CustomerRequestDTO;
import com.healthPharmacy.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        customerService.registerCustomer(customerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}