package com.healthPharmacy.demo.dto.employee;

public record EmployeeRequestDTO(
        String cpf,
        String name,
        String phoneNumber,
        String email,
        String password,
        String responsibility
){
}
