package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
<<<<<<< HEAD
    public ResponseEntity<String> login(MemberDTO memberDTO) {
        String token = memberService.login(memberDTO);
        return ResponseEntity.ok(token);
=======
    public ResponseEntity<LoginResponse> login(@RequestBody MemberDTO memberDTO) {
        LoginResponse response = memberService.login(memberDTO);
        return ResponseEntity.ok(response);
>>>>>>> 3049d46 ([Fix] 요청을 json 형식으로 받도록)
    }
}
