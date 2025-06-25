package com.healthPharmacy.demo.dto.customer;

public record CustomerRequestDTO(
    String cpf,
    String name,
    String phoneNumber,
    String email,
    String password,
    Integer age,
    String address)
{}
