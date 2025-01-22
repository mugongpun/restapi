package com.example.restapi.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class OrderProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderPrice; //주문 당시 가격

    private int count; //주문수량

    public void changeOrder(Order order) {
        this.order = order;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }

    public void changeOrderPrice(int orderPrice) {
        this.orderPrice = this.orderPrice;
    }

    public void changeCount(int count) {
        this.count = count;
    }

    public void cancel(int count) {
        getProduct().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.changeProduct(product);
        orderProduct.changeOrderPrice(orderPrice);
        orderProduct.changeCount(count);
        product.removeStock(count);
        return orderProduct;
    }

}
