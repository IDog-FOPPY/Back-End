package com.idog.FOPPY.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginUserResponse {
    private Long userId;
    private String token;
}
