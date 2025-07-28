package com.healthPharmacy.demo.dto.responses;

public record ErrorResponse(
        String error,
        String message
){
}
