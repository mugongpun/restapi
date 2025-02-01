package com.example.restapi.controller;

import com.example.restapi.dto.MemberRegisterDTO;
import com.example.restapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> register(@RequestBody @Validated MemberRegisterDTO memberRegisterDTO) {
        String registerMid = memberService.register(memberRegisterDTO);
        return ResponseEntity.ok(ApiResponse.success("당신의 아이디는 :" + registerMid + "입니다"));
    }

    //회원수정


}
