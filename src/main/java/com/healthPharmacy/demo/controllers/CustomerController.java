package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.customer.CustomerRequestDTO;
import com.healthPharmacy.demo.dto.customer.CustomerResponseDTO;
import com.healthPharmacy.demo.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

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

    @GetMapping("/customers/{cpf}")
    public ResponseEntity<CustomerResponseDTO> findCustomerByCpf(@PathVariable String cpf){
        CustomerResponseDTO customerResponseDTO = customerService.getCustomerByCpf(cpf);
        return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
    }
}