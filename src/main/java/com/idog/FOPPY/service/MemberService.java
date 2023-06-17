package com.idog.FOPPY.service;

import com.idog.FOPPY.dto.member.MemberResponseDTO;
import com.idog.FOPPY.dto.pet.PetRequestDTO;
import com.idog.FOPPY.dto.pet.PetResponseDTO;
import com.idog.FOPPY.entity.Member;
import com.idog.FOPPY.dto.member.MemberRequestDTO;
import com.idog.FOPPY.entity.PetDogs;
import com.idog.FOPPY.exception.AppException;
import com.idog.FOPPY.exception.ErrorCode;
import com.idog.FOPPY.repository.MemberRepository;
import com.idog.FOPPY.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 회원가입
     */
    public String saveMember(MemberRequestDTO memberRequestDTO) {

        //아이디 중복 체크
        memberRepository.findByUsername(memberRequestDTO.getUsername())
                .ifPresent(member -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, ": Username is already exist");
                });

        //패스워드 암호화
        memberRequestDTO.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        memberRepository.save(memberRequestDTO.toEntity());
        return "SUCCESS";
    }

    /**
     * 로그인
     */
    public String login(MemberRequestDTO memberRequestDTO) {

        // username 없음
        Member member = memberRepository.findByUsername(memberRequestDTO.getUsername())
                .orElseThrow(() -> {
                    throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ": Username is not found");
                });
        log.info(">> username: {}, password: {}", member.getUsername(), member.getPassword());

        // password 틀림
        if (!passwordEncoder.matches(memberRequestDTO.getPassword(), member.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, ": Password is incorrect");
        }
        String token = JwtUtil.createJwt(member.getUsername(), secretKey, expireTimeMs);

        return token;
    }

    /**
     * 전체 사용자 정보 조회
     */
    public List<MemberResponseDTO> findAll() {
        List<Member> list = memberRepository.findAll();
        return list.stream().map(MemberResponseDTO::new).collect(Collectors.toList());
    }

    /**
     * ID로 사용자 상세정보 조회
     */
    @Transactional
    public MemberResponseDTO findById(final Long uid) {
        Member member = memberRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));
        return new MemberResponseDTO(member);
    }

    /**
     * 사용자 정보 수정
     */
    @Transactional
    public Long update(Long uid, MemberRequestDTO params) {
        Member member = memberRepository.findById(uid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));

        member.updateMember(params.getUsername(), params.getPassword(), params.getEmail(),
                params.getPhoneNum(), params.getAddress(), params.getProfileURL());

        return uid;
    }


    /**
     * 사용자의 반려견 리스트 추가
     */
    public void updatePetId(Long memID, Long petID) {

        Member member = memberRepository.findById(memID)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 정보입니다."));
        List<Long> petIDs = new ArrayList<>();
        petIDs.add(petID);
        member.setPetIDs(petIDs);
        memberRepository.save(member);
    }

    /**
     * 사용자 정보 삭제
     */
    @Transactional
    public void deleteById(Long uid){
        memberRepository.deleteById(uid);
    }


}
