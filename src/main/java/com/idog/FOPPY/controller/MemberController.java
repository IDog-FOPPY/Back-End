package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.member.LoginResponse;
import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.dto.member.MemberRequestDTO;
import com.idog.FOPPY.dto.member.MemberResponseDTO;
import com.idog.FOPPY.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 API")
@RestController
@RequestMapping("/api/v1/member")
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberController {

     private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> saveMember(@RequestBody MemberDTO memberDTO) {
//        return memberService.saveMember(memberDTO);
        return ResponseEntity.ok(memberService.saveMember(memberDTO));
//        memberService.saveMember(memberDTO);
//        return "redirect:/";
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody MemberDTO memberDTO) {
        LoginResponse response = memberService.login(memberDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "유저의 반려견Id 리스트 조회")
    @GetMapping("/getPet/{uid}")
    public List<Long> getPetId(@PathVariable Long uid){
        return memberService.getPetId(uid);
    }


    @Operation(summary = "사용자 상세정보 조회")
    @GetMapping("getDetail/{uid}")
    public MemberResponseDTO findById(@PathVariable(name = "uid") final Long uid){
        return memberService.findById(uid);
    }


    @Operation(summary = "사용자 정보 수정")
    @PatchMapping("/update/{uid}")
    public Long update(@PathVariable(name = "uid") final Long uid,@RequestBody final MemberRequestDTO params) {
        return memberService.update(uid, params);
    }


    @Operation(summary = "사용자 정보 삭제")
    @DeleteMapping("/delete/{uid}")
    public void delete(@PathVariable(name = "uid") final Long uid){memberService.deleteById(uid);
    }
}
