package com.idog.FOPPY.controller;

import com.idog.FOPPY.domain.dto.MemberDTO;
import com.idog.FOPPY.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
//@AllArgsConstructor
@RequiredArgsConstructor
public class MemberController {

     private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> saveMember(MemberDTO memberDTO) {
//        return memberService.saveMember(memberDTO);
        return ResponseEntity.ok(memberService.saveMember(memberDTO));
//        memberService.saveMember(memberDTO);
//        return "redirect:/";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(MemberDTO memberDTO) {
        String token = memberService.login(memberDTO);
        return ResponseEntity.ok(token);
    }
}
