package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.member.LoginResponse;
import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.exception.AppException;
import com.idog.FOPPY.exception.ErrorCode;
import com.idog.FOPPY.repository.MemberRepository;
import com.idog.FOPPY.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expire}")
    private Long expireTimeMs;
    public String saveMember(MemberDTO memberDTO) {

        //아이디 중복 체크
        memberRepository.findByUsername(memberDTO.getUsername())
                .ifPresent(member -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, ": Username is already exist");
                });

        //패스워드 암호화
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        memberRepository.save(memberDTO.toEntity());
        return "SUCCESS";
    }

    public LoginResponse login(MemberDTO memberDTO) {

        // username 없음
        Member member = memberRepository.findByUsername(memberDTO.getUsername())
                .orElseThrow(() -> {
                    throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ": Username is not found");
                });
        log.info(">> username: {}, password: {}", member.getUsername(), member.getPassword());

        // password 틀림
        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ": Password is incorrect");
        }

        String token = JwtUtil.createJwt(member.getUsername(), secretKey, expireTimeMs);

        return LoginResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .id(member.getUid())
                .username(member.getUsername())
                .build();
    }
}
