//package com.idog.FOPPY.service.impl;
//
//import com.idog.FOPPY.config.jwt.JwtProvider;
//import com.idog.FOPPY.dto.member.JoinResponse;
//import com.idog.FOPPY.dto.member.LoginResponse;
//import com.idog.FOPPY.entity.Member;
//import com.idog.FOPPY.repository.MemberRepository;
//import com.idog.FOPPY.service.SignService;
//import lombok.AllArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//@AllArgsConstructor
//public class SignServiceImpl implements SignService {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);
//
//    public MemberRepository memberRepository;
//    public JwtProvider jwtProvider;
//    public PasswordEncoder passwordEncoder;
//
//    @Override
//    public JoinResponse join(String username, String password, String role) {
//        LOGGER.info("[JoinResponse] join() called");
//        Member member;
//        if (role.equalsIgnoreCase("admin")) {
//            member = Member.builder()
//                    .username(username)
//                    .password(passwordEncoder.encode(password))
//                    .role(Collections.singletonList("ROLE_ADMIN"))
//                    .build();
//        }
//    }
//
//    @Override
//    public LoginResponse login(String username, String password) throws RuntimeException {
//        return null;
//    }
//}
