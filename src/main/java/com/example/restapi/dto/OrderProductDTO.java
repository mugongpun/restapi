package com.example.restapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO {
    //상품ID
    @NotNull(message = "상품 아이디가 없으면 안됩니다")
    private Long productId;

    //주문수량
    @Size(min = 1, message = "최소 수량은 1개 이상입니다")
    private int quantity;
}
