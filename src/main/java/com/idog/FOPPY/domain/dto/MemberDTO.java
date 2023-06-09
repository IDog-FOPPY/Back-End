package com.idog.FOPPY.domain.dto;

import com.idog.FOPPY.domain.MemberEntity;
import lombok.*;

@Data
public class MemberDTO {

//    private Long uid;
    private String username;
    private String password;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
//                .uid(uid)
                .username(username)
                .password(password)
                .build();
    }
}
