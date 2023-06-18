package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDTO {
    private  Long uid;
    private  String password;
    private List<Long> petIds;
    private String email;
    private String phoneNum;
    private String address;

    public MemberResponseDTO(Member entity){
        this.uid = entity.getUid();
        this.password = entity.getPassword();
        this.petIds = entity.getPetIds();
        this.email = entity.getEmail();
        this.phoneNum = entity.getPhoneNum();
        this.address = entity.getAddress();
    }
}
