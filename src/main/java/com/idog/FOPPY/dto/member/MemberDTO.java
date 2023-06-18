package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import lombok.*;

import java.util.List;

@Data
public class MemberDTO {

//    private Long uid;
    private String username;
    private String password;

    public Member toEntity() {
        return Member.builder()
//                .uid(uid)
                .username(username)
                .password(password)
                .build();
    }
}
