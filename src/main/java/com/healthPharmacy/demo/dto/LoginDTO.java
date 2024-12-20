package com.healthPharmacy.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO (
    @NotNull @NotBlank
    String email,
    @NotNull @NotBlank
    String password)
{}
