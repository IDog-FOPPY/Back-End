package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.MemberDTO;
import com.idog.FOPPY.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Long saveMember(MemberDTO memberDTO) {
        //패스워드 암호화
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        memberRepository.save(memberDTO.toEntity());
        return memberDTO.getId();
    }
}
