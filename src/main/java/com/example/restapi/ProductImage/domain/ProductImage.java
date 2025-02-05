package com.example.restapi.ProductImage.domain;

import com.example.restapi.Product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners({AuditingEntityListener.class})
public class ProductImage {

    @Id
    @GeneratedValue
    @Column(name = "product_image_id")
    private Long id;

    //인덱스 번호
    private int idx;

    //파일 이름
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @CreatedDate
    private LocalDateTime createDate;

    public void changeIdx(int idx) {
        this.idx = idx;
    }

    public void changeFileName(String fileName) {
        this.fileName = fileName;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }

    public static ProductImage createProductImage(String fileName) {
        ProductImage productImage = new ProductImage();
        productImage.changeFileName(fileName);
        return productImage;
    }
}
