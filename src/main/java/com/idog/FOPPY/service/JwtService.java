package com.idog.FOPPY.service;

import com.idog.FOPPY.config.jwt.JwtProvider;
import com.idog.FOPPY.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        if(!jwtProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getId();
        Member member = memberService.findById(memberId);

//        return jwtProvider.createToken(member, Duration.ofHours(1));
        return jwtProvider.createAccessToken(member, 24 * 60 * 60 * 1000L);
    }
}
