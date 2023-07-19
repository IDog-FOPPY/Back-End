package com.idog.FOPPY.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {
    private String email;
    private String nickName;
    private String password;
    private String phone;
}
