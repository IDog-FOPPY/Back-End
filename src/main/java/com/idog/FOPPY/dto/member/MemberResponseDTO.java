package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDTO {
    private  Long uid;
    private  String password;
    private List<Long> petIds;

    public MemberResponseDTO(Member entity){
        this.uid = entity.getUid();
        this.password = entity.getPassword();
        this.petIds = entity.getPetIds();
    }
}
