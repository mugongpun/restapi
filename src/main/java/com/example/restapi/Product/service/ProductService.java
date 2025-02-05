package com.example.restapi.Product.service;

import com.example.restapi.Product.domain.Product;
import com.example.restapi.Product.dto.ProductDTO;
import com.example.restapi.Product.repository.ProductRepository;
import com.example.restapi.ProductImage.UploadUtil;
import com.example.restapi.ProductImage.domain.ProductImage;
import com.example.restapi.global.exception.ProductTaskException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UploadUtil uploadUtil;

    //상품등록
    public Long save(ProductDTO productDTO, MultipartFile[] images) {
        //등록전 이미 있는 제품인지 확인
        if (productRepository.existsByName(productDTO.getName())) {
            throw ProductTaskException.Exceptions.DUPLICATE_PRODUCT.get();
        }

        //이미지를 업로드 경로에 저장 -> 저장된 파일의 이름을 productEntity fileName으로 정하고 생성
        //만들어진 엔티티를 상품 생성인자로 넣어줌
        //이미지가 없으면 없는대로 생성
        if (images == null || Arrays.stream(images)
                                    .allMatch(image -> image.isEmpty())) {
            //이미지 없으면 없는대로
            Product savedProduct = productRepository.save(Product.createProduct(productDTO.getName(), productDTO.getPrice(), productDTO.getStockQuantity()));
            return savedProduct.getId();
        }
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            //이미지가 있으면 파일 타입을 체크
            checkFileType(image.getOriginalFilename());
        }
        List<String> uploadNames = uploadUtil.upload(images);
        for (String uploadName : uploadNames) {
            productImages.add(ProductImage.createProductImage(uploadName));
        }
        Product savedProduct = productRepository.save(Product.createProduct(productDTO.getName(), productDTO.getPrice(), productDTO.getStockQuantity(), productImages));
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
    public ProductDTO changeProduct(Long id, ProductDTO productDTO, MultipartFile[] images) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(
                                                   () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get()
                                           );
        //이미지가 없으면 그 외의 것만 수정
        if (images == null || Arrays.stream(images)
                                    .allMatch(image -> image.isEmpty())) {
            product.changeProduct(productDTO.getPrice(), productDTO.getName(), productDTO.getStockQuantity());
            return new ProductDTO(product);
        }

        //이미지가 존재한다면, 기존에 있는 이미지를 전부 삭제하고 -> 다시 등록하는 방식
        //if문으로 감싼 이유는 혹시라도 파일이 존재하지 않을까봐(모종의 이유로)
        if (!product.getProductImages()
                    .isEmpty()) {
            for (ProductImage productImage : product.getProductImages()) {
                uploadUtil.deleteFile(productImage.getFileName());
            }
            //가진 이미지 리스트를 초기화
            product.getProductImages()
                   .clear();
        }

        //이미지 파일을 가져와서 타입 체크
        for (MultipartFile image : images) {
            checkFileType(image.getOriginalFilename());
        }

        //상품 수정
        List<ProductImage> productImageList = new ArrayList<>();
        List<String> uploadNames = uploadUtil.upload(images);
        for (String uploadName : uploadNames) {
            productImageList.add(ProductImage.createProductImage(uploadName));
        }
        product.changeProduct(productDTO.getPrice(), productDTO.getName(), productDTO.getStockQuantity(), productImageList);
        return new ProductDTO(product);
    }

    public void deleteProduct(Long productId) {
        Product findProduct = productRepository.findById(productId)
                                               .orElseThrow(
                                                       () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get());
        //상품 이미지 삭제 생각해야함
        findProduct.getProductImages()
                   .forEach(productImage -> uploadUtil.deleteFile(productImage.getFileName()));
        productRepository.delete(findProduct);
    }

    public ProductDTO findOne(Long productId) {
        Product findProduct = productRepository.findById(productId)
                                               .orElseThrow(
                                                       () -> ProductTaskException.Exceptions.NOT_EXIST_PRODUCT.get()
                                               );
        return new ProductDTO(findProduct);

    }


    public List<ProductDTO> findAll() {
        List<Product> allProduct = productRepository.findAllProduct();
        List<ProductDTO> list = allProduct
                .stream()
                .map(product -> new ProductDTO(product))
                .toList();

        return list;
    }


    private void checkFileType(String fileName) {
        //뒤에 확장자만 추출
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        //정규표현식으로 해당되는 확장자만 찾으려고
        String regExp = "^(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF|bmp|BMP)";

        if (!suffix.matches(regExp)) {
            throw ProductTaskException.Exceptions.UPLOAD_NOT_SUPPORT.get();
        }

    }
}
