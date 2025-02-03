package com.example.restapi.OrderProduct.domain;

import com.example.restapi.Order.domain.Order;
import com.example.restapi.Product.domain.Product;
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

    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.changeProduct(product);
        orderProduct.changeOrderPrice(orderPrice);
        orderProduct.changeCount(count);
        product.removeStock(count);
        return orderProduct;
    }

    public void changeOrder(Order order) {
        this.order = order;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }

    public void changeOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void changeCount(int count) {
        this.count = count;
    }

    public void cancel() {
        getProduct().addStock(this.count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
