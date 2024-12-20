package com.healthPharmacy.demo.controllers;

import com.healthPharmacy.demo.dto.LoginDTO;
import com.healthPharmacy.demo.dto.LoginResponseDTO;
import com.healthPharmacy.demo.models.PersonModel;
import com.healthPharmacy.demo.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {


    private AuthenticationManager authenticationManager;

    private TokenService tokenService;

    AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {

        return ResponseEntity.ok(tokenService.login(loginDTO));

    }
}
