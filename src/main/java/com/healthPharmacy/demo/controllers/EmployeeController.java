package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.employee.EmployeeRequestDTO;
import com.healthPharmacy.demo.dto.employee.EmployeeResponseDTO;
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
    public ResponseEntity<Void> registerEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO){
        employeeService.registerEmployee(employeeRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String cpf) {
        employeeService.deleteEmployee(cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<EmployeeResponseDTO> findEmployeeByCpf(@PathVariable String cpf){
        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeByCpf(cpf);
        return new ResponseEntity<>(employeeResponseDTO, HttpStatus.OK);
    }
}
