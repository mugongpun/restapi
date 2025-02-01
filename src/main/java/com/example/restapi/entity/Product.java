package com.example.restapi.entity;

import com.example.restapi.exception.OrderTaskException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "product_name", unique = true)
    private String name;
    private int price;
    private int stockQuantity;

    public void addStock(int count) {
        this.stockQuantity += count;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw OrderTaskException.Exceptions.NOT_ENOUGH_STOCK_EXCEPTION.createException();
        }
        this.stockQuantity = restStock;
    }

    public void changeStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void changeProduct(int price, String name, int stockQuantity) {
        if (price <= 0) {
            throw OrderTaskException.Exceptions.NEGATIVE_PRICE_EXCEPTION.createException();
        }
        this.price = price;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }

    public static Product createProduct(String name, int price, int stockQuantity) {
        Product product = new Product();
        product.name = name;
        product.stockQuantity = stockQuantity;
        product.price = price;
        return product;
    }
}
