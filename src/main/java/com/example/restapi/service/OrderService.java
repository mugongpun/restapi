package com.example.restapi.service;

import com.example.restapi.dto.OrderDTO;
import com.example.restapi.dto.OrderProductDTO;
import com.example.restapi.dto.OrderRequestDTO;
import com.example.restapi.entity.Member;
import com.example.restapi.entity.Order;
import com.example.restapi.entity.OrderProduct;
import com.example.restapi.entity.Product;
import com.example.restapi.exception.MemberTaskException;
import com.example.restapi.exception.OrderTaskException;
import com.example.restapi.exception.ProductTaskException;
import com.example.restapi.repository.MemberRepository;
import com.example.restapi.repository.OrderRepository;
import com.example.restapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    //주문
    public OrderDTO order(OrderRequestDTO orderRequestDTO) {
        List<OrderProductDTO> orderProductDTOList = orderRequestDTO.getOrderProductDTOList();
        if (orderProductDTOList.isEmpty()) {
            throw OrderTaskException.Exceptions.NOT_PRODUCT_LIST.createException();
        }
        /**
         * 1. 상품이 존재하는지 확인 (existsBy) -> 없으면 오류발생
         * 2. 상품이 존재한다면 createOrderProduct
         * 3. orderProduct를 리스트로 반환
         * 4. 반환한 리스트로 createOrder
         */
        List<OrderProduct> orderProductList = orderProductDTOList.stream()
                                                        .map(orderProductDTO -> {
                                                            Product findProduct = productRepository.findById(orderProductDTO.getProductId())
                                                                                                   .orElseThrow(() -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.createException());
                                                            OrderProduct orderProduct = OrderProduct.createOrderProduct(findProduct, findProduct.getPrice(), orderProductDTO.getQuantity());
                                                            return orderProduct;
                                                        })
                                                        .collect(Collectors.toList());

        Member findMember = memberRepository.findByMid(orderRequestDTO.getMid())
                                        .orElseThrow(() -> MemberTaskException.Exceptions.NOT_FOUND.createException());
        Order savedOrder = orderRepository.save(Order.createOrder(findMember, orderProductList));
            return new OrderDTO(savedOrder);
    }
    //주문수정
    public OrderDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        /**
         * 1. 주문을 찾아옴.
         * 2. 기존 주문을 취소 -> 업데이트된 주문 내역으로 다시 주문하는 로직
         */
        Order findOrder = orderRepository.findById(orderId)
                                     .orElseThrow(() -> OrderTaskException.Exceptions.NOT_FOUND_ORDER.createException());
        findOrder.cancel();

        return order(orderRequestDTO);
    }

    //주문취소
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId)
                                         .orElseThrow(() -> OrderTaskException.Exceptions.NOT_FOUND_ORDER.createException());

        findOrder.cancel();
    }

    public List<OrderDTO> orderList(String mid) {//조회로직은 N+1 문제 생각해서 쿼리짜기
        /**
         * 주문자 MID를 이용해 자기가 주문한 오더의 목록을 쭉 반환해주는 메서드
         */
        List<Order> orderList = orderRepository.findOrderByMemberMid(mid);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        if (orderList.size() == 0 || orderList.isEmpty()) {
            throw OrderTaskException.Exceptions.NOT_FOUND_ORDER_LIST.createException();
        }

        List<OrderDTO> list = orderList.stream()
                                       .map(order -> new OrderDTO(order))
                                       .toList();
        return list;

    }
}
