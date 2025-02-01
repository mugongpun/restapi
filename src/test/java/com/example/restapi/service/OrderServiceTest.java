package com.example.restapi.service;

import com.example.restapi.dto.MemberRegisterDTO;
import com.example.restapi.dto.OrderDTO;
import com.example.restapi.dto.OrderProductDTO;
import com.example.restapi.dto.OrderRequestDTO;
import com.example.restapi.entity.Member;
import com.example.restapi.entity.Order;
import com.example.restapi.entity.OrderProduct;
import com.example.restapi.entity.Product;
import com.example.restapi.repository.MemberRepository;
import com.example.restapi.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    EntityManager em;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberService memberService;

    @Commit
    @Test
    void 오더_테스트() throws Exception {
        Member member = memberRepository.findByMid("user1")
                                        .get();

        Product product1 = Product.createProduct("product 1", 1000, 10);
        Product product2 = Product.createProduct("product 2", 2000, 40);
        em.persist(product1);
        em.persist(product2);

        OrderProduct orderProduct1 = OrderProduct.createOrderProduct(product1, 1000, 5);
        OrderProduct orderProduct2 = OrderProduct.createOrderProduct(product2, 1000, 3);

        Order order = Order.createOrder(member, orderProduct1, orderProduct2);
        em.persist(order);
    }

    @BeforeEach
    @Commit
    @Test
    void init() throws Exception {
        MemberRegisterDTO memberRegisterDTO = new MemberRegisterDTO("user1", "1111", "aaa@gmail.com", "test1");
        memberService.register(memberRegisterDTO);
        for (int i = 0; i < 30; i++) {
            Product product = Product.createProduct("상품번호" + (i + 1), 1000 + i + 1, 30);
            productRepository.save(product);
        }
    }

    @Test
    @Commit
    void 오더_서비스테스트() throws Exception {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        OrderProductDTO orderProductDTO = new OrderProductDTO(1L, 10);
        OrderProductDTO orderProductDTO1 = new OrderProductDTO(2L, 10);
        OrderProductDTO orderProductDTO2 = new OrderProductDTO(3L, 10);
        OrderProductDTO orderProductDTO3 = new OrderProductDTO(4L, 10);
        orderProductDTOList.add(orderProductDTO);
        orderProductDTOList.add(orderProductDTO1);
        orderProductDTOList.add(orderProductDTO2);
        orderProductDTOList.add(orderProductDTO3);
        orderRequestDTO.setMid("user1");
        orderRequestDTO.setOrderProductDTOList(orderProductDTOList);


        OrderDTO order = orderService.order(orderRequestDTO);
        System.out.println(order);

    }

}