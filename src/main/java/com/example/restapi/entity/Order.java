package com.example.restapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>(); //양방향

    private LocalDateTime orderDate;

    @Enumerated
    private OrderStatus status;

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

    public static Order createOrder(Member member, OrderProduct... orderProducts) {
        Order order = new Order();
        order.changeMember(member);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        order.changeStatus(OrderStatus.ORDER);
        return order;
    }

}
