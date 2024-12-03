package com.healthPharmacy.demo.dto;

public record CustomerDTO (
    String cpf,
    String name,
    String phoneNumber,
    String email,
    String password,
    Integer age,
    String address)
    {

    }
