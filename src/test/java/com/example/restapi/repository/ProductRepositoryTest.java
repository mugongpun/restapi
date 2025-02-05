package com.example.restapi.repository;

import com.example.restapi.Product.domain.Product;
import com.example.restapi.Product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void 상품_저장() throws Exception {
        //given(이런 데이터가 주어지면)
        Product product1 = Product.builder()
                                  .price(1000)
                                  .name("hi")
                                  .stockQuantity(10)
                                  .build();
        //when(이걸 실행했을때)
        productRepository.save(product1);
        //then(결과예측)
        assertThat(productRepository.findAll()
                                    .size())
                .isEqualTo(1);
    }
}