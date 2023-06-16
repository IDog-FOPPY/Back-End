package com.idog.FOPPY.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(
        info = @Info(title = "FOPPY",
                description = "Foppy api명세",
                version = "ver 1.0.0"))
@RequiredArgsConstructor
@Configuration
class SwaggerConfig {

}