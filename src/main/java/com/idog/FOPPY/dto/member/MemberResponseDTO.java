package com.idog.FOPPY.dto.member;

import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.entity.PetDogs;
import jakarta.persistence.Column;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResponseDTO {
    private Long uid;
    private String username;
    private String password;
    private String email;
    private String phoneNum;
    private String address;
    private String profileURL;
    private List<Long> petIDs;

    public MemberResponseDTO(Member entity) {
        this.uid = entity.getUid();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.phoneNum = entity.getPhoneNum();
        this.address = entity.getAddress();
        this.profileURL = entity.getProfileURL();
        this.petIDs = entity.getPetIDs();
    }
}
