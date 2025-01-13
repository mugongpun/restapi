package com.example.restapi.entity.repository;

import com.example.restapi.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Commit
    void testInsert() throws Exception {
        //given(이런 데이터가 주어지면)
        for (int i = 1; i <= 10; i++) {
            Member member = Member.builder()
                                 .userId("user" + i)
                                 .pwd(passwordEncoder.encode("1111"))
                                 .name("user" + i)
                                 .email("user" + i + "@aaa.com")
                                 .role(i < 5 ? "USER" : "ADMIN")
                                 .build();
            memberRepository.save(member);
        }
    }

}