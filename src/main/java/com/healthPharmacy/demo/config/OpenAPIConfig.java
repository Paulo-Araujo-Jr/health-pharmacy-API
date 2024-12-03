package com.healthPharmacy.demo.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API de Farmácia", version = "v1", description = "Documentação da API de Farmácia"))
public class OpenAPIConfig {
}
