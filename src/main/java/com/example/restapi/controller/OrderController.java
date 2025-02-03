package com.example.restapi.controller;

import com.example.restapi.dto.OrderDTO;
import com.example.restapi.dto.OrderRequestDTO;
import com.example.restapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    //주문
    @PostMapping()
    public ResponseEntity<ApiResponse<OrderDTO>> order(@RequestBody @Validated OrderRequestDTO orderRequestDTO) {
//        if (principal.getName() != orderRequestDTO.getMid()) { 좀있다가 추가하기
//            throw MemberTaskException.Exceptions.USERNAME_MISMATCH.get();
//        }
        OrderDTO orderDTO = orderService.order(orderRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(orderDTO));
    }

    //주문수정
    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> changeOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderRequestDTO orderRequestDTO) {
        //        if (principal.getName() != orderRequestDTO.getMid()) { 좀있다가 추가하기
//            throw MemberTaskException.Exceptions.USERNAME_MISMATCH.get();
//        }
        OrderDTO updateOrderDto = orderService.updateOrder(orderId, orderRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(updateOrderDto));
    }

    //주문취소
    @PostMapping("/{orderId}")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success("주문 취소가 완료되었습니다"));
    }

    //자기가 주문한 목록 보기 (상품까지 같이 가져오는 로직 만들기)
    @GetMapping("/list/{mid}")
    public void list(@PathVariable("mid") String mid) {

        orderService.orderList(mid);
    }
}
