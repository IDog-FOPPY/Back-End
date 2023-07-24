package com.idog.FOPPY.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserResponse {
    private Long id;
    private String email;
}
