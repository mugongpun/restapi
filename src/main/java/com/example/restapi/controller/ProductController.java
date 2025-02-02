package com.example.restapi.controller;

import com.example.restapi.dto.ProductDTO;
import com.example.restapi.entity.Product;
import com.example.restapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품리스트
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findAll() {
        List<Product> productList = productService.findAll();
        List<ProductDTO> list = productList.stream()
                                           .map(product -> {
                                               return new ProductDTO(product);
                                           })
                                           .toList();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    //상품수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> changeProduct(@PathVariable("id") Long id, @RequestBody ProductDTO productDTO) {
        //추후 개선사항 => 상품을 등록한 사람만이 수정할수있게끔 변경
        ProductDTO changeProduct = productService.changeProduct(id,productDTO);
        return ResponseEntity.ok(ApiResponse.success(changeProduct));
    }
    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> delete(@PathVariable("id") Long id) {
        //삭제하고 삭제된 상품 id 반환
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.PRODUCT_DELETE_SUCCESS, id));
    }
    //상품 등록
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Long>> save(@RequestBody @Validated ProductDTO productDTO) {
        //성공시 상품 id번호 반환
        Long id = productService.save(productDTO);
        return ResponseEntity.ok(ApiResponse.success(id));
    }
}
