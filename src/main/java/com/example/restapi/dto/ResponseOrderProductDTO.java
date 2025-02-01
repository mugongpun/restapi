package com.example.restapi.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderProductDTO {
    private Long productId;

    private String name;

    //주문수량
    private int quantity;
}
