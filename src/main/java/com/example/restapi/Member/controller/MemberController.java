package com.example.restapi.Member.controller;

import com.example.restapi.Member.dto.MemberDTO;
import com.example.restapi.Member.dto.MemberRegisterDTO;
import com.example.restapi.Member.service.MemberService;
import com.example.restapi.Member.dto.RMemberDTO;
import com.example.restapi.global.ApiResponse;
import com.example.restapi.global.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    /**
     * 추후에 전부 Authentication 혹은 Principal을 이용해 변경하는 방식으로 변경할예정
     * Pathvariable로 mid를 받는건 별로인거같음
     */

    private final MemberService memberService;

    //회원등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ResponseMessage>> register(@RequestBody @Validated MemberRegisterDTO memberRegisterDTO) {

        String registerMid = memberService.register(memberRegisterDTO);

        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.MEMBER_REGISTER_SUCCESS));
    }

    //회원수정
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<ResponseMessage>> update(@RequestBody MemberDTO memberUpdateDTO) {
        //mid,pwd,email,name 필요
        MemberDTO memberDTO = memberService.update(memberUpdateDTO);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.MEMBER_UPDATE_SUCCESS));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<ResponseMessage>> delete(@RequestBody MemberDTO memberDeleteDTO) {
        //mid, pwd만 필요
        memberService.delete(memberDeleteDTO);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.MEMBER_DELETE_SUCCESS));
    }

    //자기 정보 조회
    @GetMapping("/my-page")
    public ResponseEntity<ApiResponse<RMemberDTO>> read(@RequestBody MemberDTO memberDTO) {
        //mid랑 pwd만 필요
        MemberDTO read = memberService.read(memberDTO.getMid(), memberDTO.getPwd());
        //memberDTO에 너무 많은 정보가 있어서 mid,pwd,email만 가진 dto반환
        RMemberDTO rMemberDTO = new RMemberDTO(read);
        return ResponseEntity.ok(ApiResponse.success(rMemberDTO));
    }

}
