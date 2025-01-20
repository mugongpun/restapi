package com.example.restapi.controller;

import com.example.restapi.dto.MemberDTO;
import com.example.restapi.jwt.JWTUtil;
import com.example.restapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/make")
    public ResponseEntity<Map<String, String>> makeToken(@RequestBody MemberDTO memberDTO) {
        MemberDTO findMemberDTO = memberService.read(memberDTO.getMid(), memberDTO.getPwd());
        Map<String, Object> dataMap = findMemberDTO.getDataMap();
        String accessToken = jwtUtil.creatToken(dataMap, 10);
        String refreshToken = jwtUtil.creatToken(Map.of("mid", memberDTO.getMid()), 60 * 24 * 7);
        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));

    }
}
