package com.example.restapi.controller;

import com.example.restapi.dto.MemberRegisterDTO;
import com.example.restapi.dto.MemberUpdateDTO;
import com.example.restapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    //회원등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ResponseMessage>> register(@RequestBody @Validated MemberRegisterDTO memberRegisterDTO) {

        String registerMid = memberService.register(memberRegisterDTO);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.MEMBER_REGISTER_SUCCESS));
    }

    //회원수정
    @PutMapping("/{mid}")
    public ResponseEntity<ApiResponse<ResponseMessage>> update(@PathVariable("mid") String mid, @RequestBody MemberUpdateDTO memberUpdateDTO) {

    }

}
