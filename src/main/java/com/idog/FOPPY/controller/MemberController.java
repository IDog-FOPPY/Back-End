package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberController {

     private final MemberService memberService;

    @Operation(summary = "회원가입")
    @PostMapping("/join")
    public ResponseEntity<String> saveMember(MemberDTO memberDTO) {
//        return memberService.saveMember(memberDTO);
        return ResponseEntity.ok(memberService.saveMember(memberDTO));
//        memberService.saveMember(memberDTO);
//        return "redirect:/";
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<String> login(MemberDTO memberDTO) {
        String token = memberService.login(memberDTO);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "유저의 반려견Id 리스트 조회")
    @GetMapping("/getPet/{uid}")
    public List<Long> getPetId(@PathVariable Long uid){
        return memberService.getPetId(uid);
    }
}
