package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.EmployeeDTO;
import com.healthPharmacy.demo.dto.customer.CustomerResponseDTO;
import com.healthPharmacy.demo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registerEmployee(@RequestBody EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CustomerResponseDTO> findAllByCpf(@PathVariable String cpf){
        CustomerResponseDTO customerResponseDTO = employeeService.getCustomerByCpf(cpf);
        return new ResponseEntity<>(customerResponseDTO, HttpStatus.OK);
    }
}
