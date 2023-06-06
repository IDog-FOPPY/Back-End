package com.idog.FOPPY.dto;

import com.idog.FOPPY.entity.MemberEntity;
import lombok.*;

@Data
public class MemberDTO {

    private Long id;
    private String username;
    private String password;
    private String email;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .memberId(id)
                .memberUsername(username)
                .memberPassword(password)
                .memberEmail(email)
                .build();
    }
}
