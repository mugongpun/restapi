package com.example.restapi.Order.domain;

import com.example.restapi.Member.domain.Member;
import com.example.restapi.Order.OrderStatus;
import com.example.restapi.OrderProduct.domain.OrderProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>(); //양방향

    @CreatedDate
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public static Order createOrder(Member member, OrderProduct... orderProducts) {
        Order order = new Order();
        order.changeMember(member);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        order.changeStatus(OrderStatus.ORDER);
        return order;
    }

    public static Order createOrder(Member member, List<OrderProduct> orderProductList) {
        Order order = new Order();
        order.changeMember(member);
        for (OrderProduct orderProduct : orderProductList) {
            order.addOrderProduct(orderProduct);
        }
        order.changeStatus(OrderStatus.ORDER);
        return order;
    }

    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.changeOrder(this);
    }

    //주문취소
    public void cancel() {
        this.status = OrderStatus.CANCEL; //주문 상태를 취소로 변경
        for (OrderProduct orderProduct : orderProductList) {
            orderProduct.cancel();
        }
    }

    public int getTotalPrice() {
        return orderProductList.stream()
                               .mapToInt(orderProduct -> orderProduct.getTotalPrice())
                               .sum();
    }
}
