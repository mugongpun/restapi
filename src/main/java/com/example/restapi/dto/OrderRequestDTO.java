package com.example.restapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    //주문한 사람의 ID
    private String mid;

    private List<OrderProductDTO> orderProductDTOList;

}
