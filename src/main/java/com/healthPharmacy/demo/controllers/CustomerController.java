package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}