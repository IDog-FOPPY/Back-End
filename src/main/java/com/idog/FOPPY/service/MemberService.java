package com.idog.FOPPY.service;

import com.idog.FOPPY.config.jwt.JwtProvider;
import com.idog.FOPPY.dto.member.MemberRequestDTO;
import com.idog.FOPPY.dto.member.MemberResponseDTO;
import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.dto.member.LoginResponse;
import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.exception.AppException;
import com.idog.FOPPY.exception.ErrorCode;
import com.idog.FOPPY.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private Long accessExpireTimeMs = 60 * 60 * 1000L;  // 1시간
    private Long refreshExpireTimeMs = 14 * 24 * 60 * 60 * 1000L;  // 14일

    public Long saveMember(MemberDTO memberDTO) {

        //아이디 중복 체크
        memberRepository.findByUsername(memberDTO.getUsername())
                .ifPresent(member -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, ": Username is already exist");
                });

        //패스워드 암호화
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        return memberRepository.save(memberDTO.toEntity()).getId();
    }

    public LoginResponse login(MemberDTO memberDTO) {

        // username 없음
        Member member = memberRepository.findByUsername(memberDTO.getUsername())
                .orElseThrow(() -> {
                    throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ": Username is not found");
                });
//        log.info(">> username: {}, password: {}", member.getUsername(), member.getPassword());

        // password 틀림
        if (!passwordEncoder.matches(memberDTO.getPassword(), member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ": Password is incorrect");
        }

        String access_token = jwtProvider.createAccessToken(member, accessExpireTimeMs);
        String refresh_token = jwtProvider.createRefreshToken(member, refreshExpireTimeMs);

        return LoginResponse.builder()
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .tokenType("Bearer")
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ": Username is not found"));
      
//     /**
//      * 견주의 반려견 리스트 조회
//      */
//     public List<Long> getPetId(Long uid) {
//         Member member = memberRepository.findById(uid)
//                                 .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));
//         return member.getPetIds();
//     }

//     /**
//      * ID로 반려견 상세정보 조회
//      */
//     @Transactional
//     public MemberResponseDTO findById(final Long uid) {
//         Member member = memberRepository.findById(uid)
//                 .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));
//         return new MemberResponseDTO(member);
//     }

//     /**
//      * 사용자 정보 수정
//      */
//     @Transactional
//     public Long update(Long uid, MemberRequestDTO params) {
//         Member member = memberRepository.findById(uid)
//                 .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));

//         member.update(params.getUsername(), params.getPassword(), params.getEmail(), params.getPhoneNum(), params.getPhoneNum());

//         return uid;
//     }


//     /**
//      * 사용자 정보 삭제
//      */
//     @Transactional
//     public void deleteById(Long uid){
//         memberRepository.deleteById(uid);
//     }
}

