package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.EmployeeDTO;
import com.healthPharmacy.demo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    private EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    public ResponseEntity<Void> registerEmployee(EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
