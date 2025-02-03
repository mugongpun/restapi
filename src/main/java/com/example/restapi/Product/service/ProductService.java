package com.example.restapi.Product.service;

import com.example.restapi.Product.dto.ProductDTO;
import com.example.restapi.Product.domain.Product;
import com.example.restapi.Product.repository.ProductRepository;
import com.example.restapi.global.exception.ProductTaskException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            throw ProductTaskException.Exceptions.DUPLICATE_PRODUCT.get();
        }
        Product savedProduct = productRepository.save(Product.createProduct(productDTO.getName(), productDTO.getPrice(), productDTO.getStockQuantity()));
        return savedProduct.getId();
    }

    //상품 재고 수정
    public ProductDTO changeProductStock(Long productId, int quantity) {
        Optional<Product> byId = productRepository.findById(productId);
        Product findProduct = byId.orElseThrow(
                () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get()
        );
        findProduct.changeStock(quantity);
        return new ProductDTO(findProduct);
    }

    //상품 수정
    public ProductDTO changeProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(
                                                   () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get()
                                           );

        product.changeProduct(productDTO.getPrice(), productDTO.getName(), productDTO.getStockQuantity());

        return new ProductDTO(product);
    }

    public void deleteProduct(Long productId) {
        Product findProduct = productRepository.findById(productId)
                                               .orElseThrow(
                                                       () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get());
        productRepository.delete(findProduct);
    }

    public void deleteProduct(String productName) {
        Product findProduct = productRepository.findByName(productName)
                                               .orElseThrow(
                                                       () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get()
                                               );
        productRepository.delete(findProduct);
    }

    public List<Product> findAll() {
        List<Product> productList = productRepository.findAll();
        return productList;
    }
}
