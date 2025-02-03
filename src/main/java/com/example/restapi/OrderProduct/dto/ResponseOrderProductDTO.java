package com.example.restapi.OrderProduct.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderProductDTO {

    //주문 조회시 주문에 관련된 상품들을 DTO로 반환하기 위한 DTO

    private Long productId;

    private String name;

    //주문수량
    private int quantity;
}
