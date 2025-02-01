package com.example.restapi.service;

import com.example.restapi.dto.ProductDTO;
import com.example.restapi.entity.Product;
import com.example.restapi.exception.ProductTaskException;
import com.example.restapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //상품등록
    public Long save(ProductDTO productDTO) {
        //등록전 이미 있는 제품인지 확인
        if (productRepository.existsByName(productDTO.getName())) {
            throw ProductTaskException.Exceptions.DUPLICATE_PRODUCT.createException();
        }
        Product savedProduct = productRepository.save(Product.createProduct(productDTO.getName(), productDTO.getPrice(), productDTO.getStockQuantity()));
        return savedProduct.getId();
    }

    //상품 재고 수정
    public ProductDTO changeProductStock(Long productId, int quantity) {
        Optional<Product> byId = productRepository.findById(productId);
        Product findProduct = byId.orElseThrow(() -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.createException());
        findProduct.changeStock(quantity);
        return new ProductDTO(findProduct);
    }

    //상품 수정
    public ProductDTO changeProduct(ProductDTO productDTO) {
        if (!productRepository.existsByName(productDTO.getName())) {
            throw ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.createException();
        }
        Product findProduct = productRepository.findByName(productDTO.getName())
                                           .get();
        return new ProductDTO(findProduct);
    }

    public void deleteProduct(Long productId) {
        Product findProduct = productRepository.findById(productId)
                                               .orElseThrow(() -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.createException());
        productRepository.delete(findProduct);
    }

    public void deleteProduct(String productName) {
        Product findProduct = productRepository.findByName(productName)
                                           .orElseThrow(() -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.createException());
        productRepository.delete(findProduct);
    }
}
