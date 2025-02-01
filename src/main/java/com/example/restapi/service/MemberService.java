package com.example.restapi.service;

import com.example.restapi.dto.MemberDTO;
import com.example.restapi.dto.MemberRegisterDTO;
import com.example.restapi.entity.Member;
import com.example.restapi.exception.MemberTaskException;
import com.example.restapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;

    public MemberDTO read(String mid, String pwd) {

        Optional<Member> result = memberRepository.findByMid(mid);
        Member findMember = result.orElseThrow(() -> MemberTaskException.Exceptions.BAD_CREDENTIALS.createException());
        if (!encoder.matches(pwd, findMember.getPwd())) {
            throw MemberTaskException.Exceptions.BAD_CREDENTIALS.createException();
        }
        return new MemberDTO(findMember);
    }

    public MemberDTO getByMid(String mid) {
        Member findMember = memberRepository.findByMid(mid)
                                            .orElseThrow(() -> MemberTaskException.Exceptions.NOT_FOUND.createException());


        return new MemberDTO(findMember);
    }

    public String register(MemberRegisterDTO memberRegisterDTO) {
        String mid = memberRegisterDTO.getMid();
        if (memberRepository.findByMid(mid)
                            .isPresent()) {
            throw MemberTaskException.Exceptions.DUPLICATE_VALUE.createException();
        }
        Member member = Member.builder()
                              .mid(memberRegisterDTO.getMid())
                              .pwd(encoder.encode(memberRegisterDTO.getPwd()))
                              .name(memberRegisterDTO.getName())
                              .email(memberRegisterDTO.getEmail())
                              .role("USER")
                              .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getMid();
    }
}
