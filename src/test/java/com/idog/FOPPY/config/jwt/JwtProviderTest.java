package com.idog.FOPPY.config.jwt;

import com.idog.FOPPY.domain.User;
import com.idog.FOPPY.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.jwt.access-secret}")
    private String accessSecretKey;
    @Value("${spring.jwt.refresh-secret}")
    private String refreshSecretKey;

    private Long accessExpireTimeMs = 60 * 60 * 1000L;  // 1시간
    private Long refreshExpireTimeMs = 14 * 24 * 60 * 60 * 1000L;  // 14일

    @DisplayName("createAccessToken()")
    @Test
    void createAccessToken() {
        // given
        User testUser = User.builder()
                .email("test@gmail.com")
                .password(passwordEncoder.encode("test"))
                .build();

        // when
        String token = jwtProvider.createAccessToken(testUser, accessExpireTimeMs);

        // then
        String email = jwtProvider.getUsername(token);
        assertThat(email).isEqualTo(testUser.getEmail());
    }

    @DisplayName("validateToken()")
    @Test
    void validateToken() {
        // given
        Claims claims = Jwts.claims();
        claims.put("username", "test@gmail.com");
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        // when
        boolean isValid = jwtProvider.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("getAuthentication()")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "test@gmail.com";
        Claims claims = Jwts.claims();
        claims.put("username", userEmail);
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        // when
        Authentication authentication = jwtProvider.getAuthentication(token);
        // then
        assertThat((String) authentication.getPrincipal()).isEqualTo(userEmail);
    }

    @DisplayName("getUsername()")
    @Test
    void getUsername() {
        // given
        String userEmail = "test@gmail.com";
        Claims claims = Jwts.claims();
        claims.put("username", userEmail);
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        // when
        String username = jwtProvider.getUsername(token);
        // then
        assertThat(username).isEqualTo(userEmail);
    }
}