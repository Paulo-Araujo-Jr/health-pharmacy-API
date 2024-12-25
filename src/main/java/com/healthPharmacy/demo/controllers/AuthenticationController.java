package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.LoginDTO;
import com.healthPharmacy.demo.dto.LoginResponseDTO;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.services.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {

        return ResponseEntity.ok(tokenService.login(loginDTO));

    }
}
