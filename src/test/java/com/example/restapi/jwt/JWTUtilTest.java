package com.example.restapi.jwt;

import com.example.restapi.dto.MemberDTO;
import com.example.restapi.entity.Member;
import com.example.restapi.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class JWTUtilTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JWTUtil jwtUtil;

    @Test
    void 토큰생성테스트() throws Exception {
        //given(이런 데이터가 주어지면)
        Member findMember = memberRepository.findByMid("user2")
                                        .get();
        MemberDTO memberDTO = new MemberDTO(findMember);
        Map<String, Object> dataMap = memberDTO.getDataMap();
        String token = jwtUtil.createToken(dataMap, 60);
        System.out.println(token);

    }
}