package com.idog.FOPPY.dto.member;

import lombok.*;

@Getter @Setter
@Builder
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private final Long id;
    private final String username;
}
