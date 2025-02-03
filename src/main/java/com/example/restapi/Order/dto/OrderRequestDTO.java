package com.example.restapi.Order.dto;

import com.example.restapi.OrderProduct.dto.OrderProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    //주문한 사람의 ID
    private String mid;

    private List<OrderProductDTO> orderProductDTOList;

}
