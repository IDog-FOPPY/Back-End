package com.idog.FOPPY.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://foppy.netlify.app", "http://localhost:8080", "http://localhost:3000", "http://localhost:3001")
                .allowCredentials(true)
                .allowedHeaders("Accept", "Content-Type", "Origin", "Authorization", "X-Auth-Token")
                .exposedHeaders("X-Auth-Token", "Authorization")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","));
    }
}
