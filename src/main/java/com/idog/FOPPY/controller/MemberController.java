package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.MemberDTO;
import com.idog.FOPPY.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MemberController {

     private final MemberService memberService;

    @PostMapping("v1/member/save")
    public Long saveMember(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        return memberService.saveMember(memberDTO);
//        memberService.saveMember(memberDTO);
//        return "redirect:/";
    }
}
