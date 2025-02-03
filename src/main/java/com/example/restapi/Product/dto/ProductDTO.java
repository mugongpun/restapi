package com.example.restapi.Product.dto;

import com.example.restapi.Product.domain.Product;
import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private int price;
    private int stockQuantity;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
    }
}
