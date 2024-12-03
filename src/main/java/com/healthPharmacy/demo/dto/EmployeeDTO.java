package com.healthPharmacy.demo.dto;

public record EmployeeDTO(
        String cpf,
        String name,
        String phoneNumber,
        String email,
        String password,
        String responsibility
){
}
