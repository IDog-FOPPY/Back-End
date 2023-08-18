package com.idog.FOPPY.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginUserResponse {

    private Long userId;
    private String email;

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
