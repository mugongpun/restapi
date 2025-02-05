package com.example.restapi.Product.controller;

import com.example.restapi.Product.domain.Product;
import com.example.restapi.Product.dto.ProductDTO;
import com.example.restapi.Product.service.ProductService;
import com.example.restapi.global.ApiResponse;
import com.example.restapi.global.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping(value = "/add")
    public ResponseEntity<ApiResponse<Long>> save(
            @RequestPart("product") @Validated ProductDTO productDTO,
            @RequestPart(name = "images", required = false) MultipartFile[] images
    ) {
        //성공시 상품 id번호 반환
        Long savedId = productService.save(productDTO, images);
        return ResponseEntity.ok(ApiResponse.success(savedId));
    }

    //특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> findOne(@PathVariable("id") Long id) {
        ProductDTO findProduct = productService.findOne(id);
        return ResponseEntity.ok(ApiResponse.success(findProduct));
    }

    //상품리스트
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findAll() {
        List<ProductDTO> list = productService.findAll();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    //상품수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> changeProduct(
            @PathVariable("id") Long id,
            @RequestPart(name = "product") ProductDTO productDTO,
            @RequestPart(name = "images") MultipartFile[] images
    ) {
        //추후 개선사항 => 상품을 등록한 사람만이 수정할수있게끔 변경
        ProductDTO changeProduct = productService.changeProduct(id, productDTO, images);
        return ResponseEntity.ok(ApiResponse.success(changeProduct));
    }

    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> delete(@PathVariable("id") Long id) {
        //삭제하고 삭제된 상품 id 반환
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(ResponseMessage.PRODUCT_DELETE_SUCCESS, id));
    }
}
