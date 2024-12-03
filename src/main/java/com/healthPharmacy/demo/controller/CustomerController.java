package com.healthPharmacy.demo.controller;

import com.healthPharmacy.demo.dto.CustomerDTO;
import com.healthPharmacy.demo.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class CustomerController {

    private CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}