package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import lombok.*;

import java.util.List;

@Data
@Getter
public class MemberRequestDTO {

    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String address;


    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .phoneNum(phoneNum)
                .address(address)
                .build();
    }
}
