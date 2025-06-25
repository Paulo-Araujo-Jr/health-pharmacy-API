package com.healthPharmacy.demo.dto.employee;

public record EmployeeResponseDTO (
        String cpf,
        String name,
        String phoneNumber,
        String email,
        String responsibility,
        boolean active
){
}
