package com.healthPharmacy.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.healthPharmacy.demo.dto.LoginDTO;
import com.healthPharmacy.demo.dto.LoginResponseDTO;
import com.healthPharmacy.demo.models.PersonModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private AuthenticationManager authenticationManager;
    TokenService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(PersonModel user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("api-v1-auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("api-v1-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid token", exception);
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    public LoginResponseDTO login(LoginDTO loginDTO){
        try {
            var userPassword = new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
            var auth = this.authenticationManager.authenticate(userPassword);
            var token = generateToken((PersonModel) auth.getPrincipal());
            return new LoginResponseDTO(token);
        } catch (Exception e) {
            throw new RuntimeException("Error while logging in", e.getCause());
        }
    }

}