package com.healthPharmacy.demo.dto.customer;

public record CustomerResponseDTO (
        String cpf,
        String name,
        String email,
        String phoneNumber,
        Integer age,
        String address,
        Boolean active
)
{}