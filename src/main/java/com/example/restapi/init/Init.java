package com.example.restapi.init;

import com.example.restapi.Member.domain.Member;
import com.example.restapi.Product.domain.Product;
import com.example.restapi.Member.repository.MemberRepository;
import com.example.restapi.Product.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Init {

    private final InitService initService;

    @PostConstruct
    @Transactional
    public void init() {
        initService.init();
    }


    @Component
    @RequiredArgsConstructor
    @Transactional
    private static class InitService{
        private final ProductRepository productRepository;
        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {

            for (int i = 0; i < 30; i++) {
                Product product = Product.createProduct("상품번호" + (i + 1), 1000 + i + 1, 30);
                productRepository.save(product);
            }

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
    }

}
