package com.idog.FOPPY.controller;

import com.idog.FOPPY.dto.member.LoginResponse;
import com.idog.FOPPY.dto.member.MemberDTO;
import com.idog.FOPPY.dto.member.MemberRequestDTO;
import com.idog.FOPPY.dto.member.MemberResponseDTO;
import com.idog.FOPPY.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    public ResponseEntity<Long> saveMember(@RequestBody MemberDTO memberDTO) {
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

    @Operation(summary = "로그아웃")  // FIXME: 동작 제대로 안 됨
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  // 현재 인증 정보
        if (auth == null) {
            return new ResponseEntity<>("No active session to logout from.", HttpStatus.UNAUTHORIZED);
        }
        new SecurityContextLogoutHandler().logout(request, response, auth);
        return ResponseEntity.ok("Logged out successfully");
      
    @Operation(summary = "유저의 반려견Id 리스트 조회")
    @GetMapping("/getPet/{id}")
    public List<Long> getPetId(@PathVariable Long id){
        return memberService.getPetId(id);
    }


    @Operation(summary = "사용자 상세정보 조회")
    @GetMapping("getDetail/{id}")
    public MemberResponseDTO findById(@PathVariable(name = "id") final Long id){
        return memberService.findById(id);
    }


    @Operation(summary = "사용자 정보 수정")
    @PatchMapping("/update/{id}")
    public Long update(@PathVariable(name = "id") final Long id,@RequestBody final MemberRequestDTO params) {
        return memberService.update(id, params);
    }


    @Operation(summary = "사용자 정보 삭제")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") final Long id) {memberService.deleteById(id);
    }
}
