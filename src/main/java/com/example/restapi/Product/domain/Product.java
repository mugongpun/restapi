package com.example.restapi.Product.domain;

import com.example.restapi.global.exception.OrderTaskException;
import com.example.restapi.ProductImage.domain.ProductImage;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();


    public void addStock(int count) {
        this.stockQuantity += count;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw OrderTaskException.Exceptions.NOT_ENOUGH_STOCK_EXCEPTION.get();
        }
        this.stockQuantity = restStock;
    }

    public void changeStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void changeProduct(int price, String name, int stockQuantity) {
        if (price <= 0) {
            throw OrderTaskException.Exceptions.NEGATIVE_PRICE_EXCEPTION.get();
        }
        this.price = price;
        this.name = name;
        this.stockQuantity = stockQuantity;
    }


    public void changeProduct(int price, String name, int stockQuantity,List<ProductImage> productImages) {
        if (price <= 0) {
            throw OrderTaskException.Exceptions.NEGATIVE_PRICE_EXCEPTION.get();
        }
        this.price = price;
        this.name = name;
        this.stockQuantity = stockQuantity;
        for (ProductImage productImage : productImages) {
            this.addImages(productImage);
        }
    }

    //양방향 관계
    public void addImages(ProductImage productImage) {
        productImages.add(productImage);
        productImage.changeProduct(this);
        productImage.changeIdx(productImages.size());
    }

    public static Product createProduct(String name, int price, int stockQuantity, List<ProductImage> productImages) {
        Product product = new Product();
        product.name = name;
        product.stockQuantity = stockQuantity;
        product.price = price;
        for (ProductImage productImage : productImages) {
            product.addImages(productImage);
        }
        return product;
    }


    public static Product createProduct(String name, int price, int stockQuantity) {
        Product product = new Product();
        product.name = name;
        product.stockQuantity = stockQuantity;
        product.price = price;
        return product;
    }
}
