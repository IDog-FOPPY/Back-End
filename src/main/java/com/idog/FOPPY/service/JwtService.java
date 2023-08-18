package com.idog.FOPPY.service;

import com.idog.FOPPY.config.jwt.JwtProvider;
import com.idog.FOPPY.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if(!jwtProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);

//        return jwtProvider.createToken(member, Duration.ofHours(1));
        return jwtProvider.createAccessToken(user, 24 * 60 * 60 * 1000L); // 1시간
    }
}
