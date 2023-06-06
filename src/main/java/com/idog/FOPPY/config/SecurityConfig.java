package com.idog.FOPPY.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("======================================");
        log.info(">> Run on Spring security");
        log.info("======================================");

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v1/save").permitAll()
                        .requestMatchers("/v1/member/save").permitAll()
//                        .anyRequest().authenticated()) // FIXME
                        .anyRequest().permitAll()) // FIXME
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
