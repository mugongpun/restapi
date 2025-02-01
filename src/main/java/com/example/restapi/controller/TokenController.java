package com.example.restapi.controller;

import com.example.restapi.dto.MemberDTO;
import com.example.restapi.dto.TokenResponseDTO;
import com.example.restapi.exception.TokenTaskException;
import com.example.restapi.jwt.JWTUtil;
import com.example.restapi.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Slf4j
public class TokenController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/make")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> makeToken(@RequestBody MemberDTO memberDTO) {
        MemberDTO findMemberDTO = memberService.read(memberDTO.getMid(), memberDTO.getPwd());
        Map<String, Object> dataMap = findMemberDTO.getDataMap();
        String accessToken = jwtUtil.createToken(dataMap, 60);
        String refreshToken = jwtUtil.createToken(Map.of("mid", memberDTO.getMid()), 60 * 24 * 7);
        return ResponseEntity.ok(ApiResponse.success(new TokenResponseDTO(accessToken, refreshToken)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> refreshToken(@RequestHeader("Authorization") String accessTokenStr,
                                                            @RequestHeader("Refresh-Token") String refreshToken,
                                                            @RequestParam("mid") String mid) {
        log.info("refresh-token = {}", refreshToken);

        if (accessTokenStr == null || !accessTokenStr.startsWith("Bearer ")) {
            throw TokenTaskException.Exceptions.NO_ACCESS_TOKEN.createException();
        }
        if (refreshToken == null) {
            throw TokenTaskException.Exceptions.NO_REFRESH_TOKEN.createException();
        }
        if (mid == null) {
            throw TokenTaskException.Exceptions.NO_MID.createException();
        }
        String accessToken = accessTokenStr.substring(7);
        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            //만약 만료되지 않았다면(expired 오류가 떠야 만료임) 다시 토큰 반환
            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(accessToken, refreshToken);
            return ResponseEntity.ok(ApiResponse.success(tokenResponseDTO));
        } catch (ExpiredJwtException accessExpired) {
            //만료돼서 refresh, access 둘다 생성
            return handleRefreshToken(mid, refreshToken);
        }
    }


    public ResponseEntity<ApiResponse<TokenResponseDTO>> handleRefreshToken(String mid, String refreshToken) {
        try {
            //refresh 토큰은 만료되지 않은경우
            Map<String, Object> midClaims = jwtUtil.validateToken(refreshToken);
            if (!mid.equals(midClaims.get("mid")
                                     .toString())) {
                throw TokenTaskException.Exceptions.INVALID_HOST.createException();
            }
            MemberDTO MemberDTO = memberService.getByMid(mid);
            Map<String, Object> claims = MemberDTO.getDataMap();
            String accessToken = jwtUtil.createToken(claims, 60);
            String newRefreshToken = jwtUtil.createToken(Map.of("mid", mid), 60 * 24 * 7);
            return ResponseEntity.ok(ApiResponse.success(new TokenResponseDTO(accessToken, newRefreshToken)));
        } catch (ExpiredJwtException e) {
            //refreshToken도 만료되었을때는
            throw TokenTaskException.Exceptions.TOKEN_EXPIRED_LOGIN_REQUIRED.createException();
        }
    }
}
