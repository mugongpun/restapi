package com.example.restapi.Product.dto;

import com.example.restapi.Product.domain.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private String name;
    @Min(value = 1, message = "최소 1원 이상이여야 합니다")
    @Max(value = Integer.MAX_VALUE, message = "가격은 최대값을")
    private int price;
    @Min(value = 0,message = "최소 수량은 0개 입니다")
    @Max(value = 99999, message = "최대 수량은 99999개 입니다")
    private int stockQuantity;
    private List<String> fileNames;

    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.fileNames = product.getProductImages()
                                .stream()
                                .map(productImage -> productImage.getFileName())
                                .toList();
    }
}
