package com.example.restapi.Member.service;

import com.example.restapi.Member.domain.Member;
import com.example.restapi.Member.dto.MemberDTO;
import com.example.restapi.Member.dto.MemberRegisterDTO;
import com.example.restapi.Member.repository.MemberRepository;
import com.example.restapi.global.exception.MemberTaskException;
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
        Member findMember = result.orElseThrow(() -> MemberTaskException.Exceptions.BAD_CREDENTIALS.get());
        if (!encoder.matches(pwd, findMember.getPwd())) {
            throw MemberTaskException.Exceptions.BAD_CREDENTIALS.get();
        }
        return new MemberDTO(findMember);
    }

    public MemberDTO getByMid(String mid) {
        Member findMember = memberRepository.findByMid(mid)
                                            .orElseThrow(() -> MemberTaskException.Exceptions.NOT_FOUND.get());


        return new MemberDTO(findMember);
    }

    public String register(MemberRegisterDTO memberRegisterDTO) {
        String mid = memberRegisterDTO.getMid();
        if (memberRepository.findByMid(mid)
                            .isPresent()) {
            throw MemberTaskException.Exceptions.DUPLICATE_VALUE.get();
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

    public MemberDTO update(MemberDTO memberUpdateDTO) {
        Member member = memberRepository.findByMid(memberUpdateDTO.getMid())
                                        .orElseThrow(
                                                () -> MemberTaskException.Exceptions.NOT_FOUND.get()
                                        );
        if (!encoder.matches(memberUpdateDTO.getPwd(), member.getPwd())) {
            throw MemberTaskException.Exceptions.BAD_CREDENTIALS.get();
        }
        member.changeEmail(memberUpdateDTO.getEmail());
        member.changePassword(encoder.encode(memberUpdateDTO.getPwd()));
        member.changeName(memberUpdateDTO.getName());
        return new MemberDTO(member);
    }

    public void delete(MemberDTO memberDeleteDTO) {
        Member member = memberRepository.findByMid(memberDeleteDTO.getMid())
                                        .orElseThrow(
                                                () -> MemberTaskException.Exceptions.NOT_FOUND.get()
                                        );
        if (!encoder.matches(memberDeleteDTO.getPwd(), member.getPwd())) {
            throw MemberTaskException.Exceptions.BAD_CREDENTIALS.get();
        }
        memberRepository.delete(member);
    }
}
