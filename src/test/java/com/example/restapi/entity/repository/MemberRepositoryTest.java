package com.example.restapi.entity.repository;

import com.example.restapi.entity.Member;
import com.example.restapi.exception.MemberTaskException;
import com.example.restapi.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    @Test
    @Commit
    void testInsert() throws Exception {
        //given(이런 데이터가 주어지면)
        for (int i =1; i <= 10; i++) {
                Member member = Member.builder()
                                      .mid("user" + i)
                                      .pwd(passwordEncoder.encode("1111"))
                                      .name("user" + i)
                                      .email("user" + i + "@aaa.com")
                                      .role("user")
                                      .build();
            memberRepository.save(member);
        }
    }

    @Test
    void testRead() throws Exception {
        //given(이런 데이터가 주어지면)
        String userId = "user1";
        //when(이걸 실행했을때)
        Optional<Member> memberEntity = memberRepository.findByMid(userId);
        //then(결과예측)
        System.out.println(memberEntity);

    }

    @Test
    @Transactional
    @Commit
    void testUpdate() throws Exception {
        //given(이런 데이터가 주어지면)
        String mid = "user1";
        //when(이걸 실행했을때)
        Optional<Member> result = memberRepository.findByMid(mid);
        Member member = result.orElseThrow(() -> MemberTaskException.Exceptions.NOT_FOUND.get());
        member.changeName("user1 수정");
        em.flush();
        em.clear();
        //then(결과예측)
        Optional<Member> result2 = memberRepository.findByMid(mid);
        Member changeMember = result2.get();
        Assertions.assertThat(changeMember.getName())
                  .isEqualTo("user1 수정");

    }

    @Test
    @Transactional
    @Commit
    void testDelete() throws Exception {
        //given(이런 데이터가 주어지면)
        String mid = "user10";
        //when(이걸 실행했을때)
        Optional<Member> byMid = memberRepository.findByMid(mid);
        Member findMember = byMid.orElseThrow(MemberTaskException.Exceptions.NOT_FOUND::get);
        memberRepository.delete(findMember);
        //then(결과예측)
    }

}