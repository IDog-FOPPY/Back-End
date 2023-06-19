package com.idog.FOPPY.config.jwt;

import com.idog.FOPPY.entity.Member;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final Logger LOGGER = Logger.getLogger(JwtProvider.class.getName());

    @Value("${spring.jwt.access-secret}")
    private String accessSecretKey;
    @Value("${spring.jwt.refresh-secret}")
    private String refreshSecretKey;

    // Access Token 생성
    public String createAccessToken(Member member, Long expireTimeMs) {
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims();
        claims.put("username", member.getUsername());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTimeMs);

        String token = Jwts.builder()
                // 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 페이로드
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                // 서명
                .signWith(SignatureAlgorithm.HS256, accessSecretKey)
                .compact();

        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;
    }

    // Access Token 생성
    public String createRefreshToken(Member member, Long expireTimeMs) {
        LOGGER.info("[createRefreshToken] 토큰 생성 시작");
        Claims claims = Jwts.claims();
        claims.put("username", member.getUsername());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTimeMs);

        String token = Jwts.builder()
                // 헤더
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                // 페이로드
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                // 서명
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();

        LOGGER.info("[createRefreshToken] 토큰 생성 완료");
        return token;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        LOGGER.info("[validateToken] 토큰 검증 시작");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(accessSecretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[validateToken] 토큰 검증 예외 발생");
            return false;
        }
    }

    // 토큰으로부터 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject(), "", authorities), token, authorities);
    }

    // 토큰으로부터 회원 id 추출
    public Long getMemberId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 토큰으로부터 회원 username 추출
     public String getMemberUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
     }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(accessSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
