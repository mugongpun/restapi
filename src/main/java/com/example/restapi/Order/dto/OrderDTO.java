package com.example.restapi.Order.dto;

import com.example.restapi.Order.domain.Order;
import com.example.restapi.OrderProduct.dto.ResponseOrderProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {
    /**
     * 주문내역
     * 1. 주문번호
     * 2. 내가 주문한 상품들 리스트로 반환 (상품번호, 수량)
     * 3. 총 금액
     */
    private Long orderId;
    private List<ResponseOrderProductDTO> orderProductDTOList;
    private int totalPrice;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.orderProductDTOList = order.getOrderProductList()
                                             .stream() //아마 이부분에서 쿼리가 많이 발생될거같은데.. 실행되는 쿼리보고
                                             .map(orderProduct -> new ResponseOrderProductDTO(orderProduct.getProduct().getId(), orderProduct.getProduct().getName(),orderProduct.getCount()))
                                             .collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice();
    }
}
