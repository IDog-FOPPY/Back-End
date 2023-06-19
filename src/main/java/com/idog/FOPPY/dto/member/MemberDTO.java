package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import lombok.*;

import java.util.List;

@Data
public class MemberDTO {

    private String username;
    private String password;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .build();
    }
}
